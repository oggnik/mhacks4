package view;

import com.neurosky.thinkgear.ThinkGear;

import model.SensorValue;

/**
 * The Display
 *
 */
public class View {
	public View() {
		
	}
	
	/**
	 * Pass a SensorValue to the view.
	 * The view updates with the new values.
	 * @param sensorValue A new SensorValue
	 */
	public void update(SensorValue sensorValue) {
		System.out.println(sensorValue.alpha1);
		System.out.println(sensorValue.alpha2);
		System.out.println(sensorValue.attention);
		System.out.println(sensorValue.battery);
		System.out.println(sensorValue.beta1);
		System.out.println(sensorValue.beta2);
		System.out.println(sensorValue.delta);
		System.out.println(sensorValue.gamma1);
		System.out.println(sensorValue.gamma2);
		System.out.println(sensorValue.meditation);
		System.out.println(sensorValue.poorSignal);
		System.out.println(sensorValue.raw);
		System.out.println(sensorValue.theta);
		System.out.println("-----------------\n");
	}
}
