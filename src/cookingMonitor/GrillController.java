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
	}
	
	public double getDesiredTemp() {
		return this.desiredTemp;
	}
	
	
	public void handleGrillTemperature(TemperatureReading reading) {
		
	}
}
