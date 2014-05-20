package exceptions;

public class IllegalMoveException extends Exception{

	private String name;
	
	public IllegalMoveException(String pieceName){
		super();
		this.name = pieceName;
		System.err.println("Invalid move for " + name);
	}
	
	public String getName(){
		return name;
	}
	
}
