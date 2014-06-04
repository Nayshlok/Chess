package factories;

import model.Knight;
import model.Piece;

public class KnightFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight, int rowCount, int colCount) {
		return new Knight(isLight, rowCount, colCount);
	}

	@Override
	public String toString(){
		return "Knight";
	}
}
