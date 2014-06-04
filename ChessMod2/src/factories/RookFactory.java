package factories;

import model.Rook;
import model.Piece;

public class RookFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight, int rowCount, int colCount) {
		return new Rook(isLight, rowCount, colCount);
	}

	@Override
	public String toString(){
		return "Rook";
	}
}
