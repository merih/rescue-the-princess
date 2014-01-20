import java.util.ArrayList;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class Prince extends DifferentialPilot {

	private static final int BACKOUT_DISTANCE = -3;
	private static final int STAMINA = 30;
	private static final float SHOE_SIZE = 5.6f;
	private static final float STEP_LENGTH = 11.5f;
	
	private static Prince prince = null;
	private Coordinate startLocation;
	private Coordinate currentLocation;
	private int currentDirection;
	private boolean thinking;
	
	private int maxX = 0;
	private int maxY = 0;
	
	private Prince() {
		super(SHOE_SIZE, STEP_LENGTH, Motor.C, Motor.A, true);
		
		startLocation = new Coordinate();
		currentLocation = new Coordinate(3, 2);
		currentDirection = Direction.NORTH;
		thinking = false;
	}
	
	public static Prince getPrince() {
		if(prince == null) {
			prince =  new Prince();
			prince.setTravelSpeed(10);
			prince.setRotateSpeed(20);
		}
		
		return prince;
	}
	
	public Coordinate getStartLocation() {
		return startLocation;
	}
	
	public void setStartLocation(Coordinate location) {
		startLocation = location;
	}
	
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	public void setCurrentDirection(int direction) {
		currentDirection = direction;
	}
	
	public int getX() {
		return currentLocation.getX();
	}
	
	public int getY() {
		return currentLocation.getY();
	}
	
	public int getMaxX() {
		return maxX;
	}
	
	public int getMaxY() {
		return maxY;
	}
	
	private void swapMaxXY() {
		int tmp = maxX;
		maxX = maxY;
		maxY = tmp;
	}
	
	public Coordinate getCurrentLocation() {
		return currentLocation;
	}
	
	public void setCurrentLocation(Coordinate c) {
		currentLocation = c;
	}
	
	public void resetCurrentLocation() {
		currentLocation.setX(0);
		currentLocation.setY(0);
	}
	
	public void rotateMapRight() {
		int x = getX();
		int y = getY();
		currentLocation.setX(y);
		currentLocation.setY(maxX - x);
		swapMaxXY();
		
		currentDirection = Direction.turnRight(currentDirection);
	}
	
	public void rotateMapLeft() {
		int x = getX();
		int y = getY();
		currentLocation.setX(maxY - y);
		currentLocation.setY(x);
		swapMaxXY();
		
		currentDirection = Direction.turnLeft(currentDirection);
	}
	
	public void panMapRight() {
		currentLocation.setX(0);
		maxX++;
	}
	
	public void turnRight() {
		rotate(90);
		currentDirection = Direction.turnRight(currentDirection);
	}
	
	public void turnLeft() {
		rotate(-90);
		currentDirection = Direction.turnLeft(currentDirection);
	}
	
	public void turnBack() {
		rotate(180);
		currentDirection = Direction.turnBack(currentDirection);
	}
	
	public void walk() {
		travel(STAMINA);
		rotate(-1);
		currentLocation.goFront(currentDirection);
		
		if(getX() > maxX) {
			maxX = getX();
		}
		
		if(getY() > maxY) {
			maxY = getY();
		}
	}
	
	public void walkSilently() {
		travel(STAMINA);
	}
	
	public void run() {
		forward();
	}
	
	public boolean followPath(ArrayList<Coordinate> path) {
		thinking = false;
		
		for(Coordinate destination : path) {
			if(thinking) {
				return false;
			}
			
			int direction = Direction.toDirection(currentLocation, destination);
			
			if(Direction.turnLeft(currentDirection) == direction) {
				prince.turnLeft();
			} else if(Direction.turnRight(currentDirection) == direction) {
				prince.turnRight();
			} else if(Direction.turnBack(currentDirection) == direction) {
				prince.turnBack();
			} // else directions are the same
			
			prince.walk();
		}
		
		if(currentDirection == Direction.NORTH) {
			prince.turnRight();
		}
		
		return true;
	}
	
	public boolean findKing(ArrayList<Coordinate> path) {
		thinking = false;
		
		for(Coordinate destination : path) {
			if(thinking) {
				return false;
			}
			
			int direction = Direction.toDirection(currentLocation, destination);
			
			if(Direction.turnLeft(currentDirection) == direction) {
				prince.turnLeft();
			} else if(Direction.turnRight(currentDirection) == direction) {
				prince.turnRight();
			} else if(Direction.turnBack(currentDirection) == direction) {
				prince.turnBack();
			} // else directions are the same
			
			prince.walk();
		}
		
		return true;
	}
	
	public void backout() {
		travel(BACKOUT_DISTANCE);
	}
	
	public void climbDown() {
		travel(-STAMINA);
	}
	
	public void thinkAboutIt() {
		stop();
		thinking = true;
	}
}
