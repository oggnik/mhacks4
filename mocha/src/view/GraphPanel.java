package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.SensorValue;

public class GraphPanel extends JPanel {
	public static final int GRAPH_WIDTH = 800;
	public static final int GRAPH_HEIGHT = 500;
	public static final int NUM_VALUES = 75;
	// Values will hold 100 values
	private ArrayList<SensorValue> values;
	
	public GraphPanel() {
		setPreferredSize(new Dimension(GRAPH_WIDTH, GRAPH_HEIGHT));
		values = new ArrayList<SensorValue>();
	}
	
	public void updateValues(SensorValue value) {
		values.add(value);
		if (values.size() > NUM_VALUES) {
			values.remove(0);
		}
		repaint();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, GRAPH_WIDTH, GRAPH_HEIGHT);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, GRAPH_WIDTH, GRAPH_HEIGHT);
		
		if (values.isEmpty())
			return;
		
		double maxValue = getMaxValue();
		double xScale = ((double) GRAPH_WIDTH) / NUM_VALUES;
		double yScale = GRAPH_HEIGHT / maxValue;
		
		SensorValue last = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			SensorValue value = values.get(i);
			int x = (int) (i * xScale);
			int lastX = (int) ((i - 1) * xScale);
			
			g2.setColor(View.ALPHA1_COLOR);
			drawLine(lastX, last.alpha1, x, value.alpha1, yScale, g2);
			g2.setColor(View.ALPHA2_COLOR);
			drawLine(lastX, last.alpha2, x, value.alpha2, yScale, g2);
			g2.setColor(View.BETA1_COLOR);
			drawLine(lastX, last.beta1, x, value.beta1, yScale, g2);
			g2.setColor(View.BETA2_COLOR);
			drawLine(lastX, last.beta2, x, value.beta2, yScale, g2);
			g2.setColor(View.DELTA_COLOR);
			drawLine(lastX, last.delta, x, value.delta, yScale, g2);
			g2.setColor(View.GAMMA1_COLOR);
			drawLine(lastX, last.gamma1, x, value.gamma1, yScale, g2);
			g2.setColor(View.GAMMA2_COLOR);
			drawLine(lastX, last.gamma2, x, value.gamma2, yScale, g2);
			g2.setColor(View.THETA_COLOR);
			drawLine(lastX, last.theta, x, value.theta, yScale, g2);
			g2.setColor(Color.BLACK);
			
			last = value;
		}
	}
	
	private void drawLine(int x1, double lastVal, int x2, double val, double yScale, Graphics2D g2) {
		int lastY = (int) (lastVal * yScale);
		int curY = (int) (val * yScale);
		g2.drawLine(x1, GRAPH_HEIGHT - lastY, x2, GRAPH_HEIGHT - curY);
	}
	
	private double getMaxValue() {
		double max = 0;
		for (SensorValue value : values) {
			if (value.alpha1 > max) {
				max = value.alpha1;
			}
			if (value.alpha2 > max) {
				max = value.alpha2;
			}
			if (value.beta1 > max) {
				max = value.beta1;
			}
			if (value.beta2 > max) {
				max = value.beta2;
			}
			if (value.delta > max) {
				max = value.delta;
			}
			if (value.gamma1 > max) {
				max = value.gamma1;
			}
			if (value.gamma2 > max) {
				max = value.gamma2;
			}
			if (value.theta > max) {
				max = value.theta;
			}
		}
		return max;
	}
	
}
