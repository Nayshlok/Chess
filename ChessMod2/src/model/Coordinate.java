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
		x = ensureRange(x);
			this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y){
		y = ensureRange(y);
		this.y = y;
	}
	
	public int convertNumbertoInt(char y){
		return (int)y - '1';
	}
	
	public int convertLettertoInt(char x){
		x = Character.toUpperCase(x);
		int col = (int)x - 'A';
		return col;
	}
	
	public char convertInttoChar(int x){
		x += 'A';
		char letter = (char)x;
		return letter;
	}
	
	public String toString(){
		return (convertInttoChar(this.x) + "" + (y + 1));
	}
	
	public int ensureRange(int n){
		if(n > 7){
			n = 7;
		}
		if(n < 0){
			n = 0;
		}
		
		return n;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
