package cookingMonitor;

public class GrillTemperatureListener implements TemperatureListener {
	private GrillController grillController;
	
	public GrillTemperatureListener(GrillController gc) {
		this.grillController = gc;
	}
	
	@Override
	public void onTemperatureUpdate(TemperatureReading reading) {
		grillController.handleGrillTemperature(reading);
	}
}
