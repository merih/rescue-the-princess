import java.util.ArrayList;
import java.util.Hashtable;

import lejos.robotics.subsumption.Behavior;


public class BehaviorGoToMountainSkirts implements Behavior {
	Prince prince;
	Story story;
	Village village;
	
	public BehaviorGoToMountainSkirts() {
		prince = Prince.getPrince();
		story = Story.getStory();
		village = Village.getVillage();
	}
	
	private ArrayList<Coordinate> AStar(Coordinate start, Coordinate goal) {
		ArrayList<Coordinate> closedSet = new ArrayList<Coordinate>();
		ArrayList<Coordinate> openSet = new ArrayList<Coordinate>();
		openSet.add(start);
		Hashtable<Coordinate, Coordinate> cameFrom = new Hashtable<Coordinate, Coordinate>();
		Coordinate current;
		
		start.setG(0);
		start.setF(start.getG() + heuristic(start, goal));
		
		while(!openSet.isEmpty()) {
			current = getCoordinateWithLowestFInOpenSet(openSet);
			
			if(current.equals(goal)) {
				return reconstructPath(cameFrom, current);
			}
			
			openSet.remove(current);
			closedSet.add(current);
			for(Coordinate neighbor : current.neighbors()) {
				
				if(closedSet.contains(neighbor)) {
					continue;
				}
				
				int tentativeG = current.getG() + distance(current, neighbor);
				
				if(!openSet.contains(neighbor) || tentativeG < neighbor.getG()) {
					cameFrom.put(neighbor, current);
					
					neighbor.setG(tentativeG);
					neighbor.setF(tentativeG + heuristic(neighbor, goal));
					
					if(!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}
		
		return null;
	}
	
	private ArrayList<Coordinate> reconstructPath(Hashtable<Coordinate, Coordinate> cameFrom, Coordinate current) {
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();

		while(cameFrom.get(current) != null) {
			path.add(0, current);
			current = cameFrom.get(current);
		}
		
		return path;
	}
	
	private Coordinate getCoordinateWithLowestFInOpenSet(ArrayList<Coordinate> openSet) {
		int cost = Integer.MAX_VALUE;
		Coordinate coordinateWithLowestF = null;
		
		for(Coordinate c : openSet) {
			if(c.getF() < cost) {
				cost = c.getF();
				coordinateWithLowestF = c;
			}
		}
		
		return coordinateWithLowestF;
	}
	
	public int heuristic(Coordinate current, Coordinate goal) {
		return Math.abs(current.getX() - goal.getX()) + 
				Math.abs(current.getY() - goal.getY());
	}
	
	public int distance(Coordinate current, Coordinate neighbor) {
		int direction = Direction.toDirection(current, neighbor);
		
		if(village.isThereAWall(current.getX(), current.getY(), direction)) {
			return 100;
		} else {
			return 1;
		}
	}
	
	@Override
	public boolean takeControl() {
		return story.isTruthUnfold() &&
				!story.isAtTheSkirtsOfTheMountain();
	}

	@Override
	public void action() {
		ArrayList<Coordinate> path = AStar(prince.getCurrentLocation(), new Coordinate(3, 10));
		
		if(prince.followPath(path)) {
			story.reachedToTheSkirtsOfTheMountain();			
		}
	}

	@Override
	public void suppress() {
		prince.thinkAboutIt();
	}
}
