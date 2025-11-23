package cookingMonitor;

import java.util.List;

import javax.swing.JOptionPane;

import java.util.ArrayList;

public class AppController implements GrillTemperatureObserver, MainScreenCallbacks {
	private final MainWindow mainWindow;
	private final MainScreen mainScreen;
	private final FoodSelectionScreen foodSelectionScreen;
	private final PreheatScreen preheatScreen;
	
	private GrillController grillController;
	private GrillDevice grill;
	private TemperatureListener grillListener;
	private SessionController sessionController;
	private ThermometerManager thermometerManager;
	private String pendingThermometerID;
	private boolean grillPreheatedOnce = false;
	private String pendingSessionID;
	
	public AppController() {
		sessionController = new SessionController();
		grill = new SimulatedGrill("sim-grill");
		grillController = new GrillController(grill,sessionController);
		grillListener = new GrillTemperatureListener(grillController, this);
		grill.addListener(grillListener);
		thermometerManager = new ThermometerManager();
		
		mainWindow = new MainWindow();
		mainScreen = new MainScreen(this);
		foodSelectionScreen = new FoodSelectionScreen(this);
		preheatScreen = new PreheatScreen(this);
		
		mainWindow.addScreen(MainWindow.SCREEN_MAIN, mainScreen);
		mainWindow.addScreen(MainWindow.SCREEN_FOOD_SELECTION, foodSelectionScreen);
		mainWindow.addScreen(MainWindow.SCREEN_PREHEAT, preheatScreen);
		mainWindow.setVisible(true);
		refreshMainScreen();
		mainWindow.showScreen(MainWindow.SCREEN_MAIN);
	}
	
	public void refreshMainScreen() {
		List<ThermometerViewModel> models = buildThermometerViewModels();
		mainScreen.updateThermometers(models);
	}
	
	public List<ThermometerViewModel> buildThermometerViewModels() {
		List<ThermometerViewModel> list = new ArrayList<>();
		
		boolean grillPreheating = grillController.isPreheating();
		for (ThermometerDevice device : thermometerManager.getAllThermometers()) {
			ThermometerViewModel vm = new ThermometerViewModel();
			vm.deviceID = device.getDeviceID();
			
			if (sessionController.hasSession(device.getDeviceID())) {
				CookingSession session = sessionController.getSession(device.getDeviceID());
				vm.status = ThermometerStatus.COOKING;
				vm.progress = session.getProgress();
			} else if (grillPreheating) {
				vm.status = ThermometerStatus.PREHEATING;
				vm.progress = 0.0;
			} else {
				vm.status = ThermometerStatus.AVAILABLE;
				vm.progress = 0.0;
			}
			
			list.add(vm);
		}
		
		return list;
	}
	
	public void start() {
		//grillController.setDesiredTemp(232.2);
		grillController.preheat();
	}
	
	public void handleStartSession(String deviceID) {
		this.pendingThermometerID = deviceID;
		mainWindow.showScreen(MainWindow.SCREEN_FOOD_SELECTION);
	}
	
	public void handleFoodSelected(Dish dish) {
		if (dish == Dish.STEAK) {
			mainWindow.showScreen(MainWindow.SCREEN_DONENESS);
			return;
		}
		
		FoodProfile profile = FoodProfileFactory.create(dish);
		sessionController.addSession(
				new CookingSession(profile, thermometerManager.getThermometer(pendingThermometerID))
			);
		
		String sessionID = sessionController.getSession(
				thermometerManager.getThermometer(
						pendingThermometerID
						).getDeviceID()).getSessionID();
		
		if (!grillPreheatedOnce) {
			grillController.setDesiredTemp(232.2);
			
			grillController.preheat();
			
			pendingSessionID = sessionID;
			preheatScreen.setTargetTemperature(grillController.getDesiredTemp());
			mainWindow.showScreen(MainWindow.SCREEN_PREHEAT);
		} else {
			startCooking(sessionID);
		}
			
	}
	
	public void startCooking(String sessionId) {
		sessionController.startSession(sessionId);
		refreshMainScreen();
		mainWindow.showScreen(MainWindow.SCREEN_MAIN);
	}
	
	public void stopCooking(String sessionId) {
		sessionController.stopSession(sessionId);
		refreshMainScreen();
	}
	
	@Override
	public void onGrillTemperatureUpdate(double temp) {
		preheatScreen.updateTemperature(temp);
		
		if (!grillPreheatedOnce) {
			double target = grillController.getDesiredTemp();
			if (temp > target - 5.0 && temp < target + 5.0) {
	            preheatScreen.showReadyPrompt();
	        }
		}
	}

	@Override
	public void handleActiveSessionClick(String deviceID) {
		int choice = JOptionPane.showConfirmDialog(
				mainWindow,
				"End the cooking session for the thermometer " + deviceID + "?",
				"End Session",
				JOptionPane.YES_NO_OPTION
		);
		
		if (choice == JOptionPane.YES_OPTION) {
			stopCooking(deviceID);
		}
	}

	@Override
	public void handleFoodPlacedOnGrill() {
		grillPreheatedOnce = true;
		if (pendingSessionID != null) {
			startCooking(pendingSessionID);
			pendingSessionID = null;
		}
		
		mainWindow.showScreen(MainWindow.SCREEN_MAIN);
		refreshMainScreen();
	}
}
