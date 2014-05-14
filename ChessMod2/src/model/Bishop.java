package model;

public class Bishop extends Piece {

	public Bishop(boolean isLight) {
		super(isLight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public char getPieceCharacter() {
		if(isLight()){
			return 'B';
		}
		else{
			return 'b';
		}
	}

}
