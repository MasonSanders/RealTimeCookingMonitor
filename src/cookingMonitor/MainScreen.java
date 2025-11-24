package cookingMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainScreen extends JPanel {
	private final MainScreenCallbacks controller;
	private final JPanel listPanel;
	
	public MainScreen(MainScreenCallbacks controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		add(new JLabel("Thermometers"), BorderLayout.NORTH);
		
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(listPanel);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void updateThermometers(List<ThermometerViewModel> models) {
		listPanel.removeAll();
		
		for (ThermometerViewModel vm : models) {
			ThermometerRowPanel row = new ThermometerRowPanel(vm, controller);
			listPanel.add(row);
		}
		
		listPanel.revalidate();
		listPanel.repaint();
	}
}
