package cookingMonitor;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
	private static class TestCase {
		private final String name;
		private final Runnable test;
		
		TestCase(String name, Runnable test) {
			this.name = name;
			this.test = test;
		}
	}
	
	private final List<TestCase> cases = new ArrayList<>();
	
	public void add(String name, Runnable test) {
		cases.add(new TestCase(name, test));
	}
	
	public int runAll() {
		int failures = 0;
		for (TestCase c : cases) {
			try {
				c.test.run();
				System.out.println("[PASS] " + c.name);
			} catch (Throwable t) {
				failures++;
				System.out.println("[FAIL] " + c.name + " -> " + t.getMessage());
				t.printStackTrace(System.out);
			}
		}
		System.out.println("Ran " + cases.size() + " tests: " + (cases.size() - failures) + " passed, " + failures + " failed.");
		return failures;
	}
	
	public static void main(String[] args) {
		TestRunner runner = new TestRunner();
		
		FoodProfileFactoryTest.register(runner);
		LinearEstimatorTest.register(runner);
		CookingSessionTest.register(runner);
		SessionControllerTest.register(runner);
		ThermometerManagerTest.register(runner);
		GrillControllerTest.register(runner);
		
		int failures = runner.runAll();
		if (failures > 0) {
			System.exit(1);
		}
	}
}
