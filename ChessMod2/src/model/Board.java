package model;

import exceptions.NoPieceException;
import exceptions.BadMoveException;
import exceptions.OccupiedSpaceException;

public class Board {

	private Piece[][] board;
	private final int ROW = 8;
	private final int COLUMN = 8;
	
	public Board(){
		board = new Piece[COLUMN][ROW];
	}

	public String printBoard(){
		
		String printedBoard = "  ABCDEFGH\n";
		int count = 1;
		for(int i = 0; i < board.length; i++){
			printedBoard += count + "|";
			count++;
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == null){
					printedBoard += '-';
				}
				else{					
					printedBoard += board[i][j].getPieceCharacter();
				}
			}
			printedBoard += "\n";
		}


		return printedBoard;
	}

	public void movePieces(Coordinate location1, Coordinate location2, boolean capture) throws NoPieceException, BadMoveException{
		
		Piece holder = null;
		if(!isEmpty(location1)){
			holder = getPiece(location1);
			if((isEmpty(location2) && !capture) ||
					(!isEmpty(location2) && capture)){
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

	public void placePiece(Piece piece, Coordinate location) throws OccupiedSpaceException{
		if(isEmpty(location)){
			board[location.getY()][location.getX()] = piece;
		}
		else {
			throw new OccupiedSpaceException(location);
		}
		
	}

	public boolean isEmpty(Coordinate location){
		if(board[location.getY()][location.getX()] == null){
			return true;
		}
		else{
			return false;
		}
	}

	public Piece getPiece(Coordinate location) throws NoPieceException{
		if(board[location.getY()][location.getX()] != null){
			return board[location.getY()][location.getX()];
		}
		else{
			throw new NoPieceException(location);
		}
	}

}
