package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Board;
import model.Coordinate;
import model.Piece;
import controller.ChessManager;
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
import factories.KnightFactory;
import factories.QueenFactory;
import factories.RookFactory;

public class ChessBoard extends JPanel implements Observer{

	private final int SQUARE_SIZE = 50;
	private MouseController controller;
	private Board board;
	private HashMap<Character, Image> pieceImages;
	private Square[][] guiBoard;
	private Collection<Coordinate> highlight;
	private Image moving;
	private ChessManager manager;
	private int currentX, currentY;
	private Coordinate start, end;
	private boolean isLightTurn;
	
	
	public ChessBoard(Board board, ChessManager manager){
		this.controller = new MouseController();
		controller.addObserver(this);
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
		this.setPreferredSize(new Dimension(SQUARE_SIZE * board.getColumns(), SQUARE_SIZE * board.getRows()));
		this.board = board;
		this.manager = manager;	
		
		highlight = board.allAvailableMoves(true).keySet();
		this.guiBoard = new Square[board.getColumns()][board.getRows()];
		for(int i = 0; i < guiBoard.length; i++){
			for(int j = 0; j < guiBoard[i].length; j++){
				guiBoard[i][j] = new Square();
			}
		}
		
		pieceImages = new HashMap<Character, Image>();
		try {
			pieceImages.put('k', ImageIO.read(new File("data\\images\\black_king.gif")));
			pieceImages.put('K', ImageIO.read(new File("data\\images\\white_king.gif")));
			pieceImages.put('q', ImageIO.read(new File("data\\images\\black_queen.gif")));
			pieceImages.put('Q', ImageIO.read(new File("data\\images\\white_queen.gif")));
			pieceImages.put('b', ImageIO.read(new File("data\\images\\black_bishop.gif")));
			pieceImages.put('B', ImageIO.read(new File("data\\images\\white_bishop.gif")));
			pieceImages.put('n', ImageIO.read(new File("data\\images\\black_knight.gif")));
			pieceImages.put('N', ImageIO.read(new File("data\\images\\white_knight.gif")));
			pieceImages.put('r', ImageIO.read(new File("data\\images\\black_rook.gif")));
			pieceImages.put('R', ImageIO.read(new File("data\\images\\white_rook.gif")));
			pieceImages.put('p', ImageIO.read(new File("data\\images\\black_pawn.gif")));
			pieceImages.put('P', ImageIO.read(new File("data\\images\\white_pawn.gif")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		drawBoard(g);
		highlightAvailableMoves(g);
		dragPiece(g);
	}
	
	public void drawBoard(Graphics g){
		for(Square[] b : guiBoard){
			for(Square a : b){
				g.setColor(a.getColor());
				g.fillRect(a.getX() * SQUARE_SIZE, ( a.getY()) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				if(a.hasPiece()){
					g.drawImage(a.getPiece(), a.getX() * SQUARE_SIZE + 4, (a.getY()) * SQUARE_SIZE + 4, this);
				}
			}
		}
	}
	
	public void updateBoard(){
		
		//Saddle Brown 	139-69-19
		//Brown 	165-42-42
		Color brown = new Color(165, 42, 42);
		boolean drawWhite = true;
		
		for(int i = 0; i < board.getRows(); i++){
			for(int j = 0; j < board.getColumns(); j++){
				Coordinate square = new Coordinate(i, j);
				try {
					guiBoard[i][j].updateSquare(i, j, (board.isEmpty(square) ? null : pieceImages.get((Character)board.getPiece(square).getPieceCharacter())), (drawWhite ? Color.WHITE : brown) );
				} catch (NoPieceException e) {
					//Purposely ignored.
				}
				drawWhite = !drawWhite;
			}
			drawWhite = !drawWhite;
		}
		
		repaint();
	}

	public void highlightAvailableMoves(Graphics g){
		for(Coordinate a : highlight){
			g.setColor(Color.GREEN);
			g.drawRect(a.getX() * SQUARE_SIZE, a.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			g.drawRect(a.getX() * SQUARE_SIZE + 1, a.getY() * SQUARE_SIZE + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
			g.drawRect(a.getX() * SQUARE_SIZE + 2, a.getY() * SQUARE_SIZE + 2, SQUARE_SIZE - 4, SQUARE_SIZE - 4);

		}
	}
	
	public void dragPiece(Graphics g){
		if(moving != null){
			g.drawImage(moving, currentX, currentY, this);
		}
	}
	
	public boolean makeMove(){
		boolean success = false;	
		
		String move = start + " " + end + (board.isEmpty(end) ? "" : "*");
		try {
			manager.interpretMove(move);
			guiBoard[end.getX()][end.getY()].setPiece(moving);
			moving = null;
			boolean isLight = manager.isLightTurn();
			if(board.canPromotePawn()){
				Factory[] pieces = {new QueenFactory(), new BishopFactory(), new KnightFactory(), new RookFactory()};
				Factory pieceMaker = (Factory) JOptionPane.showInputDialog(this, "Which piece do you want to promote to: ", "Pawn Promotion", JOptionPane.QUESTION_MESSAGE, null, pieces, 0);
				Piece replacement = pieceMaker.create(!manager.isLightTurn(), board.getRows(), board.getColumns());
				board.promotePawn(replacement);
				updateBoard();
			}
			if(board.isInCheck(board.getKing(isLight), isLight)){
				JOptionPane.showMessageDialog(this, ((isLight ? "White King" : "Dark King") + " is in check!") , "In Check", JOptionPane.WARNING_MESSAGE);
			}
			if(!board.haveMovesLeft(isLight)){
				JOptionPane.showMessageDialog(this, (isLight ? "White King" : "Dark King") + " is in " + (board.inCheckMate(isLight) ? "checkmate" : "stalemate"), "Checkmate", JOptionPane.INFORMATION_MESSAGE);
				if(JOptionPane.showConfirmDialog(this, "Do you want to play a new game?", "End game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
					manager.startGame();
					updateBoard();
				}
				else{
					JOptionPane.showMessageDialog(this, "Have a good day", "Good Bye", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				}
				
			}
		} catch (NoPieceException | BadMoveException | BlockedPathException
				| NoFriendlyFireException | IllegalMoveException
				| CheckException | OutOfOrderException | CastleException
				| OccupiedSpaceException | BadPromotionException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Bad Move Made", JOptionPane.ERROR_MESSAGE);
			guiBoard[start.getX()][start.getY()].setPiece(moving);
			moving = null;
		}
		
		
		return success;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		boolean change = false;
		
		if(arg instanceof MouseEvent){
			MouseEvent e = (MouseEvent) arg;
			int x = (int) (e.getX() / SQUARE_SIZE);
			int y = (int) (e.getY() / SQUARE_SIZE);
			if(e.getID() == MouseEvent.MOUSE_MOVED){
				Coordinate space = new Coordinate(x, y);
				try {
					Collection<Coordinate> newHighlight = ((!board.isEmpty(space) && board.getPiece(space).isLight() == manager.isLightTurn())
							? board.availableMoves(space) : board.allAvailableMoves(manager.isLightTurn()).keySet());
					if(!newHighlight.equals(highlight)){
						highlight = newHighlight;
						change = true;
					}
				} catch (NoPieceException ex) {
					ex.printStackTrace();
				}
			}
			if(e.getID() == MouseEvent.MOUSE_PRESSED){
				moving = guiBoard[x][y].getPiece();
				guiBoard[x][y].setPiece(null);
				start = new Coordinate(x, y);
			}
			if(e.getID() == MouseEvent.MOUSE_DRAGGED){
				if(moving != null){
					currentX = e.getX();
					currentY = e.getY();
					change = true;
				}
			}
			if(e.getID() == MouseEvent.MOUSE_RELEASED){
				if(moving != null){
					end = new Coordinate(x, y);
					if(!start.equals(end)){
						makeMove();
					}
					else{
						guiBoard[start.getX()][start.getY()].setPiece(moving);
						moving = null;
					}
					change = true;
				}
			}
		}

		if(change){
			repaint();
		}
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
}
