package exceptions;

import model.Coordinate;
import model.Piece;

public class BadMoveException extends Exception{

	private Coordinate a; //Where the error occurred
	private boolean capture;
	
	public BadMoveException(Coordinate a, boolean capture){
		super();
		if(!capture){
			System.err.println("There is a piece at " + a.toString() + " and capture is off.");
		}
		else{
			System.err.println("There is not a at " + a.toString() + " and capture is on");
		}
		
		this.a = a;
		this.capture = capture;
	}
	
	public Coordinate getCoordinate(){
		return a;
	}
	
	public boolean getCapture(){
		return capture;
	}
	
}
