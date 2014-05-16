package model;

public class King extends Piece {

	public King(boolean isLight, int rowCount, int colCount) {
		super(isLight, rowCount, colCount);
		
	}

	
	
	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'K';
		}
		else{
			return 'k';
		}
	}

	@Override
	public void move(Coordinate toLocation, boolean capture) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean moveCheck(Coordinate location1, Coordinate location2, boolean capture) {
		boolean isValid = false;
		
		if((location2.getX() == location1.getX())
				|| (location2.getX() == location1.getX() + 1)
				|| (location2.getX() == location1.getX() - 1)){
			if((location2.getY() == location1.getY())
					|| (location2.getY() == location1.getY() + 1)
					|| (location2.getY() == location1.getY() - 1)){
				isValid = true;
			}
		}
		
		return isValid;
	}

	public String toString(){
		if(isLight()){
			return "Light King";
		}
		else{
			return "Dark King";
		}
	}
}
