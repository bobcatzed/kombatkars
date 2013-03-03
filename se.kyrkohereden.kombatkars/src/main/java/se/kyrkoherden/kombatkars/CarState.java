package se.kyrkoherden.kombatkars;

import java.util.HashSet;
import java.util.Set;

public class CarState {	
	private final Car car;
	private Position position;
	private Direction direction;
	private int speed;
	private Set<Car> targets;
	private int shotsLeft;
	private boolean hasReturnedFire;
	
	public CarState(Car car, Position position, Direction direction) {
		super();
		this.car = car;
		this.position = position;
		this.direction = direction;
		this.speed = 0;
		this.targets = new HashSet<Car>();
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void accelerate(int diff) {
		this.speed += Math.min(car.getMaxSpeed(),diff);
	}
	
	public void turn(int angle) {
		this.direction = this.direction.turn(angle);
	}
	
	public void targetCar(Car target) {
		this.targets.add(target);
	}
	
	public void fire() {
		this.shotsLeft--;
	}
	
	public void returnFire() {
		this.hasReturnedFire = true;
	}
	
	public void resetRound() {
		this.targets.clear();
		this.shotsLeft = car.getMaxShots();
		this.hasReturnedFire = false;
	}
	
	public void move() {
		int dx = 0;
		int dy = 0;
		switch(direction) {
		case N:
			dy=1;
			break;
		case NE:
			dy=1;
			dx=1;
			break;
		case E:
			dx=1;
			break;
		case SE:
			dx=1;
			dy=-1;
			break;
		case S:
			dy=-1;
			break;
		case SW:
			dy=-1;
			dx=-1;
			break;
		case W:
			dx=-1;
			break;
		case NW:
			dx=-1;
			dy=1;
			break;
		}			
		int x = position.getX() + dx;
		int y = position.getY() + dy;					
		position = new Position(x, y);
	}

	public Car getCar() {
		return car;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getSpeed() {
		return speed;
	}

	public Set<Car> getTargets() {
		return targets;
	}

	public int getShotsLeft() {
		return shotsLeft;
	}

	public boolean hasReturnedFire() {
		return hasReturnedFire;
	}

	@Override
	public String toString() {
		return "CarState [car=" + car + ", position=" + position
				+ ", direction=" + direction + ", speed=" + speed
				+ ", targets=" + targets + "]";
	}
	
	
	
	
}
