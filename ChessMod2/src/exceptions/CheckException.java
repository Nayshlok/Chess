package exceptions;

public class CheckException extends Exception{

	public CheckException(boolean isLight){
		super("The move puts the " + (isLight ? "Light" : "Dark") + " king in check");
	}
}
