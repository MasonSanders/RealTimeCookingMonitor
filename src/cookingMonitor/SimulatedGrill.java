package cookingMonitor;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SimulatedGrill implements GrillDevice, Runnable {
	private final String deviceId;
	private final List<TemperatureListener> listeners = new ArrayList<>();
	
	private double currentTemp;
	private double ambientTemp;
	private double targetTemp;
	private boolean heating = false;
	private double heatRate = 3.0;
	private double coolRate = 3.0;
	private Thread simulationThread;
	private volatile boolean running = false;
	private boolean connected;
	
	
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
	
	private double calculateNewTemp () {
		double min = -2.0;
		double max = 2.0;
		
		double randomValue = ThreadLocalRandom.current().nextDouble(min, max);
		
		return currentTemp + randomValue;
	}
	
	private void simulationLoop() throws InterruptedException {
		running = true;
		while (running) {
			// add variation to the grill to simulate feedback control.
			
			currentTemp = calculateNewTemp();
			
			if (heating && currentTemp < targetTemp) {
				currentTemp += heatRate;
			} else if (!heating && currentTemp > ambientTemp) {
				currentTemp -= coolRate;
			}
			notifyListeners();
			Thread.sleep(1000);
		}
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
	public void setTargetTemperature(double target) {
		targetTemp = target;
	}
	
	
	@Override
	public void connect() {
		simulationThread = new Thread(this);
		connected = true;
		currentTemp = ambientTemp;
		running = true;
		heating = true;
		simulationThread.start();
	}
	
	@Override
	public void disconnect() {
		running = false;
		heating = false;
		try {
			simulationThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		connected = false;
	}
	
	@Override
	public void setPower(boolean on) {
		if (on) {
			connect();
		} else {
			disconnect();
		}
	}
	
	@Override
	public void setHeating(boolean on) {
		heating = on;	
	}
	
	public boolean getConnected() {
		return connected;
	}
}
