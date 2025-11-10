package cookingMonitor;

public class RareSteakProfile implements FoodProfile {
	private String name = "Steak";
	private double targetTemp = 48.0;
	
	public String getName() {
		return this.name;
	}
	
	public double getTargetTemp() {
		return this.targetTemp;
	}
}
