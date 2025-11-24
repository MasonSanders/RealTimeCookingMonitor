package cookingMonitor;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.util.ArrayList;

public class AppController implements GrillTemperatureObserver, MainScreenCallbacks {
	private final MainWindow mainWindow;
	private final MainScreen mainScreen;
	private final FoodSelectionScreen foodSelectionScreen;
	private final PreheatScreen preheatScreen;
	private final SteakDonenessScreen steakDonenessScreen;
	
	private GrillController grillController;
	private GrillDevice grill;
	private TemperatureListener grillListener;
	private SessionController sessionController;
	private ThermometerManager thermometerManager;
	private String pendingThermometerID;
	private boolean grillPreheatedOnce = false;
	private String pendingSessionID;
	
	private Timer uiTimer;
	
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
		steakDonenessScreen = new SteakDonenessScreen(this);
		
		mainWindow.addScreen(MainWindow.SCREEN_MAIN, mainScreen);
		mainWindow.addScreen(MainWindow.SCREEN_FOOD_SELECTION, foodSelectionScreen);
		mainWindow.addScreen(MainWindow.SCREEN_PREHEAT, preheatScreen);
		mainWindow.addScreen(MainWindow.SCREEN_DONENESS, steakDonenessScreen);
		mainWindow.setVisible(true);
		uiTimer = new Timer(1000, e -> refreshMainScreen());
		
		refreshMainScreen();
		mainWindow.showScreen(MainWindow.SCREEN_MAIN);
		uiTimer.start();
	}
	
	public void refreshMainScreen() {
		List<ThermometerViewModel> models = buildThermometerViewModels();
		mainScreen.updateThermometers(models);
	}
	
	public List<ThermometerViewModel> buildThermometerViewModels() {
		List<ThermometerViewModel> list = new ArrayList<>();
		
		boolean grillPreheating = !grillPreheatedOnce && grillController.isPreheating();
		for (ThermometerDevice device : thermometerManager.getAllThermometers()) {
			ThermometerViewModel vm = new ThermometerViewModel();
			vm.deviceID = device.getDeviceID();
			vm.label = device.getDeviceID();
			
			if (sessionController.hasSession(device.getDeviceID())) {
				CookingSession session = sessionController.getSession(device.getDeviceID());
				vm.progress = session.getProgress();
				
				if (session.isDone()) {
					vm.status = ThermometerStatus.DONE;
				} else {
					vm.status = ThermometerStatus.COOKING;
				}
				vm.timeRemaining = session.getTimeRemaining();
			} else if (grillPreheating) {
				vm.status = ThermometerStatus.PREHEATING;
				vm.progress = 0.0;
				vm.timeRemaining = "";
			} else {
				vm.status = ThermometerStatus.AVAILABLE;
				vm.progress = 0.0;
				vm.timeRemaining = "";
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
		ThermometerDevice thermo = thermometerManager.getThermometer(pendingThermometerID);
		CookingSession session = new CookingSession(profile, thermo);
		
		sessionController.addSession(session);
		
		String deviceID = pendingThermometerID;
		
		if (!grillPreheatedOnce) {
			grillController.setDesiredTemp(232.2);
			
			grillController.preheat();
			
			pendingSessionID = deviceID;
			preheatScreen.setTargetTemperature(grillController.getDesiredTemp());
			mainWindow.showScreen(MainWindow.SCREEN_PREHEAT);
		} else {
			startCooking(deviceID);
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
		
		//mainWindow.showScreen(MainWindow.SCREEN_MAIN);
		//refreshMainScreen();
	}
	
	@Override
	public void handleSteakDonenessSelected(Doneness doneness) {
		FoodProfile profile = FoodProfileFactory.createSteakProfile(doneness);
		
		ThermometerDevice thermo = thermometerManager.getThermometer(pendingThermometerID);
		
		CookingSession session = new CookingSession(profile, thermo);
		
		sessionController.addSession(session);
		
		String deviceID = pendingThermometerID;
		
		if (!grillPreheatedOnce) {
			double target = 232.2;
			grillController.setDesiredTemp(target);
			grillController.preheat();
			pendingSessionID = deviceID;
			preheatScreen.setTargetTemperature(target);
			mainWindow.showScreen(MainWindow.SCREEN_PREHEAT);
		} else {
			startCooking(deviceID);
			mainWindow.showScreen(MainWindow.SCREEN_MAIN);
			refreshMainScreen();
		}
		
		
	}
}
