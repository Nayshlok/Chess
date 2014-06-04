package module2;

import view.Chessgui;
import controller.ChessManager;

public class MainFile {

	public static void main(String[] args){
		ChessManager moveReader = new ChessManager();
		moveReader.run(args[0]);
	}

}
