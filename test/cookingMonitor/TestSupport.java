package cookingMonitor;

public final class TestSupport {
	public static void assertEquals(double expected, double actual, double delta, String message) {
		if (Double.isNaN(expected) || Double.isNaN(actual)) {
			throw new AssertionError(message + " expected:<" + expected + "> but was:<" + actual + ">");
		}
		
		if (Math.abs(expected - actual) > delta) {
			throw new AssertionError(message + " expected:<" + expected + "> but was:<" + actual + ">");
		}
	}
	
	public static void assertEquals(Object expected, Object actual, String message) {
		if (expected == null && actual == null) {
			return;
		}
		
		if (expected != null && expected.equals(actual)) {
			return;
		}
		
		throw new AssertionError(message + " expected:<" + expected + "> but was:<" + actual + ">");
	}
	
	public static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
	
	public static void assertFalse(boolean condition, String message) {
		assertTrue(!condition, message);
	}
}
