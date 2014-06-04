package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.ChessManager;
/*
public class ChessMenu extends JPanel implements Observer{

	private ChessManager manager;
	private MouseController controller;
	private Polygon startButton;
	private Point lastMove, lastClick;
	
	public ChessMenu(ChessManager manager){
		this.manager = manager;
		controller = new MouseController();
		controller.addObserver(this);
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
		
		this.setPreferredSize(new Dimension(400, 400));
		
		int buttonPointsX[] = {100, 100, 250, 250};
		int buttonPointsY[] = {100, 150, 150, 100};
		this.startButton = new Polygon(buttonPointsX, buttonPointsY, 4);
		
		lastMove = new Point(0, 0);
		lastClick = new Point(0, 0);
	}
	
	@Override
	public void paintComponent(Graphics g){
		this.controller = new MouseController();
		
		g.setColor(new Color(225, 225, 225));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(new Color(255, 255, 200));
		g.fillPolygon(startButton);
		
		g.setColor(Color.BLACK);
		g.drawString("Start Game", 125, 125);
		
		g.drawString("Last Move:" + lastMove.getX() + ", " + lastMove.getY(), 10, 10);
		g.drawString("Last Click: " + lastClick.getX() + ", " + lastClick.getY(), 10, 30);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof MouseEvent){
			MouseEvent e = (MouseEvent) arg;
			if(e.getID() == MouseEvent.MOUSE_MOVED){
				lastMove = e.getPoint();
			}
			if(e.getID() == MouseEvent.MOUSE_CLICKED){
				lastClick = e.getPoint();				
				if(startButton.contains(e.getPoint())){
					manager.startGame();
				}
			}
		}
		
		repaint();
	}
	
}
*/
