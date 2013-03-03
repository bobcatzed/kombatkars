package se.kyrkoherden.kombatkars;

import java.util.Random;

public class EightSidedDie {
	private final Random random = new Random();
	public int roll() {
		return random.nextInt(8) + 1;
	}

}
