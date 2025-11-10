package cookingMonitor;

public class WellSteakProfile implements FoodProfile {
	private String name = "Steak";
	private double targetTemp = 68.0;
	
	public String getName() {
		return this.name;
	}
	
	public double getTargetTemp() {
		return this.targetTemp;
	}

}
