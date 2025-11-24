package cookingMonitor;

public class CookingSession implements TemperatureListener {
	private String sessionID;
	private double progress;
	private String timeRemaining;
	private FoodProfile foodProfile;
	private double currentTemp;
	private double rate;
	public static int nextSID = 1;
	private ProgressEstimator estimator;
	private ThermometerDevice thermometer;
	private boolean done = false;
	
	public CookingSession(FoodProfile fp, ThermometerDevice thermometer) {
		this.sessionID = "session-" + nextSID++;
		this.progress = 0.0;
		this.foodProfile = fp;
		this.timeRemaining = "";
		this.estimator = new LinearEstimator();
		this.currentTemp = 0.0;
		this.rate = 0.0;
		this.thermometer = thermometer;
		
	}
	
	public String getSessionID() {
		return this.sessionID;
	}
	
	public void startCooking() {
		thermometer.addListener(this);
	}
	
	public void stopCooking() {
		thermometer.removeListener(this);
		this.progress = 0.0;
		this.currentTemp = 0.0;
	}
	
	public double getProgress() {
		return this.progress;
	}
	
	public String getTimeRemaining() {
		return this.timeRemaining;
	}
	
	public ThermometerDevice getThermometer() {
		return thermometer;
	}
	
	public boolean isDone() {
		return done;
	}
	
	
	
	public void onTemperatureUpdate(TemperatureReading reading) {
		double temp = reading.getTemperature();
		double newrate = this.estimator.calculateRate(temp, this.currentTemp);
		
		if (newrate != 0) {
			this.rate = newrate;
		}
		
		this.currentTemp = temp;
		
		this.progress = this.estimator.estimatePercent(
				this.currentTemp, 
				this.foodProfile.getTargetTemp()
		);
		
		if (this.progress >= 1.0) {
			this.progress = 1.0;
			this.done = true;
		}
		
		this.timeRemaining = this.estimator.estimateTimeRemaining(
				this.currentTemp,
				this.foodProfile.getTargetTemp(),
				this.rate
		);
	}
}
