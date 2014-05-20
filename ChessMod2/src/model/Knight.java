package model;

import java.util.ArrayList;

import exceptions.OutOfBoardRange;

public class Knight extends Piece {

	public Knight(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'N';
		}
		else{
			return 'n';
		}
	}

	@Override
	public void move(Coordinate toLocation, boolean capture) {

	}

	@Override
	public boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture) {
		boolean isValid = false;
		
		int changeX = Math.abs(location1.getX() - location2.getX());
		int changeY = Math.abs(location1.getY() - location2.getY());
		
		if((changeX == 2 && changeY == 1) || (changeX == 1 && changeY == 2)){
			isValid = true;
		}
		
		return isValid;
	}

	public String toString(){
		if(isLight()){
			return "Light Knight";
		}
		else{
			return "Dark Knight";
		}
	}

	@Override
	public ArrayList<Coordinate> possibleMoves(Coordinate location1) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		int x = location1.getX();
		int y = location1.getY();
		Coordinate[] possibleMoves = {
				new Coordinate(x + 2, y - 1),
				new Coordinate(x + 2, y + 1),
				new Coordinate(x - 2, y - 1),
				new Coordinate(x - 2, y + 1),
				new Coordinate(x + 1, y - 2),
				new Coordinate(x - 1, y - 2),
				new Coordinate(x + 1, y + 2),
				new Coordinate(x - 1, y + 2)	
		};
		
		for(int i = 0; i < possibleMoves.length; i++){
			if(this.checkInRange(possibleMoves[i])){
				moves.add(possibleMoves[i]);
			}
		}
		
		return moves;
	}
	
}
