package se.kyrkoherden.kombatkars;

public class Rocket extends ObjectState implements BoardObject {

	public Rocket(Position position, Direction direction) {
		super(position, direction);		
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return 1;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return 1;
	}

	public int getType() {
		return 3;
	}

	public String getSymbol() {
		return ".";
	}

}
