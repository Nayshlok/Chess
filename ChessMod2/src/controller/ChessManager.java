package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Board;
import model.Coordinate;
import model.Piece;
import exceptions.BadMoveException;
import exceptions.BlockedPathException;
import exceptions.CastleException;
import exceptions.IllegalMoveException;
import exceptions.NoFriendlyFireException;
import exceptions.NoPieceException;
import exceptions.OccupiedSpaceException;
import exceptions.OutOfBoardRange;
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

	private int movesMade, failedMoves;
	private Board gameBoard;
	private HashMap<Character, Factory> pieceNames;
	private boolean isLightTurn;
	
	public ChessManager(){
		movesMade =  failedMoves = 0;
		gameBoard = new Board();
		isLightTurn = true;
		
		pieceNames = new HashMap<Character, Factory>();
		pieceNames.put('Q', new QueenFactory());
		pieceNames.put('K', new KingFactory());
		pieceNames.put('B', new BishopFactory());
		pieceNames.put('N', new KnightFactory());
		pieceNames.put('R', new RookFactory());
		pieceNames.put('P', new PawnFactory());
	}

	public void run(String fileName){
		
		readMoves("data\\setupNoPawn.txt");
		System.out.println("Number of succesful moves " + movesMade);
		System.out.println("Number of failed moves " + failedMoves);
		
		System.out.println(gameBoard.printBoard());
		
		readMoves(fileName);
		System.out.println("Number of succesful moves " + movesMade);
		System.out.println("Number of failed moves " + failedMoves);

		try {
			ArrayList<Coordinate> moves = this.gameBoard.getPiece(new Coordinate('G', '8')).possibleMoves(new Coordinate('G', '8'));
			System.out.println();
		} catch (NoPieceException | OutOfBoardRange e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(gameBoard.printBoard());
		
	}
	
	/**
	 * Reads a text file at the path passed into parameter. It will then pass the lines read to the 
	 * interpretMoves method.
	 * @param fileName
	 */
	public void readMoves(String fileName){
		movesMade = 0;
		failedMoves = 0;
		
		FileReader read;
		try {
			read = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(read);
			String line = "";
			
			while((line = reader.readLine()) != null){
				try{
					if(interpretMove(line)){
						movesMade++;
					}
					else if(!line.equalsIgnoreCase("")){
						failedMoves++;
					}
				}
				catch(OutOfBoardRange | OutOfOrderException e){
					//e.printStackTrace();
				}

			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("No file was found at the given location");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("The file did not read properly. Make sure it is a text file with proper information.");
			e.printStackTrace();
		}
		
	}

	/**
	 * Takes in a string and verifies whether it contains move commands, and if it does performs that move if possible
	 * @param move
	 * @return
	 * @throws OutOfBoardRange
	 * @throws OutOfOrderException
	 */
	public boolean interpretMove(String move) throws OutOfBoardRange, OutOfOrderException{
		
		boolean success = false;
		move = move.toUpperCase();
		//Placing pieces
		if(move.matches("[KQBNRP][LD][A-H][1-8]")){

			createPiece(move);
			success = true;
		}
		//Moving one piece, possibly with capture
		if(move.matches("[A-Ha-h][1-8] [A-Ha-h][1-8]\\*?")){
			char col1 = move.charAt(0);
			char row1 = move.charAt(1);
			char col2 = move.charAt(3);
			char row2 = move.charAt(4);
			boolean capture;
			int a = move.length();
			if(move.length() == 6){
				capture = true;
			}
			else{
				capture = false;
			}
			
			Coordinate location1 = new Coordinate(col1, row1);
			Coordinate location2 = new Coordinate(col2, row2);
			
			try {
				Piece piece1 = this.gameBoard.getPiece(location1);
				if(isLightTurn != piece1.isLight()){
					throw new OutOfOrderException(piece1.isLight());
				}

				String response = "Move " + piece1.toString() + " at " + col1 + row1 + " to the location " + col2 + row2;
				if(capture){response += " and capture the " + this.gameBoard.getPiece(location2) + " in that spot.";}

				this.gameBoard.movePieces(location1, location2, capture, false);

				System.out.println(response);
				success = true;
				isLightTurn = !isLightTurn;
				if(!piece1.hasMoved()){piece1.setMoved(true);}
				System.out.println(this.gameBoard.printBoard());

			} catch (NoPieceException | BadMoveException | BlockedPathException | NoFriendlyFireException | IllegalMoveException e) {
				
			}
			
		}
		//Castling move, or any other double move.
		if(move.matches("[A-Ha-h][1-8] [A-Ha-h][1-8] [A-Ha-h][1-8] [A-Ha-h][1-8]")){
			try {
				castle(move);
				success = true;
				isLightTurn = !isLightTurn;
			} catch (CastleException e) {
				System.err.println("The castling failed because " + e.getMessage());
			}	
		}
		return success;
	}
	
	public void createPiece(String move) throws OutOfBoardRange{

		char piece = move.charAt(0);
		char color = move.charAt(1);
		char col = move.charAt(2);
		char row = move.charAt(3);
		
		Coordinate location = new Coordinate(col, row);
		boolean isLight;

		if(color == 'L'){
			isLight = true;
		} else {
			isLight = false;
		}
		
		Piece newPiece = pieceNames.get(piece).create(isLight, gameBoard.getRows(), gameBoard.getColumns());
		
		try {
			this.gameBoard.placePiece(newPiece, location);
		} catch (OccupiedSpaceException e) {

		}			
		
		//System.out.println("Place " + newPiece + " at location " 
		//		+ Character.toUpperCase(col) + row);
	}
	
	/**
	 * Takes in a double move string and tests first if the pieces are valid for a castle,
	 * then it will see if the coordinates work for a valid 
	 * @param move
	 * @throws CastleException
	 * @throws OutOfOrderException
	 */
	public void castle(String move) throws CastleException, OutOfOrderException{
		char col1Piece1 = move.charAt(0);
		char row1Piece1 = move.charAt(1);
		char col2Piece1 = move.charAt(3);
		char row2Piece1 = move.charAt(4);
		
		char col1Piece2 = move.charAt(6);
		char row1Piece2 = move.charAt(7);
		char col2Piece2 = move.charAt(9);
		char row2Piece2 = move.charAt(10);
		
		try {
					
			Coordinate kingLocation1;
			Coordinate kingLocation2;
			Coordinate rookLocation1;
			Coordinate rookLocation2;
			Piece piece1 = gameBoard.getPiece(new Coordinate(col1Piece1, row1Piece1));
			Piece piece2 = gameBoard.getPiece(new Coordinate(col1Piece2, row1Piece2));
			
			if(isLightTurn != piece1.isLight()){
				throw new OutOfOrderException(piece1.isLight());
			}
			//Making sure the pieces are right
			if(piece1.isLight() != piece2.isLight()){
				throw new CastleException("Piece are not the same color");
			}
			if(!piece1.hasMoved() && !piece2.hasMoved()){
				if(Character.toUpperCase(piece1.getPieceCharacter()) == 'K' && Character.toUpperCase(piece2.getPieceCharacter()) == 'R'){
					kingLocation1 = new Coordinate(col1Piece1, row1Piece1);
					kingLocation2 = new Coordinate(col2Piece1, row2Piece1);
					rookLocation1 = new Coordinate(col1Piece2, row1Piece2);
					rookLocation2 = new Coordinate(col2Piece2, row2Piece2);									
				}
				else if (Character.toUpperCase(piece1.getPieceCharacter()) == 'R' && Character.toUpperCase(piece2.getPieceCharacter()) == 'K'){
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
			
			
			if(!gameBoard.checkPath(rookLocation1, kingLocation1)){
				throw new BlockedPathException();
			}
			
			//Logic of the movement
			int kingChangeX = Math.abs(kingLocation1.getX() - kingLocation2.getX());
			int kingChangeY = Math.abs(kingLocation1.getY() - kingLocation2.getY());
			int rookChangeX = Math.abs(rookLocation1.getX() - rookLocation2.getX());
			int rookChangeY = Math.abs(rookLocation1.getY() - rookLocation2.getY());
			int rookMove;			
			if(rookLocation1.getX() < kingLocation1.getX()){
				rookMove = 3;
			} else{
				rookMove = 2;
			}
			
			if(kingChangeY == 0 && kingChangeX == 2 && rookChangeY == 0 && rookChangeX == rookMove){
				gameBoard.movePieces(rookLocation1, rookLocation2, false, true);
				gameBoard.movePieces(kingLocation1, kingLocation2, false, true);
			} else{
				throw new CastleException("The coordinates were not right for a castle");
			}
			piece1.setMoved(true);
			piece2.setMoved(true);
			System.out.println(this.gameBoard.printBoard());
			
		} catch (NoPieceException | OutOfBoardRange | BadMoveException | NoFriendlyFireException | BlockedPathException | IllegalMoveException e) {
			
		}
	}
}
