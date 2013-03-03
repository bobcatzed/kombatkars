package se.kyrkoherden.kombatkars;

import java.util.HashMap;
import java.util.Map;

public class DirectionMap {
	private static final Map<Integer,Direction> map = initializeMap();
	
	public static Direction getDirection(int value){
		return map.get(value);
	}
	
	private static Map<Integer,Direction> initializeMap() {
		HashMap<Integer,Direction> m = new HashMap<Integer,Direction>();
		for(Direction d: Direction.values()) {
			m.put(d.getValue(), d);
		}
		return m;
	}
}