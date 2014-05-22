package exceptions;

import model.Coordinate;
import model.Piece;

public class BadMoveException extends Exception{

	private Coordinate a; //Where the error occurred
	private boolean capture;
	
	public BadMoveException(Coordinate a, boolean capture){
		super("There is a piece at " + a.toString() + (capture ? " and capture is on" : " and capture is off."));
		
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
