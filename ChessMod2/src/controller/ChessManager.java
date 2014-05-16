package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import model.Board;
import model.Coordinate;
import model.Pawn;
import model.Piece;
import exceptions.BadMoveException;
import exceptions.BlockedPathException;
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

		//System.out.println(gameBoard.printBoard());
		
	}
	
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

	public boolean interpretMove(String move) throws OutOfBoardRange, OutOfOrderException{
		
		boolean success = false;
		move = move.toUpperCase();
		if(move.matches("[KQBNRP][LD][A-H][1-8]")){

			createPiece(move);
			success = true;
		}
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
				boolean legalMove = false;

				if(piece1.moveCheck(location1, location2, capture)){

					String response = "Move " + piece1.toString() + " at " + col1 + row1 + " to the location " + col2 + row2;
					if(capture){response += " and capture the " + this.gameBoard.getPiece(location2) + " in that spot.";}

					this.gameBoard.movePieces(location1, location2, capture);

					System.out.println(response);
					success = true;
					isLightTurn = !isLightTurn;
					if(!piece1.hasMoved()){piece1.setMoved(true);}
					System.out.println(this.gameBoard.printBoard());
				}
				else{
					System.err.println("Invalid move for " + piece1);
				}
			} catch (NoPieceException | BadMoveException | BlockedPathException | NoFriendlyFireException e) {
				
			}
			
		}
		if(move.matches("[A-Ha-h][1-8] [A-Ha-h][1-8] [A-Ha-h][1-8] [A-Ha-h][1-8]")){
			char col1Piece1 = move.charAt(0);
			char row1Piece1 = move.charAt(1);
			char col2Piece1 = move.charAt(3);
			char row2Piece1 = move.charAt(4);
			
			char col1Piece2 = move.charAt(6);
			char row1Piece2 = move.charAt(7);
			char col2Piece2 = move.charAt(9);
			char row2Piece2 = move.charAt(10);
			
			try {

				this.gameBoard.castle(new Coordinate(col1Piece1, row1Piece1), new Coordinate(col2Piece1, row2Piece1), 
						new Coordinate(col1Piece2, row1Piece2), new Coordinate(col2Piece2, row2Piece2));
				System.out.println("Move piece at " + col1Piece1 + row1Piece1 + " to " + col2Piece1 + row2Piece1 + " and move piece at " 
						+ col1Piece2 + row1Piece2 + " to " + col2Piece2 + row2Piece2);
				success = true;
				
			} catch (NoPieceException e) {
				
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
}
