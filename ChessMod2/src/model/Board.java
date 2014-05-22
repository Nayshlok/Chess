package model;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.BadMoveException;
import exceptions.BlockedPathException;
import exceptions.CheckException;
import exceptions.IllegalMoveException;
import exceptions.NoFriendlyFireException;
import exceptions.NoPieceException;
import exceptions.OccupiedSpaceException;

/**
 * The board holds the locations of all the pieces as well as performs the actual move. It will check to see if the move can 
 * be performed or if there is something in the way. It doesn't care about the pieces individual rules, but rather the physical
 * possibility of a move.
 * @author David Borland
 *
 */
public class Board {

	private Piece[][] board;
	private final int ROW = 8;
	private final int COLUMN = 8;
	private Coordinate lightKing, darkKing;
	
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
	 * @throws CheckException 
	 */	
	private boolean tryMove(Coordinate location1, Coordinate location2, boolean capture, boolean castle) throws NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException{
		
		Piece holder = getPiece(location1);
		pieceMoveCheck(location1, location2, capture);
		spaceCheck(location2, capture);
		friendlyFireCheck(location1, location2, capture);
		if((checkPath(location1, location2))
				|| (holder instanceof Knight)
				|| castle){
			Coordinate king = holder.isLight() ? lightKing : darkKing;
			boolean inCheck = false;
			if(!isEmpty(location2)){
				Piece holder2 = getPiece(location2);
				board[location1.getY()][location1.getX()] = null;
				board[location2.getY()][location2.getX()] = holder;
				inCheck = checkForCheck(king, holder.isLight());
				board[location1.getY()][location1.getX()] = holder;
				board[location2.getY()][location2.getX()] = holder2;
			} else{
				board[location1.getY()][location1.getX()] = null;
				inCheck = checkForCheck(king, holder.isLight());
				board[location1.getY()][location1.getX()] = holder;
			}
			if(inCheck){
				throw new CheckException(holder.isLight());
			}else {
				return true;
			}
		}
		else{
			throw new BlockedPathException();
		}
	}
	
	/**
	 * If the move is valid as checked in the tryMove() method, it will move the pieces on the board.
	 * @param location1
	 * @param location2
	 * @param capture
	 * @param castle
	 * @throws NoPieceException
	 * @throws BadMoveException
	 * @throws BlockedPathException
	 * @throws NoFriendlyFireException
	 * @throws IllegalMoveException
	 * @throws CheckException 
	 */
	public void movePieces(Coordinate location1, Coordinate location2, boolean capture, boolean castle) throws NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException{
		
		if(tryMove(location1, location2, capture, castle)){
			Piece holder = getPiece(location1);
			if(holder instanceof King){
				if(holder.isLight()){
					lightKing = location2;
				} else{
					darkKing = location2;
				}
			}
			board[location1.getY()][location1.getX()] = null;
			board[location2.getY()][location2.getX()] = holder;
		}
	
	}
	
	public boolean legalMove(Coordinate location1, Coordinate location2, boolean capture, boolean castle) throws NoPieceException {

		try {
			tryMove(location1, location2, capture, castle);
			return true;
		} catch (BadMoveException | BlockedPathException
				| NoFriendlyFireException | IllegalMoveException | CheckException e) {
			//Purposely ignore. The point of this method is to see if it is possible without caring about exceptions.
		}
		return false;
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
	
	public void friendlyFireCheck(Coordinate location1, Coordinate location2, boolean capture) throws NoFriendlyFireException{
		if(capture){
			if(board[location1.getY()][location1.getX()].isLight() == board[location2.getY()][location2.getX()].isLight()){
				throw new NoFriendlyFireException();
			}
		}
	}
	
	public boolean spaceCheck(Coordinate location, boolean capture) throws BadMoveException{
		if((isEmpty(location) && !capture) ||
		(!isEmpty(location) && capture)){
			return true;
		}
		else{
			throw new BadMoveException(location, capture);
		}
	}
	
	public boolean pieceMoveCheck(Coordinate location1, Coordinate location2, boolean capture) throws NoPieceException, IllegalMoveException{
		Piece holder = getPiece(location1);
		if(holder.moveCheck(location1, location2, capture)){
			return true;
		} else{
			throw new IllegalMoveException(holder.toString());
		}
	}
	/**
	 * Runs through the board checking to see if the king of the color passed in is in check in the specified location.
	 * @param king
	 * @param isLight
	 * @return
	 */
	public boolean checkForCheck(Coordinate king, boolean isLight){
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				Coordinate space = new Coordinate(i, j);
				if(!isEmpty(space)){
					try{
						Piece holder = getPiece(space);
						if((holder.isLight() != isLight) &&	findDanger(king, space, holder.possibleMoves(space))){
							return true;
						}
					} catch(NoPieceException e){
						
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean findDanger(Coordinate king, Coordinate otherPiece, ArrayList<Coordinate> moves) throws NoPieceException{
		boolean isDanger = false;
		
		if(moves.contains(king) && legalMove(otherPiece, king, true, false)){
			isDanger = true;
		}
		
		return isDanger;
	}
	
	public ArrayList<Coordinate> availableMoves(Coordinate location) throws NoPieceException{
		
		ArrayList<Coordinate> available = new ArrayList<Coordinate>();
		
		for(Coordinate a: getPiece(location).possibleMoves(location)){
			if(legalMove(location, a, false, false) ||
					legalMove(location, a, true, false)){
				available.add(a);
			}
		}
		
		return available;
	}
	
	public HashMap<Coordinate, ArrayList<Coordinate>> allAvailableMoves(boolean isLight) throws NoPieceException{
		HashMap<Coordinate, ArrayList<Coordinate>> allMoves = new HashMap<Coordinate, ArrayList<Coordinate>>();
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				Coordinate test = new Coordinate(i, j);
				if(!isEmpty(test) && getPiece(test).isLight() == isLight){
					allMoves.put(test, availableMoves(test));
				}
			}
		}
		
		return allMoves;
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
			if(piece instanceof King){
				if (piece.isLight()){
					lightKing = location;
				} else{
					darkKing = location;
				}
			}
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

	public Coordinate getKing(boolean isLight){
		Coordinate king = isLight ? lightKing : darkKing;
		return king;
	}
	
	public int getColumns(){
		return COLUMN;
	}
	
	public int getRows(){
		return ROW;
	}
	
}
