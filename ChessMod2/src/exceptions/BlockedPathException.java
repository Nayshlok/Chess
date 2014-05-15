package exceptions;

public class BlockedPathException extends Exception{

	public BlockedPathException(){
		super();
		System.err.println("The attempted move was blocked.");
	}
	
}
