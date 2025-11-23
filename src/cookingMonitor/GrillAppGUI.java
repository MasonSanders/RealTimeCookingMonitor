package cookingMonitor;

public class GrillAppGUI {

    private AppController appController;
    private MainWindow mainWindow;

    public GrillAppGUI() {
        SessionController sessionController = new SessionController();
        ThermometerManager thermometerManager = new ThermometerManager();

        this.appController = new AppController(sessionController, thermometerManager);
        this.mainWindow = new MainWindow(appController);
    }

    public void start() {
        mainWindow.showWindow();
    }

    public static void main(String[] args) {
        new GrillAppGUI().start();
    }
}
