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
	private int numVals = 0;
	private int colorIndex = 0;
	private View view;
	private PatternMatcher patternMatcher;
	
	public Calibrator(View view, PatternMatcher patternMatcher) {
		this.view = view;
		this.patternMatcher = patternMatcher;
		patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern("yellow"));
		patterns.add(new Pattern("green"));
		patterns.add(new Pattern("blue"));
		patterns.add(new Pattern("red"));
		displayPrompt();
	}
	
	public void calibrate(SensorValue val) {
		Pattern currentPattern = patterns.get(colorIndex);
		// Oh dear
		if (val.alpha1 < currentPattern.loweralpha1) {
			currentPattern.loweralpha1 = val.alpha1;
		}
		if (val.alpha1 > currentPattern.higheralpha1) {
			currentPattern.higheralpha1 = val.alpha1;
		}
		if (val.alpha2 < currentPattern.loweralpha2) {
			currentPattern.loweralpha2 = val.alpha2;
		}
		if (val.alpha2 > currentPattern.higheralpha2) {
			currentPattern.higheralpha2 = val.alpha2;
		}
		if (val.beta1 < currentPattern.lowerbeta1) {
			currentPattern.lowerbeta1 = val.beta1;
		}
		if (val.beta1 > currentPattern.lowerbeta1) {
			currentPattern.lowerbeta1 = val.beta1;
		}
		if (val.beta2 < currentPattern.lowerbeta2) {
			currentPattern.lowerbeta2 = val.beta2;
		}
		if (val.beta2 > currentPattern.lowerbeta2) {
			currentPattern.lowerbeta2 = val.beta2;
		}
		if (val.delta < currentPattern.lowerdelta) {
			currentPattern.lowerdelta = val.delta;
		}
		if (val.delta > currentPattern.higherdelta) {
			currentPattern.higherdelta = val.delta;
		}
		if (val.gamma1 < currentPattern.lowergamma1) {
			currentPattern.lowergamma1 = val.gamma1;
		}
		if (val.gamma1 > currentPattern.highergamma1) {
			currentPattern.highergamma1 = val.gamma1;
		}
		if (val.gamma2 < currentPattern.lowergamma2) {
			currentPattern.lowergamma2 = val.gamma2;
		}
		if (val.gamma2 > currentPattern.highergamma2) {
			currentPattern.highergamma2 = val.gamma2;
		}
		if (val.theta < currentPattern.lowertheta) {
			currentPattern.lowertheta = val.theta;
		}
		if (val.theta > currentPattern.highertheta) {
			currentPattern.highertheta = val.theta;
		}
		
		numVals++;
		
		if (numVals > 5000) {
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
	
	private void displayPrompt() {
		JOptionPane.showMessageDialog(null, "Please calibrate the color: " + patterns.get(colorIndex).color);
	}
}
