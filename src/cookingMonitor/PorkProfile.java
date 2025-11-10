package cookingMonitor;

public class PorkProfile implements FoodProfile {
	private String name = "Pork";
	private double targetTemp = 63.0;
	
	public String getName() {
		return this.name;
	}
	
	public double getTargetTemp() {
		return this.targetTemp;
	}
}
