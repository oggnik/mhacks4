package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.SensorValue;
import matcher.*;
/**
 * The Display
 *
 */
public class View {
	public static final Color ALPHA1_COLOR = Color.RED;
	public static final Color ALPHA2_COLOR = Color.RED.brighter();
	public static final Color BETA1_COLOR = Color.BLUE;
	public static final Color BETA2_COLOR = Color.BLUE.brighter();
	public static final Color DELTA_COLOR = Color.CYAN;
	public static final Color GAMMA1_COLOR = Color.GREEN;
	public static final Color GAMMA2_COLOR = Color.GREEN.brighter();
	public static final Color THETA_COLOR = Color.ORANGE;
	
	private JFrame frame;
	private GraphPanel graphPanel;
	private ValuePanel valuePanel;
	private boolean calibrate;
	private boolean running;
	private PatternMatcher patternPatcher;
	
	public View() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calibrate = false;
		running = false;
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBackground(Color.BLACK);
		
		/*
		 * Create the buttons
		 */
		JPanel buttonPanel = new JPanel();
		JButton calibrateButton = new JButton("Calibrate");
		calibrateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Calibrate");
				calibrate = true;
				running = false;
			}
		});
		buttonPanel.add(calibrateButton);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start");
				running = true;
			}
		});
		buttonPanel.add(startButton);
		
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Stop");
				running = false;
			}
		});
		buttonPanel.add(stopButton);
		
		buttonPanel.setBackground(Color.BLACK);
		content.add(buttonPanel, BorderLayout.SOUTH);
		
		graphPanel = new GraphPanel();
		content.add(graphPanel, BorderLayout.CENTER);
		
		valuePanel = new ValuePanel();
		content.add(valuePanel, BorderLayout.EAST);
		
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Pass a SensorValue to the view.
	 * The view updates with the new values.
	 * @param sensorValue A new SensorValue
	 */
	public void update(SensorValue sensorValue) {
		graphPanel.updateValues(sensorValue);
		valuePanel.updateValues(sensorValue);
		/**System.out.println("alpha1: " + sensorValue.alpha1);
		System.out.println("alpha2: " + sensorValue.alpha2);
		System.out.println("beta1: " + sensorValue.beta1);
		System.out.println("beta2: " + sensorValue.beta2);
		System.out.println("delta: " + sensorValue.delta);
		System.out.println("gamma1: " + sensorValue.gamma1);
		System.out.println("gamma1: " + sensorValue.gamma2);
		System.out.println("theta: " + sensorValue.theta);**/
		System.out.println("-----------------\n");
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

	/**
	 * Determine if we should be running
	 * @return
	 */
	public boolean getRunning() {
		return running;
	}
}
