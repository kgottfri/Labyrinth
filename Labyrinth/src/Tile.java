import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

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
        boolean hasTreasure = false;
	ArrayList<Player> playersOnTile = new ArrayList<Player>();
        
    private Rectangle[][] shapes = new Rectangle[3][3];
        
    double step = Board.squareSize/3;

    Color path;
    Color background;
        
    double upperLeftX;
    double upperLeftY;
    Board board;

	Tile(Board board, double upperLeftX, double upperLeftY,boolean up, boolean right, boolean down, boolean left, boolean canMove) {
		
            
                        //set colors
                        path = Color.DARKSLATEGREY;
                        background = canMove ? Color.WHITE : Color.LIGHTGREY;
                        
                        
                        //add rectangle to board
                        //shape = new Rectangle(upperLeftX, upperLeftY, Board.squareSize, Board.squareSize);
                        //shape.setFill(Color.BLUE);
                        //shape.setStroke(Color.BLACK);
                        //board.getChildren().add(shape);
                         //System.out.println("tile");
                        
			this.up = up;
			this.right = right;
			this.down = down;
			this.left = left;
			this.canMove = canMove;	

                        this.upperLeftX = upperLeftX;
                        this.upperLeftY = upperLeftY;
                        this.board = board;
                        
                        updateColors();

                        
	}

    public Tile(Tile toClone){
        this.up = toClone.up;
        this.right = toClone.right;
        this.down = toClone.down;
        this.left = toClone.left;
        this.canMove = toClone.canMove;
        this.treasure = toClone.treasure;
        this.playersOnTile = toClone.playersOnTile;
        this.shapes = toClone.shapes;
        this.step = toClone.step;
        this.path = toClone.path;
        this.background = toClone.background;
        this.upperLeftX = toClone.upperLeftX;
        this.upperLeftY = toClone.upperLeftY;
        this.board = toClone.board;
    }
        /**
         * Set color - used every time somethign is changed
         * 
         */
        protected void updateColors() {

            for (int i = 0; i < shapes.length; i++) {
                for (int j = 0; j < shapes[i].length; j++) {
                    shapes[i][j] = new Rectangle(upperLeftX + (j*step), upperLeftY + (i*step), step, step);
                    shapes[i][j].setFill(background);
                    //System.out.println(i + " " + j + " added");
                    board.getChildren().add(shapes[i][j]);
                }
            }
                        
            //adjust to reflect up/right/down/left
                        
            //middle: always set to blue
            shapes[1][1].setFill(path);

            // Temp - color center tile the color of the player
            for (Player p:playersOnTile){
                shapes[1][1].setFill(p.color);
            }

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
	public void setTileTreasure(char t){
            this.treasure = t;
            this.hasTreasure = true;
            printTileTreasure();
        }
        public boolean hasTreasure(){
            return hasTreasure;
            
        }
        public void printTileTreasure(){
            if(treasure != 'Z'){
                Label treasure = new Label(""+this.treasure);
                treasure.setFont(Font.font("Cambria",21));
                double shift = .75;
                treasure.relocate(upperLeftX + 4, upperLeftY + shift);
                board.getChildren().add(treasure);
            }
        }
	public void rotateRight(int numberOfTimes) {
		
        if (canMove) {
		    if (numberOfTimes > 0 ) {
			
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
            updateColors();
        }
	}

	public void setPosition(double upperLeftX, double upperLeftY){
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        updateColors();
    }

    public void setPlayer(Player player){
	    playersOnTile.add(player);
        updateColors();
    }

    public void removePlayer(Player player){
        playersOnTile.remove(player);
        updateColors();
    }

    public boolean hasPlayer(Player player){
        boolean playerFound = false;
        for (Player p:playersOnTile){
            if (p.color == player.color){
                playerFound = true;
            }
        }
        return playerFound;
    }
	
	
	

}
