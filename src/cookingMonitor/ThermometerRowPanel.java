package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class ThermometerRowPanel extends JPanel {
	public ThermometerRowPanel(ThermometerViewModel vm, MainScreenCallbacks controller) {
		setLayout(new BorderLayout());
		JLabel nameLabel = new JLabel("(" + vm.deviceID + ") ");
		add(nameLabel, BorderLayout.WEST);
		
		JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		
		String centerText;
        if (vm.status == ThermometerStatus.COOKING) {
            int pct = (int) (Math.floor(vm.progress * 100));
            centerText = vm.status + " (" + pct + "%)";
        } else {
            centerText = vm.status.toString();
        }
		
		JLabel statusLabel = new JLabel(centerText);
		centerPanel.add(statusLabel, BorderLayout.CENTER);
		
		if (vm.status == ThermometerStatus.COOKING && vm.timeRemaining != null && !vm.timeRemaining.isEmpty()) {
            JLabel timeLabel = new JLabel("Time remaining: " + vm.timeRemaining);
            centerPanel.add(timeLabel);
        }
		
		add(centerPanel, BorderLayout.CENTER);
		
		JButton actionButton = new JButton();
		switch (vm.status) {
		case AVAILABLE:
			actionButton.setText("Start Session");
			actionButton.addActionListener(e -> controller.handleStartSession(vm.deviceID));
			break;
		case PREHEATING:
			actionButton.setText("Preheating...");
			actionButton.setEnabled(false);
			break;
		case COOKING:
			actionButton.setText("Stop");
			actionButton.addActionListener(e -> controller.handleActiveSessionClick(vm.deviceID));
			break;
		case DONE:
			actionButton.setText("Done");
			actionButton.addActionListener(e -> controller.handleActiveSessionClick(vm.deviceID));
			break;
		}
		add(actionButton, BorderLayout.EAST);
	}
}
