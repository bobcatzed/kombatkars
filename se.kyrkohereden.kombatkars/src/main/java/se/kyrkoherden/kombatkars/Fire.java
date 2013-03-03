package se.kyrkoherden.kombatkars;

public class Fire extends Action {
	private final int direction;
	private final int weapon;
	
	public Fire(int direction, int weapon) {
		super();
		this.direction = direction;
		this.weapon = weapon;
	}
	public int getDirection() {
		return direction;
	}
	public int getWeapon() {
		return weapon;
	}
	
	
}
