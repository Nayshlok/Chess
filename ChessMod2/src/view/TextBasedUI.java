package view;

import java.util.Scanner;

import model.Coordinate;
import controller.ChessManager;

public class TextBasedUI {

	private TextDisplay display;
	private ChessManager manager;
	private Scanner input;
	
	public TextBasedUI(TextDisplay display, ChessManager manager){
		this.display = display;
		this.manager = manager;
		input = new Scanner(System.in);
	}
	
	public void getMove(){
		boolean success = false;
		Coordinate piece = null;
		
		System.out.println((manager.isLightTurn() ? "White Turn:" : "Black Turn:"));
		display.displayPieceMoves(manager.isLightTurn());
		while(!success){
			System.out.println("Which piece do you want to move: ");
			String move = input.nextLine();
			if(move.matches("[A-Ha-h][1-8]")){
				piece = new Coordinate(move.charAt(0), move.charAt(1));
				
			}
		}
		
		
	}
	
}
