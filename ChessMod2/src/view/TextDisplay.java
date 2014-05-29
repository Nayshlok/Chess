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
	
	public TextDisplay(Board board, ChessManager manager){
		this.board = board;
		this.manager = manager;
		input = new Scanner(System.in);
	}
	
	public void displayText(String message){
		System.out.println(message);
	}
	
	public void displayMove(Piece piece1, Coordinate location1, Piece piece2, Coordinate location2, boolean capture){
		String response = "Move " + piece1.toString() + " at " + location1.toString() + " to the location " + location2.toString();
		if(capture){response += " and capture the " + piece2 + " in that spot.";}
		System.out.println(response);
	}
	
	public void displayBoard(){
		System.out.println(board.printBoard());
	}
		
	public void displayCheckStatus(boolean isLight){
		String message = (isLight ? "White King" : "Dark King") + " is in check: " + board.isInCheck(board.getKing(isLight), isLight);
		System.out.println(message);
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
						success = true;
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
		success = false;
		while(!success){
			System.out.println("Enter the coordinates you want to move to: ");
			pieceLocation2 = getCoordinate();
			if(pieceLocation2.equals("0")){
				askForMove(isLightTurn);
				return;
				}
			Coordinate piece2 = new Coordinate(pieceLocation2.charAt(0), pieceLocation2.charAt(1));
			try {
				if(board.availableMoves(piece1).contains(piece2)){
					success = true;
				}
				else{
					System.err.println("The piece can't move there");
				}
			} catch (NoPieceException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Move " + pieceLocation1 + " " + pieceLocation2);
		try {
			manager.interpretMove(pieceLocation1 + " " + pieceLocation2);
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
			if(pieceLocation.matches("[A-Ha-h][1-8]") || pieceLocation.equals("0")){
				return pieceLocation;
			} else{
				System.err.println("Invalid coordinate: " + pieceLocation);
			}
		}
	}
	
}
