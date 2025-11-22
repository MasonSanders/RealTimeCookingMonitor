package cookingMonitor;

import java.util.Map;
import java.util.HashMap;

public class SessionController {
	private Map<String, CookingSession> sessions = new HashMap<>();
	
	
	public void addSession(CookingSession session) {
		sessions.put(session.getSessionID(), session);
	}
	
	public void removeSession(String sessionID) {
		sessions.remove(sessionID);
	}
	
	public void removeAllSessions() {
		sessions.clear();
	}
	
	public void startSession(String sessionID) {
		sessions.get(sessionID).startCooking();
	}
	
	public void stopSession(String sessionID) {
		sessions.get(sessionID).stopCooking();
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
