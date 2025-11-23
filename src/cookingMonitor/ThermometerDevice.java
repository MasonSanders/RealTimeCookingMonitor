package cookingMonitor;

public interface ThermometerDevice {
	public void connect();
	public void disconnect();
	public void addListener(TemperatureListener listener);
	public void removeListener(TemperatureListener listener);
	public String getDeviceID();
}
