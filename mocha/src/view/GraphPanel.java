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
	public static final int NUM_VALUES = 5000;
	
	// Values will hold NUM_VALUES values
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
		
		SensorValue maxValue = getMaxValue();
		double xScale = ((double) GRAPH_WIDTH) / NUM_VALUES;
		
		SensorValue last = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			SensorValue value = getAverage(values, i, 150);
			int x = (int) (i * xScale);
			int lastX = (int) ((i - 1) * xScale);
			
			if (last != null && value != null) {
				g2.setColor(View.ALPHA1_COLOR);
				drawLine(lastX, last.alpha1, x, value.alpha1, GRAPH_HEIGHT / maxValue.alpha1, g2);
				g2.setColor(View.ALPHA2_COLOR);
				drawLine(lastX, last.alpha2, x, value.alpha2, GRAPH_HEIGHT / maxValue.alpha2, g2);
				g2.setColor(View.BETA1_COLOR);
				drawLine(lastX, last.beta1, x, value.beta1, GRAPH_HEIGHT / maxValue.beta1, g2);
				g2.setColor(View.BETA2_COLOR);
				drawLine(lastX, last.beta2, x, value.beta2, GRAPH_HEIGHT / maxValue.beta2, g2);
				g2.setColor(View.DELTA_COLOR);
				drawLine(lastX, last.delta, x, value.delta, GRAPH_HEIGHT / maxValue.delta, g2);
				g2.setColor(View.GAMMA1_COLOR);
				drawLine(lastX, last.gamma1, x, value.gamma1, GRAPH_HEIGHT / maxValue.gamma1, g2);
				g2.setColor(View.GAMMA2_COLOR);
				drawLine(lastX, last.gamma2, x, value.gamma2, GRAPH_HEIGHT / maxValue.gamma2, g2);
				g2.setColor(View.THETA_COLOR);
				drawLine(lastX, last.theta, x, value.theta, GRAPH_HEIGHT / maxValue.theta, g2);
				g2.setColor(Color.BLACK);
			}
			
			last = value;
		}
	}
	
	private void drawLine(int x1, double lastVal, int x2, double val, double yScale, Graphics2D g2) {
		int lastY = (int) (lastVal * yScale);
		int curY = (int) (val * yScale);
		g2.drawLine(x1, GRAPH_HEIGHT - lastY, x2, GRAPH_HEIGHT - curY);
	}
	
	private SensorValue getMaxValue() {
		SensorValue max = new SensorValue();
		Object[] valuesArray = values.toArray();
		for (Object o : valuesArray) {
			SensorValue value = (SensorValue) o;
			if (value == null) {
				continue;
			}
			if (value.alpha1 > max.alpha1) {
				max.alpha1 = value.alpha1;
			}
			if (value.alpha2 > max.alpha2) {
				max.alpha2 = value.alpha2;
			}
			if (value.beta1 > max.beta1) {
				max.beta1 = value.beta1;
			}
			if (value.beta2 > max.beta2) {
				max.beta2 = value.beta2;
			}
			if (value.delta > max.delta) {
				max.delta = value.delta;
			}
			if (value.gamma1 > max.gamma1) {
				max.gamma1 = value.gamma1;
			}
			if (value.gamma2 > max.gamma2) {
				max.gamma2 = value.gamma2;
			}
			if (value.theta > max.theta) {
				max.theta = value.theta;
			}
		}
//		max.alpha1 += val.alpha1;
//		max.alpha2 += val.alpha2;
//		max.beta1 += val.beta1;
//		max.beta2 += val.beta2;
//		max.delta += val.delta;
//		max.gamma1 += val.gamma1;
//		max.gamma2 += val.gamma2;
//		max.theta += val.theta;
		return max;
	}
	
	private SensorValue getAverage(ArrayList<SensorValue> values, int index, int radius) {
		SensorValue average = new SensorValue();
		int numVals = 0;
		for (int i = index - radius; i <= index + radius; i++) {
			if (i < 0) {
				continue;
			}
			if (i >= values.size()) {
				break;
			}
			
			SensorValue val = (SensorValue) values.get(i);
			if (val == null) {
				continue;
			}
			average.alpha1 += val.alpha1;
			average.alpha2 += val.alpha2;
			average.beta1 += val.beta1;
			average.beta2 += val.beta2;
			average.delta += val.delta;
			average.gamma1 += val.gamma1;
			average.gamma2 += val.gamma2;
			average.theta += val.theta;
			
			numVals++;
		}
		
		average.alpha1 /= numVals;
		average.alpha2 /= numVals;
		average.beta1 /= numVals;
		average.beta2 /= numVals;
		average.delta /= numVals;
		average.gamma1 /= numVals;
		average.gamma2 /= numVals;
		average.theta /= numVals;
		
		return average;
	}
}
