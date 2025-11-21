package cookingMonitor;

public class CookingSession implements TemperatureListener {
	private String sessionID;
	private double progress;
	private String timeRemaining;
	private FoodProfile foodProfile;
	private double currentTemp;
	private double rate;
	private ProgressEstimator estimator;
	
	public CookingSession(String sid, FoodProfile fp) {
		this.sessionID = sid;
		this.progress = 0.0;
		this.foodProfile = fp;
		this.timeRemaining = "";
		this.estimator = new LinearEstimator();
		this.currentTemp = 0.0;
		this.rate = 0.0;
	}
	
	public String getSessionID() {
		return this.sessionID;
	}
	
	public void startCooking() {
		
	}
	
	public void stopCooking() {
		
	}
	
	public double getProgress() {
		return this.progress;
	}
	
	public String getTimeRemaining() {
		return this.timeRemaining;
	}
	
	public void onTemperatureUpdate(TemperatureReading reading) {
		double temp = reading.getTemperature();
		this.rate = this.estimator.calculateRate(temp, this.currentTemp);
		this.currentTemp = temp;
		
		this.progress = this.estimator.estimatePercent(
				this.currentTemp, 
				this.foodProfile.getTargetTemp()
		);
		
		this.timeRemaining = this.estimator.estimateTimeRemaining(
				this.currentTemp,
				this.foodProfile.getTargetTemp(),
				this.rate
		);
	}
	
	
	
}
