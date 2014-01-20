import lejos.nxt.LightSensor;
import lejos.robotics.subsumption.Behavior;


public class BehaviorRescuePrincess implements Behavior {
		
	LightSensor ls;
	
	Prince prince;
	Story story;
	Village village;
	
	public BehaviorRescuePrincess(LightSensor ls) {
		this.ls = ls;
		
		prince = Prince.getPrince();
		story = Story.getStory();
		village = Village.getVillage();
	}
	
	@Override
	public boolean takeControl() {
		return story.isPrincessDoorFound();
	}

	@Override
	public void action() {
		prince.walkSilently();
		
		if(village.searchPrincess(ls.getLightValue())) {
			prince.rotate(360);
			prince.rotate(-360);
			prince.followPath(village.getReturnPath());
			prince.turnRight();
			story.happyEnding();
		}
	}

	@Override
	public void suppress() {
		prince.stop();
	}
}
