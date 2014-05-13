package factories;

import model.Rook;
import model.Piece;

public class RookFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight) {
		return new Rook(isLight);
	}

	
}
