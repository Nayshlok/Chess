package factories;

import model.Bishop;
import model.Piece;

public class BishopFactory implements Factory{
	
	@Override
	public Piece create(boolean isLight, int rowCount, int colCount) {
		return new Bishop(isLight, rowCount, colCount);
	}

	@Override
	public String toString(){
		return "Bishop";
	}
}
