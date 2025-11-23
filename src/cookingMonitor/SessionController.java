package cookingMonitor;

import java.util.HashMap;
import java.util.Map;

public class SessionController {

    private Map<String, CookingSession> sessions;

    public SessionController() {
        this.sessions = new HashMap<>();
    }

    public String createSession(ThermometerDevice device, FoodProfile profile) {

        String sessionId = "session-" + (sessions.size() + 1);

        CookingSession session = new CookingSession(sessionId, profile);

        device.addListener(session);

        sessions.put(sessionId, session);

        return sessionId;
    }

    public void startSession(String sessionId) {
        CookingSession session = sessions.get(sessionId);
        if (session != null) {
            session.startCooking();
        }
    }

    public void stopSession(String sessionId) {
        CookingSession session = sessions.get(sessionId);
        if (session != null) {
            session.stopCooking();
        }
    }

    public Map<String, CookingSession> getSessions() {
        return this.sessions;
    }
}
