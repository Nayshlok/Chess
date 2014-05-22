package exceptions;

public class OutOfOrderException extends Exception{

	private boolean light;
	
	public OutOfOrderException(boolean light){
		super((light ? "Light player" : "Dark player") + " acted out of turn");
		this.light = light;
	}
	
	public boolean getLight(){
		return light;
	}
}
