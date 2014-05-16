package exceptions;

public class OutOfOrderException extends Exception{

	private boolean light;
	
	public OutOfOrderException(boolean light){
		this.light = light;
		System.err.println((light ? "Light player" : "Dark player") + " acted out of turn");
	}
	
	public boolean getLight(){
		return light;
	}
}
