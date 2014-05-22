package exceptions;

import model.Coordinate;

public class OccupiedSpaceException extends Exception{

	private Coordinate a;
	
	public OccupiedSpaceException(Coordinate a){
		super("There is a piece at the location " + a.toString());
		this.a = a;
	}
	
	public Coordinate getCoordinate(){
		return a;
	}
	
}
