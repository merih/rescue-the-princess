import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorStopClimbing implements Behavior {
	
	private final int HORIZON_DISTANCE = 60;
	UltrasonicSensor us;
	
	Prince prince;
	Story story;
	
	public BehaviorStopClimbing(UltrasonicSensor us) {
		this.us = us;
		
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isClimbing() &&
				!story.isAtTheTopOfTheMountain() &&
				us.getDistance() > HORIZON_DISTANCE;
	}

	@Override
	public void action() {
		prince.walkSilently();
		prince.backout();
		prince.turnRight();
		
		story.reachedToTheTopOfTheMountain();
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
