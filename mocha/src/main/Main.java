package main;

import matcher.PatternMatcher;
import model.SensorValue;
import view.View;

import com.neurosky.thinkgear.ThinkGear;

public class Main {
	static {
		try {
	    	System.load("C:\\thinkgear.dll");
	    } catch (UnsatisfiedLinkError e) {
	      System.err.println("Native code library failed to load.\n" + e);
	      System.exit(1);
	    }
	}
	public static void main(String[] args) {
		// Insert global logic here
		View view = new View();
		PatternMatcher patternMatcher = new PatternMatcher();
		
		/*
		 * Open the connection
		 * 
		 * Get Connection ID
		 * Connect
		 */
		int connectionID = ThinkGear.GetNewConnectionId();
		if (connectionID == -1) {
			System.out.println("There are too many connection ids");
			System.exit(1);
		}
		
		String serialPort = "\\\\.\\COM5";
		int result = ThinkGear.Connect(connectionID, serialPort, ThinkGear.BAUD_9600, ThinkGear.STREAM_PACKETS);
		if (result == -1) {
			System.out.println("Invalid connectionID");
			System.exit(1);
		}
		
		/*
		 * Do magic calibration stuff
		 */
		
		while (true) {
			/*
			 * Call ReadPacket to get a new packet
			 * Create a SensorValue to hold the information
			 * Fill the SensorValue with the data
			 * Pass the SensorValue to the view and the patternMatcher
			 */
			ThinkGear.ReadPackets(connectionID, 1);
			SensorValue sensorValue = new SensorValue();
			
			sensorValue.alpha1 = ThinkGear.GetValue(connectionID, ThinkGear.DATA_ALPHA1);
			sensorValue.alpha2 = ThinkGear.GetValue(connectionID, ThinkGear.DATA_ALPHA2);
			sensorValue.attention = ThinkGear.GetValue(connectionID, ThinkGear.DATA_ATTENTION);
			sensorValue.battery = ThinkGear.GetValue(connectionID, ThinkGear.DATA_BATTERY);
			sensorValue.beta1 = ThinkGear.GetValue(connectionID, ThinkGear.DATA_BETA1);
			sensorValue.beta2 = ThinkGear.GetValue(connectionID, ThinkGear.DATA_BETA2);
			sensorValue.delta = ThinkGear.GetValue(connectionID, ThinkGear.DATA_DELTA);
			sensorValue.gamma1 = ThinkGear.GetValue(connectionID, ThinkGear.DATA_GAMMA1);
			sensorValue.gamma2 = ThinkGear.GetValue(connectionID, ThinkGear.DATA_GAMMA2);
			sensorValue.meditation = ThinkGear.GetValue(connectionID, ThinkGear.DATA_MEDITATION);
			sensorValue.poorSignal = ThinkGear.GetValue(connectionID, ThinkGear.DATA_POOR_SIGNAL);
			sensorValue.raw = ThinkGear.GetValue(connectionID, ThinkGear.DATA_RAW);
			sensorValue.theta = ThinkGear.GetValue(connectionID, ThinkGear.DATA_THETA);
			
			// Update the view
			view.update(sensorValue);
			
			// Update the pattern matcher
		}
	}
}
