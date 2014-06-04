package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import model.Board;
import model.Coordinate;
import model.Piece;
import controller.ChessManager;
import exceptions.BadMoveException;
import exceptions.BlockedPathException;
import exceptions.CastleException;
import exceptions.CheckException;
import exceptions.IllegalMoveException;
import exceptions.NoFriendlyFireException;
import exceptions.NoPieceException;
import exceptions.OccupiedSpaceException;
import exceptions.OutOfOrderException;

public class TextDisplay {

	private Board board;
	private ChessManager manager;
	private Scanner input;
	private HashMap<String, Character> pieceNames;

	
	public TextDisplay(Board board, ChessManager manager){
		this.board = board;
		this.manager = manager;
		input = new Scanner(System.in);
		pieceNames = new HashMap<String, Character>();
		pieceNames.put("queen", 'Q');
		pieceNames.put("bishop", 'B');
		pieceNames.put("knight", 'N');
		pieceNames.put("rook", 'R');
	}
	
	public void displayText(String message){
		System.out.println(message);
	}
	
	public void displayMove(Piece piece1, Coordinate location1, Piece piece2, Coordinate location2, boolean capture){
		String response = "Move " + piece1.toString() + " at " + location1.toString() + " to the location " + location2.toString();
		if(capture){response += " and capture the " + piece2 + " in that spot.";}
		System.out.println(response);
	}
	public void displayStandardMove(Coordinate location1, Coordinate location2, boolean capture){
		System.out.println(location1 + " " + location2 + (capture ? "*" : ""));
	}
	
	public void displayBoard(){
		System.out.println(board.printBoard());
	}
		
	public void displayCheckStatus(boolean isLight){
		if(board.isInCheck(board.getKing(isLight), isLight)){
			String message = ((isLight ? "White King" : "Dark King") + " is in check!");
			System.out.println(message);
		}
	}
	
	public void displayCheckMate(boolean isLight){
		if(!board.haveMovesLeft(isLight)){
			String message = (isLight ? "White King" : "Dark King") + " is in " + (board.inCheckMate(isLight) ? "checkmate" : "stalemate");
			System.out.println(message);
		}

	}
	
	public void displayException(Exception e){
		System.err.println(e.getMessage());
	}
	
	public void displayPieceMoves(boolean isLight){
		try {
			HashMap<Coordinate, ArrayList<Coordinate>> moves = board.allAvailableMoves(isLight);
			Iterator<Coordinate> pieceLocations = moves.keySet().iterator();
			while(pieceLocations.hasNext()){
				Coordinate piece = pieceLocations.next();
				if(!moves.get(piece).isEmpty()){
					System.out.println(board.getPiece(piece) + " at location " + piece);
				}
			}
		}
		catch (NoPieceException e) {
			
		}
	}
	
	public void displayAvailableMoves(Coordinate piece) throws NoPieceException{
		for(Coordinate a : board.availableMoves(piece)){
			System.out.println(board.getPiece(piece) + " at location " + piece + " can move to " + a);
		}
	}
	
	public void askForMove(boolean isLightTurn){
		boolean success = false;
		String pieceLocation1 = "";
		String pieceLocation2 = "";
		Coordinate piece1 = null;
		Coordinate piece2 = new Coordinate(0, 0);
		
		displayCheckStatus(isLightTurn);
		displayCheckMate(isLightTurn);
		
		displayPieceMoves(isLightTurn);
		while(!success){
			System.out.println("Enter the coordinates of the piece you want to move: ");
			pieceLocation1 = getCoordinate();
			try {
				piece1 = new Coordinate(pieceLocation1.charAt(0), pieceLocation1.charAt(1));
				HashMap<Coordinate, ArrayList<Coordinate>> moves = board.allAvailableMoves(isLightTurn);
				if(moves.containsKey(piece1)){
					displayAvailableMoves(piece1);
					if(!moves.get(piece1).isEmpty()){
						boolean secondMove = false;
						while(!secondMove){
							System.out.println("Enter the coordinates you want to move to (a0 to go back): ");
							pieceLocation2 = getCoordinate();
							if(pieceLocation2.equals("a0")){
								System.out.println();
								displayPieceMoves(isLightTurn);
								secondMove = true;
								}
							piece2 = new Coordinate(pieceLocation2.charAt(0), pieceLocation2.charAt(1));
							if(board.availableMoves(piece1).contains(piece2)){
								success = true;
								secondMove = true;
							}
							else{
								System.err.println("The piece can't move there");
							}
						}
					}
					else{
						System.err.println("That piece has no moves");
					}
				} else{
					System.err.println("There are no moves for you at that coordinate");
				}
			} catch (NoPieceException e) {
				System.err.println("The location " + e.getCoordinate() + " doesn't have a piece" );
			}
		}
		
		String move = pieceLocation1 + " " + pieceLocation2 + (board.isEmpty(piece2) ? "" : "*") ;
		System.out.println("Move " + move);
		try {
			manager.interpretMove(move);
		} catch (NoPieceException | BadMoveException | BlockedPathException
				| NoFriendlyFireException | IllegalMoveException
				| CheckException | OutOfOrderException | CastleException
				| OccupiedSpaceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String getCoordinate(){
		while(true){
			String pieceLocation = input.nextLine();
			if(pieceLocation.matches("([A-Ha-h][1-8])|[Aa]0")){
				return pieceLocation;
			} else{
				System.err.println("Invalid coordinate: " + pieceLocation);
			}
		}
	}
	
	public char getPieceToPromote(){
		Character piece = null;
		
		while (piece == null){
			System.out.println("What would you like to promote your pawn to? (Enter full name, no pawn or king) ");
			String user = input.nextLine();
			user = user.toLowerCase();
			piece = pieceNames.get(user);
			if(piece == null){
				System.err.println("That was not a valid name.");
			}
		}
		return piece;
	}
	
}
