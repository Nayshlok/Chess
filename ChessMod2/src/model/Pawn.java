package model;

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
		
		if((location1.getX() == location2.getX()) && !capture){
			if((isLight() && (location2.getY() == location1.getY() + 1))
					|| (!isLight() && (location2.getY() == location1.getY() - 1))) {
				isValid = true;
			}
			if(!hasMoved() && ((isLight() && (location2.getY() == location1.getY() + 2)) 
					|| (!isLight() && (location2.getY() == location1.getY() - 2)))) {
				isValid = true;
			}
		}
		else if(((location2.getX() == location1.getX() + 1) || (location2.getX() == location1.getX() - 1)) && capture){
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
	
}
