
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author AlexBeard
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
    Label treasureLabel;

    char treasure = 'Z'; //(Z = no treasure)

    ArrayList<Player> playersOnTile = new ArrayList<Player>();

    private Rectangle[][] shapes = new Rectangle[3][3];

    double step = Board.squareSize / 3;

    Color path;
    Color background;

    double upperLeftX;
    double upperLeftY;
    Board board;

    Tile(Board board, double upperLeftX, double upperLeftY, boolean up, boolean right, boolean down, boolean left, boolean canMove, boolean startTile) {

        //set colors
        path = Color.DARKSLATEGREY;
        background = canMove ? Color.WHITE : Color.LIGHTGREY;
        this.isStartTile = startTile;

        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.canMove = canMove;

        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.board = board;

        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();

    }

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
    protected void updateMultiColors(){
        
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
        
        // Color center tile the color of the player
        shapes[1][1].setFill(playersOnTile.get(1).color);
            double[] points = new double[6];
            points[0] = shapes[1][1].getX();
            points[1] = shapes[1][1].getY();
            points[2] = shapes[1][1].getX();
            points[3] = shapes[1][1].getY() + step;
            points[4] = shapes[1][1].getX() + step;
            points[5] = shapes[1][1].getY();
            Polygon triangle = new Polygon(points);
            triangle.setFill(playersOnTile.get(0).color);
            board.getChildren().add(triangle);

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
     * Set color - used every time something is changed
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

    public void setTileTreasure(char t) {
        this.treasure = t;
        printTileTreasure();
    }

    public char getTreasure() {
        return treasure;
    }

    public boolean hasTreasure() {
        if (treasure != 'Z') {
            return true;
        } else {
            return false;
        }

    }

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
    
    public void removeTreasure() {
        treasure = 'Z';
        board.getChildren().remove(treasureLabel);
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

    public void setPosition(double upperLeftX, double upperLeftY) {
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();
    }

    public void setPlayer(Player player) {
        playersOnTile.add(player);
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();
        printTileTreasure();
    }

    public void removePlayer(Player player) {
        playersOnTile.remove(player);
        if (playersOnTile.size() > 1)
            updateMultiColors();
        else
            updateColors();
        printTileTreasure();
    }

    public boolean hasPlayer(Player player) {
        boolean playerFound = false;
        for (Player p : playersOnTile) {
            if (p.color == player.color) {
                playerFound = true;
            }
        }
        return playerFound;
    }

    public boolean getIsStart() {
        return isStartTile;
    }

}
