package matcher;

import model.SensorValue;
import matcher.ColorPattern;
import model.Pattern;
/**
 * The pattern matching part
 *
 */
public class PatternMatcher {
	private double alpha1Value; 
	private double alpha2Value;
	private double beta1Value;
	private double beta2Value;
	private double deltaValue;
	private double thetaValue;
	private double gamma1Value;
	private double gamma2Value;
	public ColorPattern colorPattern;
	private boolean calibrate;
	int colorNum;
	public PatternMatcher() {
		colorPattern = new ColorPattern();
		colorNum =colorPattern.count;
	}
	
	/**
	 * Updates the pattern matcher with a single sensor value
	 * @param sensorValue
	 */
	public void update(SensorValue sensorValue) {
		alpha1Value = sensorValue.alpha1;
		alpha2Value = sensorValue.alpha2;
		beta1Value = sensorValue.beta1;
		beta2Value = sensorValue.beta2;
		deltaValue = sensorValue.delta;
		thetaValue = sensorValue.theta;
		gamma1Value = sensorValue.gamma1;
		gamma2Value = sensorValue.gamma2;
	}
	
	/**
	 * Find the current color pattern. If necessary, change 
	 * return type of findMatch()
	 * @param sensorValue
	 */
	public String findMatch(PatternMatcher patternMatcher){
		String match;
		for(int i = 0; i < 1; i++){
			if(patternMatcher.matchColor((Pattern)colorPattern.patternArray.get(i)))
				return "Matched color is found at "+i;
		}
		return "No match";
	}
	
	
	/**
	 * Check whether received sensor values match a specific color pattern
	 * @param pattern
	 * @return
	 */
	public boolean matchColor (Pattern pattern){
		if(alpha1Value < pattern.loweralpha1 || alpha1Value > pattern.higheralpha1)
			return false;
		if(alpha2Value < pattern.loweralpha2 || alpha1Value > pattern.higheralpha2)
			return false;
		if(beta1Value < pattern.lowerbeta1 || beta1Value > pattern.higherbeta1)
			return false;
		if(beta2Value < pattern.lowerbeta2 || beta2Value > pattern.higherbeta2)
			return false;
		if(gamma1Value < pattern.lowergamma1 || gamma1Value > pattern.highergamma1)
			return false;
		if(gamma2Value < pattern.lowergamma2 || gamma2Value > pattern.highergamma2)
			return false;
		if(thetaValue < pattern.lowertheta || thetaValue > pattern.highertheta)
			return false;
		if(deltaValue < pattern.lowerdelta || alpha1Value > pattern.higherdelta)
			return false;
		return true;
	}
	/**
	 * Determine if we should calibrate or not
	 * @return calibrate
	 */
	public boolean getCalibrate() {
		return calibrate;
	}
	
	/**
	 * Set the calibration
	 * @param c
	 */
	public void setCalibrate(boolean c) {
		calibrate = c;
	}
}
