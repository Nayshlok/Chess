package factories;

import model.Knight;
import model.Piece;

public class KnightFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight) {
		return new Knight(isLight);
	}

	
}
