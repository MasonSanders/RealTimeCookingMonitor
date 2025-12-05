package cookingMonitor;

import java.util.ArrayList;
import java.util.List;

class FakeThermometer implements ThermometerDevice {
	private final String deviceId;
	private final List<TemperatureListener> listeners = new ArrayList<>();
	private boolean connected;
	
	FakeThermometer(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void emit(double temperature) {
		TemperatureReading reading = new TemperatureReading(deviceId, temperature);
		for (TemperatureListener listener : listeners) {
			listener.onTemperatureUpdate(reading);
		}
	}
	
	public int listenerCount() {
		return listeners.size();
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	@Override
	public void connect() {
		connected = true;
	}

	@Override
	public void disconnect() {
		connected = false;
	}

	@Override
	public void addListener(TemperatureListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(TemperatureListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String getDeviceID() {
		return deviceId;
	}
}

class FakeGrill implements GrillDevice {
	private final List<TemperatureListener> listeners = new ArrayList<>();
	private boolean connected;
	private boolean heating;
	private double targetTemp;
	
	public void emit(double temperature) {
		TemperatureReading reading = new TemperatureReading("fake-grill", temperature);
		for (TemperatureListener listener : listeners) {
			listener.onTemperatureUpdate(reading);
		}
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public boolean isHeating() {
		return heating;
	}
	
	public double getTargetTemp() {
		return targetTemp;
	}
	
	@Override
	public void connect() {
		connected = true;
		heating = true;
	}

	@Override
	public void disconnect() {
		connected = false;
		heating = false;
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

	@Override
	public boolean getHeating() {
		return heating;
	}

	@Override
	public boolean getConnected() {
		return connected;
	}

	@Override
	public void setTargetTemperature(double temp) {
		targetTemp = temp;
	}

	@Override
	public void addListener(TemperatureListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(TemperatureListener listener) {
		listeners.remove(listener);
	}
}

class TrackingSessionController extends SessionController {
	private int startAllCount;
	
	@Override
	public void startAllSessions() {
		startAllCount++;
	}
	
	public boolean wasStartAllCalled() {
		return startAllCount > 0;
	}
	
	public int getStartAllCount() {
		return startAllCount;
	}
}
