package cookingMonitor;

public interface GrillDevice {
	public void connect();
	public void disconnect();
	public void setPower(boolean on);
	public void setHeating(boolean on);
	public void setTargetTemperature(double temp);
	public void addListener(TemperatureListener l);
	public void removeListener(TemperatureListener l);
}
