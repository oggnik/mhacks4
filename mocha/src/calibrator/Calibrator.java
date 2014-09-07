package calibrator;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import matcher.ColorPattern;
import matcher.PatternMatcher;
import model.Pattern;
import model.SensorValue;
import view.View;

public class Calibrator {
	private ArrayList<Pattern> patterns;
	private ArrayList<SensorValue> sensorValues;
	private int numVals = 0;
	private int colorIndex = 0;
	private View view;
	private PatternMatcher patternMatcher;
	
	public final static int BUF_SIZE = 5000;
	
	public Calibrator(View view, PatternMatcher patternMatcher) {
		this.view = view;
		this.patternMatcher = patternMatcher;
		sensorValues = new ArrayList<SensorValue>();
		patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern("yellow"));
		patterns.add(new Pattern("green"));
		patterns.add(new Pattern("blue"));
		patterns.add(new Pattern("red"));
		displayPrompt();
	}
	
	public void calibrate(SensorValue val) {
		
		if (val.attention < 55) {
			return;
		}
		sensorValues.add(val);
		if (sensorValues.size() > BUF_SIZE) {
			sensorValues.remove(0);
		}
		numVals++;
		
		if (numVals > BUF_SIZE) {
			Pattern currentPattern = patterns.get(colorIndex);
			SensorValue average = getAverage();
			currentPattern.sensorValue = average;
			
			colorIndex++;
			numVals = 0;
			if (colorIndex < patterns.size()) {
				displayPrompt();
			}
		}
		
		if (colorIndex >= patterns.size()) {
			view.setCalibrate(false);
			patternMatcher.setColorPattern(new ColorPattern(patterns));
			JOptionPane.showMessageDialog(null, "Calibration complete.");
		}
	}
	
	private SensorValue getAverage() {
		SensorValue val = new SensorValue();
		double alpha1ave = 0;
		double alpha2ave = 0;
		double beta1ave = 0;
		double beta2ave = 0;
		double gamma1ave = 0;
		double gamma2ave = 0;
		double deltaave = 0;
		double thetaave = 0;
		for(int i =0; i < sensorValues.size(); i++){
			alpha1ave += sensorValues.get(i).alpha1;
			alpha2ave += sensorValues.get(i).alpha2;
			beta1ave += sensorValues.get(i).beta1;
			beta2ave += sensorValues.get(i).beta2;
			gamma1ave += sensorValues.get(i).gamma1;
			gamma2ave += sensorValues.get(i).gamma2;
			deltaave += sensorValues.get(i).delta;
			thetaave += sensorValues.get(i).theta;
		}
		val.alpha1 = alpha1ave / sensorValues.size();
		val.alpha2 = alpha2ave / sensorValues.size();
		val.beta1 = beta1ave / sensorValues.size();
		val.beta2 = beta2ave / sensorValues.size();
		val.gamma1 = gamma1ave / sensorValues.size();
		val.gamma2 = gamma2ave / sensorValues.size();
		val.delta = deltaave / sensorValues.size();
		val.theta = thetaave / sensorValues.size();
		
		return val;
	}
	
	private void displayPrompt() {
		JOptionPane.showMessageDialog(null, "Please calibrate the color: " + patterns.get(colorIndex).color);
	}
}
