package factories;

import model.King;
import model.Piece;

public class KingFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight, int rowCount, int colCount) {
		return new King(isLight, rowCount, colCount);
	}

	
}
