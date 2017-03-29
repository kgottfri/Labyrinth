import javafx.scene.layout.*;

public class board extends Pane{
	public static final int rows = 7;
	public static final int cols = 7;
	public static final int squareSize=100;
	
	public board(){
		this.setPrefHeight(cols*squareSize);
		this.setPrefWidth(rows*squareSize);
	}
	//test
	public int getX_DIM(){
		return rows;
	}
	public int getY_DIM(){
		return cols;
	}
}