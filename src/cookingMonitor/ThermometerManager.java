package cookingMonitor;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class ThermometerManager {
	private Map<String, ThermometerDevice> devices = new HashMap<>();
	
	public ThermometerManager() {
		addThermometer(new SimulatedThermometer());
		addThermometer(new SimulatedThermometer());
		addThermometer(new SimulatedThermometer());
		addThermometer(new SimulatedThermometer());
	}
	
	public void addThermometer(ThermometerDevice device) {
		devices.put(device.getDeviceID(), device);
	} 
	
	public ThermometerDevice getThermometer(String deviceID) {
		return devices.get(deviceID);
	}
	
	public List<ThermometerDevice> getAllThermometers() {
		return new ArrayList<>(devices.values());
	}
}
