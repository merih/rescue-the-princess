import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorDetectWall implements Behavior {

	private final int WALL_DISTANCE = 20;
	
	TouchSensor ts;
	UltrasonicSensor us;
	
	Prince prince;
	Village village;
	Story story;
		
	public BehaviorDetectWall(TouchSensor ts, UltrasonicSensor us) {
		this.ts = ts;
		this.us = us;
		
		prince = Prince.getPrince();
		village = Village.getVillage();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return !(story.isTruthUnfold()) && ts.isPressed();
	}

	@Override
	public void action() {
		prince.backout();
		village.thereIsAWallHere();
		
		if(us.getDistance() < WALL_DISTANCE) {
			prince.turnLeft();
		} else {
			prince.turnRight();
		}
	}

	@Override
	public void suppress() {
		prince.stop();
	}

}
