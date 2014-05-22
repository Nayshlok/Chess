package exceptions;

public class OutOfBoardRange extends Exception{

	public OutOfBoardRange(){
		super();
	}
	public OutOfBoardRange(int number){
		super(number + " is out of the range of the board, it goes between 0 and 7.");
	}
	
}
