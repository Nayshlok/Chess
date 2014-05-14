package exceptions;

import model.Coordinate;

public class OccupiedSpaceException extends Exception{

	private Coordinate a;
	
	public OccupiedSpaceException(Coordinate a){
		super();
		this.a = a;
		System.err.println("There is a piece at the location " + a.toString());
	}
	
	public Coordinate getCoordinate(){
		return a;
	}
	
}
