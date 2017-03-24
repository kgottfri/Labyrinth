import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A Pane in which tetris squares can be displayed.
 *
 * @author pipWolfe/Alex Beard
 */
public class TetrisBoard extends Pane {

    // The size of the side of a tetris square
    public static final int SQUARE_SIZE = 20;

    // The number of squares that fit on the screen in the x and y dimensions
    //not final so it can be changed depending on gameType
    public int X_DIM_SQUARES = 10;
    public int Y_DIM_SQUARES = 20;

    //Array to contain squares that are filled ( [y][x] format )
    public TetrisSquare[][] filledSquares = new TetrisSquare[Y_DIM_SQUARES + 1][X_DIM_SQUARES + 1];

    /**
     * Sizes the board to hold the specified number of squares in the x and y
     * dimensions.
     */
    public TetrisBoard() {
        this.setPrefHeight((Y_DIM_SQUARES + 1) * SQUARE_SIZE);
        this.setPrefWidth((X_DIM_SQUARES + 1) * SQUARE_SIZE);

    }

    /**
     * resize tetris board (called if dimensions are changed)
     * changes board to correct dimensions
     */
    public void resize() {

        this.setPrefHeight((Y_DIM_SQUARES + 1) * SQUARE_SIZE);
        this.setPrefWidth((X_DIM_SQUARES + 1) * SQUARE_SIZE);
    }

    /**
     *
     * @param piece to be tested if it is on a filled space (clone piece)
     * @return true if piece is on a filled space, false if not
     */
    private Boolean filled(TetrisPiece piece) {

        if (filledSquares[piece.square1.getY()][piece.square1.getX()] != null
                || filledSquares[piece.square2.getY()][piece.square2.getX()] != null
                || filledSquares[piece.square3.getY()][piece.square3.getX()] != null
                || filledSquares[piece.square4.getY()][piece.square4.getX()] != null) {

            return true;

        } else {

            return false;
        }

    }

    /**
     *
     * @param piece to be tested if it is out of bounds (clone piece)
     * @return whether or not that spot is out of bounds
     */
    private Boolean outOfBounds(TetrisPiece piece) {

        if (Math.max(piece.square1.getX(), Math.max(piece.square2.getX(), Math.max(piece.square3.getX(), piece.square4.getX()))) > X_DIM_SQUARES) {
            return true;
        }

        if (Math.min(piece.square1.getX(), Math.min(piece.square2.getX(), Math.min(piece.square3.getX(), piece.square4.getX()))) < 0) {
            return true;

        }

        if (Math.max(piece.square1.getY(), Math.max(piece.square2.getY(), Math.max(piece.square3.getY(), piece.square4.getY()))) > Y_DIM_SQUARES) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param piece to be tested
     * @return if piece's location is safe (not out of bounds or filled)
     */
    public Boolean safe(TetrisPiece piece) {

        if (outOfBounds(piece)) {
            return false;

        } else {
            if (filled(piece)) {
                return false;

            }
        }

        return true;

    }

    //square versions of above methods
    //used for moving after row deletion 
    //individual squares need to be moved instead of pieces
    
    /**
     *
     * @param square to be tested if it is on a filled space (clone piece)
     * @return true if piece is on a filled space, false if not
     */
    private Boolean filled(TetrisSquare square) {

        if (filledSquares[square.getY()][square.getX()] != null) {

            return true;

        } else {

            return false;
        }

    }

    /**
     *
     * @param piece to be tested if it is out of bounds (clone piece)
     * @return whether or not that spot is out of bounds
     */
    private Boolean outOfBounds(TetrisSquare square) {

        if (square.getX() > this.X_DIM_SQUARES) {

            return true;

        }

        if (square.getY() > this.Y_DIM_SQUARES) {

            return true;

        }

        return false;

    }

    /**
     *
     * @param square to be tested
     * @return if piece's location is safe (not out of bounds or filled)
     */
    public Boolean safe(TetrisSquare square) {

        if (outOfBounds(square)) {
            return false;

        } else {
            if (filled(square)) {
                return false;

            }
        }

        return true;

    }

}