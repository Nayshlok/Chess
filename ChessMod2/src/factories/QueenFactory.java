package factories;

import model.Queen;
import model.Piece;

public class QueenFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight, int rowCount, int colCount) {
		return new Queen(isLight, rowCount, colCount);
	}

	@Override
	public String toString(){
		return "Queen";
	}
	
}
