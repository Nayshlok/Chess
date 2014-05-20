package model;

import java.util.ArrayList;

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
	public boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture) {
		
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

	@Override
	public ArrayList<Coordinate> possibleMoves(Coordinate location1) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

		int startX = location1.getX();
		int startY = location1.getY();
		int x = startX;
		int y = startY;

		while(x < this.getColCount()){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x++;
		}
		x = startX;
		while(x >= 0){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x--;
		}
		x = startX;
		while(y < this.getRowCount()){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			y++;
		}
		y = startY;
		while(y >= 0){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			y--;
		}
		
		return moves;
	}
	
}


