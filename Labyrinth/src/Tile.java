import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author AlexBeard
 *
 *One individual tile with all attributes included. Includes rotate, flip, etc
 *
 */
public class Tile {
	
	//whether tile has a road in/out of that direction of the card
	boolean up;
	boolean right;
	boolean down;
	boolean left;
        
	
	boolean canMove;
	
	//not implemented yet (Z = no treasure)
	char treasure = 'Z';
        
        private Rectangle[][] shapes = new Rectangle[3][3];
        
        

	
	Tile(Board board, int upperLeftX, int upperLeftY,boolean up, boolean right, boolean down, boolean left, boolean canMove) {
		
            
                        //set colors
                        Color path = Color.DARKSLATEGREY;
                        Color background = canMove ? Color.WHITE : Color.LIGHTGREY;
                        
                        
                        //add rectangle to board
                        //shape = new Rectangle(upperLeftX, upperLeftY, Board.squareSize, Board.squareSize);
                        //shape.setFill(Color.BLUE);
                        //shape.setStroke(Color.BLACK);
                        //board.getChildren().add(shape);
                         System.out.println("tile");
                        double step = Board.squareSize/3;
            
                        for (int i = 0; i < shapes.length; i++) {
                            for (int j = 0; j < shapes[i].length; j++) {
                                shapes[i][j] = new Rectangle(upperLeftX + (j*step), upperLeftY + (i*step), step, step);
                                shapes[i][j].setFill(background);
                                System.out.println(i + " " + j + " added");
                                board.getChildren().add(shapes[i][j]);
                            }
                        }
                        
                        //adjust to reflect up/right/down/left
                        
                        //middle: always set to blue
                        shapes[1][1].setFill(path);
                        
                        if (up) { shapes[0][1].setFill(path); }
                        if (right) { shapes[1][2].setFill(path); }
                        if (down) { shapes[2][1].setFill(path); }
                        if (left) { shapes[1][0].setFill(path); }

                         //set strokes to null
                         for (int i = 0; i < shapes.length; i++) {
                            for (int j = 0; j < shapes[i].length; j++) {
                                shapes[i][j].setStroke(shapes[i][j].getFill());

                                }
                            }
                        
                        
                        
                        //outer border
                        Rectangle outer = new Rectangle(upperLeftX, upperLeftY, Board.squareSize, Board.squareSize);
                        outer.setFill(null);
                        outer.setStroke(Color.BLACK);
                        outer.setStrokeWidth(3);
                        board.getChildren().add(outer);
                        
                        
			this.up = up;
			this.right = right;
			this.down = down;
			this.left = left;
			this.canMove = canMove;	


	}
	
	/**
	 * 
	 * Only needed for command line testing
	 */
	public String stringRepresentation() {
		
		ArrayList<String> s = new ArrayList<String>();
		
		if (left) { s.add("L"); }
		if (up) { s.add("U"); }
		if (down) { s.add("D"); }
		if (right) { s.add("R"); }
		if (canMove) { s.add("-"); }
		
		return String.join("",s);

		
	}
	
	public void rotateRight(int numberOfTimes) {
		
		if (numberOfTimes > 0) {
			
			//save old values
			boolean tempUp = up;
			boolean tempRight = right;
			boolean tempDown = down;
			boolean tempLeft = left;
			
			//switch new ones
			up = tempLeft;
			right = tempUp;
			down = tempRight;
			left = tempDown;
			
			//recursive call
			rotateRight(numberOfTimes - 1);
			
		}
			
		
		
	}
	
	
	

}
