import lejos.robotics.subsumption.Behavior;


public class BehaviorClimb implements Behavior {
		
	Prince prince;
	Story story;
	
	public BehaviorClimb() {
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isAtTheSkirtsOfTheMountain();
	}

	@Override
	public void action() {
		prince.walkSilently();
		prince.turnRight();
		prince.run();
		story.startedToClimb();
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
