package cookingMonitor;

public interface ProgressEstimator {
	public double estimatePercent(double currentTemp, double targetTemp);
	public String estimateTimeRemaining(double currentTemp, double targetTemp, double rate);
	public double calculateRate(double currentTemp, double prevTemp);
}
