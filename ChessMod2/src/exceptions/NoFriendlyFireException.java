package exceptions;

public class NoFriendlyFireException extends Exception{

	public NoFriendlyFireException(){
		super("You cannot take your own pieces");
	}
	
}
