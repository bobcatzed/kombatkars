package se.kyrkoherden.kombatkars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class KombatKars {

	private final EightSidedDie die = new EightSidedDie();
	
	private final Map<Car,CarState> cars = new HashMap<Car,CarState>();
	private	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public void printState() {
		for(Map.Entry<Car,CarState> e : cars.entrySet()) {
			System.out.format("%s  %s%n", e.getValue(), e.getKey());
		}
	}
	
	public void addCar(Car car, Position pos, Direction dir) {
		cars.put(car, new CarState(car, pos, dir));
	}
	
	public void apply(Car car, Action a) {	
		CarState state = cars.get(car);
		if( a instanceof Drive) {
			state.move();						
		} else if(a instanceof Turn) {
			state.turn(((Turn)a).getAngle());
		}
	}
	

	
	private int readInt(String prompt) {
		int value = Integer.MIN_VALUE;
		Scanner in = new Scanner(System.in);
		System.out.println(prompt);
		while(value == Integer.MIN_VALUE) {					
			try {
				value= in.nextInt();
			} catch (Exception e) {
				in.nextLine();
				System.out.println("Not an int try again:\n" +prompt);
			}
		}
		return value;
	}
	
	private String readValidString(String prompt, List<String> valid) throws IOException {
		String line = readString(prompt);
		while (!valid.contains(line)) {
			line = readString("Wrong input, try again!\n" + prompt);
		}

		return line;
	}
	
	private String readString(String prompt) throws IOException {
		System.out.println(prompt);
		return reader.readLine();
	}
	
	
	public void selectAction(Car car) {
		CarState carState = cars.get(car);
		ActionState actionState = new ActionState(Math.abs(carState.getSpeed()), car.getMaxShots());
		
		boolean finished = false;
		while(!finished) {
			List<String> valid = new LinkedList<String>();
			StringBuilder sb = new StringBuilder();

			if (actionState.movesLeft > 0) {
				sb.append("Move (m)\n");
				valid.add("m");
			} else {
				sb.append("Done (d)\n");
				valid.add("d");
			}
			
			if (actionState.shotsLeft > 0 && !actionState.hasFired
					&& (Math.abs(carState.getSpeed()) - car.getMaxShots() < 0 || actionState.hasMoved)) {
				sb.append("Fire (f)\n");
				valid.add("f");
			}
			
			if ((carState.getSpeed() == 0 || actionState.hasMoved) && !actionState.hasTurned) {
				sb.append("Turn (t)\n");
				valid.add("t");
			}
			
			sb.append(">");
			
			try {
				String action = readValidString(sb.toString(), valid);
				if (action.equalsIgnoreCase("m")) {
					carState.move();
					System.out.println(carState);
					actionState.hasMoved = true;
					actionState.movesLeft--;
					actionState.hasFired = false;
					actionState.hasTurned = false;
				} else if (action.equalsIgnoreCase("f")) {
					System.out.println("Fire away!");
					actionState.shotsLeft--;
					actionState.hasFired = true;
				} else if (action.equalsIgnoreCase("t")) {
					System.out.println("Turning, turning, turning!");
					actionState.hasTurned = true;
				} else if (action.equalsIgnoreCase("d")) {
					finished = true;
				}
				
				if (carState.getSpeed() == 0) {
					if (actionState.shotsLeft == 0) {
						finished = true;
					} else {
						actionState.hasFired = false;
					}
				} else {
					if (actionState.movesLeft == 0 && actionState.hasTurned && (actionState.hasFired || actionState.shotsLeft == 0)) {
						finished = true;
					}
				}
			} catch (IOException e) {
				throw new RuntimeException("DU är ett orpon!");
			}
		}
	}
	
	public void selectSpeed(Car car) {
	
			CarState state = cars.get(car);
			System.out.format("%s%s%n", state.getPosition(), state.getDirection());
			int min,max;

			if(state.getSpeed() < 0) {
				min = Math.max(-3, state.getSpeed() - 1 * car.getMaxAcceleration());
				max = Math.min(0, state.getSpeed() + car.getMaxDeacceleration());
			} else if(state.getSpeed() == 0) {
				min = Math.max(-3, state.getSpeed() - 1 * car.getMaxAcceleration());
				max = state.getSpeed() + car.getMaxAcceleration();
			} else {
				min = Math.max(0, state.getSpeed() - car.getMaxDeacceleration()); 
				max = Math.min(9, state.getSpeed() + car.getMaxAcceleration());
			}

			String prompt = String.format("Select speed [%d,%d]: ", min, max);
			int newSpeed = Integer.MIN_VALUE;
			while(newSpeed < min || newSpeed > max) {
				newSpeed = readInt(prompt);
			}
			state.setSpeed(newSpeed);			
	}
		

	private static class ActionState {
		private boolean hasMoved;
		private boolean hasTurned;
		private boolean hasFired;
		private int movesLeft;
		private int shotsLeft;
		private Set<Car> targets;
		
		public ActionState(int movesLeft, int shotsLeft) {
			super();
			this.movesLeft = movesLeft;
			this.shotsLeft = shotsLeft;
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		KombatKars kk = new KombatKars();
		System.out.println("***************");
		System.out.println("* KOMBAT KARS *");
		System.out.println("***************");
		Car car = new Car("Bilen");
		kk.addCar(car, new Position(10, 10), Direction.N);
		kk.selectSpeed(car);
		kk.selectAction(car);
//		kk.printState();
//		kk.apply(car, new Turn(3));
//		kk.printState();
//		kk.apply(car, new Drive(1));
//		kk.printState();
	}

}
