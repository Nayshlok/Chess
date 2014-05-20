package model;

import exceptions.BlockedPathException;
import exceptions.IllegalMoveException;
import exceptions.NoFriendlyFireException;
import exceptions.NoPieceException;
import exceptions.BadMoveException;
import exceptions.OccupiedSpaceException;
import exceptions.OutOfBoardRange;

/**
 * The board holds the locations of all the pieces as well as peforms the actual move. It will check to see if the move can 
 * be performed or if there is something in the way. It doesn't care about the pieces individual rules, but rather the physical
 * possibility of a move.
 * @author David Borland
 *
 */
public class Board {

	private Piece[][] board;
	private final int ROW = 8;
	private final int COLUMN = 8;
	
	public Board(){
		board = new Piece[COLUMN][ROW];
	}
	
	/**
	 * Returns a string containing an ascii board of the current status of chess.
	 * @return
	 */
	public String printBoard(){
		
		String printedBoard = "  A B C D E F G H\n";
		for(int i = 0; i < board.length; i++){
			printedBoard += (i+1) + "|";
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

	/**
	 * This method performs checks to see first if there is a piece to grab, then if there is a piece at the location you want to move to.
	 * At the same time it checks to see if you gave the command to capture. If the capture is true and the place is empty it will throw a
	 * BadMoveException(), also if you aren't capturing and the second place is full it will throw a BadMoveException. Finally it makes sure
	 * that there is a clear path to the second location. It ignores individual piece rules.
	 * @param location1
	 * @param location2
	 * @param capture
	 * @param castle
	 * @throws NoPieceException
	 * @throws BadMoveException
	 * @throws BlockedPathException
	 * @throws NoFriendlyFireException
	 * @throws IllegalMoveException 
	 */	
	public void movePieces(Coordinate location1, Coordinate location2, boolean capture, boolean castle) throws NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException{
		
		Piece holder = getPiece(location1);
		if(holder.moveCheck(location1, location2, capture)){
			if((isEmpty(location2) && !capture) ||
					(!isEmpty(location2) && capture)){
				if(capture){
					if(board[location1.getY()][location1.getX()].isLight() == board[location2.getY()][location2.getX()].isLight()){
						throw new NoFriendlyFireException();
					}
				}
				if((checkPath(location1, location2))
						|| (Character.toUpperCase(holder.getPieceCharacter()) == 'N'
						|| castle) ){
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
		} else{
			throw new IllegalMoveException(holder.toString());
		}
	}

	/**
	 * This method will draw a diagonal or straight path from the second location to the first checking that all spaces are empty.
	 * If a piece exists on any square it will return false.
	 * @param location1
	 * @param location2
	 * @return
	 */
	public boolean checkPath(Coordinate location1, Coordinate location2){
		boolean pathClear = true;
		
		int x1 = location1.getX();
		int y1 = location1.getY();
		int x2 = location2.getX();
		int y2 = location2.getY();
		boolean firstCheckDone = false;
		
		PathChecker: while(x2 != x1 || y2 != y1){		
			
			if(firstCheckDone){
				if(!isEmpty(new Coordinate(x2, y2))){
					pathClear = false;
					break PathChecker;
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
	
	/**
	 * If the location specified is clear, it will place a piece on the board.
	 * @param piece
	 * @param location
	 * @throws OccupiedSpaceException
	 */
	public void placePiece(Piece piece, Coordinate location) throws OccupiedSpaceException{
		if(isEmpty(location)){
			board[location.getY()][location.getX()] = piece;
		}
		else {
			throw new OccupiedSpaceException(location);
		}
		
	}

	/**
	 * Checks to see if the specified space is clear. Returns false if a piece exists at the coordinate
	 * @param location
	 * @return
	 */
	public boolean isEmpty(Coordinate location){
		if(board[location.getY()][location.getX()] == null){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Returns the piece at a specified coordinate. If no piece exists it will throw a NoPieceException
	 * @param location
	 * @return
	 * @throws NoPieceException
	 */
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
