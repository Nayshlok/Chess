package factories;

import model.Pawn;
import model.Piece;

public class PawnFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight, int rowCount, int colCount) {
		return new Pawn(isLight, rowCount, colCount);
	}

	
}
