package factories;

import model.Pawn;
import model.Piece;

public class PawnFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight) {
		return new Pawn(isLight);
	}

	
}
