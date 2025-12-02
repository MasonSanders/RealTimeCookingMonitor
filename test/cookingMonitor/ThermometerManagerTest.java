package cookingMonitor;

public class ThermometerManagerTest {
	public static void register(TestRunner runner) {
		runner.add("ThermometerManager seeds four simulated thermometers", ThermometerManagerTest::testDefaultThermometers);
		runner.add("ThermometerManager stores and retrieves devices by id", ThermometerManagerTest::testAddAndRetrieveThermometer);
	}
	
	private static void testDefaultThermometers() {
		ThermometerManager manager = new ThermometerManager();
		TestSupport.assertEquals(4, manager.getAllThermometers().size(), "Manager should create four default thermometers");
	}
	
	private static void testAddAndRetrieveThermometer() {
		ThermometerManager manager = new ThermometerManager();
		FakeThermometer thermometer = new FakeThermometer("custom-thermo");
		manager.addThermometer(thermometer);
		
		TestSupport.assertEquals(thermometer, manager.getThermometer("custom-thermo"), "Should retrieve same thermometer instance by id");
		TestSupport.assertTrue(manager.getAllThermometers().contains(thermometer), "getAllThermometers should include added device");
	}
}
