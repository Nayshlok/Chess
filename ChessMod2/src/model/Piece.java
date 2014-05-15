package model;

import exceptions.BadMoveException;

public abstract class Piece {

	private boolean isLight;
	private int rowCount, colCount;
	
	public Piece(boolean isLight, int rowCount, int colCount){
		this.setLight(isLight);
		this.rowCount = rowCount;
		this.colCount = colCount;
	}
	
	public abstract void move(Coordinate toLocation, boolean capture) throws BadMoveException;
	
	public abstract boolean moveCheck(Coordinate location1, Coordinate location2);
	
	public boolean isLight() {
		return isLight;
	}

	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}
	
	public abstract char getPieceCharacter();
	
	public boolean checkInRange(Coordinate location){
		boolean inRange = false;
		
		if((location.getX() < colCount && location.getX() >= 0)
				&& (location.getY() < rowCount && location.getY() >= 0)){
			inRange = true;
		}	
		
		return inRange;
	}
	
}
