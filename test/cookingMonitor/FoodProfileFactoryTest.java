package cookingMonitor;

public class FoodProfileFactoryTest {
	public static void register(TestRunner runner) {
		runner.add("FoodProfileFactory returns chicken profile", FoodProfileFactoryTest::testChickenProfile);
		runner.add("FoodProfileFactory returns hamburger profile", FoodProfileFactoryTest::testHamburgerProfile);
		runner.add("FoodProfileFactory returns pork profile", FoodProfileFactoryTest::testPorkProfile);
		runner.add("FoodProfileFactory returns steak profiles by doneness", FoodProfileFactoryTest::testSteakProfiles);
	}
	
	private static void assertProfile(FoodProfile profile, String expectedName, double expectedTemp) {
		TestSupport.assertEquals(expectedName, profile.getName(), "Profile name mismatch");
		TestSupport.assertEquals(expectedTemp, profile.getTargetTemp(), 0.0001, "Target temperature mismatch");
	}
	
	private static void testChickenProfile() {
		FoodProfile profile = FoodProfileFactory.create(Dish.CHICKEN);
		assertProfile(profile, "Chicken", 74.0);
	}
	
	private static void testHamburgerProfile() {
		FoodProfile profile = FoodProfileFactory.create(Dish.HAMBURGER);
		assertProfile(profile, "Hamburger", 71.0);
	}
	
	private static void testPorkProfile() {
		FoodProfile profile = FoodProfileFactory.create(Dish.PORK);
		assertProfile(profile, "Pork", 63.0);
	}
	
	private static void testSteakProfiles() {
		assertProfile(FoodProfileFactory.createSteakProfile(Doneness.RARE), "Steak", 48.0);
		assertProfile(FoodProfileFactory.createSteakProfile(Doneness.MEDIUM), "Steak", 68.0);
		assertProfile(FoodProfileFactory.createSteakProfile(Doneness.WELL), "Steak", 68.0);
	}
}
