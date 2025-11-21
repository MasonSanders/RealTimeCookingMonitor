package cookingMonitor;
import java.util.List;
import java.util.ArrayList;

public class SimulatedGrill implements GrillDevice, Runnable {
	private final String deviceId;
	private final List<TemperatureListener> listeners = new ArrayList<>();
	
	private double currentTemp;
	private double ambientTemp;
	private double targetTemp;
	private boolean heating;
	private double heatRate;
	private double rampRate;
	private double coolRate;
	
	public SimulatedGrill(String deviceId) {
		this.deviceId = deviceId;
		this.ambientTemp = 25.0;
		this.currentTemp = ambientTemp;
	}
	
	public void addListener(TemperatureListener l) {
		listeners.add(l);
	}
	
	public void removeListener(TemperatureListener l) {
		listeners.remove(l);
	}
	
	private void notifyListeners() {
		TemperatureReading reading = new TemperatureReading(deviceId, currentTemp);
		for (TemperatureListener l : listeners) {
			l.onTemperatureUpdate(reading);
		}
	}
	
	@Override
	public void run() {
		
	}
	
	@Override
	public void setTargetTemperature(double target) {
		targetTemp = target;
	}
	
	@Override
	public void connect() {}
	
	@Override
	public void disconnect() {}
	
	@Override
	public void setPower(boolean on) {}
	
	
	
}
