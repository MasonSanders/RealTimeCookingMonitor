package cookingMonitor;

public class GrillControllerTest {
	public static void register(TestRunner runner) {
		runner.add("GrillController sets grill target temperature and preheats", GrillControllerTest::testSetDesiredTemperatureAndPreheat);
		runner.add("GrillController handles temperature updates and toggles heating", GrillControllerTest::testHandleTemperatureUpdates);
	}
	
	private static void testSetDesiredTemperatureAndPreheat() {
		FakeGrill grill = new FakeGrill();
		TrackingSessionController sessionController = new TrackingSessionController();
		GrillController controller = new GrillController(grill, sessionController);
		
		controller.setDesiredTemp(200.0);
		TestSupport.assertEquals(200.0, grill.getTargetTemp(), 0.0001, "Desired temperature should be forwarded to grill");
		
		controller.preheat();
		TestSupport.assertTrue(grill.isConnected(), "Preheat should connect grill");
		TestSupport.assertTrue(controller.isPreheating(), "Controller should report preheating when grill heating");
	}
	
	private static void testHandleTemperatureUpdates() {
		FakeGrill grill = new FakeGrill();
		TrackingSessionController sessionController = new TrackingSessionController();
		GrillController controller = new GrillController(grill, sessionController);
		
		controller.setDesiredTemp(180.0);
		
		controller.handleGrillTemperature(new TemperatureReading("fake-grill", 178.0));
		TestSupport.assertTrue(sessionController.wasStartAllCalled(), "Sessions should start when grill reaches target band");
		TestSupport.assertEquals(1, sessionController.getStartAllCount(), "startAllSessions should only fire once when entering band");
		
		controller.handleGrillTemperature(new TemperatureReading("fake-grill", 160.0));
		TestSupport.assertTrue(grill.isHeating(), "Heating should turn on when below tolerance after preheat");
		
		controller.handleGrillTemperature(new TemperatureReading("fake-grill", 190.0));
		TestSupport.assertFalse(grill.isHeating(), "Heating should turn off when above tolerance");
		
		controller.handleGrillTemperature(new TemperatureReading("fake-grill", 182.0));
		TestSupport.assertEquals(1, sessionController.getStartAllCount(), "startAllSessions should not fire again after already preheated");
	}
}
