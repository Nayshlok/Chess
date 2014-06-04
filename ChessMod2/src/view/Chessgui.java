package view;

import javax.swing.JFrame;

import model.Board;
import controller.ChessManager;

public class Chessgui {

	private JFrame window;
	private ChessBoard mainPanel;
	
	public Chessgui(Board board, ChessManager manager){
		
		this.mainPanel = new ChessBoard(board, manager);
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(mainPanel);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	
	public void playGame(Board board){
		mainPanel.setBoard(board);
		window.repaint();
	}
	
	public void update(){
		mainPanel.updateBoard();
	}
	
}
