import lejos.robotics.subsumption.Behavior;


public class BehaviorGoToMountainPeak implements Behavior {
		
	Prince prince;
	Story story;
	
	public BehaviorGoToMountainPeak() {
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isPrincessFound();
	}

	@Override
	public void action() {
		prince.run();
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
