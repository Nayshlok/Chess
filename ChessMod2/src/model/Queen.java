package model;

public class Queen extends Piece {

	public Queen(boolean isLight) {
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
			return 'Q';
		}
		else{
			return 'q';
		}
	}

}
