package model;

import java.util.ArrayList;

import exceptions.BadMoveException;

public class Bishop extends Piece {

	public Bishop(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getPieceCharacter() {
		char piece = (isLight()) ? 'B' : 'b';
		return piece;
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
		String message = isLight() ? "Light Bishop" : "Dark Bishop";		
		return message;
	}

	@Override
	public ArrayList<Coordinate> possibleMoves(Coordinate location1) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		int startX = location1.getX();
		int startY = location1.getY();
		int x = startX;
		int y = startY;
		
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
		
		return moves;
	}
	
	/*
	public void addMoves(Coordinate location1, int maxY, int maxX, int xInc, int yInc, ArrayList<Coordinate> moves){
		int startX = location1.getX();
		int startY = location1.getY();
		int x = startX;
		int y = startY;
		
		while(x < maxX && y < maxY){
			if(x != startX && y != startY){
				Coordinate toAdd = new Coordinate(x, y);
				if(this.checkInRange(toAdd)){
					moves.add(new Coordinate(x, y));
				}
			}
			x += xInc;
			y += yInc;
		}
	}
	*/
}
