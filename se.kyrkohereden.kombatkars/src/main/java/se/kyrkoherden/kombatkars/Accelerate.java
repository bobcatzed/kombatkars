package se.kyrkoherden.kombatkars;

public class Accelerate extends Action {
	private final int value;

	public Accelerate(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
