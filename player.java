import java.util.LinkedList;
import javafx.scene.layout.*;

public class player extends Pane{
        protected LinkedList<Card> treasuresHand;
        public boolean activePlayer = false;
        protected int score = 0;
        protected int successfulRequest = 0;
        protected int totalRequest = 0;
        protected int treasuresGotten = 0;
	public static final int Prows = 7;
	public static final int Pcols = 1;
	public static final int squareWidth=450;
	public static final int squareHeight=100;
	
	public player(){
		this.setPrefHeight(Prows*squareHeight);
		this.setPrefWidth(Pcols*squareWidth);
	}
	
	public int getX_DIM(){
		return Prows;
	}
	public int getY_DIM(){
		return Pcols;
	}
}