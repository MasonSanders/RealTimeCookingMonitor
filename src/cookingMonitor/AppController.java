package cookingMonitor;

public class AppController {
	private GrillController grillController;
	private GrillDevice grill;
	private TemperatureListener grillListener;
	private SessionController sessionController;
	
	public AppController() {
		sessionController = new SessionController();
		grill = new SimulatedGrill("sim-grill");
		grillController = new GrillController(grill,sessionController);
		grillListener = new GrillTemperatureListener(grillController);
		grill.addListener(grillListener);
	}
	
	public void start() {
		grillController.setDesiredTemp(232.2);
		grillController.preheat();
	}
	
	public void startCooking(String sessionId) {
		
	}
	
	public void stopCooking(String sessionId) {
		
	}
}
