package exceptions;

public class BlockedPathException extends Exception{

	public BlockedPathException(){
		super("The attempted move was blocked.");
	}
	
}
