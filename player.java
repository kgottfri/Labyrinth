import javafx.scene.layout.*;

public class player extends Pane{
	public static final int Prows = 7;
	public static final int Pcols = 1;
	public static final int squareWidth=450;
	public static final int squareHeight=100;
	
	public player(){
		this.setPrefHeight(Prows*squareHeight);
		this.setPrefWidth(Pcols*squareWidth);
		this.setStyle("-fx-background-color: Black");
	}
	
	public int getX_DIM(){
		return Prows;
	}
	public int getY_DIM(){
		return Pcols;
	}
}