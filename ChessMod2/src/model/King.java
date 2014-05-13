package model;

public class King extends Piece {

	public King(boolean isLight) {
		super(isLight);
		
	}

	
	
	@Override
	public char getPieceCharacter() {
		if(isLight() == true){
			return 'K';
		}
		else{
			return 'k';
		}
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
