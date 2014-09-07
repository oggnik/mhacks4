package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import matcher.PatternMatcher;
import model.Pattern;
import model.SensorValue;
import sphero.SpheroManager;
import view.View;
import calibrator.Calibrator;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		// Insert global logic here
		View view = new View();
		PatternMatcher patternMatcher = new PatternMatcher();
//		SpheroManager spheroManager = new SpheroManager();
//		Calibrator calibrator = new Calibrator(view, patternMatcher);
		
		boolean startCalibration = false;
		while (true) {
			/*
			 * Do magic calibration stuff?
			 */
			
			boolean wait = true;
			while (wait) {
				try {
					File stageFile = new File("../new/app/app/state.txt");
					Scanner input = new Scanner(stageFile);
					if (input.hasNext()) {
						String state = input.next();
						if (state.equals("0")) {
							wait = false;
						}
					}
					input.close();
				} catch (Exception e) {
					System.err.println(e);
					e.printStackTrace();
				}
			}
			//System.out.println("Reading");
			
			File alpha1File = new File("../new/app/app/alpha1.txt");
			Scanner alpha1In = new Scanner(alpha1File);
			File alpha2File = new File("../new/app/app/alpha2.txt");
			Scanner alpha2In = new Scanner(alpha2File);
			File beta1File = new File("../new/app/app/beta1.txt");
			Scanner beta1In = new Scanner(beta1File);
			File beta2File = new File("../new/app/app/beta2.txt");
			Scanner beta2In = new Scanner(beta2File);
			File deltaFile = new File("../new/app/app/delta.txt");
			Scanner deltaIn = new Scanner(deltaFile);
			File gamma1File = new File("../new/app/app/gamma1.txt");
			Scanner gamma1In = new Scanner(gamma1File);
			File gamma2File = new File("../new/app/app/gamma2.txt");
			Scanner gamma2In = new Scanner(gamma2File);
			File thetaFile = new File("../new/app/app/theta.txt");
			Scanner thetaIn = new Scanner(thetaFile);
			
			File attentionFile = new File("../new/app/app/attention.txt");
			Scanner attentionIn = new Scanner(attentionFile);
			
			while (alpha1In.hasNextDouble()) {
				/*
				 * Call ReadPacket to get a new packet
				 * Create a SensorValue to hold the information
				 * Fill the SensorValue with the data
				 * Pass the SensorValue to the view and the patternMatcher
				 */
				SensorValue sensorValue = new SensorValue();
				
				sensorValue.alpha1 = alpha1In.nextDouble();
				//patternMatcher.average.alpha1 = sensorValue.alpha1;
				sensorValue.alpha2 = alpha2In.nextDouble();
				//patternMatcher.average.alpha2 = sensorValue.alpha2;
				sensorValue.beta1 = beta1In.nextDouble();
				sensorValue.beta2 = beta2In.nextDouble();
				sensorValue.delta = deltaIn.nextDouble();
				sensorValue.gamma1 = gamma1In.nextDouble();
				sensorValue.gamma2 = gamma2In.nextDouble();
				sensorValue.theta = thetaIn.nextDouble();
				
				sensorValue.attention = attentionIn.nextDouble();
				
//				sensorValue.battery = alpha1In.nextDouble();
//				sensorValue.meditation = alpha1In.nextDouble();
//				sensorValue.poorSignal = alpha1In.nextDouble();
//				sensorValue.raw = alpha1In.nextDouble();
				
				// Update the view
				view.update(sensorValue);
				
//				if (startCalibration && view.getCalibrate()) {
//					calibrator.calibrate(sensorValue);
//				}
				
				// Update the pattern matcher if it should be running
				if (!view.getCalibrate() && view.getRunning()) {
					patternMatcher.update(sensorValue);
				}
			}
			
			// Determine the move to make
//			if (!view.getCalibrate() && view.getRunning()) {
//				determineMove(patternMatcher, spheroManager);
//			}
			
			
			//System.out.println("Done reading");
			try {
				File stageFile = new File("../new/app/app/state.txt");
				PrintWriter stageOut = new PrintWriter(stageFile);
				stageOut.print('1');
				stageOut.close();
				//System.out.println("Wrote a 1");
			} catch (Exception e) {
				System.err.println("Error writing a 1: " + e);
				e.printStackTrace();
			}
			startCalibration = true;
		}
	}
	
	public static void determineMove(PatternMatcher patternMatcher, SpheroManager spheroManager) {
		Pattern match = patternMatcher.findMatch();
		if (match == null) {
			System.out.println("No match");
			return;
		}
		System.out.println(match.color);
		if (match.color.equals("yellow")) {
			spheroManager.turnLeft();
		} else if (match.color.equals("green")) {
			spheroManager.moveForward();
		} else if (match.color.equals("blue")) {
			spheroManager.turnRight();
		} else if (match.color.equals("red")) {
			// Do nothing
		}
	}
}
