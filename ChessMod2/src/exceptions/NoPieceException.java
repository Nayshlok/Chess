package exceptions;

import model.Coordinate;

public class NoPieceException extends Exception{

	private Coordinate a;
	
	public NoPieceException(Coordinate a){
		super("No piece at " + a.toString());
		this.a = a;
	}
	
	
	public Coordinate getCoordinate(){
		return a;
	}
}
