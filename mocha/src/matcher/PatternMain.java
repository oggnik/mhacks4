package matcher;
import model.Pattern;
import model.SensorValue;
public class PatternMain {
	public static void main(String[] args){
		PatternMatcher pm = new PatternMatcher();
		SensorValue sv = new SensorValue();
		sv.alpha1 = 0.17;
		sv.alpha2=0.27;
		sv.beta1 =0.37;
		sv.beta2 = 0.47;
		sv.gamma1 = 0.53;
		sv.gamma2 = 0.57;
		sv.delta = 0.67;
		sv.theta = 0.77;
		pm.update(sv);
		System.out.println(pm.colorPattern.patternArray.size());

		System.out.println(pm.findMatch(pm));
	}
}
