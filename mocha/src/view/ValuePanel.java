package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.SensorValue;

public class ValuePanel extends JPanel {
	private JLabel alpha1Label;
	private JLabel alpha2Label;
	private JLabel beta1Label;
	private JLabel beta2Label;
	private JLabel deltaLabel;
	private JLabel gamma1Label;
	private JLabel gamma2Label;
	private JLabel thetaLabel;
	public ValuePanel() {
		setPreferredSize(new Dimension(200, GraphPanel.GRAPH_HEIGHT));
		setBackground(Color.BLACK);
		setLayout(new GridLayout(0, 1));
		alpha1Label = new JLabel("");
		alpha2Label = new JLabel("");
		beta1Label = new JLabel("");
		beta2Label = new JLabel("");
		deltaLabel = new JLabel("");
		gamma1Label = new JLabel("");
		gamma2Label = new JLabel("");
		thetaLabel = new JLabel("");
		alpha1Label.setForeground(View.ALPHA1_COLOR);
		alpha2Label.setForeground(View.ALPHA2_COLOR);
		beta1Label.setForeground(View.BETA1_COLOR);
		beta2Label.setForeground(View.BETA2_COLOR);
		deltaLabel.setForeground(View.DELTA_COLOR);
		gamma1Label.setForeground(View.GAMMA1_COLOR);
		gamma2Label.setForeground(View.GAMMA2_COLOR);
		thetaLabel.setForeground(View.THETA_COLOR);
		add(alpha1Label);
		add(alpha2Label);
		add(beta1Label);
		add(beta2Label);
		add(deltaLabel);
		add(gamma1Label);
		add(gamma2Label);
		add(thetaLabel);
	}
	
	public void updateValues(SensorValue value) {
		alpha1Label.setText(String.format("Alpha 1: %.3f", value.alpha1));
		alpha2Label.setText(String.format("Alpha 2: %.3f", value.alpha2));
		beta1Label.setText(String.format("Beta 1: %.3f", value.beta1));
		beta2Label.setText(String.format("Beta 2: %.3f", value.beta2));
		deltaLabel.setText(String.format("Delta: %.3f", value.delta));
		gamma1Label.setText(String.format("Gamma 1: %.3f", value.gamma1));
		gamma2Label.setText(String.format("Gamma 2: %.3f", value.gamma2));
		thetaLabel.setText(String.format("Theta: %.3f", value.theta));
	}
}
