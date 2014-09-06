package model;

/**
 * This will hold all the data values
 */
public class SensorValue {

	public SensorValue() {
		// do nothing
	}

	/*
	 * All these values should be filled upon querying packet data.
	 */
	
	public int battery;
	public int poorSignal;
	public int attention;
	public int meditation;
	public int raw;
	public int delta;
	public int theta;
	public int alpha1;
	public int alpha2;
	public int beta1;
	public int beta2;
	public int gamma1;
	public int gamma2;
}
