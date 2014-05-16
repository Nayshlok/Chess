package model;

import exceptions.BadMoveException;

public class Bishop extends Piece {

	public Bishop(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'B';
		}
		else{
			return 'b';
		}
	}

	@Override
	public void move(Coordinate toLocation, boolean capture) throws BadMoveException{
						
	}

	@Override
	public boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture) {
		boolean isValid = false;
		
		if(!checkInRange(location1) || !checkInRange(location2)){
			return false;
		}
		
		int changeX = Math.abs(location1.getX() - location2.getX());
		int changeY = Math.abs(location1.getY() - location2.getY());
		
		if(changeX == changeY){
			isValid = true;
		}
		
		return isValid;
	}

	@Override
	public String toString(){
		if(isLight()){
			return "Light Bishop";
		}
		else{
			return "Dark Bishop";
		}
	}
	
}
