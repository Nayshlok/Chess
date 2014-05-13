package factories;

import model.Queen;
import model.Piece;

public class QueenFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight) {
		return new Queen(isLight);
	}

	
}
