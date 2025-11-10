package cookingMonitor;

public class ChickenProfile implements FoodProfile {
	private String name = "Chicken";
	private double targetTemp = 74.0;
	
	public String getName() {
		return this.name;	
	}
	
	public double getTargetTemp() {
		return this.targetTemp;
	}
}
