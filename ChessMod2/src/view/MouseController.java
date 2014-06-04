package view;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

public class MouseController extends Observable implements MouseListener, MouseMotionListener{

	private Point lastMove, lastPress, lastRelease;
	
	public MouseController() {
		lastMove = new Point(0, 0);
		lastPress = new Point(0, 0);
		lastRelease = new Point(0, 0);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		lastMove = e.getPoint();
		setChanged();
		notifyObservers(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastMove = e.getPoint();
		setChanged();
		notifyObservers(e);		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setChanged();
		notifyObservers(e);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastPress = e.getPoint();
		setChanged();
		notifyObservers(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastRelease = e.getPoint();
		setChanged();
		notifyObservers(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public Point getLastMove() {
		return lastMove;
	}

	public Point getLastPress() {
		return lastPress;
	}

	
	
}
