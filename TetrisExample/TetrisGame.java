import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This should be implemented to include your game control.
 *
 * @author pipWolfe/Alex Beard
 *
 *
 */
public class TetrisGame {

    private final Tetris tetrisApp;
    TetrisPiece currentPiece;
    TetrisBoard board;
    
    //used for testing if moves/drops are safe
    TetrisPiece testPiece;
    TetrisSquare testSquare;

    //game speed (can be changed)
    public double millisec = 800;

    //initialize score variable
    int score;

    //each board instance has a scoreLabel
    Label scoreLabel;

    int gameType;

    boolean gameOver = false;

    //used for score bonuses
    int numberOfRowsDeleted = 0;
    
    //puzzle squares - could have done this in a better way
    TetrisSquare pSquare1;
    TetrisSquare pSquare2;
    TetrisSquare pSquare3;
    TetrisSquare pSquare4;
    TetrisSquare pSquare5;
    TetrisSquare pSquare6;
    TetrisSquare pSquare7;
    TetrisSquare pSquare8;

    /**
     * @param tetrisApp A reference to the application (use to set messages).
     * @param board A reference to the board on which Squares are drawn
     */
    public TetrisGame(Tetris tetrisApp, TetrisBoard board, int gameType) {
   
        //applicable for all game types
        this.board = board;

        this.gameType = gameType;

        this.tetrisApp = tetrisApp;

        //initialize label as null;
        scoreLabel = new Label(" ");

        //create first piece
        TetrisPiece piece = new TetrisPiece(board.X_DIM_SQUARES / 2, 1, randomPieceNumber(), board);

        //set currentPiece
        currentPiece = piece;

        //create and assign testPiece
        TetrisPiece testPiece = new TetrisPiece(board.X_DIM_SQUARES / 2, 1, 1, board);

        this.testPiece = testPiece;

        //hide testPiece
        testPiece.hide();

        //create and assign testSquare
        TetrisSquare testSquare = new TetrisSquare(board);

        this.testSquare = testSquare;

        //hide testSquare
        testSquare.hide();

        //initialize label as blank
        scoreLabel = new Label(" ");

        //specific to gameType:
        
        //battle mode
        if (gameType == 0) {

            //change statusLabel to instructions
            tetrisApp.setMessage("Outlast the other player!");

            //change update speed
            //starts slightly slower, is then updated to be faster and faster
            millisec = 900;

            //make board smaller
            board.X_DIM_SQUARES = 8;
            board.Y_DIM_SQUARES = 15;

            //resize board
            board.resize();

        }

        //score mode
        if (gameType == 1) {

            //change statusLabel to instructions
            tetrisApp.setMessage("First player to 200 wins!");

            //add score label
            scoreLabel.setText(score + " ");

        }

        //puzzle mode
        if (gameType == 2) {
            
            //change board size
            board.Y_DIM_SQUARES = 15;
            board.resize();

            //change statusLabel to instructions
            tetrisApp.setMessage("Get rid of the Blue Puzzle Piece!");

            //create puzzle piece with squares, change colors, move to correct locations
            pSquare1 = new TetrisSquare(board);
            pSquare2 = new TetrisSquare(board);
            pSquare3 = new TetrisSquare(board);
            pSquare4 = new TetrisSquare(board);
            pSquare5 = new TetrisSquare(board);
            pSquare6 = new TetrisSquare(board);
            pSquare7 = new TetrisSquare(board);
            pSquare8 = new TetrisSquare(board);

            pSquare1.moveToTetrisLocation(4, board.Y_DIM_SQUARES - 2);
            pSquare2.moveToTetrisLocation(4, board.Y_DIM_SQUARES - 3);
            pSquare3.moveToTetrisLocation(4, board.Y_DIM_SQUARES - 4);
            pSquare4.moveToTetrisLocation(5, board.Y_DIM_SQUARES - 4);
            pSquare5.moveToTetrisLocation(6, board.Y_DIM_SQUARES - 4);
            pSquare6.moveToTetrisLocation(7, board.Y_DIM_SQUARES - 4);
            pSquare7.moveToTetrisLocation(7, board.Y_DIM_SQUARES - 3);
            pSquare8.moveToTetrisLocation(7, board.Y_DIM_SQUARES - 2);

            board.filledSquares[pSquare1.getY()][pSquare1.getX()] = pSquare1;
            board.filledSquares[pSquare2.getY()][pSquare2.getX()] = pSquare2;
            board.filledSquares[pSquare3.getY()][pSquare3.getX()] = pSquare3;
            board.filledSquares[pSquare4.getY()][pSquare4.getX()] = pSquare4;
            board.filledSquares[pSquare5.getY()][pSquare5.getX()] = pSquare5;
            board.filledSquares[pSquare6.getY()][pSquare6.getX()] = pSquare6;
            board.filledSquares[pSquare7.getY()][pSquare7.getX()] = pSquare7;
            board.filledSquares[pSquare8.getY()][pSquare8.getX()] = pSquare8;

            pSquare1.setColor(Color.DARKBLUE);
            pSquare2.setColor(Color.DARKBLUE);
            pSquare3.setColor(Color.DARKBLUE);
            pSquare4.setColor(Color.DARKBLUE);
            pSquare5.setColor(Color.DARKBLUE);
            pSquare6.setColor(Color.DARKBLUE);
            pSquare7.setColor(Color.DARKBLUE);
            pSquare8.setColor(Color.DARKBLUE);
            
          
        }

    }

    /**
     * Animate the game, by moving the current tetris piece down.
     */
    void update() {
        
        //gradually increase speed if battle mode
        if (gameType == 0 && millisec >= 0) {
        millisec -= 5;
        }
       
        
        //move testPiece to currentPiece location
        testPiece.moveTestPiece(currentPiece);
        testPiece.down();

        //if testPiece is safe: move currentPiece
        if (board.safe(testPiece)) {
            currentPiece.down();
        } else {

            switchPieces();

        }

        //hide testPiece
        testPiece.hide();

        //check for rows that can be deleted
        checkRows();

    }

    /**
     * check for rows that can be deleted
     */
    void checkRows() {

        //initialize rowFilled variable as true (disproved if a spot does not contain a square)
        boolean rowFilled = true;

        //start from top row
        //check each row - delete if necessary
        for (int i = 0; i <= board.Y_DIM_SQUARES; i++) {

            //check each square in row
            for (int k = 0; k <= board.X_DIM_SQUARES; k++) {
                if (board.filledSquares[i][k] == null) {
                    rowFilled = false;
                }

            }
            //if row is filled: delete row
            if (rowFilled) {

                deleteRows(i);
                updateSquares(i);

                //add 20 to score
                score = score + 20;

                //add 1 to # of rows deleted
                numberOfRowsDeleted += 1;
                
                
                //increase speed by 50 when row is deleted if battle mode
            if (gameType == 0 && millisec >= 0) {
            millisec -= 50;
            }
                
                

            }
            //reinitialize rowFilled as true
            rowFilled = true;

        }

        if (numberOfRowsDeleted != 0) {


            if (gameType == 1) {

                //add row bonus to score ONLY if score mode
                //bonus is 20 extra points (on top of 20 points/row) for each row above 1 deleted at a time
                score = score + (numberOfRowsDeleted - 1) * 20;

                //update score label
                scoreLabel.setText(score + " ");

                //reset numberOfRowsDeleted
                numberOfRowsDeleted = 0;

                //game ends when someone hits 200
                if (score >= 200) {
                    gameOver = true;
                    tetrisApp.endGame();
                }
            }

            //puzzle mode: if piece is gone, end game -reached goal
            //NOT COMPLETELY FUNCTIONAL - doesn't end game when it should
            if (gameType == 2){
                
                if (puzzlePieceGone()) {
                    
                    gameOver = true;
                    tetrisApp.endGame();
                }
            }
                

        }

    }

    /**
     *
     * @param i row to be deleted
     */
    void deleteRows(int i) {

        //for each square in row
        for (int j = 0; j <= board.X_DIM_SQUARES; j++) {

            //hide/delete squares in row
            board.filledSquares[i][j].hide();
            board.filledSquares[i][j] = null;
        }

    }

    /**
     *
     * @param i - row that was just deleted (used as reference point)
     */
    void updateSquares(int i) {

        //iterate through columns
        for (int k = 0; k <= board.X_DIM_SQUARES; k++) {

            //iterate through rows
            for (int j = i - 1; j > 0; j--) {

                //if square exists at this location:
                if (board.filledSquares[j][k] != null) {

                    //move test square one ahead of square    
                    testSquare.moveToTetrisLocation(k, j + 1);

                    //move square down as much as possible
                    //make into drop method?
                    while (board.safe(testSquare)) {

                        //System.out.println("DOWN");
                        board.filledSquares[j][k].down();

                        //move test square accordingly ( to stay 1 ahead)
                        testSquare.down();

                    }

                //System.out.println(testSquare.getX() + " " + testSquare.getY());
                    //hide testSquare
                    testSquare.hide();

                    //update location of square:
                    //set to new array location
                    board.filledSquares[board.filledSquares[j][k].getY()][board.filledSquares[j][k].getX()] = board.filledSquares[j][k];

                    //set old location to null
                    board.filledSquares[j][k] = null;

                }

            }

        }

    }
    
    /**
     * 
     * @return true if puzzle piece is gone
     * NOT FUNCTIONAL - NOT SURE WHY
     */
    public boolean puzzlePieceGone() {
        if (pSquare1 == null && pSquare2 == null && pSquare3 == null && pSquare4 == null && pSquare5 == null && pSquare6 == null && pSquare7 == null && pSquare8 == null) {
    
        //if (board.filledSquares[pSquare1.getY()][pSquare1.getX()] == null && board.filledSquares[pSquare2.getY()][pSquare2.getX()] == null && board.filledSquares[pSquare3.getY()][pSquare3.getX()] == null && board.filledSquares[pSquare4.getY()][pSquare4.getX()] == null && board.filledSquares[pSquare5.getY()][pSquare5.getX()] == null && board.filledSquares[pSquare6.getY()][pSquare6.getX()] == null && board.filledSquares[pSquare7.getY()][pSquare7.getX()] == null && board.filledSquares[pSquare8.getY()][pSquare8.getX()] == null) {
            
        return true;
        }
        
        return false;
        
    }

    /**
     * add individual squares in piece to filledSquares array create new piece,
     * make that the currentPiece
     */
    void switchPieces() {

        //add piece squares to filledSquares 2D array
        board.filledSquares[currentPiece.square1.getY()][currentPiece.square1.getX()] = currentPiece.square1;
        board.filledSquares[currentPiece.square2.getY()][currentPiece.square2.getX()] = currentPiece.square2;
        board.filledSquares[currentPiece.square3.getY()][currentPiece.square3.getX()] = currentPiece.square3;
        board.filledSquares[currentPiece.square4.getY()][currentPiece.square4.getX()] = currentPiece.square4;

            //set colors to red
        //board.filledSquares[currentPiece.square1.getY()][currentPiece.square1.getX()].setColor(Color.CRIMSON);
        //board.filledSquares[currentPiece.square2.getY()][currentPiece.square2.getX()].setColor(Color.CRIMSON);
        //board.filledSquares[currentPiece.square3.getY()][currentPiece.square3.getX()].setColor(Color.CRIMSON);
        //board.filledSquares[currentPiece.square4.getY()][currentPiece.square4.getX()].setColor(Color.CRIMSON);
        //board.filledSquares[]
        currentPiece = new TetrisPiece(board.X_DIM_SQUARES / 2, 1, randomPieceNumber(), board);

        //check spot immediately below it
        testSquare.moveToTetrisLocation(board.X_DIM_SQUARES / 2, 2);

        //if new piece cannot move down at all: end game (due to failure)
        if (board.safe(testSquare) == false) {
            gameOver = true;
            tetrisApp.endGameFilled();
        }

        testSquare.hide();
    }

    /**
     * Move the current tetris piece left.
     */
    void left() {
        System.out.println("left key was pressed!");

        //move testPiece
        testPiece.moveTestPiece(currentPiece);
        testPiece.left();

        //if testPiece is safe: move currentPiece
        if (board.safe(testPiece)) {
            currentPiece.left();

        }

        //hide testPiece
        testPiece.hide();
    }

    /**
     * Move the current tetris piece right.
     */
    void right() {
        System.out.println("right key was pressed!");

        //move testPiece
        testPiece.moveTestPiece(currentPiece);
        testPiece.right();

        //if testPiece is safe: move currentPiece
        if (board.safe(testPiece)) {
            
            
            currentPiece.right();

        }

        //hide testPiece
        testPiece.hide();

    }

    /**
     * Drop the current tetris piece.
     */
    void drop() {

        System.out.println("drop key was pressed!");

        //move testPiece
        testPiece.moveTestPiece(currentPiece);
        testPiece.down();

        while (board.safe(testPiece)) {

            currentPiece.down();

            testPiece.down();
        }

        //hide testPiece
        testPiece.hide();

        switchPieces();

    }

    /**
     * Rotate the current piece counter-clockwise.
     */
    void rotateLeft() {
        System.out.println("rotate left key was pressed!");

        //move testPiece
        testPiece.moveTestPiece(currentPiece);
        testPiece.rotateLeft();

        //if testPiece is safe: move currentPiece
        if (board.safe(testPiece)) {
            
            
            
            currentPiece.rotateLeft();

        }

        //hide testPiece
        testPiece.hide();
    }

    /**
     * Rotate the current piece clockwise.
     */
    void rotateRight() {
        System.out.println("rotate right key was pressed!");

        //move testPiece
        testPiece.moveTestPiece(currentPiece);
        testPiece.rotateRight();

        //if testPiece is safe: move currentPiece
        if (board.safe(testPiece)) {
            
            currentPiece.rotateRight();

        }

        //hide testPiece
        testPiece.hide();
    }

    /**
     * Generates a random int between 1 and 7 for use in getting a random piece
     */
    private int randomPieceNumber() {

        return (int) (Math.random() * 7) + 1;

    }

}