package matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.Pattern;
import model.SensorValue;
import sphero.SpheroManager;
/**
 * The pattern matching part
 *
 */
public class PatternMatcher {
	public static final int BUFFER_SIZE = 500;
	ArrayList<SensorValue> sensorvalues; 
	private ArrayList<SensorValue> averageValues; 
	public SensorValue val;
	private ColorPattern colorPattern;
	int colorNum = 0;
	
	public PatternMatcher() {
		sensorvalues = new ArrayList<SensorValue>();
		averageValues = new ArrayList<SensorValue>();
		colorPattern = null;
		val = new SensorValue();
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
		// Get the average of the buffer
		getAverage();
		
		// Add the average to the buffer average
		averageValues.add(val);
		if (averageValues.size() > BUFFER_SIZE) {
			averageValues.remove(0);
		}
		
		
		System.out.println("alpha1: "+this.val.alpha1+"   alpha2:"+this.val.alpha2);
		System.out.println("beta1: "+this.val.beta1+"   beta2:"+this.val.beta2);
		System.out.println("gamma1: " +this.val.gamma1+"  gamma2: "+ this.val.gamma2);
		System.out.println("delta: "+ this.val.delta+"  theta: "+this.val.theta);
		System.out.println("-----------------\n");
	}
	
	public void getAverage(){
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
		val.alpha1 = alpha1ave /BUFFER_SIZE;
		val.alpha2 = alpha2ave /BUFFER_SIZE;
		val.beta1 = beta1ave /BUFFER_SIZE;
		val.beta2 = beta2ave /BUFFER_SIZE;
		val.gamma1 = gamma1ave /BUFFER_SIZE;
		val.gamma2 = gamma2ave /BUFFER_SIZE;
		val.delta = deltaave /BUFFER_SIZE;
		val.theta = thetaave /BUFFER_SIZE;
		/*System.out.println("alpha1: "+average.alpha1+"   alpha2:"+average.alpha2);
		System.out.println("beta1: "+average.beta1+"   beta2:"+average.beta2);
		System.out.println("gamma1: " +average.gamma1+"  gamma2: "+ average.gamma2);
		System.out.println("delta: "+ average.delta+"  theta: "+average.theta);*/
	}
	
	
	/**
	 * Find the current color pattern. If necessary, change 
	 * return type of findMatch()
	 * @param sensorValue
	 */
	public Pattern findMatch() {
		HashMap<Pattern, Integer> map = new HashMap<Pattern, Integer>();
		for (SensorValue val : averageValues) {
			Pattern matchPattern = findMatch(val);
			map.put(matchPattern, map.get(matchPattern) + 1);
		}
		
		Pattern maxMatchingPattern = null;
		int maxMatches = 0;
		
		for (Map.Entry<Pattern, Integer> entry : map.entrySet()) {
			if (entry.getValue() > maxMatches) {
				maxMatchingPattern = entry.getKey();
				maxMatches = entry.getValue();
			}
		}
		if (maxMatches > BUFFER_SIZE / 2) {
			return maxMatchingPattern;
		}
		return null;
	}
	
	/**
	 * For a specific SensorValue, which pattern does it match the best?
	 * @param val
	 * @return The best matching pattern
	 */
	public Pattern findMatch(SensorValue val) {
		Pattern bestPattern = colorPattern.patternArray.get(0);
		double max = 0;
		for (int i = 0; i < colorPattern.patternArray.size(); i++) {
			double match = matchColor(val, colorPattern.patternArray.get(i));
			if (match > max) {
				max = match;
				bestPattern = colorPattern.patternArray.get(i);
			}
		}
		return bestPattern;
	}
	
	
	/**
	 * Check whether received sensor values match a specific color pattern
	 * @param pattern
	 * @return
	 */
	public double matchColor (SensorValue val, Pattern pattern){
		int matches = 0;
		if(val.alpha1 > pattern.loweralpha1 && val.alpha1 < pattern.higheralpha1)
			matches++;
		if(val.alpha2 > pattern.loweralpha2 && val.alpha2 < pattern.higheralpha2)
			matches++;
		if(val.beta1 > pattern.lowerbeta1 && val.beta1 < pattern.higherbeta1)
			matches++;
		if(val.beta2 > pattern.lowerbeta2 && val.beta2 < pattern.higherbeta2)
			matches++;
		if(val.gamma1 > pattern.lowergamma1 && val.gamma1 < pattern.highergamma1)
			matches++;
		if(val.gamma2 > pattern.lowergamma2 && val.gamma2 < pattern.highergamma2)
			matches++;
		if(val.theta > pattern.lowertheta && val.theta < pattern.highertheta)
			matches++;
		if(val.delta > pattern.lowerdelta && val.alpha1 < pattern.higherdelta)
			matches++;
		return matches / 8.0;
	}
	
	public void setColorPattern(ColorPattern c) {
		colorPattern = c;
		colorNum = colorPattern.patternArray.size();
	}
}
