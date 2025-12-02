package cookingMonitor;

public class SessionControllerTest {
	public static void register(TestRunner runner) {
		runner.add("SessionController starts and stops a session", SessionControllerTest::testStartAndStopSession);
		runner.add("SessionController reports presence of sessions", SessionControllerTest::testSessionPresence);
	}
	
	private static CookingSession newSession(FakeThermometer thermometer) {
		return new CookingSession(new ChickenProfile(), thermometer);
	}
	
	private static void testStartAndStopSession() {
		SessionController controller = new SessionController();
		FakeThermometer thermometer = new FakeThermometer("thermo-1");
		CookingSession session = newSession(thermometer);
		
		controller.addSession(session);
		controller.startSession(thermometer.getDeviceID());
		
		TestSupport.assertTrue(thermometer.isConnected(), "Thermometer should connect when session starts");
		TestSupport.assertEquals(1, thermometer.listenerCount(), "CookingSession should add itself as listener");
		
		controller.stopSession(thermometer.getDeviceID());
		TestSupport.assertFalse(thermometer.isConnected(), "Thermometer should disconnect when session stops");
		TestSupport.assertFalse(controller.hasSession(thermometer.getDeviceID()), "Session should be removed when stopped");
	}
	
	private static void testSessionPresence() {
		SessionController controller = new SessionController();
		FakeThermometer thermometer = new FakeThermometer("thermo-2");
		CookingSession session = newSession(thermometer);
		
		TestSupport.assertFalse(controller.hasSession(thermometer.getDeviceID()), "Should not have session before adding");
		
		controller.addSession(session);
		TestSupport.assertTrue(controller.hasSession(thermometer.getDeviceID()), "Session should be reported after adding");
		TestSupport.assertEquals(session, controller.getSession(thermometer.getDeviceID()), "Retrieved session should match added instance");
		
		controller.removeSession(thermometer.getDeviceID());
		TestSupport.assertFalse(controller.hasSession(thermometer.getDeviceID()), "Session should be removable");
	}
}
