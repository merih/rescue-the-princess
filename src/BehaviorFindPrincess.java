import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorFindPrincess implements Behavior {
	
	private final int WALL_DISTANCE = 20;
	UltrasonicSensor us;
	private int doorCount;
	
	Prince prince;
	Story story;
	
	public BehaviorFindPrincess(UltrasonicSensor us) {
		this.us = us;
		doorCount = 0;
		
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isAtTheTopOfTheMountain() &&
				!story.isPrincessDoorFound();
	}

	@Override
	public void action() {
		while(true) {
			prince.walk();
			
			if(us.getDistance() > WALL_DISTANCE) {
				doorCount++;
				
				if(doorCount == story.princessDoorNumber()) {
					story.knockKnock();
					prince.turnRight();
					prince.resetCurrentLocation();
					return;
				}
			}
		}
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
