package exceptions;

public class IllegalMoveException extends Exception{

	private String name;
	
	public IllegalMoveException(String pieceName){
		super("Invalid move for " + pieceName);

	}
	
}
