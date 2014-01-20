import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorPreventFalling implements Behavior {
	
	TouchSensor ts;
	
	Prince prince;
	Story story;
	
	public BehaviorPreventFalling(TouchSensor ts) {
		this.ts = ts;
		
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isPrincessFound() &&
				!story.isReadyToClimbDown() &&
				ts.isPressed();
	}

	@Override
	public void action() {
		prince.backout();
		prince.turnRight();
		
		story.readyToClimbDown();
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
