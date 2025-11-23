package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class ThermometerRowPanel extends JPanel {
	public ThermometerRowPanel(ThermometerViewModel vm, MainScreenCallbacks controller) {
		setLayout(new BorderLayout());
		JLabel nameLabel = new JLabel(vm.label + " (" + vm.deviceID + ")");
		add(nameLabel, BorderLayout.WEST);
		
		JLabel statusLabel = new JLabel(vm.status.toString());
		add(statusLabel, BorderLayout.CENTER);
		
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
			actionButton.setEnabled(false);
			break;
		}
		add(actionButton, BorderLayout.EAST);
	}
}
