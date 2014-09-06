package main;

import com.neurosky.thinkgear.ThinkGear;

import matcher.PatternMatcher;
import view.View;

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
		}
	}
}
