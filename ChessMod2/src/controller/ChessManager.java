package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import model.Board;
import model.Coordinate;
import model.King;
import model.Piece;
import model.Rook;
import view.Chessgui;
import view.TextDisplay;
import exceptions.BadMoveException;
import exceptions.BadPromotionException;
import exceptions.BlockedPathException;
import exceptions.CastleException;
import exceptions.CheckException;
import exceptions.IllegalMoveException;
import exceptions.NoFriendlyFireException;
import exceptions.NoPieceException;
import exceptions.OccupiedSpaceException;
import exceptions.OutOfOrderException;
import factories.BishopFactory;
import factories.Factory;
import factories.KingFactory;
import factories.KnightFactory;
import factories.PawnFactory;
import factories.QueenFactory;
import factories.RookFactory;

/**
 * The purpose of this class is to act as the player. He is the one who asks the pieces if they can 
 * generally make a specific move, the submits it to the board to make the move.
 * @author David Borland
 *
 */
public class ChessManager {

	private Board gameBoard;
	private TextDisplay textDisplay;
	private HashMap<Character, Factory> pieceNames;
	private boolean isLightTurn;
	private Chessgui gui;
	
	public ChessManager(){
		gameBoard = new Board();
		textDisplay = new TextDisplay(gameBoard, this);
		isLightTurn = true;
		
		pieceNames = new HashMap<Character, Factory>();
		pieceNames.put('Q', new QueenFactory());
		pieceNames.put('K', new KingFactory());
		pieceNames.put('B', new BishopFactory());
		pieceNames.put('N', new KnightFactory());
		pieceNames.put('R', new RookFactory());
		pieceNames.put('P', new PawnFactory());
		
		
		gui = new Chessgui(gameBoard, this);
	}

	public void run(String fileName){

		readMoves("data\\setup.txt");

		//textDisplay.displayBoard();

		readMoves(fileName);
//		textDisplay.displayBoard();
//		this.playGame();
		
//		textDisplay.displayBoard();
//		textDisplay.displayCheckStatus(true);
//		textDisplay.displayCheckStatus(false);
//		textDisplay.displayCheckMate(true);
//		textDisplay.displayCheckMate(false);
		gui.update();
		//textDisplay.displayPieceMoves(true);

	}

	public void startGame(){
		gameBoard = new Board();
		readMoves("data\\setup.txt");
		isLightTurn = true;
		gui.playGame(gameBoard);
	}
	
//	public void playGame(){
//		while(gameBoard.haveMovesLeft(isLightTurn)){
//			textDisplay.askForMove(isLightTurn);
//			if(gameBoard.canPromotePawn()){
//				Piece replacement = pieceNames.get(textDisplay.getPieceToPromote()).create(!isLightTurn, gameBoard.getRows(), gameBoard.getColumns());
//				try {
//					gameBoard.promotePawn(replacement);
//				} catch (BadPromotionException e) {
//					textDisplay.displayException(e);
//				}
//			}
//			
//			textDisplay.displayBoard();
//		}
//	}
	
	/**
	 * Reads a text file at the path passed into parameter. It will then pass the lines read to the 
	 * interpretMoves method.
	 * @param fileName
	 */
	public void readMoves(String fileName){
	
		FileReader read;
		try {
			read = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(read);
			String line = "";
			
			while((line = reader.readLine()) != null){
				try{
					interpretMove(line);
				}
				catch(OutOfOrderException | NoPieceException | BadMoveException | BlockedPathException | NoFriendlyFireException | IllegalMoveException | CheckException | CastleException | OccupiedSpaceException e){
//					textDisplay.displayException(e);
					System.err.println(e.getMessage());
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 *Takes in a string and verifies whether it contains move commands, and if it does performs that move if possible
	 * @param move
	 * @return
	 * @throws NoPieceException
	 * @throws BadMoveException
	 * @throws BlockedPathException
	 * @throws NoFriendlyFireException
	 * @throws IllegalMoveException
	 * @throws CheckException
	 * @throws OutOfOrderException
	 * @throws CastleException
	 * @throws OccupiedSpaceException 
	 */
	public boolean interpretMove(String move) throws NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException, OutOfOrderException, CastleException, OccupiedSpaceException{
		
		boolean success = false;
		move = move.toUpperCase();
		//Placing pieces
		if(move.matches("[KQBNRP][LD][A-H][1-8]")){
			char piece = move.charAt(0);
			char color = move.charAt(1);
			char col = move.charAt(2);
			char row = move.charAt(3);
			
			Coordinate location = new Coordinate(col, row);
			boolean isLight = (color == 'L') ? true : false;
			
			createPiece(piece, location, isLight);
			success = true;
		}
		//Moving one piece, possibly with capture
		if(move.matches("[A-Ha-h][1-8] [A-Ha-h][1-8]\\*?")){
			char col1 = move.charAt(0);
			char row1 = move.charAt(1);
			char col2 = move.charAt(3);
			char row2 = move.charAt(4);
			boolean capture = (move.length() == 6) ? true : false;
			
			Coordinate location1 = new Coordinate(col1, row1);
			Coordinate location2 = new Coordinate(col2, row2);
			
	
			if(movePiece(location1, location2, capture)){
				changeTurn();
				success = true;
			}
			
		}
		//Castling move, or any other double move.
		if(move.matches("[A-Ha-h][1-8] [A-Ha-h][1-8] [A-Ha-h][1-8] [A-Ha-h][1-8]")){
				//switch turns
				if(castle(move)){
					changeTurn();
					success = true;
				}	
		}
		return success;
	}
	
	public void createPiece(char piece, Coordinate location, boolean isLight) throws OccupiedSpaceException{
		
		Piece newPiece = pieceNames.get(piece).create(isLight, gameBoard.getRows(), gameBoard.getColumns());		
		this.gameBoard.placePiece(newPiece, location);
	}
	
	
	public boolean movePiece(Coordinate location1, Coordinate location2, boolean capture) throws OutOfOrderException, NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException{
		
		//Turn handling
		Piece piece1 = this.gameBoard.getPiece(location1);
		if(isLightTurn != piece1.isLight()){
			throw new OutOfOrderException(piece1.isLight());
		}
		Piece piece2 = null;
		if(!gameBoard.isEmpty(location2)){
			piece2 = gameBoard.getPiece(location2);
		}
		this.gameBoard.movePieces(location1, location2, capture, false);

		textDisplay.displayStandardMove(location1, location2, capture);
		//textDisplay.displayMove(piece1, location1, piece2, location2, capture);
		//Switch turns
		if(!piece1.hasMoved()){piece1.setMoved(true);}
		//textDisplay.displayBoard();
		gui.update();
		
		return true;
	}
	
	//This doesn't work right now. It needs to check if the king is in check, or passes through check
	/**
	 * Takes in a double move string and tests first if the pieces are valid for a castle,
	 * then it will see if the coordinates work for a valid 
	 * @param move
	 * @throws CastleException
	 * @throws OutOfOrderException
	 * @throws CheckException 
	 * @throws IllegalMoveException 
	 * @throws NoFriendlyFireException 
	 * @throws BlockedPathException 
	 * @throws BadMoveException 
	 * @throws NoPieceException 
	 */
	public boolean castle(String move) throws CastleException, OutOfOrderException, NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException{
		char col1Piece1 = move.charAt(0);
		char row1Piece1 = move.charAt(1);
		char col2Piece1 = move.charAt(3);
		char row2Piece1 = move.charAt(4);
		
		char col1Piece2 = move.charAt(6);
		char row1Piece2 = move.charAt(7);
		char col2Piece2 = move.charAt(9);
		char row2Piece2 = move.charAt(10);		
				
		Coordinate kingLocation1;
		Coordinate kingLocation2;
		Coordinate rookLocation1;
		Coordinate rookLocation2;
		Piece piece1 = gameBoard.getPiece(new Coordinate(col1Piece1, row1Piece1));
		Piece piece2 = gameBoard.getPiece(new Coordinate(col1Piece2, row1Piece2));
		//Turn handling
		if(isLightTurn != piece1.isLight()){
			throw new OutOfOrderException(piece1.isLight());
		}
		//Making sure the pieces are right
		if(piece1.isLight() != piece2.isLight()){
			throw new CastleException("Piece are not the same color");
		}
		if(!piece1.hasMoved() && !piece2.hasMoved()){
			if(piece1 instanceof King && piece2 instanceof Rook){
				kingLocation1 = new Coordinate(col1Piece1, row1Piece1);
				kingLocation2 = new Coordinate(col2Piece1, row2Piece1);
				rookLocation1 = new Coordinate(col1Piece2, row1Piece2);
				rookLocation2 = new Coordinate(col2Piece2, row2Piece2);									
			}
			else if (piece1 instanceof Rook && piece2 instanceof King){
				kingLocation1 = new Coordinate(col1Piece2, row1Piece2);
				kingLocation2 = new Coordinate(col2Piece2, row2Piece2);
				rookLocation1 = new Coordinate(col1Piece1, row1Piece1);
				rookLocation2 = new Coordinate(col2Piece1, row2Piece1);
			}
			else{
				throw new CastleException("Wrong piece or pieces");
			}
		} else{
			throw new CastleException("Piece has already moved.");
		}
		castleMove(kingLocation1, kingLocation2, rookLocation1, rookLocation2);
		piece1.setMoved(true);
		piece2.setMoved(true);
//		textDisplay.displayBoard();
		return true;

	}
	
	public boolean castleMove(Coordinate kingLocation1, Coordinate kingLocation2, Coordinate rookLocation1, Coordinate rookLocation2) throws CastleException, NoPieceException, BadMoveException, BlockedPathException, NoFriendlyFireException, IllegalMoveException, CheckException{
		
		Piece king = gameBoard.getPiece(kingLocation1);
		if(!gameBoard.pathIsClear(rookLocation1, kingLocation1)){
			throw new BlockedPathException();
		}
		
		Coordinate secondCheck = kingLocation1.getX() > kingLocation2.getX() ? new Coordinate(kingLocation1.getX() - 1, kingLocation1.getY()) : new Coordinate(kingLocation1.getX() + 1, kingLocation1.getY());
		if(gameBoard.isInCheck(kingLocation1, king.isLight())
				|| gameBoard.isInCheck(secondCheck, king.isLight())
				|| gameBoard.isInCheck(kingLocation2, king.isLight())){
			throw new CastleException("The castling puts the king in check");
		}
		
		//Logic of the movement
		int kingChangeX = Math.abs(kingLocation1.getX() - kingLocation2.getX());
		int kingChangeY = Math.abs(kingLocation1.getY() - kingLocation2.getY());
		int rookChangeX = Math.abs(rookLocation1.getX() - rookLocation2.getX());
		int rookChangeY = Math.abs(rookLocation1.getY() - rookLocation2.getY());
		int rookMove = (rookLocation1.getX() > kingLocation1.getX()) ? 3 : 2;
		
		if(kingChangeY == 0 && kingChangeX == 2 && rookChangeY == 0 && rookChangeX == rookMove){
			gameBoard.movePieces(rookLocation1, rookLocation2, false, true);
			gameBoard.movePieces(kingLocation1, kingLocation2, false, true);
		} else{
			throw new CastleException("The coordinates were not right for a castle");
		}
		return true;
	}
	
	public void changeTurn(){
		isLightTurn = !isLightTurn;
	}

	public boolean isLightTurn() {
		return isLightTurn;
	}
	
}
