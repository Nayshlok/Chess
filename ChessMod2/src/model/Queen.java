package model;

import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getPieceCharacter() {
		char piece = (isLight()) ? 'Q' : 'q';
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
		
		if(changeX == changeY){
			isValid = true;
		}
		if((location1.getX() == location2.getX()) || (location1.getY() == location2.getY())){
			isValid = true;
		}
		
		return isValid;
	}

	public String toString(){
		String message = isLight() ? "Light Queen" : "Dark Queen";		
		return message;
	}

	@Override
	public ArrayList<Coordinate> possibleMoves(Coordinate location1) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		
		int startX = location1.getX();
		int startY = location1.getY();
		int x = startX;
		int y = startY;
		
		//Bishop moves
		while(x < this.getRowCount() && y < this.getColCount()){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x++;
			y++;
		}
		x = startX;
		y = startY;
		while(x >= 0 && y < this.getColCount()){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x--;
			y++;
		}
		x = startX;
		y = startY;
		//Rook moves
		while(x < this.getRowCount() && y >= 0){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x++;
			y--;
		}
		x = startX;
		y = startY;
		while(x >= 0 && y >= 0){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x--;
			y--;
		}
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
