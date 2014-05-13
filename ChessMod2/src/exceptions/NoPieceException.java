package exceptions;

import model.Coordinate;

public class NoPieceException extends Exception{

	public NoPieceException(Coordinate a){
		super();
		System.err.println("No piece at " + a.toString());
	}
	
}
