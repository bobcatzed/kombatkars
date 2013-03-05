package se.kyrkoherden.kombatkars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;

public class GameBoard {
	private static final Color COLOR_ASPHALT = Color.BLACK;
	private Screen screen;
	private final int height;	
	private final int width;
	private List<BoardObject> boardObjects;
	private Map<Integer,Terminal.Color> typeColors;
	private int x0 = 0;
	private int y0 = 10;
	

//	private int x0 = 10;
//	private int y0 = 5;

	
	public GameBoard(Screen screen,int width, int height) {
		super();
		this.height = height;
		this.width = width;
		this.screen = screen;
		this.boardObjects = new ArrayList<BoardObject>();
		this.typeColors = new HashMap<Integer,Color>();
//		this.screen = TerminalFacade.createScreen();
	}

	public void drawBorder() {
		drawRectangle(x0,y0, this.width, this.height, "." ,Terminal.Color.WHITE, false);
	}

	public BoardObject getObjectAtPosition(Position p) {
		for(BoardObject bo : boardObjects) {
			if(contains(bo,p)) {
				return bo;
			}
		}
		return null;
	}
	
	public void drawAt(Position pos,String str, Color color) {
		screen.putString(x0 + pos.getX(), y0 + pos.getY(), str, color,
				Color.BLACK);
	}
	
	public void moveCar(Position oldPosition, Position position, String c, Color color) {
		if(!position.equals(oldPosition)) {
			screen.putString(x0 + position.getX(), y0 + position.getY(), c,
					color, COLOR_ASPHALT);
			if(oldPosition != null) {
				screen.putString(x0 + oldPosition.getX(), y0 + oldPosition.getY(), " ",
						color, COLOR_ASPHALT);
			};
		}
	}
	
	public void refresh() {
		screen.refresh();
	}
	
	public void draw() {
		screen.clear();
		drawBorder();
		drawObjects();
	}
	
	public void addBoardObject(BoardObject bo) {
		boardObjects.add(bo);
	}
	
	private void putString(Position pos, String str, Color color) {
		screen.putString(x0 + pos.getX(), y0 + pos.getY(), str, color, Color.BLACK);
	}
	
	private boolean contains(BoardObject bo, Position p) {
		int x0 = bo.getPosition().getX();
		int y0 = bo.getPosition().getY();
		int h = bo.getHeight();
		int w = bo.getWidth();
		int x = p.getX();
		int y = p.getY();
		return x0 <= x && x < x0 + w && y0 <= y && y < y0 + h;
	}
	
	public boolean checkCollision(ObjectState carState) {
		Position peek = carState.getNextPosition();
		for(BoardObject bo : boardObjects) {
			if(bo != carState && contains(bo, peek)) {
				return true;
			}
		}
		return false;
	}
	
	public void drawObjects() {
		for(BoardObject bo : boardObjects) {
			Position p = bo.getPosition();
			Color color = typeColors.get(bo.getType());
			if(color == null) {
				color = Color.WHITE;
			}
			if(bo instanceof CarState) {				
				putString(bo.getPosition(), bo.getSymbol(), bo.getColor());			
			} else {
				drawRectangle(x0 + p.getX(), y0 + p.getY(), bo.getWidth(), bo.getHeight(), bo.getSymbol(),
						bo.getColor(), true);
			}
		}
	}
	
	
	
	public void drawRectangle(int x0, int y0, int width, int height, String symbol, Terminal.Color color, boolean fill) {
		ScreenWriter writer = new ScreenWriter(screen);		
		if(fill) {			
			writer.setForegroundColor(color);
			for(int i = x0; i < x0 + width; i++) {
				for(int j = y0; j < y0 + height; j++){
					writer.drawString(i, j, symbol);
				}
			}
		} else {
			writer.setBackgroundColor(Color.BLACK);
			writer.setForegroundColor(color);
			String c = symbol;
			for(int i = 0; i < width; i++) {
				writer.drawString(x0 + i, y0, c);
				writer.drawString(x0 + i, y0 + height, c);
			}
			for(int j = 0; j < height; j++) {
				writer.drawString(x0 , y0 + j, c);
				writer.drawString(x0 + width, y0 + j, c);
			}
		}
	}
	
	public void start() {
		screen.startScreen();
	}
	
	public void stop() {
		screen.stopScreen();
	}
	
	public static void main(String argv[]) {
//		GameBoard gb = new GameBoard(TerminalFacade.createScreen(),50, 18);
//		gb.start();
//		gb.boardObjects.add( new House(new Position(12,12),5,2));
//		gb.drawBorder();
//		gb.drawObjects();
//		gb.refresh();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		gb.stop();
	}

	public void removeObject(BoardObject object) {
		boardObjects.remove(object);
		
	}
	
}
