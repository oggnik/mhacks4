package sphero;

import java.util.ArrayList;
import java.util.Collection;

import se.nicklasgavelin.bluetooth.Bluetooth;
import se.nicklasgavelin.bluetooth.Bluetooth.EVENT;
import se.nicklasgavelin.bluetooth.BluetoothDevice;
import se.nicklasgavelin.bluetooth.BluetoothDiscoveryListener;
import se.nicklasgavelin.sphero.Robot;
import se.nicklasgavelin.sphero.RobotListener;
import se.nicklasgavelin.sphero.command.CommandMessage;
import se.nicklasgavelin.sphero.command.RollCommand;
import se.nicklasgavelin.sphero.exception.InvalidRobotAddressException;
import se.nicklasgavelin.sphero.exception.RobotBluetoothException;
import se.nicklasgavelin.sphero.response.InformationResponseMessage;
import se.nicklasgavelin.sphero.response.ResponseMessage;

public class SpheroManager implements RobotListener, BluetoothDiscoveryListener {

	private Bluetooth bt;
	private Collection<Robot> robots;
	private int responses = 0;
	
	public SpheroManager() {
		this.robots = new ArrayList<Robot>();
		
		// Connect to the sphero
		try {
			bt = new Bluetooth( this, Bluetooth.SERIAL_COM );
			bt.discover();
		} catch (Exception e) {
			System.err.println("Error!!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Move the sphero forward for .5 second
	 */
	public void moveForward() {
		for (Robot r : robots) {
			r.sendCommand( new RollCommand( (float) .5, 0, false ) );
			r.sendCommand( new RollCommand( (float) .5, 0, true ), 500 );
		}
	}
	
	/**
	 * Turn the sphero right 30 degrees
	 */
	public void turnRight() {
		for (Robot r : robots) {
			r.sendCommand( new RollCommand( (float) .5, 90, false ) );
			r.sendCommand( new RollCommand( (float) .5, 90, true ), 500 );
		}
	}
	
	/**
	 * Turn the sphero left 30 degrees
	 */
	public void turnLeft() {
		for (Robot r : robots) {
			r.sendCommand( new RollCommand( (float) .5, 270, false ) );
			r.sendCommand( new RollCommand( (float) .5, 270, true ), 500 );
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Called when the device search is completed with detected devices
	 * 
	 * @param devices The devices detected
	 */
	public void deviceSearchCompleted( Collection<BluetoothDevice> devices )
	{
		// Device search is completed
		System.out.println( "Completed device discovery" );

		// Try and see if we can find any Spheros in the found devices
		for( BluetoothDevice d : devices )
		{
			// Check if the Bluetooth device is a Sphero device or not
			if( Robot.isValidDevice( d ) )
			{
				System.out.println( "Found robot " + d.getAddress() );

				// We got a valid device (Sphero device), connect to it and
				// have some fun with colors.
				try
				{
					// Create our robot from the Bluetooth device that we got
					Robot r = new Robot( d );

					// Add ourselves as listeners for the responses
					r.addListener( this );

					// Check if we can connect
					if( r.connect() )
					{
						// Add robots to our connected robots list
						robots.add( r );

						System.out.println( "Connected to " + d.getName() + " : " + d.getAddress() );
					}
					else
						System.err.println( "Failed to connect to robot" );
				}
				catch( InvalidRobotAddressException ex )
				{
					ex.printStackTrace();
				}
				catch( RobotBluetoothException ex )
				{
					ex.printStackTrace();
				}
			}
		}

		// Disable the thread and set connected button state
		if( robots.isEmpty() )
		{
			System.out.println("No Sphero connected!");
		}
	}
	/**
	 * Called when the search is started
	 */
	public void deviceSearchStarted()
	{
		System.out.println( "Started device search" );
	}

	/**
	 * Called if something went wrong with the device search
	 * 
	 * @param error The error that occurred
	 */
	public void deviceSearchFailed( EVENT error )
	{
		System.err.println( "Failed with device search: " + error );
	}

	/**
	 * Called when a Bluetooth device is discovered
	 * 
	 * @param device The device discovered
	 */
	public void deviceDiscovered( BluetoothDevice device )
	{
		System.out.println( "Discovered device " + device.getName() + " : " + device.getAddress() );
	}

	/*
	 * ********************************************
	 * ROBOT STUFF
	 */

	/**
	 * Called when a response is received from a robot
	 * 
	 * @param r The robot the event concerns
	 * @param response The response received
	 * @param dc The command the response is concerning
	 */
	public void responseReceived( Robot r, ResponseMessage response, CommandMessage dc )
	{
		System.out.println( "(" + ( ++responses ) + ") Received response: " + response.getResponseCode() + " to message " + dc.getCommand() );
	}

	/**
	 * Event that may occur for a robot
	 * 
	 * @param r The robot the event concerns
	 * @param code The event code for the event
	 */
	public void event( Robot r, EVENT_CODE code )
	{
		System.out.println( "Received event: " + code );
	}

	public void informationResponseReceived( Robot r, InformationResponseMessage response )
	{
		// Information response (Ex. Sensor data)
	}
}
