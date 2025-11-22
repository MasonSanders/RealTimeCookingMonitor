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
	private double coolRate;
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
	
	private void simulationLoop() throws InterruptedException {
		running = true;
		while (running) {
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
	
	public boolean getConnected() {
		return connected;
	}
}
