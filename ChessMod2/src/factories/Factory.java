package factories;

import model.Piece;

public interface Factory {

	public Piece create(boolean isLight);
	
}
