package model;

import exceptions.BlockedPathException;
import exceptions.NoFriendlyFireException;
import exceptions.NoPieceException;
import exceptions.BadMoveException;
import exceptions.OccupiedSpaceException;
import exceptions.OutOfBoardRange;

public class Board {

	private Piece[][] board;
	private final int ROW = 8;
	private final int COLUMN = 8;
	
	public Board(){
		board = new Piece[COLUMN][ROW];
	}

	public String printBoard(){
		
		String printedBoard = "  A B C D E F G H\n";
		int count = 1;
		for(int i = 0; i < board.length; i++){
			printedBoard += count + "|";
			count++;
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == null){
					printedBoard += "- ";
				}
				else{					
					printedBoard += board[i][j].getPieceCharacter() + " ";
				}
			}
			printedBoard += "\n";
		}


		return printedBoard;
	}

	public void movePieces(Coordinate location1, Coordinate location2, boolean capture) throws NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException{
		
		Piece holder = null;
		if(!isEmpty(location1)){
			holder = getPiece(location1);
			if((isEmpty(location2) && !capture) ||
					(!isEmpty(location2) && capture)){
				if(capture){
					if(board[location1.getY()][location1.getX()].isLight() == board[location2.getY()][location2.getX()].isLight()){
						throw new NoFriendlyFireException();
					}
				}
				if((checkPath(location1, location2))
						|| (Character.toUpperCase(holder.getPieceCharacter()) == 'N') ){
					board[location1.getY()][location1.getX()] = null;
					board[location2.getY()][location2.getX()] = holder;
				}
				else{
					throw new BlockedPathException();
				}
			}
			else{
				throw new BadMoveException(location2, capture);
			}
		}
		else{
			throw new NoPieceException(location1);
		}
	}

	public boolean checkPath(Coordinate location1, Coordinate location2){
		boolean pathClear = true;
		
		int x1 = location1.getX();
		int y1 = location1.getY();
		int x2 = location2.getX();
		int y2 = location2.getY();
		boolean firstCheckDone = false;
		
		PathChecker: while(x2 != x1 || y2 != y1){		
			
			if(firstCheckDone){
				try {
					if(!isEmpty(new Coordinate(x2, y2))){
						pathClear = false;
						break PathChecker;
					}
				} catch (OutOfBoardRange e) {
					e.printStackTrace();
				}
			}
			else{
				firstCheckDone = true;
			}
			
			if(x2 < x1){
				x2++;
			}
			if(x2 > x1){
				x2--;
			}
			if(y2 < y1){
				y2++;
			}
			if(y2 > y1){
				y2--;
			}
					
		}
		
		return pathClear;
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

	public int getColumns(){
		return COLUMN;
	}
	
	public int getRows(){
		return ROW;
	}
	
}
