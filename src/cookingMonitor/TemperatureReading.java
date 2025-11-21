package cookingMonitor;

public class TemperatureReading {
	private final String deviceId;
	private final double temperature;
	
	public TemperatureReading(String deviceId, double temperature) {
		this.deviceId = deviceId;
		this.temperature = temperature;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public double getTemperature() {
		return this.temperature;
	}
}
