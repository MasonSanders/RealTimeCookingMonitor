package cookingMonitor;

public class LinearEstimatorTest {
	public static void register(TestRunner runner) {
		runner.add("LinearEstimator estimates percent to target", LinearEstimatorTest::testEstimatePercent);
		runner.add("LinearEstimator estimates time remaining with rate", LinearEstimatorTest::testEstimateTimeRemaining);
		runner.add("LinearEstimator handles zero or negative rate", LinearEstimatorTest::testEstimateTimeRemainingZeroRate);
		runner.add("LinearEstimator calculates temperature rate", LinearEstimatorTest::testCalculateRate);
	}
	
	private static void testEstimatePercent() {
		LinearEstimator estimator = new LinearEstimator();
		TestSupport.assertEquals(0.25, estimator.estimatePercent(25.0, 100.0), 0.0001, "Progress percent incorrect");
		TestSupport.assertEquals(1.5, estimator.estimatePercent(150.0, 100.0), 0.0001, "Progress percent can exceed 1.0 when over target");
	}
	
	private static void testEstimateTimeRemaining() {
		LinearEstimator estimator = new LinearEstimator();
		String remaining = estimator.estimateTimeRemaining(50.0, 100.0, 10.0);
		TestSupport.assertEquals("0:05", remaining, "Time remaining not formatted correctly");
		
		String alreadyDone = estimator.estimateTimeRemaining(120.0, 100.0, 8.0);
		TestSupport.assertEquals("0:00", alreadyDone, "Should show zero when already at or above target");
	}
	
	private static void testEstimateTimeRemainingZeroRate() {
		LinearEstimator estimator = new LinearEstimator();
		String noRate = estimator.estimateTimeRemaining(40.0, 100.0, 0.0);
		TestSupport.assertEquals("0:00", noRate, "Zero rate should show zero time remaining");
		
		String tinyRate = estimator.estimateTimeRemaining(40.0, 100.0, 0.0000001);
		TestSupport.assertEquals("0:00", tinyRate, "Tiny rate should be treated as zero");
	}
	
	private static void testCalculateRate() {
		LinearEstimator estimator = new LinearEstimator();
		TestSupport.assertEquals(5.0, estimator.calculateRate(25.0, 20.0), 0.0001, "Rate calculation incorrect");
		TestSupport.assertEquals(-3.0, estimator.calculateRate(17.0, 20.0), 0.0001, "Rate should allow cooling");
	}
}
