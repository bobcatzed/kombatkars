package se.kyrkoherden.kombatkars;

import com.googlecode.lanterna.terminal.Terminal.Color;

public interface BoardObject {
	Position getPosition();
	int getWidth();
	int getHeight();
	int getType();
	String getSymbol();
	Color getColor();
}
