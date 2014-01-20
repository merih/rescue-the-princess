import lejos.nxt.LightSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorTravel implements Behavior {
	
	LightSensor ls;
	
	Prince prince;
	Story story;
	Village village;
	private boolean visited = false;
	private boolean suppressed = false;
	
	public BehaviorTravel(LightSensor ls) {
		this.ls = ls;
		
		prince = Prince.getPrince();
		story = Story.getStory();
		village = Village.getVillage();
	}
	
	@Override
	public boolean takeControl() {
		return !(story.isTruthUnfold());
	}

	@Override
	public void action() {
		if(visited) {
			visited = false;
			prince.turnLeft();
		}
		
		if(prince.getX() == 3 && prince.getY() == 10 &&
				prince.getCurrentDirection() == Direction.EAST) {
			prince.turnRight();
		}
		
		prince.walk();
		
		if(!suppressed) {
			suppressed = false;
			visited = village.talkWithVillager(ls.getLightValue());
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
		prince.stop();
	}
}
