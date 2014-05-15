package model;

public class Queen extends Piece {

	public Queen(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'Q';
		}
		else{
			return 'q';
		}
	}


	@Override
	public void move(Coordinate toLocation, boolean capture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean moveCheck(Coordinate location1, Coordinate location2) {
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
		if(isLight()){
			return "Light Queen";
		}
		else{
			return "Dark Queen";
		}
	}
	
}
