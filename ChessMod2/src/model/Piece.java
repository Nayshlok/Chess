package model;

import java.util.ArrayList;

import exceptions.BadMoveException;

public abstract class Piece {

	private boolean isLight, hasMoved;
	private int rowCount, colCount;
	
	public Piece(boolean isLight, int rowCount, int colCount){
		this.setLight(isLight);
		this.setMoved(false);
		this.rowCount = rowCount;
		this.colCount = colCount;
	}
	
	public abstract void move(Coordinate toLocation, boolean capture) throws BadMoveException;
	
	public abstract boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture);
	
	public abstract ArrayList<Coordinate> possibleMoves(Coordinate location1);
	
	public boolean isLight() {
		return isLight;
	}

	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}
	
	public boolean hasMoved(){
		return hasMoved;
	}
	
	public void setMoved(boolean hasMoved){
		this.hasMoved = hasMoved;
	}
	
	public abstract char getPieceCharacter();
	
	public abstract String toString();
	
	public boolean checkInRange(Coordinate location){
		boolean inRange = false;
		
		if((location.getX() < colCount && location.getX() >= 0)
				&& (location.getY() < rowCount && location.getY() >= 0)){
			inRange = true;
		}	
		
		return inRange;
	}
	
	public int getRowCount(){
		return rowCount;
	}
	public int getColCount(){
		return colCount;
	}
	
}
