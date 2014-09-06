package matcher;

import model.SensorValue;
import matcher.ColorPattern;
import model.Pattern;
import java.util.ArrayList;
/**
 * The pattern matching part
 *
 */
public class PatternMatcher {
	public static final int BUFFER_SIZE = 50;
	ArrayList<SensorValue> sensorvalues; 
	public SensorValue average;
	public ColorPattern colorPattern;
	private boolean calibrate;
	int colorNum;
	public PatternMatcher() {
		sensorvalues = new ArrayList<SensorValue>();
		colorPattern = new ColorPattern();
		colorNum =colorPattern.count;
		average = new SensorValue();
	}
	
	/**
	 * Updates the pattern matcher with a single sensor value
	 * @param sensorValue
	 */
	public void update(SensorValue sensorValue) {
		sensorvalues.add(sensorValue);
		if(sensorvalues.size() > BUFFER_SIZE){
			sensorvalues.remove(0);
		}
	}
	
	public SensorValue getAverage(){
		double alpha1ave = 0;
		double alpha2ave = 0;
		double beta1ave = 0;
		double beta2ave = 0;
		double gamma1ave = 0;
		double gamma2ave = 0;
		double deltaave = 0;
		double thetaave = 0;
		for(int i =0; i < sensorvalues.size(); i++){
			alpha1ave += sensorvalues.get(i).alpha1;
			alpha2ave += sensorvalues.get(i).alpha2;
			beta1ave += sensorvalues.get(i).beta1;
			beta2ave += sensorvalues.get(i).beta2;
			gamma1ave += sensorvalues.get(i).gamma1;
			gamma2ave += sensorvalues.get(i).gamma2;
			deltaave += sensorvalues.get(i).delta;
			thetaave += sensorvalues.get(i).theta;
		}
		average.alpha1 = alpha1ave /BUFFER_SIZE;
		average.alpha2 = alpha2ave /BUFFER_SIZE;
		average.beta1 = beta1ave /BUFFER_SIZE;
		average.beta2 = beta2ave /BUFFER_SIZE;
		average.gamma1 = gamma1ave /BUFFER_SIZE;
		average.gamma2 = gamma2ave /BUFFER_SIZE;
		average.delta = deltaave /BUFFER_SIZE;
		average.theta = thetaave /BUFFER_SIZE;
		return average;
	}
	
	
	/**
	 * Find the current color pattern. If necessary, change 
	 * return type of findMatch()
	 * @param sensorValue
	 */
	public String findMatch(PatternMatcher patternMatcher){
		for(int i = 0; i < sensorvalues.size(); i++){
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
		if(average.alpha1 < pattern.loweralpha1 || average.alpha1 > pattern.higheralpha1)
			return false;
		if(average.alpha2 < pattern.loweralpha2 || average.alpha2 > pattern.higheralpha2)
			return false;
		if(average.beta1 < pattern.lowerbeta1 || average.beta1 > pattern.higherbeta1)
			return false;
		if(average.beta2 < pattern.lowerbeta2 || average.beta2 > pattern.higherbeta2)
			return false;
		if(average.gamma1 < pattern.lowergamma1 || average.gamma1 > pattern.highergamma1)
			return false;
		if(average.gamma2 < pattern.lowergamma2 || average.gamma2 > pattern.highergamma2)
			return false;
		if(average.theta < pattern.lowertheta || average.theta > pattern.highertheta)
			return false;
		if(average.delta < pattern.lowerdelta || average.alpha1 > pattern.higherdelta)
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
