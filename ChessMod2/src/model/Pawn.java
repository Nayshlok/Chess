package model;

public class Pawn extends Piece {

	public Pawn(boolean isLight) {
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
			return 'P';
		}
		else{
			return 'p';
		}
	}

}
