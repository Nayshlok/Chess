package exceptions;

public class CastleException extends Exception{

	private String message;
	
	public CastleException(String message){
		super();
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
}
