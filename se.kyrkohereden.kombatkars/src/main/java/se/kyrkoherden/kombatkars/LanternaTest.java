package se.kyrkoherden.kombatkars;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

public class LanternaTest {
	public static void main(String[] argv) {
//		Terminal terminal = TerminalFacade.createTerminal();
//		terminal.enterPrivateMode();
//		
//		terminal.exitPrivateMode();
		Screen screen = TerminalFacade.createScreen();
		screen.startScreen();
		screen.putString(10, 5, "Press \u2191 q to exit...", Terminal.Color.WHITE, Terminal.Color.BLACK);
		screen.refresh();
		boolean done = false;
		try {
			while(!done) {
				Key key =screen.readInput();
				if(key != null ) {
					if(key.getCharacter() == 'q') {
						screen.putString(10,6, "That is q!!!           ", Terminal.Color.GREEN, Terminal.Color.BLACK);
						screen.refresh();
						done = true;
						Thread.sleep(2000);
					} else {
						screen.putString(10,6, "That is " + key.getCharacter() + ", not q", Terminal.Color.RED, Terminal.Color.BLACK);
						screen.refresh();
					}
				} else {
					Thread.sleep(20);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		screen.stopScreen();
	}
}
