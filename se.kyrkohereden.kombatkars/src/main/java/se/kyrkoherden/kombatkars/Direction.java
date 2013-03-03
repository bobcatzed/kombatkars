package se.kyrkoherden.kombatkars;

public enum Direction {
	N(0),
	NE(1),
	E(2),
	SE(3),
	S(4),
	SW(5),
	W(6),
	NW(7);
	private static final int NUMBER_OF_DIRECTIONS = 8;
	private final int value;
	Direction(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public Direction turn(int angle) {
		int newDir = (NUMBER_OF_DIRECTIONS + value + angle) & 0x07;
		return DirectionMap.getDirection(newDir);
	}
	
}