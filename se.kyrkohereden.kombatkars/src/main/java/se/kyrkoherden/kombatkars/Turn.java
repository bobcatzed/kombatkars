package se.kyrkoherden.kombatkars;


public class Turn extends Action {
	private final int angle;

	public Turn(int angle) {
		super();
		this.angle = angle;
	}

	public int getAngle() {
		return angle;
	}
		
}
