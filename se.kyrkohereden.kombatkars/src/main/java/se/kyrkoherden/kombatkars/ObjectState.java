package se.kyrkoherden.kombatkars;

public class ObjectState {
	private Position position;
	private Direction direction;
	protected int speed;
	
	public ObjectState(Position position, Direction direction) {
		super();
		this.position = position;
		this.direction = direction;
	}
	public void turn(int angle) {
		this.direction = this.direction.turn(angle);
	}
	public Position getNextPosition() {
		int dx = 0;
		int dy = 0;
		switch(direction) {
		case N:
			dy=-1;
			break;
		case NE:
			dy=-1;
			dx=1;
			break;
		case E:
			dx=1;
			break;
		case SE:
			dx=1;
			dy=1;
			break;
		case S:
			dy=1;
			break;
		case SW:
			dy=1;
			dx=-1;
			break;
		case W:
			dx=-1;
			break;
		case NW:
			dx=-1;
			dy=-1;
			break;
		}			
		if(speed < 0) {
			dx = -1 * dx;
			dy = -1 * dy;
		}
		int x = position.getX() + dx;
		int y = position.getY() + dy;					
		return new Position(x, y);
	}
	public void move() {
		this.position = getNextPosition();
	}
	public Position getPosition() {
		return position;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction newDirection) {
		this.direction = newDirection;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}
	@Override
	public String toString() {
		return "ObjectState [position=" + position + ", direction=" + direction
				+ ", speed=" + speed + "]";
	}
	
	
	
}
