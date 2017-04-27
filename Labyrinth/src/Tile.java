
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author AlexBeard - default author
 * All code not written by Alex - author is specified
 * 
 *
 * One individual tile with all attributes included. Includes rotate, flip, etc
 *
 */
public class Tile {

    //whether tile has a road in/out of that direction of the card
    boolean up;
    boolean right;
    boolean down;
    boolean left;

    boolean isStartTile;
    boolean canMove;

    char treasure = 'Z'; //(Z = no treasure)
    //How treasure is displayed:
    Label treasureLabel;

    //Needs to be arrayList: Can be 2 players on tile
    ArrayList<Player> playersOnTile = new ArrayList<Player>();

    //3x3 array of colored squares that make up tile
    private Rectangle[][] shapes = new Rectangle[3][3];

    //width/height of each of 9 shapes
    double step = Board.squareSize / 3;

    //colors:
    //color when path exists
    Color path;
    //color for background
    Color background;

    double upperLeftX;
    double upperLeftY;
    Board board;

    /**
     * Standard Constructor - takes in all of parameters to construct tile
     * @param board
     * @param upperLeftX
     * @param upperLeftY
     * @param up
     * @param right
     * @param down
     * @param left
     * @param canMove
     * @param startTile 
     */
    Tile(Board board, double upperLeftX, double upperLeftY, boolean up, boolean right, boolean down, boolean left, boolean canMove, boolean startTile) {

        //set colors
        path = Color.DARKSLATEGREY;
        background = canMove ? Color.WHITE : Color.LIGHTGREY;
        this.isStartTile = startTile;

        //set directions/other attributes
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.canMove = canMove;
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.board = board;

        //calls handler for >1 players on 1 tile if necessary
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            //otherwise: calls standard update colors
            updateColors();

    }

    /**
     * Exact Clone Tile constructor
     * @param toClone tile being cloned
     * Written by Eric
     */
    public Tile(Tile toClone) {
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
     * Clone Tile - Changing Board constructor
     * Used for extra tile board display
     * @param toClone tile to be cloned
     * @param board board of new tile
     */
    public Tile(Tile toClone, Board board) {
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
        this.board = board;
    }
    
    /**
     * if multiple players on one tile: 
     * Make into 2 triangles -> displaying both colors
     * Written by Kevin and Alex
     */
    protected void updateMultiColors(){
        
        //updates standard colors
        for (int i = 0; i < shapes.length; i++) {
            for (int j = 0; j < shapes[i].length; j++) {
                shapes[i][j] = new Rectangle(upperLeftX + (j * step), upperLeftY + (i * step), step, step);
                shapes[i][j].setFill(background);
                board.getChildren().add(shapes[i][j]);
            }
        }

        //adjust to reflect up/right/down/left
        //middle: always set to blue
        shapes[1][1].setFill(path);
        
        // Color center tile the color of the player 2
        shapes[1][1].setFill(playersOnTile.get(1).color);
        
        //gather points of triangle
        double[] points = new double[6];
        points[0] = shapes[1][1].getX();
        points[1] = shapes[1][1].getY();
        points[2] = shapes[1][1].getX();
        points[3] = shapes[1][1].getY() + step;
        points[4] = shapes[1][1].getX() + step;
        points[5] = shapes[1][1].getY();
         
        //add triangle that is the color of player 1
        Polygon triangle = new Polygon(points);
        triangle.setFill(playersOnTile.get(0).color);
        board.getChildren().add(triangle);

        //update standard colors
        if (up) {
            shapes[0][1].setFill(path);
        }
        if (right) {
            shapes[1][2].setFill(path);
        }
        if (down) {
            shapes[2][1].setFill(path);
        }
        if (left) {
            shapes[1][0].setFill(path);
        }

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
     * Set colors - used every time something is changed (and there are not >1 players to deal with)
     *
     */
    protected void updateColors() {

        for (int i = 0; i < shapes.length; i++) {
            for (int j = 0; j < shapes[i].length; j++) {
                shapes[i][j] = new Rectangle(upperLeftX + (j * step), upperLeftY + (i * step), step, step);
                shapes[i][j].setFill(background);
                board.getChildren().add(shapes[i][j]);
            }
        }

        //adjust to reflect up/right/down/left
        //middle: always set to blue
        shapes[1][1].setFill(path);

        // Temp - color center tile the color of the player
        for (Player p : playersOnTile) {
            shapes[1][1].setFill(p.color);
        }

        if (up) {
            shapes[0][1].setFill(path);
        }
        if (right) {
            shapes[1][2].setFill(path);
        }
        if (down) {
            shapes[2][1].setFill(path);
        }
        if (left) {
            shapes[1][0].setFill(path);
        }

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
     * @param t treasure char to set tile treasure to
     * Written by Kevin
     */
    public void setTileTreasure(char t) {
        this.treasure = t;
        printTileTreasure();
    }

    /**
     * 
     * @return treasure currently on tile
     * Written by Kevin
     */
    public char getTreasure() {
        return treasure;
    }

    /**
     * 
     * @return true if tile has treasure - false if not
     * Written by Kevin
     */
    public boolean hasTreasure() {
        if (treasure != 'Z') {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Print tile treasure char on label 
     * for tile on 7x7 board
     * Written by Kevin/Eric
     */
    public void printTileTreasure() {

        if (hasTreasure()) {
            treasureLabel = new Label("" + this.treasure);
            treasureLabel.setFont(Font.font("Lucida Grande", 21));
            treasureLabel.setTextFill(Color.web("#f44242"));
            double shift = .45;
            treasureLabel.relocate(upperLeftX + 2.77, upperLeftY - shift);
            board.getChildren().add(treasureLabel);
        }
    }
    
    /**
     * Print tile treasure char on label
     * for tile on 1x1 extra tile board
     * Written by Kevin/Eric
     */
    public void extraTileTreasure(){
              if (hasTreasure()) {
            treasureLabel = new Label("" + this.treasure);
            treasureLabel.setFont(Font.font("Lucida Grande", 21));
            treasureLabel.setTextFill(Color.web("#f44242"));
            double shift = .45;
            treasureLabel.relocate(upperLeftX + 2.77, upperLeftY - shift);
            board.getChildren().add(treasureLabel);
        }
    }  
    
    /**
     * Remove treasure from tile
     * Written by Kevin/Eric
     */
    public void removeTreasure() {
        treasure = 'Z';
        board.getChildren().remove(treasureLabel);
    }

    /**
     * Rotate tile right - only right needed (left = rotateRight(3))
     * @param numberOfTimes to rotate right
     */
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

    /**
     * Set Position of tile
     * Used when inserting tiles into a row
     * @param upperLeftX to move tile to
     * @param upperLeftY to move tile to
     * Written by Eric
     */
    public void setPosition(double upperLeftX, double upperLeftY) {
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();
    }

    /**
     * Add player to tile
     * @param player to add
     * Written by Kevin
     */
    public void setPlayer(Player player) {
        playersOnTile.add(player);
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();
        printTileTreasure();
    }

    /**
     * Remove player from tile
     * @param player to remove
     * Written by Kevin
     */
    public void removePlayer(Player player) {
        playersOnTile.remove(player);
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();
        printTileTreasure();
    }

    /**
     * Checks if a tile contains a specific player
     * @param player to be checked
     * @return true if it does - false if it doesn't
     * Written by Kevin/Eric
     */
    public boolean hasPlayer(Player player) {
        boolean playerFound = false;
        for (Player p : playersOnTile) {
            if (p.color == player.color) {
                playerFound = true;
            }
        }
        return playerFound;
    }

    /**
     * Checks if specified tile is start tile
     * @return true if so / false if not
     * Written by Eric
     */
    public boolean getIsStart() {
        return isStartTile;
    }

}
