package module2;

import controller.ChessManager;

public class MainFile {

	public static void main(String[] args){
		ChessManager moveReader = new ChessManager();
		moveReader.run("data\\moves.txt");
	}
	
}