package cookingMonitor;

public class GrillTemperatureListener implements TemperatureListener {
	private GrillController grillController;
	private GrillTemperatureObserver observer;
	
	public GrillTemperatureListener(GrillController gc, GrillTemperatureObserver observer) {
		this.grillController = gc;
		this.observer = observer;
	}
	
	@Override
	public void onTemperatureUpdate(TemperatureReading reading) {
		grillController.handleGrillTemperature(reading);
		observer.onGrillTemperatureUpdate(reading.getTemperature());
	}
}
