package model;

public class Coordinate {

	private int x, y;
	
	public Coordinate(){}
	
	public Coordinate(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	
	public Coordinate(char x, char y){
		this.setX(convertLettertoInt(x));
		this.setY(convertNumbertoInt(y));
	}

	public int getX() {
		return x;
	}

	public void setX(int x){
			this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y){
			this.y = y;
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
	
	@Override
	public boolean equals(Object a){
		boolean isEqual = false;
		
		if(a instanceof Coordinate){
			if((this.getX() == ((Coordinate)a).getX())
					&& (this.getY() == ((Coordinate)a).getY())){
				isEqual = true;
			}
		}
		
		return isEqual;
	}
}
