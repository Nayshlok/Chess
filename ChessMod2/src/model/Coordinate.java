package model;

import exceptions.OutOfBoardRange;

public class Coordinate {

	private int x, y;
	
	public Coordinate(){}
	
	public Coordinate(int x, int y) throws OutOfBoardRange{
		this.setX(x);
		this.setY(y);
	}
	
	public Coordinate(char x, char y) throws OutOfBoardRange{
		this.setX(convertLettertoInt(x));
		this.setY(convertNumbertoInt(y));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) throws OutOfBoardRange{
		if(x >= 0 && x < 8){
			this.x = x;
		}
		else{
			throw new OutOfBoardRange(x);
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int y) throws OutOfBoardRange {
		if(y >= 0 && y < 8){
			this.y = y;
		}
		else{
			throw new OutOfBoardRange(y);
		}
	}
	
	public int convertNumbertoInt(char y){
		return (int)y - 49;
	}
	
	public int convertLettertoInt(char x){
		x = Character.toUpperCase(x);
		int col = (int)x - 65;
		return col;
	}
	
	public char convertInttoChar(int x){
		x += 65;
		char letter = (char)x;
		return letter;
	}
	
	public String toString(){
		return (convertInttoChar(this.x) + "" + (y + 1));
	}
}
