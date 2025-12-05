package cookingMonitor;

public class CookingSessionTest {
	public static void register(TestRunner runner) {
		runner.add("CookingSession updates progress and completion", CookingSessionTest::testProgressAndCompletion);
		runner.add("CookingSession start/stop manages thermometer listener", CookingSessionTest::testStartStopLifecycle);
	}
	
	private static void testProgressAndCompletion() {
		FakeThermometer thermometer = new FakeThermometer("thermo-test");
		CookingSession session = new CookingSession(new ChickenProfile(), thermometer);
		
		session.startCooking();
		thermometer.emit(10.0);
		
		TestSupport.assertEquals(1, thermometer.listenerCount(), "Listener not registered on start");
		TestSupport.assertEquals(10.0 / 74.0, session.getProgress(), 0.0001, "Progress should reflect first reading");
		TestSupport.assertEquals("0:07", session.getTimeRemaining(), "Time remaining should use calculated rate");
		TestSupport.assertFalse(session.isDone(), "Session should not be complete after first reading");
		
		thermometer.emit(74.0);
		TestSupport.assertEquals(1.0, session.getProgress(), 0.0001, "Progress should clamp at 1.0 when target reached");
		TestSupport.assertTrue(session.isDone(), "Session should be marked done when reaching target temp");
		TestSupport.assertEquals("0:00", session.getTimeRemaining(), "Time remaining should be zero once done");
	}
	
	private static void testStartStopLifecycle() {
		FakeThermometer thermometer = new FakeThermometer("thermo-stop");
		CookingSession session = new CookingSession(new HamburgerProfile(), thermometer);
		
		session.startCooking();
		TestSupport.assertEquals(1, thermometer.listenerCount(), "Listener should be added on start");
		TestSupport.assertFalse(thermometer.isConnected(), "Thermometer connect is handled by controller, not session");
		
		session.stopCooking();
		TestSupport.assertEquals(0, thermometer.listenerCount(), "Listener should be removed on stop");
		TestSupport.assertEquals(0.0, session.getProgress(), 0.0001, "Progress should reset on stop");
		TestSupport.assertFalse(session.isDone(), "Stopping should not mark session done");
	}
}
