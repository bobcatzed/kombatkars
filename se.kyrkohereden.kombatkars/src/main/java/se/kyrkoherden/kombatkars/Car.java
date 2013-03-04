package se.kyrkoherden.kombatkars;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class Car {
	private final String name;
	private int maxSpeed;
	private int maxReverse;
	private int maxAcceleration = 2;
	private int maxDeacceleration = 3;
	private int hitPoints = 30;
	private int rockets = 4;
	private Color color;
	
	

	public Car(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	

	public String getName() {
		return name;
	}

	public boolean fire() {
		if(rockets > 0) {
			rockets--;
			return true;
		}
		return false;
	}
	
	public int damage(int value) {
		this.hitPoints = Math.max(0, this.hitPoints - value);
		return this.hitPoints;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getMaxReverse() {
		return maxReverse;
	}

	public void setMaxReverse(int maxReverse) {
		this.maxReverse = maxReverse;
	}

	public int getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(int maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	public int getMaxDeacceleration() {
		return maxDeacceleration;
	}

	public void setMaxDeacceleration(int maxDeacceleration) {
		this.maxDeacceleration = maxDeacceleration;
	}
	
	public boolean hasGunneryComputer() {
		return false;
	}
	
	public boolean hasAutoSteer() {
		return false;
	}
	
	public boolean hasFireExtinguisher() {
		return false;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxAcceleration;
		result = prime * result + maxDeacceleration;
		result = prime * result + maxReverse;
		result = prime * result + maxSpeed;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (maxAcceleration != other.maxAcceleration)
			return false;
		if (maxDeacceleration != other.maxDeacceleration)
			return false;
		if (maxReverse != other.maxReverse)
			return false;
		if (maxSpeed != other.maxSpeed)
			return false;		
		return true;
	}

	@Override
	public String toString() {
		return "Car [maxSpeed=" + maxSpeed + ", maxReverse=" + maxReverse
				+ ", maxAcceleration=" + maxAcceleration
				+ ", maxDeacceleration=" + maxDeacceleration + ", speed="
				+ "]";
	}

	public int getMaxShots() {
		return 2;
	}



	public int getHitPoints() {
		return hitPoints;
	}		
	
	
}
