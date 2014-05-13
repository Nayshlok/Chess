package model;

import exceptions.NoPieceException;
import exceptions.BadMoveException;

public class Board {

	private Piece[][] board;
	
	public Board(){
		board = new Piece[8][8];
		
		/*
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j] = null;
			}
		}
		*/
	}
	
	public String printBoard(){
		
		String printedBoard = "  ABCDEFGH\n";
		int count = 1;
		for(int i = 0; i < board.length; i++){
			printedBoard += count + "|";
			count++;
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] != null){
					printedBoard += board[i][j].getPieceCharacter();
				}
				else{
					printedBoard += '-';
				}
			}
			printedBoard += "\n";
		}

		
		return printedBoard;
	}
	
	public void placePiece(Piece piece, Coordinate location){
		board[location.getY()][location.getX()] = piece;
		
	}
	
	public void movePieces(Coordinate location1, Coordinate location2, boolean capture) throws NoPieceException, BadMoveException{
		
		Piece holder = null;
		Piece a = board[location2.getY()][location2.getX()];
		if(board[location1.getY()][location1.getX()] != null){
			holder = board[location1.getY()][location1.getX()];
			if((board[location2.getY()][location2.getX()] == null && capture == false) ||
					(board[location2.getY()][location2.getX()] != null && capture == true)){
				board[location1.getY()][location1.getX()] = null;
				board[location2.getY()][location2.getX()] = holder;
			}
			else{
				throw new BadMoveException(location2, capture);
			}
		}
		else{
			throw new NoPieceException(location1);
		}
	}
	
}
