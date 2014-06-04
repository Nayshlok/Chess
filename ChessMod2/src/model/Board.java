package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import exceptions.BadMoveException;
import exceptions.BadPromotionException;
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
public class Board{

	private Piece[][] board;
	private final int ROW = 8;
	private final int COLUMN = 8;
	private Coordinate lightKing, darkKing, pawnLocation;
	private boolean canPromotePawn;
	
	public Board(){
		board = new Piece[COLUMN][ROW];
	}
	
	public Board(Piece[][] newBoard){
		board = newBoard;
	}
	/**
	 * Returns a string containing an ascii board of the current status of chess.
	 * @return
	 */
	public String printBoard(){
		
		String printedBoard = "";
		for(int i = board.length - 1; i >= 0; i--){
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

		printedBoard += "  A B C D E F G H\n";
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
	private boolean tryMove(Coordinate location1, Coordinate location2, boolean capture, boolean castle, boolean checkingCheck) throws NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException{
		
		Piece holder = getPiece(location1);
		pieceMoveCheck(location1, location2, capture, castle);
		spaceCheck(location2, capture, checkingCheck);
		friendlyFireCheck(location1, location2, capture, checkingCheck);
		if((pathIsClear(location1, location2))
				|| (holder instanceof Knight)
				|| castle){
			if(!checkingCheck){
				moveCauseCheck(holder, location1, location2);
			}
			return true;
		}
		else{
			throw new BlockedPathException();
		}
	}
	
	public boolean legalMove(Coordinate location1, Coordinate location2, boolean capture, boolean castle, boolean checkingCheck) throws NoPieceException {

		try {
			tryMove(location1, location2, capture, castle, checkingCheck);
			return true;
		} catch (BadMoveException | BlockedPathException
				| NoFriendlyFireException | IllegalMoveException | CheckException e) {
			//Purposely ignore. The point of this method is to see if it is possible without caring about exceptions.
		}
		return false;
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
		
		if(tryMove(location1, location2, capture, castle, false)){
			Piece holder = getPiece(location1);
			if(holder instanceof King){
				if(holder.isLight()){
					lightKing = location2;
				} else{
					darkKing = location2;
				}
			}
			if(holder instanceof Pawn && (location2.getY() == 7 || location2.getY() == 0)){
				canPromotePawn = true;
				pawnLocation = location2;
			}
			board[location1.getY()][location1.getX()] = null;
			board[location2.getY()][location2.getX()] = holder;
		}
	
	}

	/**
	 * This method will draw a diagonal or straight path from the second location to the first checking that all spaces are empty.
	 * If a piece exists on any square it will return false.
	 * @param location1
	 * @param location2
	 * @return
	 */
	public boolean pathIsClear(Coordinate location1, Coordinate location2){
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
	
	public void moveCauseCheck(Piece holder, Coordinate location1, Coordinate location2) throws CheckException{
		Coordinate king = (holder instanceof King) ? location2 : (holder.isLight() ? lightKing : darkKing);
		boolean inCheck = false;

		Piece holder2 = null;
		try {
			holder2 = getPiece(location2);
		} catch (NoPieceException e) {
			//left blank so that the holder2 will remain null when replacing the piece.
		}
		board[location1.getY()][location1.getX()] = null;
		board[location2.getY()][location2.getX()] = holder;
		inCheck = isInCheck(king, holder.isLight());
		board[location1.getY()][location1.getX()] = holder;
		board[location2.getY()][location2.getX()] = holder2;
		
		if(inCheck){
			throw new CheckException(holder.isLight());
		}
		
	}
	
	public void friendlyFireCheck(Coordinate location1, Coordinate location2, boolean capture, boolean checkingCheck) throws NoFriendlyFireException{
		if(capture && !checkingCheck){
			if(board[location1.getY()][location1.getX()].isLight() == board[location2.getY()][location2.getX()].isLight()){
				throw new NoFriendlyFireException();
			}
		}
	}
	
	public void spaceCheck(Coordinate location, boolean capture, boolean checkingCheck) throws BadMoveException{
		if(!(isEmpty(location) && (!capture || checkingCheck)) &&
		!(!isEmpty(location) && (capture))){
			throw new BadMoveException(location, capture);
		}
	}
	
	public void pieceMoveCheck(Coordinate location1, Coordinate location2, boolean capture, boolean castle) throws NoPieceException, IllegalMoveException{
		Piece holder = getPiece(location1);
		if(!holder.moveCheck(location1, location2, capture) && !(holder instanceof King && castle)){
			throw new IllegalMoveException(holder.toString());
		}
	}
	
	/**
	 * Runs through the board checking to see if the king of the color passed in is in check in the specified location.
	 * @param king
	 * @param isLight
	 * @return
	 */ 
	public boolean isInCheck(Coordinate king, boolean isLight){
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				Coordinate space = new Coordinate(i, j);
				if(!isEmpty(space)){
					try{
						Piece holder = getPiece(space);
						if((holder.isLight() != isLight) &&	inDanger(king, space, holder.possibleMoves(space))){
							return true;
						}
					} catch(NoPieceException e){
						//Left empty because I know I am picking up empty slots while I iterate through the board.
					}
				}
			}
		}
		
		return false;
	}

	public boolean inDanger(Coordinate king, Coordinate otherPiece, ArrayList<Coordinate> moves) throws NoPieceException{
		return moves.contains(king) && legalMove(otherPiece, king, true, false, true);
	}
	
	public ArrayList<Coordinate> availableMoves(Coordinate location) throws NoPieceException{
		
		ArrayList<Coordinate> available = new ArrayList<Coordinate>();
		ArrayList<Coordinate> possible = getPiece(location).possibleMoves(location);
		
		for(int i = 0; i < possible.size(); i++){
			if(legalMove(location, possible.get(i), false, false, false) ||
					legalMove(location, possible.get(i), true, false, false)){
				available.add(possible.get(i));
			}
		}

		return available;
	}
	
	public HashMap<Coordinate, ArrayList<Coordinate>> allAvailableMoves(boolean isLight){
		HashMap<Coordinate, ArrayList<Coordinate>> allMoves = new HashMap<Coordinate, ArrayList<Coordinate>>();
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				Coordinate test = new Coordinate(i, j);
				try{
					if(getPiece(test).isLight() == isLight){
						if(!availableMoves(test).isEmpty()){
							allMoves.put(test, availableMoves(test));
						}
					}
				} catch(NoPieceException e){
					//I'm going through the entire board and expect to get empty spaces. 
				}
			}
		}
		
		return allMoves;
	}
	
	public boolean haveMovesLeft(boolean isLight){

		HashMap<Coordinate, ArrayList<Coordinate>> moves = allAvailableMoves(isLight);
		Iterator<Coordinate> pieceLocations = moves.keySet().iterator();
		for(Coordinate piece: moves.keySet()){
			ArrayList<Coordinate> pieceMoves = moves.get(piece);
			if(!pieceMoves.isEmpty()){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * If the location specified is clear, it will place a piece on the board.
	 * @param piece
	 * @param location
	 * @throws OccupiedSpaceException
	 */
	public void placePiece(Piece piece, Coordinate location) throws OccupiedSpaceException{
		if(!isEmpty(location)){
			throw new OccupiedSpaceException(location);
		}
		board[location.getY()][location.getX()] = piece;
		if(piece instanceof King){
			if (piece.isLight()){
				lightKing = location;
			} else{
				darkKing = location;
			}
		}		
	}

	/**
	 * Checks to see if the specified space is clear. Returns false if a piece exists at the coordinate
	 * @param location
	 * @return
	 */
	public boolean isEmpty(Coordinate location){
		return (board[location.getY()][location.getX()] == null);
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

	public boolean inCheckMate(boolean isLight){
		return (this.isInCheck(getKing(isLight), isLight) && !this.haveMovesLeft(isLight));
	}
	
	public void promotePawn(Piece replacement) throws BadPromotionException{
		if(!canPromotePawn){
			throw new BadPromotionException("You are not allowed to promote yet");
		}
		if(replacement instanceof King || replacement instanceof Pawn){
			throw new BadPromotionException("That is not a valid piece to promote to.");
		}
		board[pawnLocation.getY()][pawnLocation.getX()] = replacement;
		canPromotePawn = false;
	}
	
	public boolean canPromotePawn(){
		return canPromotePawn;
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
	
	public Piece[][] getBoard(){
		Piece[][] test = new Piece[COLUMN][ROW];
		for(int i = 0; i < test.length; i++){
			for(int j = 0; j < test[i].length; j++){
				test[i][j] = board[i][j];
			}
		}
		return test;
	}
}
