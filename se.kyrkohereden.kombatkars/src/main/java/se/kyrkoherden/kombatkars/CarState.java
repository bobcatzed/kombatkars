package se.kyrkoherden.kombatkars;

import java.util.HashSet;
import java.util.Set;

public class CarState extends ObjectState implements BoardObject{	
	private static final int TYPE = 2;
	final Car car;
	
	private Set<Car> targets;
	private int shotsLeft;
	private boolean hasReturnedFire;
	
	public CarState(Car car, Position position, Direction direction) {
		super(position, direction);
		this.car = car;
//		this.position = position;
//		this.direction = direction;
		this.speed = 0;
		this.targets = new HashSet<Car>();
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
	
	public Car getCar() {
		return car;
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

	public void accelerate(int diff) {
		this.speed += Math.min(car.getMaxSpeed(),diff);
	}
	
	@Override
	public String toString() {
		return "CarState [car=" + car + ", targets=" + targets + ", shotsLeft="
				+ shotsLeft + ", hasReturnedFire=" + hasReturnedFire
				+ ", [" + super.toString() + "]]";
	}

	public int getWidth() {
		return 1;
	}

	public int getHeight() {
		return 1;
	}

	public int getType() {
		return TYPE;
	}

	public String getSymbol() {
		String str = "O";
		switch(getDirection()) {
		case N:
			str="\u2191";
			break;
		case NE:
			str ="\u2197";
			break;
		case E:
			str = "\u2192";
			break;
		case SE:
			str = "\u2198";
			break;
		case S:
			str = "\u2193";
			break;
		case SW:
			str= "\u2199";
			break;
		case W:
			str = "\u2190";
			break;
		case NW:
			str = "\u2196";
			break;
		}
		return str;
	}	
	
}
