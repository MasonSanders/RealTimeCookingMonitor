package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class PreheatScreen extends JPanel {
	private final MainScreenCallbacks controller;
	
	private JLabel statusLabel;
	private JLabel tempLabel;
	private JProgressBar progressBar;
	private JButton readyButton;
	private double targetTemp = 0;
	
	
	public PreheatScreen(MainScreenCallbacks controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        statusLabel = new JLabel("Preheating grill...");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);

        // Center panel for temperature + progress
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        tempLabel = new JLabel("Current temperature: -- °C");
        tempLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(tempLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(progressBar);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom button
        readyButton = new JButton("Food is on grill and thermometer inserted");
        readyButton.setEnabled(false);
        readyButton.addActionListener(e -> controller.handleFoodPlacedOnGrill());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(readyButton);
        add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public void setTargetTemperature(double targetTemp) {
		this.targetTemp = targetTemp;
		progressBar.setValue(0);
		statusLabel.setText("Preheating grill...");
		readyButton.setEnabled(false);
	}
	
	public void updateTemperature(double currentTemp) {
        tempLabel.setText(
                String.format("Current temperature: %.1f / %.1f °C", currentTemp, targetTemp)
        );

        if (targetTemp > 0) {
            int pct = (int) Math.round((currentTemp / targetTemp) * 100.0);
            pct = Math.max(0, Math.min(100, pct));
            progressBar.setValue(pct);
        }
    }
	
	public void showReadyPrompt() {
        statusLabel.setText("Grill is ready!");
        readyButton.setEnabled(true);
    }
	
}
