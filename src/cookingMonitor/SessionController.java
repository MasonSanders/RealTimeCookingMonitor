package cookingMonitor;

import java.util.Map;
import java.util.HashMap;

public class SessionController {
	private Map<String, CookingSession> sessions = new HashMap<>();
	
	
	public void addSession(CookingSession session) {
		String deviceID = session.getThermometer().getDeviceID();
		sessions.put(deviceID, session);
	}
	
	public void removeSession(String sessionID) {
		sessions.remove(sessionID);
	}
	
	public void removeAllSessions() {
		sessions.clear();
	}
	
	public void startSession(String deviceID) {
		sessions.get(deviceID).startCooking();
	}
	
	public void stopSession(String deviceID) {
		sessions.get(deviceID).stopCooking();
	}
	
	public CookingSession getSession(String deviceID) {
		return sessions.get(deviceID);
	}
	
	public boolean hasSession(String deviceID) {
		return sessions.containsKey(deviceID);
	}
	
	public void startAllSessions() {
		for (Map.Entry<String, CookingSession> entry : sessions.entrySet()) {
			entry.getValue().startCooking();
		}
	}
	
	public void stopAllSessions() {
		for (Map.Entry<String, CookingSession> entry : sessions.entrySet()) {
			entry.getValue().stopCooking();
		}
	}
	
	public Map<String, CookingSession> getSessions() {
		return sessions;
	}
}
