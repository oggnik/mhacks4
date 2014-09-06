package main;

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
