package factories;

import model.Piece;

public interface Factory {

	public Piece create(boolean isLight, int rowCount, int colCount);
	
}
