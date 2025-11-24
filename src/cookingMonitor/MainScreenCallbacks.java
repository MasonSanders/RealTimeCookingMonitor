package cookingMonitor;

public interface MainScreenCallbacks {
	void handleStartSession(String deviceID);
	void handleActiveSessionClick(String deviceID);
	void handleFoodPlacedOnGrill();
	void handleFoodSelected(Dish dish);
	void handleSteakDonenessSelected(Doneness doneness);
}
