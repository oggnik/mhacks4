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
	public double battery;
	public double poorSignal;
	public double attention;
	public double meditation;
	public double raw;
	public double delta;
	public double theta;
	public double alpha1;
	public double alpha2;
	public double beta1;
	public double beta2;
	public double gamma1;
	public double gamma2;
}
