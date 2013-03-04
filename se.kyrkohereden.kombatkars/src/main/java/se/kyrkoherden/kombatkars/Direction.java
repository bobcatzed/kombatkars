package se.kyrkoherden.kombatkars;

public enum Direction {
	N(0, 'k'),
	NE(1, 'i'),
	E(2, 'l'),
	SE(3, 'm'),
	S(4, 'j'),
	SW(5, 'n'),
	W(6, 'h'),
	NW(7, 'u');
	private static final int NUMBER_OF_DIRECTIONS = 8;
	private final int value;
	private final char key;
	Direction(int value, char key) {
		this.value = value;
		this.key = key;
	}
	public char getKey() {
		return key;
	}
	public int getValue() {
		return value;
	}
	public Direction turn(int angle) {
		int newDir = (NUMBER_OF_DIRECTIONS + value + angle) & 0x07;
		return DirectionMap.getDirection(newDir);
	}
	
}