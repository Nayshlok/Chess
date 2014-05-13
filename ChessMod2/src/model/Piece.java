package model;

public abstract class Piece {

	private boolean isLight;
	
	public Piece(boolean isLight){
		this.setLight(isLight);
	}
	
	public abstract void move();
	
	public boolean isLight() {
		return isLight;
	}

	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}
	
	public abstract char getPieceCharacter();
	
}
