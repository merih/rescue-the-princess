import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorClimbDown implements Behavior {
		
	private static final int WALL_DISTANCE = 30;
	
	UltrasonicSensor us;
	
	Prince prince;
	Story story;
	
	public BehaviorClimbDown(UltrasonicSensor us) {
		this.us = us;
		
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isReadyToClimbDown() &&
				!story.isClimbedDown();
	}

	@Override
	public void action() {
		prince.climbDown();
		prince.climbDown();
		
		while(us.getDistance() < WALL_DISTANCE) {
			prince.climbDown();
		}
		
		story.climbedDown();
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
