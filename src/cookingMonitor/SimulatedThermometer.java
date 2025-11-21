package cookingMonitor;

import java.util.List;
import java.util.ArrayList;

public class SimulatedThermometer implements ThermometerDevice, Runnable {
	private List<TemperatureListener> listeners = new ArrayList<>();
	private volatile boolean running;
	private double currentTemp;
	private static int nextId = 1;
	private final String deviceId;
	
	public SimulatedThermometer() {
		this.deviceId = "thermo-" + nextId++;
	}
	
	@Override
	public void addListener(TemperatureListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeListener(TemperatureListener l) {
		listeners.remove(l);
	}
	
	private void notifyListeners() {
		TemperatureReading reading = new TemperatureReading(this.deviceId, currentTemp);
		for (TemperatureListener l : listeners) {
			l.onTemperatureUpdate(reading);
		}
	}
	
	
	@Override
	public void run() {
		
	}
	
	@Override
	public void connect() {}
	
	@Override
	public void disconnect() {}
	
	
}
