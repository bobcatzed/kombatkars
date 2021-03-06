package se.kyrkoherden.kombatkars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal.Color;


public class KombatKars {

	private final EightSidedDie die = new EightSidedDie();
	
	private final Map<Car,CarState> cars = new HashMap<Car,CarState>();
	private	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private GameBoard gameBoard;
	private Screen screen;
	
	public KombatKars() {
		screen = TerminalFacade.createScreen();
		gameBoard = new GameBoard(screen, 50, 18);
		screen.startScreen();
	}
	
	public void terminate() {
		screen.stopScreen();
	}
	
	public void printState() {
		for(Map.Entry<Car,CarState> e : cars.entrySet()) {
			System.out.format("%s  %s%n", e.getValue(), e.getKey());
		}
	}
	
	public void addCar(Car car, Position pos, Direction dir) {
		cars.put(car, new CarState(car, pos, dir));
		gameBoard.addBoardObject(cars.get(car));
	}
	
	public void apply(Car car, Action a) {	
		ObjectState state = cars.get(car);
		if( a instanceof Drive) {
			state.move();						
		} else if(a instanceof Turn) {
			state.turn(((Turn)a).getAngle());
		}
	}
	

	
	private int readInt(String prompt) {
		ScreenWriter writer = new ScreenWriter(screen);
		writer.setBackgroundColor(Color.BLACK);
		writer.setForegroundColor(Color.WHITE);
		writer.drawString(0, 0, getSpaces(40));
		writer.drawString(0, 0, prompt);
		screen.refresh();
		Key key = screen.readInput();
		boolean done = false;
		int pos = 0;
		StringBuilder number = new StringBuilder();
		int y =1;
		while(!done) {			
			key = screen.readInput();
			if(key != null) {
				if(Character.isDigit(key.getCharacter()) ||
						(pos == 0 && key.getCharacter() == '-')) {
					number.append(key.getCharacter());
					writer.drawString(pos, y, Character.toString(key.getCharacter()));
					screen.refresh();
					pos++;					
				} else if(key.getKind() == Key.Kind.Enter) {
					done = true;
				}
			} else {
				try {
					Thread.sleep(20);
				} catch(InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		writer.drawString(0, 0, getSpaces(40));
		writer.drawString(0, y, getSpaces(40));
		int value = 0;
		try {
			value = Integer.parseInt(number.toString());
		} catch(NumberFormatException nfe) {
			throw new RuntimeException(nfe);
		}
		return value;		
	}
	
	private char readChar(String prompt, Collection<Character> valid) {
		ScreenWriter writer = new ScreenWriter(screen);
		writer.setBackgroundColor(Color.BLACK);
		writer.setForegroundColor(Color.WHITE);
		writer.drawString(0, 0, getSpaces(40));
		writer.drawString(0, 0, prompt);
		screen.refresh();
		boolean done = false;
		char value = 0;
		while(!done) {			
			Key key = screen.readInput();
			if(key != null && valid.contains(key.getCharacter())) {
				value = key.getCharacter();
				done = true;
			 } else {
				 try {
					 Thread.sleep(20);
				 } catch(InterruptedException e) {
					 throw new RuntimeException(e);
				 }
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
	
	private String getSpaces(int count) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < count; i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	
	private String readString(String prompt) throws IOException {
//		System.out.println(prompt);
		
		ScreenWriter writer = new ScreenWriter(screen);
		writer.setBackgroundColor(Color.BLACK);
		writer.setForegroundColor(Color.WHITE);
		writer.drawString(0, 0, getSpaces(40));
		writer.drawString(0, 0, prompt);
		screen.refresh();
		Key key = screen.readInput();
		while(key == null) {
			try {
				Thread.sleep(20);
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
			key = screen.readInput();			
		}
		writer.drawString(0, 0, getSpaces(40));
		return Character.toString(key.getCharacter());
	}
	
	private void drawDirection(Direction direction) {
		int row = screen.getTerminalSize().getRows() -1;
		ScreenWriter writer = new ScreenWriter(screen);
		writer.setBackgroundColor(Color.BLACK);
		writer.setForegroundColor(Color.BLUE);
		writer.drawString(0, row, "  ");
		writer.drawString(0, row, direction.toString());
		screen.refresh();
	}
	
	private Direction selectTurnDirection(Car car) {
		ObjectState carState = cars.get(car);		
		Set<Direction> possibleTurns = new LinkedHashSet<Direction>();
		Direction oppositeDirection = DirectionMap.getDirection((carState.getDirection().getValue() + 4) % 8);
		for(Direction d : Direction.values()) {
			if(d != carState.getDirection() &&
					(carState.getSpeed() == 0 || d != oppositeDirection)) {
				possibleTurns.add(d);
			}
		}
		StringBuilder sb = new StringBuilder("Select turn direction: ");
		List<Character> valid = new ArrayList<Character>();
		for(Direction d : possibleTurns) {
			sb.append(" ");			
			sb.append(d.getKey()).append('(').append(d.toString()).append(')');
			valid.add(d.getKey());
		}
		char value = readChar(sb.toString(), valid);
		for(Direction d: Direction.values()) {
			if(d.getKey() == value){
				return d;
			}
		}
		throw new RuntimeException("WOT!");
	}
	
	public void fire(CarState carState) {
		if(carState.getCar().getRockets() > 0) {
			carState.getCar().fire();
			System.out.format("%d rockets left%n", carState.getCar().getRockets());
			Rocket rocket = new Rocket(carState.getPosition(), carState.getDirection());
			gameBoard.addBoardObject(rocket);
			for(int i = 0; i < 10; i++) {
				if(!gameBoard.checkCollision(rocket)) {
					rocket.move();
					gameBoard.draw();
					screen.refresh();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				} else {
					rocket.move();
					gameBoard.removeObject(rocket);
					gameBoard.draw();
					gameBoard.drawAt(rocket.getPosition(), "*", Color.RED);
					gameBoard.refresh();
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						break;
					}
					gameBoard.draw();
					gameBoard.refresh();
					BoardObject hitObject = gameBoard.getObjectAtPosition(rocket.getPosition());
					if(hitObject instanceof CarState) {
						CarState cs = (CarState) hitObject;
						int damage = die.roll() + die.roll() - 1;
						cs.getCar().damage(damage);
						System.out.format("Car hit, lost %d hitpoints, %d remaining", damage, cs.getCar().getHitPoints());
					}
					break;
				}

			}
			gameBoard.removeObject(rocket);
			gameBoard.draw();
		}
	}
	
	private static class SafeTurnSpeed {
		private final int safe45;
		private final int safe90;
		private final int safe135;
		public SafeTurnSpeed(int safe45, int safe90, int safe135) {
			super();
			this.safe45 = safe45;
			this.safe90 = safe90;
			this.safe135 = safe135;
		}
		public int getSkidFactor(int speed, int angle) {
			switch(Math.abs(angle)) {
			case 0:
				return 0;
			case 1:
				return Math.max(0, speed - safe45);
			case 2:
				return Math.max(0, speed - safe90);
			case 3:
				return Math.max(0, speed - safe135);
			default:
				throw new IllegalArgumentException("Abs(angle) cannot be more than 3");
				
			}
		}
	}
	
	public boolean turn(CarState carState, Direction newDirection) {
		SafeTurnSpeed safeTurnSpeed = new SafeTurnSpeed(8,4,2);
		int angle = newDirection.getValue() - carState.getDirection().getValue();
		if(angle > 3) {
			angle -= 8;
		} else if(angle < -3) {
			angle += 8;
		}
		int skidFactor = safeTurnSpeed.getSkidFactor(carState.getSpeed(), angle);
		if(skidFactor > 0) {
			int skidDistance = die.roll() - Math.max(0, 8 - skidFactor);
			System.out.format("SkidDistance: %d%n", skidDistance);
			boolean collided = false;
			for(int i=0; i < skidDistance; i++) {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!gameBoard.checkCollision(carState)) {
					carState.move();					
					gameBoard.draw();
					screen.refresh();
				} else {
					System.out.println("CRASH!");
					carState.setSpeed(0);
					collided = true;
					break;
				}
			}			
			
			int directionValue = die.roll();
			int speed = carState.getSpeed();
			if(speed == 4) {
				directionValue += 1;
			} else if(speed == 5 || speed == 6) {
				directionValue += 2; 
			} else if(speed > 6) {
				directionValue += 3;
			}
			directionValue = Math.max(0, directionValue - 4);
			int skidAngle = (directionValue+1)/2 * (directionValue % 2 == 0 ? 1 : -1);
			System.out.format("Skidding: distance=%d, angle=%d%n", skidDistance, skidAngle);
			if(skidAngle == 0 && !collided) {
				carState.setDirection(newDirection);
				gameBoard.draw();
				screen.refresh();
				return true;
			}
			carState.setSpeed(0);
			Direction directionAfterSkid = 
				DirectionMap.getDirection((8 + carState.getDirection().getValue() + skidAngle) % 8);
			carState.setDirection(directionAfterSkid);
			gameBoard.draw();
			screen.refresh();
			return false;
		} else {
			carState.setDirection(newDirection);
			gameBoard.draw();
			screen.refresh();
			return true;
		}
	}
	
	public void selectAction(Car car) {
		CarState carState = cars.get(car);
		ActionState actionState = new ActionState(Math.abs(carState.getSpeed()), car.getMaxShots());
		
		boolean finished = false;
		while(!finished) {
			gameBoard.draw();						
			drawDirection(carState.getDirection());
			screen.refresh();
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
					if(!gameBoard.checkCollision(carState)) {
						carState.move();
						System.out.println(carState);
						actionState.hasMoved = true;
						actionState.movesLeft--;
						actionState.hasFired = false;
						actionState.hasTurned = false;
						gameBoard.draw();
						screen.refresh();
					} else {
						System.out.println("CRASH!");
						carState.setSpeed(0);
						finished = true;
					}
				} else if (action.equalsIgnoreCase("f")) {
					System.out.println("Fire away!");
					fire(carState);
					actionState.shotsLeft--;
					actionState.hasFired = true;
				} else if (action.equalsIgnoreCase("t")) {
					System.out.println("Turning, turning, turning!");
					Direction newDirection = selectTurnDirection(car);
//					carState.setDirection(newDirection);
					if(!turn(carState,newDirection)) {
						actionState.movesLeft = 0;
						actionState.hasFired = true;
						actionState.hasTurned = true;
						finished = true;
					}
					drawDirection(carState.getDirection());
					gameBoard.refresh();
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
//			if(actionState.hasMoved) {
//				gameBoard.moveCar(oldPosition, carState.getPosition(), "H", Color.RED);
//			}
		}
	}
	
	public void selectSpeed(Car car) {
	
			ObjectState state = cars.get(car);
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
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		KombatKars kk = new KombatKars();
		System.out.println("***************");
		System.out.println("* KOMBAT KARS *");
		System.out.println("***************");
		Car car1 = new Car("Red", Color.RED);
		Car car2 = new Car("Blue", Color.BLUE);
		List<Car> carList = Arrays.asList(car1);
//		kk.getGameBoard().addBoardObject(new House(new Position(12,12), 5,2));
//		kk.getGameBoard().addBoardObject(new House(new Position(2,8), 5,4));
//		kk.getGameBoard().addBoardObject(new House(new Position(20,13), 10,5));
//		kk.getGameBoard().addBoardObject(new House(new Position(15, 2), 7,5));
		Random random = new Random();
		int x0 = 4;
		int x1 = 44;
		int y0 = 1;
		int y1 = 17;
		for(int i = 0; i < 40; i++) {
			int x = x0 + random.nextInt(x1);
			int y = y0 + random.nextInt(y1);
			int w = 2 + random.nextInt(4);
			w = Math.min(x1 -x, w);
			int h = 2 + random.nextInt(3);
			h = Math.min(y1 - y, h);
			kk.getGameBoard().addBoardObject(new House(new Position(x,y), w, h));
		}
		kk.addCar(car1, new Position(1, 1), Direction.E);
		kk.addCar(car2, new Position(49, 17), Direction.W);
		kk.cars.get(car1).setSpeed(8);
		boolean gameOver = false;
		while(!gameOver) {
			for(Car car : carList) {
				if(car.getHitPoints() <= 0) {
					gameOver = true;
					break;
				}
				kk.selectSpeed(car);
				kk.selectAction(car);
			}
		}
		for(Car car : carList) {
			if(car.getHitPoints() <= 0) {
				System.out.format("%s lost%n", car.getName());
			} else {
				System.out.format("%s won!%n", car.getName());
			}
		}
		kk.terminate();
	}

}
