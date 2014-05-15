package model;

import exceptions.BadMoveException;

public class Rook extends Piece {

	public Rook(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'R';
		}
		else{
			return 'r';
		}
	}


	@Override
	public void move(Coordinate toLocation, boolean capture)
			throws BadMoveException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean moveCheck(Coordinate location1, Coordinate location2) {
		
		boolean isValid = false;
		
		if((location1.getX() == location2.getX()) || (location1.getY() == location2.getY())){
			isValid = true;
		}
		
		
		return isValid;
	}

	public String toString(){
		if(isLight()){
			return "Light Rook";
		}
		else{
			return "Dark Rook";
		}
	}
	
}


