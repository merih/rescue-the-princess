import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorDetectWallOnKingsRoad implements Behavior {

	TouchSensor ts;
	
	Prince prince;
	Village village;
	Story story;
		
	public BehaviorDetectWallOnKingsRoad(TouchSensor ts) {
		this.ts = ts;
		
		prince = Prince.getPrince();
		village = Village.getVillage();
		story = Story.getStory();
	}
	
	@Override
	public boolean takeControl() {
		return story.isClimbedDown() &&
				ts.isPressed();
	}

	@Override
	public void action() {
		village.thereIsAWallHere();
		prince.backout();
	}

	@Override
	public void suppress() {
		prince.stop();
	}

}
