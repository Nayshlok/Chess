package model;

import java.util.ArrayList;

public class King extends Piece {

	public King(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		
	}

	
	
	@Override
	public char getPieceCharacter() {
		char piece = (isLight()) ? 'K' : 'k';
		return piece;
	}

	@Override
	public void move(Coordinate toLocation, boolean capture) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture) {
		boolean isValid = false;
		
		int changeX = Math.abs(location1.getX() - location2.getX());
		int changeY = Math.abs(location1.getY() - location2.getY());
		
		if((changeX <= 1 && changeX >= -1)
				&& (changeY <= 1 && changeY >= -1)){
			isValid = true;
		} 
		return isValid;
	}

	public String toString(){
		String message = isLight() ? "Light King" : "Dark King";		
		return message;
	}



	@Override
	public ArrayList<Coordinate> possibleMoves(Coordinate location1) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		int x = location1.getX();
		int y = location1.getY();
		Coordinate[] possibleMoves = {
			new Coordinate(x - 1, y - 1),
			new Coordinate(x - 1, y + 1),
			new Coordinate(x + 1, y - 1),
			new Coordinate(x + 1, y + 1),
			new Coordinate(x - 1, y),
			new Coordinate(x, y - 1),
			new Coordinate(x + 1, y),
			new Coordinate(x, y + 1)	
		};
		
		for(int i = 0; i < possibleMoves.length; i++){
			if(this.checkInRange(possibleMoves[i])){
				moves.add(possibleMoves[i]);
			}
		}
		
		return moves;
	}
}
