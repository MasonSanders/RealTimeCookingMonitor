package cookingMonitor;

public class LinearEstimator implements ProgressEstimator {
	// return the percentage as a decimal, not as actual percentage.
	public double estimatePercent(double currentTemp, double targetTemp) {
		return currentTemp / targetTemp;
	}
	
	// return the time remaining as a string that can be easily displayed on a GUI
	public String estimateTimeRemaining(double currentTemp, double targetTemp, double rate) {
	    
	    if (currentTemp >= targetTemp) {
	        return "0:00";
	    }

	   
	    if (rate <= 0.000001) {
	        return "0:00";
	    }

	   
	    double remaining = (targetTemp - currentTemp) / rate;

	    if (remaining < 0) {
	    	remaining = 0;
	    }
	    
	    int totalSeconds = (int) Math.ceil(remaining);
	    int minutes = totalSeconds / 60;
	    int seconds = totalSeconds % 60;

	    return String.format("%d:%02d", minutes, seconds);
	}

	
	// meant to be calculated once per second 
	public double calculateRate(double currentTemp, double prevTemp) {
		return currentTemp - prevTemp;
	}
}
