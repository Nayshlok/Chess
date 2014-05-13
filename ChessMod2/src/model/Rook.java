package model;

public class Rook extends Piece {

	public Rook(boolean isLight) {
		super(isLight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public char getPieceCharacter() {
		if(isLight() == true){
			return 'R';
		}
		else{
			return 'r';
		}
	}

}
