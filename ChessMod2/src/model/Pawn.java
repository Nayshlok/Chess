package model;

import java.util.ArrayList;

public class Pawn extends Piece {

	public Pawn(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}
	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'P';
		}
		else{
			return 'p';
		}
	}
	@Override
	public void move(Coordinate toLocation, boolean capture) {
		// TODO Auto-generated method stub
		
	}
	// Can only jump 2 when in start location. FIX IT!!!

	public boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture) {
		
		boolean isValid = false;
		
		int changeX = Math.abs(location1.getX() - location2.getX());
		
		if((changeX == 0) && !capture){
			if((isLight() && (location2.getY() == location1.getY() + 1))
					|| (!isLight() && (location2.getY() == location1.getY() - 1))) {
				isValid = true;
			}
			if(!hasMoved() && ((isLight() && (location2.getY() == location1.getY() + 2)) 
					|| (!isLight() && (location2.getY() == location1.getY() - 2)))) {
				isValid = true;
			}
		}
		else if((changeX == 1) && capture){
			if((isLight() && (location2.getY() == location1.getY() + 1)) 
					|| (!isLight() && (location2.getY() == location1.getY() - 1)) ){
				isValid = true;
			}
		}
		
		return isValid;
	}
	
	public String toString(){
		if(isLight()){
			return "Light Pawn";
		}
		else{
			return "Dark Pawn";
		}
	}
	@Override
	public ArrayList<Coordinate> possibleMoves(Coordinate location1) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		int x = location1.getX();
		int y = location1.getY();		
		Coordinate[] possibleMoves = new Coordinate[4];
		
		if(isLight()){
			possibleMoves[0] = new Coordinate(x, y + 1);
			possibleMoves[1] = new Coordinate(x, y + 2);
			possibleMoves[2] = new Coordinate(x + 1, y + 1);
			possibleMoves[3] = new Coordinate(x - 1, y + 1);
		}
		else{
			possibleMoves[0] = new Coordinate(x, y - 1);
			possibleMoves[1] = new Coordinate(x, y - 2);
			possibleMoves[2] = new Coordinate(x + 1, y - 1);
			possibleMoves[3] = new Coordinate(x - 1, y - 1);
		}
		
		for(int i = 0; i < possibleMoves.length; i++){
			if(this.checkInRange(possibleMoves[i])){
				moves.add(possibleMoves[i]);
			}
		}
		
		return moves;
	}
	
}
