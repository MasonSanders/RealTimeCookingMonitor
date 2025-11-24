package cookingMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class MainWindow extends JFrame {

	private final CardLayout cardLayout; 
	private final JPanel cardPanel;
	private final Map<String, JPanel> screens = new HashMap<>();
	
	public static final String SCREEN_MAIN = "main";
	public static final String SCREEN_PREHEAT = "preheat";
	public static final String SCREEN_FOOD_SELECTION = "foodSelection";
	public static final String SCREEN_DONENESS = "doneness";
	
	public MainWindow() {
		super("Cooking Monitor");
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		add(cardPanel, BorderLayout.CENTER);
	}
	
	public void addScreen(String name, JPanel panel) {
		cardPanel.add(panel, name);
		screens.put(name, panel);
	}
	
	public void showScreen(String name) {
		cardLayout.show(cardPanel, name);
		
	}
}
