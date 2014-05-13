package factories;

import model.King;
import model.Piece;

public class KingFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight) {
		return new King(isLight);
	}

	
}
