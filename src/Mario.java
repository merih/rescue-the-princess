import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Mario {

	public static void main(String[] args) {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		TouchSensor ts = new TouchSensor(SensorPort.S2);
		LightSensor ls = new LightSensor(SensorPort.S4);		
		
		// explore
		Behavior bTravel = new BehaviorTravel(ls);
		Behavior bDetectWall = new BehaviorDetectWall(ts, us);
		
		// reach to mountain
		Behavior bGoToMountainSkirts = new BehaviorGoToMountainSkirts();
		Behavior bDetectWallOnPath = new BehaviorDetectWallOnPath(ts);
		
		// climb
		Behavior bClimb = new BehaviorClimb();
		Behavior bStopClimbing = new BehaviorStopClimbing(us);
		
		// find & rescue
		Behavior bFindPrincess = new BehaviorFindPrincess(us);
		Behavior bRescuePrincess = new BehaviorRescuePrincess(ls);
		Behavior bDetectWallInDungeon = new BehaviorDetectWallInDungeon(ts, us);
		
		// climb down
		Behavior bGoToMountainTop = new BehaviorGoToMountainTop();
		Behavior bPreventFalling = new BehaviorPreventFalling(ts);
		Behavior bClimbDown = new BehaviorClimbDown(us);
		
		// return to the king
		Behavior bReturnOfTheKing = new BehaviorReturnOfTheKing();
		Behavior bDetectWallOnKingsRoad = new BehaviorDetectWallOnKingsRoad(ts);
		
		Behavior [] behaviors = {
				bTravel,
				bDetectWall,
				
				bGoToMountainSkirts,
				bDetectWallOnPath,
				
				bClimb,
				bStopClimbing,
				
				bFindPrincess,
				bRescuePrincess,
				bDetectWallInDungeon,
				
				bGoToMountainTop,
				bPreventFalling,
				bClimbDown,
				
				bReturnOfTheKing,
				bDetectWallOnKingsRoad
		};
		
		Arbitrator arby = new Arbitrator(behaviors);
		Button.ENTER.waitForPressAndRelease();
		arby.start();
	}

}
