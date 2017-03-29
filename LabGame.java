import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This should be implemented to include your game control.
 *
 * @author pipWolfe/Alex Beard
 *
 *
 */
public class LabGame {
    private final gui LabApp;
    private int rand;
    public String shape;
    private int move;
    LabSquare square1;
    LabSquare square2;
    LabSquare square3;
    LabSquare square4;
    board boardName;
    private boolean gameOver=false;
    /**
     * Initialize the game. Remove the example code and replace with code
     * that creates a random piece.
     * @param tetrisApp A reference to the application (use to set messages).
     * @param board A reference to the board on which Squares are drawn
     */
    public LabGame(gui LabApp, board boardName) {
        // Some sample code that places two squares on the board.
        // Take this out and construct your random piece here.
        square1 = new LabSquare(boardName);
        square2 = new LabSquare(boardName);
        square3 = new LabSquare(boardName);
        square4 = new LabSquare(boardName);
        this.boardName = boardName;
        square1.moveToLabLocation(boardName.getX_DIM()/2, 3);
        rand = (int )(Math.random() * 7);
        switch(rand) {
            case 0:
                shape = "square";
                createSquare(square1, square2, square3, square4);
                break;
            case 1:
                shape = "leftL";
                createLeftL(square1, square2, square3, square4);
                break;
            case 2:
                shape = "rightL";
                createRightL(square1, square2, square3, square4);
                break;
            case 3:
                shape = "leftZ";
                createLeftZ(square1, square2, square3, square4);
                break;
            case 4:
                shape = "rightZ";
                createRightZ(square1, square2, square3, square4);
                break;
            case 5:
                shape = "rectangle";
                createRectangle(square1, square2, square3, square4);
                break;
            case 6:
                shape = "T";
                createT(square1, square2, square3, square4);
                break;
        }
        
        this.LabApp = LabApp;
        // You can use this to show the score, etc.
      //  LabApp.setMessage("Game has started!");
    }

    /**
     * Animate the game, by moving the current tetris piece down.
     */
    void update() {
        
        
        
        
        boolean check1 = checkSquares(square1.getX(), square1.getY() + 1, boardName);
        boolean check2 = checkSquares(square2.getX(), square2.getY() + 1, boardName);
        boolean check3 = checkSquares(square3.getX(), square3.getY() + 1, boardName);
        boolean check4 = checkSquares(square4.getX(), square4.getY() + 1, boardName);
        
        if(check1 && check2 && check3 && check4){
            commitNewLocation(square1.getX(), square1.getY() + 1, square2.getX(), 
                    square2.getY() + 1, square3.getX(), square3.getY() + 1, 
                    square4.getX(), square4.getY() + 1);
            move++;
             
        }
        else if(move==0){
            gameOver(true);
            //LabApp.setMessage("Game Over: You Fail");
        }
            
            else{
            
            boardName.addSquare(square1);
            boardName.addSquare(square2);
            boardName.addSquare(square3);
            boardName.addSquare(square4);
           
            square1 = new LabSquare(boardName);
            square2 = new LabSquare(boardName);
            square3 = new LabSquare(boardName);
            square4 = new LabSquare(boardName);
            this.boardName = boardName;
            square1.moveToLabLocation(boardName.getX_DIM()/2, 3);
            rand = (int) (Math.random() * 7);
            switch(rand) {
                case 0:
                    shape = "square";
                    createSquare(square1, square2, square3, square4);
                    break;
                case 1:
                    shape = "leftL";
                    createLeftL(square1, square2, square3, square4);
                    break;
                case 2:
                    shape = "rightL";
                    createRightL(square1, square2, square3, square4);
                    break;
                case 3:
                    shape = "leftZ";
                    createLeftZ(square1, square2, square3, square4);
                    break;
                case 4:
                    shape = "rightZ";
                    createRightZ(square1, square2, square3, square4);
                    break;
                case 5:
                    shape = "rectangle";
                    createRectangle(square1, square2, square3, square4);
                    break;
                case 6:
                    shape = "T";
                    createT(square1, square2, square3, square4);
                    break;
                    
            }
            move=0;
            boardName.checkRows();
            //LabApp.setMessage("Number of Rows Removed: "+ board.getScore());
        }
    }
    
    /**
     * Move the current tetris piece left.
     */
    void left() {
        System.out.println("left key was pressed!");
        
        boolean check1 = checkSquares(square1.getX() - 1, square1.getY(), boardName);
        boolean check2 = checkSquares(square2.getX() - 1, square2.getY(), boardName);
        boolean check3 = checkSquares(square3.getX() - 1, square3.getY(), boardName);
        boolean check4 = checkSquares(square4.getX() - 1, square4.getY(), boardName);
        
        if(check1 && check2 && check3 && check4)
            commitNewLocation(square1.getX() - 1, square1.getY(), square2.getX() - 1, 
                    square2.getY(), square3.getX() - 1, square3.getY(), 
                    square4.getX() - 1, square4.getY());
    }

    /**
     * Move the current tetris piece right.
     */
    void right() {
        System.out.println("right key was pressed!");
        
        boolean check1 = checkSquares(square1.getX() + 1, square1.getY(), boardName);
        boolean check2 = checkSquares(square2.getX() + 1, square2.getY(), boardName);
        boolean check3 = checkSquares(square3.getX() + 1, square3.getY(), boardName);
        boolean check4 = checkSquares(square4.getX() + 1, square4.getY(), boardName);
        
        if(check1 && check2 && check3 && check4)
            commitNewLocation(square1.getX() + 1, square1.getY(), square2.getX() + 1, 
                    square2.getY(), square3.getX() + 1, square3.getY(), 
                    square4.getX() + 1, square4.getY());
    }

    /**
     * Drop the current tetris piece.
     */
    void drop() {
        System.out.println("drop key was pressed!");
        
        boolean check1 = checkSquares(square1.getX(), square1.getY() + 1, boardName);
        boolean check2 = checkSquares(square2.getX(), square2.getY() + 1, boardName);
        boolean check3 = checkSquares(square3.getX(), square3.getY() + 1, boardName);
        boolean check4 = checkSquares(square4.getX(), square4.getY() + 1, boardName);
        
        while(check1 && check2 && check3 && check4 == true) {
            
            commitNewLocation(square1.getX(), square1.getY() + 1, square2.getX(), 
                    square2.getY() + 1, square3.getX(), square3.getY() + 1, 
                    square4.getX(), square4.getY() + 1);
            
            check1 = checkSquares(square1.getX(), square1.getY() + 1, boardName);
            check2 = checkSquares(square2.getX(), square2.getY() + 1, boardName);
            check3 = checkSquares(square3.getX(), square3.getY() + 1, boardName);
            check4 = checkSquares(square4.getX(), square4.getY() + 1, boardName); 
        }
    }

    /**
     * Rotate the current piece counter-clockwise.
     */
     void rotateLeft() {
        System.out.println("rotate left key was pressed!");
        
        int square2NewRelX = square2.getY() - square1.getY();
        int square2NewRelY = -(square2.getX() - square1.getX());
        int square3NewRelX = square3.getY() - square1.getY();
        int square3NewRelY = -(square3.getX() - square1.getX());
        int square4NewRelX = square4.getY() - square1.getY();
        int square4NewRelY = -(square4.getX() - square1.getX());
       
        boolean check2 = checkSquares(square2NewRelX + square1.getX(), 
                square2NewRelY + square1.getY(), boardName);
        boolean check3 = checkSquares(square3NewRelX + square1.getX(), 
                square3NewRelY + square1.getY(), boardName);
        boolean check4 = checkSquares(square4NewRelX + square1.getX(), 
                square4NewRelY + square1.getY(), boardName);
        
        if(check2 && check3 && check4)
            commitNewLocation(square1.getX(), square1.getY(), square2NewRelX + square1.getX(), 
                    square2NewRelY + square1.getY(), square3NewRelX + square1.getX(), 
                    square3NewRelY + square1.getY(), square4NewRelX + square1.getX(), 
                    square4NewRelY + square1.getY());     
    }
    
    /**
     * Rotate the current piece clockwise.
     */
    void rotateRight() {
        System.out.println("rotate right key was pressed!");
        
        int square2NewRelX = -(square2.getY() - square1.getY());
        int square2NewRelY = square2.getX() - square1.getX();
        int square3NewRelX = -(square3.getY() - square1.getY());
        int square3NewRelY = square3.getX() - square1.getX();
        int square4NewRelX = -(square4.getY() - square1.getY());
        int square4NewRelY = square4.getX() - square1.getX();
        
        boolean check2 = checkSquares(square2NewRelX + square1.getX(), 
                square2NewRelY + square1.getY(), boardName);
        boolean check3 = checkSquares(square3NewRelX + square1.getX(), 
                square3NewRelY + square1.getY(), boardName);
        boolean check4 = checkSquares(square4NewRelX + square1.getX(), 
                square4NewRelY + square1.getY(), boardName);
        
        if(check2 && check3 && check4)
            commitNewLocation(square1.getX(), square1.getY(), square2NewRelX + square1.getX(), 
                    square2NewRelY + square1.getY(), square3NewRelX + square1.getX(), 
                    square3NewRelY + square1.getY(), square4NewRelX + square1.getX(), 
                    square4NewRelY + square1.getY());           
    }
    /**
     * Check if squares can move to desired location
     * @param sqX
     * @param sqY
     * @param board
     * @return 
     */
    boolean checkSquares(int sqX, int sqY, board board) {
        if(sqX < 0 || sqX >= board.getX_DIM())
            return false;
        else if(sqY < 0 || sqY >= board.getY_DIM())
            return false;
        else if((board.checkLocation(sqY, sqX)) == false)
            return false;
        return true;
    }
    
    void commitNewLocation(int sq1X, int sq1Y, int sq2X, int sq2Y, int sq3X,
            int sq3Y, int sq4X, int sq4Y) {
        square1.moveToLabLocation(sq1X, sq1Y);
        square2.moveToLabLocation(sq2X, sq2Y);
        square3.moveToLabLocation(sq3X, sq3Y);
        square4.moveToLabLocation(sq4X, sq4Y);
    }
    
      
    /**
     * Create a square tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createSquare(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
        
        square2.moveToLabLocation(square1.getX(), square1.getY() - 1);
        square3.moveToLabLocation(square1.getX() - 1, square1.getY() - 1);
        square4.moveToLabLocation(square1.getX() - 1, square1.getY());
        
        square1.setColor(Color.BLUE);
       
        square2.setColor(Color.BLUE);
        square3.setColor(Color.BLUE);
        square4.setColor(Color.BLUE);
    }
    
    /**
     * Create a Left L tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createLeftL(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
      
        square2.moveToLabLocation(square1.getX(), square1.getY() + 1);
        square3.moveToLabLocation(square1.getX(), square1.getY() - 1);
        square4.moveToLabLocation(square1.getX() - 1, square1.getY() - 1);
        square1.setColor(Color.RED);
        square2.setColor(Color.RED);
        square3.setColor(Color.RED);
        square4.setColor(Color.RED);
    }
    
    /**
     * Create a Right L tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createRightL(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
        
        square2.moveToLabLocation(square1.getX(), square1.getY() + 1);
        square3.moveToLabLocation(square1.getX(), square1.getY() - 1);
        square4.moveToLabLocation(square1.getX() + 1, square1.getY() - 1);
        square1.setColor(Color.VIOLET);
        square2.setColor(Color.VIOLET);
        square3.setColor(Color.VIOLET);
        square4.setColor(Color.VIOLET);
    }
    
    /**
     * Create a Left Z tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createLeftZ(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
        
        square2.moveToLabLocation(square1.getX() + 1, square1.getY());
        square3.moveToLabLocation(square1.getX() - 1, square1.getY() - 1);
        square4.moveToLabLocation(square1.getX(), square1.getY() - 1);
        square1.setColor(Color.DARKMAGENTA);
        square2.setColor(Color.DARKMAGENTA);
        square3.setColor(Color.DARKMAGENTA);
        square4.setColor(Color.DARKMAGENTA);
    }
    
    /**
     * Create a Right Z tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createRightZ(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
        
        square2.moveToLabLocation(square1.getX() - 1, square1.getY());
        square3.moveToLabLocation(square1.getX(), square1.getY() - 1);
        square4.moveToLabLocation(square1.getX() + 1, square1.getY() - 1);
        square1.setColor(Color.GREEN);
        square2.setColor(Color.GREEN);
        square3.setColor(Color.GREEN);
        square4.setColor(Color.GREEN);
    }
    
    /**
     * Create a Rectangle tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createRectangle(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
        
        square2.moveToLabLocation(square1.getX(), square1.getY() - 1);
        square3.moveToLabLocation(square1.getX(), square1.getY() + 1);
        square4.moveToLabLocation(square1.getX(), square1.getY() + 2);
        square1.setColor(Color.BLACK);
        square2.setColor(Color.BLACK);
        square3.setColor(Color.BLACK);
        square4.setColor(Color.BLACK);
    }
    
    /**
     * Create a T tetris piece
     * @param square1
     * @param square2
     * @param square3
     * @param square4 
     */
    void createT(LabSquare square1, LabSquare square2, 
            LabSquare square3, LabSquare square4) {
        
        square2.moveToLabLocation(square1.getX() - 1, square1.getY());
        square3.moveToLabLocation(square1.getX() + 1, square1.getY());
        square4.moveToLabLocation(square1.getX(), square1.getY() + 1);
        square1.setColor(Color.AQUA);
        square2.setColor(Color.AQUA);
        square3.setColor(Color.AQUA);
        square4.setColor(Color.AQUA);
    }
    void noShape() {
        square1.moveToLabLocation(0, 0);
        square2.moveToLabLocation(0, 0);
        square3.moveToLabLocation(0, 0);
        square4.moveToLabLocation(0,0);
    }
    void gameOver(boolean game){
        gameOver=game;
    }
    boolean SetGameOver(){
        return gameOver;
    }
}

