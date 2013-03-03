package se.kyrkoherden.kombatkars;

import java.util.List;

public class Move {
	private final Car car;
	private final List<Action> actions;

	public Move(Car car,List<Action> actions) {
		super();
		this.car = car;
		this.actions = actions;
	}

	public List<Action> getActions() {
		return actions;
	}

	public Car getCar() {
		return car;
	}
	
	
	
}
