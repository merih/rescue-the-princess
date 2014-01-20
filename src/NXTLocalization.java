import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.comm.*;
import java.io.*;

public class NXTLocalization {

	private static UltrasonicSensor sonic;
	
	private static int finalDistance = 0;
	private static int motionDistance = 0;
	
	public static void main(String[] args) throws Exception {
		float WHEEL_DIAMETER = 5.6f;
		float TRACK_WIDTH = 11.2f;
		
		DifferentialPilot robot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.C, true);
		robot.setTravelSpeed(20);
		
		sonic = new UltrasonicSensor(SensorPort.S1);
		
		String connected = "Connected";
        String waiting = "Waiting";
        
		LCD.drawString(waiting,0,0);
		LCD.refresh();

        BTConnection btc = Bluetooth.waitForConnection();
        
		LCD.clear();
		LCD.drawString(connected,0,0);
		LCD.refresh();	
		
		OutputStream os = btc.openOutputStream();
		
		DataOutputStream str = new DataOutputStream(os);
		
		float finalDistance = 0;
		
		while(finalDistance < 200) {
			motionDistance = (int) Math.floor(Math.random() * 20 + 10);
			
			robot.travel(motionDistance);
			finalDistance += motionDistance;
			
			showLocalizationData();
			sendLocalizationData(str);
		}
		
		//go backwards, remove this part if not neccessary
		while(finalDistance > 0) {
			motionDistance = -(int) Math.floor(Math.random() * 20 + 10);
			
			robot.travel(motionDistance);
			finalDistance += motionDistance;
			
			showLocalizationData();
			sendLocalizationData(str);
		}

		btc.close();
		LCD.clear();
	}
	
	private static void sendLocalizationData(DataOutputStream stream) throws Exception {
		stream.writeInt(motionDistance);
		stream.flush();

		stream.writeInt(sonic.getDistance());
		stream.flush();
	}
	
	private static void showLocalizationData() throws Exception {
		LCD.clear();
		LCD.drawString(sonic.getVersion(), 0, 0);
		LCD.drawString(sonic.getProductID(), 0, 1);
		LCD.drawString(sonic.getSensorType(), 0, 2);
		Thread.sleep(200);
		LCD.drawInt(sonic.getDistance(), 0, 3);
		LCD.drawInt((int)finalDistance, 0, 4);
		LCD.refresh();
		Thread.sleep(500);
	}
	
	
}