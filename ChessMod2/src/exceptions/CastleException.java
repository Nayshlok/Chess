package exceptions;

public class CastleException extends Exception{

	private String message;
	
	public CastleException(String message){
		super("The castling failed because " + message);

	}
	
}
