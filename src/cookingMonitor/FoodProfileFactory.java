package cookingMonitor;

public class FoodProfileFactory {
	
	public static FoodProfile create(Dish dish) {
		switch(dish) {
		case CHICKEN:
			return createChickenProfile();
		case HAMBURGER:
			return createHamburgerProfile();
		case PORK:
			return createPorkProfile();
		default:
			System.out.println("Food Profile Factory cannot discern steak profile");
			System.exit(1);
			break;
		}
		return createChickenProfile();
	}
	
	public static FoodProfile createWellSteakProfile() {
		return new WellSteakProfile();
	}
	
	public static FoodProfile createMediumSteakProfile() {
		return new MediumSteakProfile();
	}
	
	public static FoodProfile createRareSteakProfile() {
		return new RareSteakProfile();
	}
	
	public static FoodProfile createHamburgerProfile() {
		return new HamburgerProfile();
	}
	
	public static FoodProfile createChickenProfile() {
		return new ChickenProfile();
	}
	
	public static FoodProfile createPorkProfile() {
		return new PorkProfile();
	}
	
}
