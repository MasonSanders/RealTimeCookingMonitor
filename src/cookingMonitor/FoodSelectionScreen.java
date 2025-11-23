package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class FoodSelectionScreen extends JPanel {
	public FoodSelectionScreen (MainScreenCallbacks controller) {
		setLayout(new GridLayout(4, 1, 10, 10));
		JButton steakBtn = new JButton("steak");
		JButton burgerBtn = new JButton("Hamburger");
		JButton chickenBtn = new JButton("Chicken");
		JButton porkBtn = new JButton("Pork");
		
		steakBtn.addActionListener(e -> controller.handleFoodSelected(Dish.STEAK));
        burgerBtn.addActionListener(e -> controller.handleFoodSelected(Dish.HAMBURGER));
        chickenBtn.addActionListener(e -> controller.handleFoodSelected(Dish.CHICKEN));
        porkBtn.addActionListener(e -> controller.handleFoodSelected(Dish.PORK));

        add(steakBtn);
        add(burgerBtn);
        add(chickenBtn);
        add(porkBtn);
	}

}
