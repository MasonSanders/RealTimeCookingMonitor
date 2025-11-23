package cookingMonitor;

import java.util.List;
import java.util.ArrayList;

public class SimulatedThermometer implements ThermometerDevice, Runnable {
	private List<TemperatureListener> listeners = new ArrayList<>();
	private volatile boolean running = false;
	private double currentTemp;
	private static int nextId = 1;
	private boolean connected = false;
	private final String deviceId;
	Thread simulationThread;
	
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
	
	private void simulationLoop() throws InterruptedException {
		running = true;
		while (running) {
			currentTemp = computeNextTemperature();
			notifyListeners();
			Thread.sleep(1000);
		}
		
	}
	
	private double computeNextTemperature() {
		return currentTemp + 0.6;
	}
	
	@Override
	public void run() {
		try {
			simulationLoop();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public void connect() {
		simulationThread = new Thread(this);
		connected = true;
		running = true;
		simulationThread.start();
	}
	
	@Override
	public void disconnect() {
		try {
			simulationThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		running = false;
		connected = false;	
	}
	
	@Override
	public String getDeviceID() {
		return deviceId;
	}
	
	public boolean getConnected() {
		return connected;
	}
	
}
