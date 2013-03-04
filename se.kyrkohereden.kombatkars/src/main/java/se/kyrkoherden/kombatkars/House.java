package se.kyrkoherden.kombatkars;

public class House implements BoardObject {
	private static final int TYPE = 1;
	private final Position position;
	private int width;
	private int height;
	
	public House(Position position,int width, int height) {
		super();
		this.position = position;
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Position getPosition() {
		return position;
	}

	public int getType() {
		return TYPE;
	}

	public String getSymbol() {
		return "#";
	}

}
