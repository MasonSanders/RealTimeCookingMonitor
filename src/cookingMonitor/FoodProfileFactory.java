package cookingMonitor;

public class FoodProfileFactory {
	
	public FoodProfile createWellSteakProfile() {
		return new WellSteakProfile();
	}
	
	public FoodProfile createMediumSteakProfile() {
		return new MediumSteakProfile();
	}
	
	public FoodProfile createRareSteakProfile() {
		return new RareSteakProfile();
	}
	
	public FoodProfile createHamburgerProfile() {
		return new HamburgerProfile();
	}
	
	public FoodProfile createChickenProfile() {
		return new ChickenProfile();
	}
	
	public FoodProfile createPorkProfile() {
		return new PorkProfile();
	}
	
}
