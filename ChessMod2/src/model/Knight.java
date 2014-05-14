package model;

public class Knight extends Piece {

	public Knight(boolean isLight) {
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
			return 'N';
		}
		else{
			return 'n';
		}
	}

}
