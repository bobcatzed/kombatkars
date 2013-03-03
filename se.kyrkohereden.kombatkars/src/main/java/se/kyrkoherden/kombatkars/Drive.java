package se.kyrkoherden.kombatkars;

public class Drive extends Action {
	private final int distance;

	public Drive(int distance) {
		super();
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}
	
}
