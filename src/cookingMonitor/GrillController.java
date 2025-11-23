package cookingMonitor;

public class GrillController {
	
	private double desiredTemp;
	private GrillDevice grill;
	private SessionController sessionController;
	private boolean preheated = false;
	
	
	
	public GrillController(GrillDevice grill, SessionController sessionController) {
		this.grill = grill;
		this.sessionController = sessionController;
	}
	
	public void setDesiredTemp(double desiredTemp) {
		this.desiredTemp = desiredTemp;
		grill.setTargetTemperature(this.desiredTemp);
	}
	
	public double getDesiredTemp() {
		return this.desiredTemp;
	}
	
	public void preheat() {
		grill.connect();
	}
	
	public boolean isPreheating() {
		return grill.getHeating();
	}
	
	public void shutdown() {
		grill.disconnect();
	}
	
	public boolean isGrillConnected() {
		return grill.getConnected();
	}
	
	public void handleGrillTemperature(TemperatureReading reading) {
		if (reading.getTemperature() > desiredTemp - 5.0 && reading.getTemperature() < desiredTemp + 5.0) {
			if (!preheated)
			{
				// need to prompt the user to put food on the grill and insert thermometers.
				//...
				sessionController.startAllSessions(); // start all sessions. sessions will not be started again if they are
				preheated = true;
			}
		} else if (reading.getTemperature() <= desiredTemp - 5.0) {
			if (preheated) {
				grill.setHeating(true);
			}
		} else if (reading.getTemperature() >= desiredTemp + 5.0) {
			if (preheated) {
				grill.setHeating(false);
			}
		}
	}
}
