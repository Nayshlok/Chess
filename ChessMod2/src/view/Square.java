package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Square{

	private Image piece;
	private int x, y;
	private Color color;
	
	public Square(){
		
	}
	
	public Square(int x, int y, Image piece, Color color){
		this.x = x;
		this.y = y;
		this.piece = piece;
		this.color = color;
	}

	public void updateSquare(int x, int y, Image piece, Color color){
		this.x = x;
		this.y = y;
		this.piece = piece;
		this.color = color;
	}
	
	public boolean hasPiece(){
		return (piece != null);
	}	
	public Image getPiece() {
		return piece;
	}
	
	public void setPiece(Image piece){
		this.piece = piece;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public Color getColor(){
		return color;
	}

}
