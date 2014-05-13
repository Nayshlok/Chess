package factories;

import model.Bishop;
import model.Piece;

public class BishopFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight) {
		return new Bishop(isLight);
	}

	
}
