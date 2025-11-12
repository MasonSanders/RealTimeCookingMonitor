package cookingMonitor;

public class LinearEstimator implements ProgressEstimator {
	// return the percentage as a decimal, not as actual percentage.
	public double estimatePercent(double currentTemp, double targetTemp) {
		return currentTemp / targetTemp;
	}
	
	// return the time remaining as a string that can be easily displayed on a GUI
	public String estimateTimeRemaining(double currentTemp, double targetTemp, double rate) {
		// this will give the time in seconds.
		int seconds = (int) Math.ceil((targetTemp - currentTemp) / rate);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		
		// construct string in the format minutes:seconds
		String result = Integer.toString(minutes);
		result = result + ":";
		if (seconds < 10)
			result = result + "0" + Integer.toString(seconds);
		else
			result = result + Integer.toString(seconds);
		
		return result;
	}
	
	// meant to be calculated once per second 
	public double calculateRate(double currentTemp, double prevTemp) {
		return currentTemp - prevTemp;
	}
}
