package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class SteakDonenessScreen extends JPanel {
	public SteakDonenessScreen(MainScreenCallbacks controller) {
		setLayout(new GridLayout(3, 1, 10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JButton rareBtn = new JButton("Rare");
		JButton mediumBtn = new JButton("Medium");
		JButton wellBtn = new JButton("Well-Done");
		
		rareBtn.addActionListener(e -> controller.handleSteakDonenessSelected(Doneness.RARE));
		mediumBtn.addActionListener(e -> controller.handleSteakDonenessSelected(Doneness.MEDIUM));
		wellBtn.addActionListener(e -> controller.handleSteakDonenessSelected(Doneness.WELL));
		
		add(rareBtn);
		add(mediumBtn);
		add(wellBtn);
		
	}
}
