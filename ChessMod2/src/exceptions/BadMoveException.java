package exceptions;

import model.Coordinate;

public class BadMoveException extends Exception{

	public BadMoveException(Coordinate a, boolean capture){
		super();
		if(capture == false){
			System.err.println("There is a piece at " + a.toString() + " and capture is off.");
		}
		else{
			System.err.println("There is not a piece at " + a.toString() + " and capture is on");
		}
		
	}
	
}
