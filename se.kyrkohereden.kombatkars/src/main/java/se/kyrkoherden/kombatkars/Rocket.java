package se.kyrkoherden.kombatkars;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class Rocket extends ObjectState implements BoardObject {

	public Rocket(Position position, Direction direction) {
		super(position, direction);		
	}

	public int getWidth() {
		return 1;
	}

	public int getHeight() {
		return 1;
	}

	public int getType() {
		return 3;
	}

	public String getSymbol() {
		return ".";
	}

	public Color getColor() {
		return Color.WHITE;
	}

}
