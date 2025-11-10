package cookingMonitor;

public class HamburgerProfile implements FoodProfile {
	private String name = "Hamburger";
	private double targetTemp = 71.0;
	
	public String getName() {
		return this.name;
	}
	
	public double getTargetTemp() {
		return this.targetTemp;
	}
}
