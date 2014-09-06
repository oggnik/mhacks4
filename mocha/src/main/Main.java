package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import matcher.PatternMatcher;
import model.SensorValue;
import view.View;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		// Insert global logic here
		View view = new View();
		PatternMatcher patternMatcher = new PatternMatcher();
		
		
		File alpha1File = new File("alpha1.txt");
		Scanner alpha1In = new Scanner(alpha1File);
		File alpha2File = new File("alpha2.txt");
		Scanner alpha2In = new Scanner(alpha2File);
		File beta1File = new File("beta1.txt");
		Scanner beta1In = new Scanner(beta1File);
		File beta2File = new File("beta2.txt");
		Scanner beta2In = new Scanner(beta2File);
		File deltaFile = new File("delta.txt");
		Scanner deltaIn = new Scanner(deltaFile);
		File gamma1File = new File("gamma1.txt");
		Scanner gamma1In = new Scanner(gamma1File);
		File gamma2File = new File("gamma2.txt");
		Scanner gamma2In = new Scanner(gamma2File);
		File thetaFile = new File("theta.txt");
		Scanner thetaIn = new Scanner(thetaFile);
		
		
		while (true) {
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
				SensorValue sensorValue = new SensorValue();
				
				sensorValue.alpha1 = alpha1In.nextDouble();
				sensorValue.alpha2 = alpha2In.nextDouble();
				sensorValue.beta1 = beta1In.nextDouble();
				sensorValue.beta2 = beta2In.nextDouble();
				sensorValue.delta = deltaIn.nextDouble();
				sensorValue.gamma1 = gamma1In.nextDouble();
				sensorValue.gamma2 = gamma2In.nextDouble();
				sensorValue.theta = thetaIn.nextDouble();
				
//				sensorValue.attention = alpha1In.nextDouble();
//				sensorValue.battery = alpha1In.nextDouble();
//				sensorValue.meditation = alpha1In.nextDouble();
//				sensorValue.poorSignal = alpha1In.nextDouble();
//				sensorValue.raw = alpha1In.nextDouble();
				
				// Update the view
				view.update(sensorValue);
				if (view.getCalibrate()) {
					break;
				}
				
				// Update the pattern matcher if it should be running
				if (view.getRunning()) {
					patternMatcher.update(sensorValue);
				}
			}
		}
	}
}
