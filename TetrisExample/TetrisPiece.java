
import javafx.scene.paint.Color;

/**
 *
 * @author Alex Beard
 * 
 */
public class TetrisPiece {
        
    
        //initialize tetris squares
        TetrisSquare square1;
        TetrisSquare square2;
        TetrisSquare square3;
        TetrisSquare square4;
  
    
    /**
     * 
     * @param centerX: starting X of center square
     * @param centerY: starting Y of center square
     * @param shapeType: random int that determines which type of shape it will be
     * @param board: Which board the piece will be located on
     */
    TetrisPiece(int centerX, int centerY, int shapeType, TetrisBoard board) {
        
        square1 = new TetrisSquare(board);
        square2 = new TetrisSquare(board);
        square3 = new TetrisSquare(board);
        square4 = new TetrisSquare(board);
        
        //colors array
        Color[] colors = {Color.CRIMSON, Color.DARKBLUE, Color.DARKGREEN};
        
        //set each piece to random color
        Color pieceColor = colors [((int) (Math.random() * colors.length))];
        
        //change squares to same random color
        square1.setColor(pieceColor);
        square2.setColor(pieceColor);
        square3.setColor(pieceColor);
        square4.setColor(pieceColor);
        



        
        
        
        
        
        
        
        //depending on which random integer 1 through 7 is passed in, creates that shape at center location
        switch(shapeType) {
           
           case 1: 
               square(centerX, centerY);
               break;
               
           case 2: 
               letterL(centerX, centerY);
               break;
               
           case 3: 
               reverseLetterL(centerX, centerY);
               break;
               
           case 4: 
               letterZ(centerX, centerY);
               break;
               
           case 5:
               reverseLetterZ(centerX, centerY);
               break;
               
           case 6: 
               line(centerX, centerY);
               break;
               
           case 7: 
               plusSign(centerX, centerY);
               break;
               
        }
        
        
    }
    
   /**
    * hide a piece by hiding each individual square
    */ 
   void hide() {
       
       square1.hide();
       square2.hide();
       square3.hide();
       square4.hide();
       
   }
    /**
     * move test piece to the same location as an original piece (to be tested)
     * @param originalPiece 
     */    
    void moveTestPiece(TetrisPiece originalPiece) {
            
        //move clone squares to originalPiece locations
        this.square1.moveToTetrisLocation(originalPiece.square1.getX(), originalPiece.square1.getY());
        this.square2.moveToTetrisLocation(originalPiece.square2.getX(), originalPiece.square2.getY());
        this.square3.moveToTetrisLocation(originalPiece.square3.getX(), originalPiece.square3.getY());
        this.square4.moveToTetrisLocation(originalPiece.square4.getX(), originalPiece.square4.getY());
        
    }
    
    
    /**
     * move each square's actual location 1 to the left (-1)
     */
    void left() {
        
        square1.moveToTetrisLocation(square1.getX() - 1, square1.getY());
        square2.moveToTetrisLocation(square2.getX() - 1, square2.getY());
        square3.moveToTetrisLocation(square3.getX() - 1, square3.getY());
        square4.moveToTetrisLocation(square4.getX() - 1, square4.getY());
    }
    
    /**
     * move each square's actual location 1 to the right (+1)
     */
    void right() {
        
        square1.moveToTetrisLocation(square1.getX() + 1, square1.getY());
        square2.moveToTetrisLocation(square2.getX() + 1, square2.getY());
        square3.moveToTetrisLocation(square3.getX() + 1, square3.getY());
        square4.moveToTetrisLocation(square4.getX() + 1, square4.getY());
        
    }

    /**
     * rotate piece to the left by changing relative locations
     */
    void rotateLeft() {
       
        square2.setRelativeLocation(square2.getRelativeY(square1), 0 - square2.getRelativeX(square1), square1);
        square3.setRelativeLocation(square3.getRelativeY(square1), 0 - square3.getRelativeX(square1), square1);
        square4.setRelativeLocation(square4.getRelativeY(square1), 0 - square4.getRelativeX(square1), square1);
          
    }
    
    
    /**
     * rotate piece to the right by changing relative locations
     */
    void rotateRight() {
        
        square2.setRelativeLocation(0 - square2.getRelativeY(square1), square2.getRelativeX(square1), square1);
        square3.setRelativeLocation(0 - square3.getRelativeY(square1), square3.getRelativeX(square1), square1);
        square4.setRelativeLocation(0 - square4.getRelativeY(square1), square4.getRelativeX(square1), square1);
        
    }
    
    /**
     * move each square in piece down by 1
     */
    void down() {
        
        square1.moveToTetrisLocation(square1.getX(), square1.getY() + 1);
        square2.moveToTetrisLocation(square2.getX(), square2.getY() + 1);
        square3.moveToTetrisLocation(square3.getX(), square3.getY() + 1);
        square4.moveToTetrisLocation(square4.getX(), square4.getY() + 1);
        
    }
    
    //shape methods: take in starting point of center square, move squares to that location/ corresponding relative locations
    
    /**
     * create a square piece at location given
     * @param centerX
     * @param centerY 
     */
    void square(int centerX, int centerY) {
        
               square1.moveToTetrisLocation(centerX, centerY);
               square2.setRelativeLocation(-1, 0, square1);
               square3.setRelativeLocation(-1, -1, square1);
               square4.setRelativeLocation(0, -1, square1);
    }
    
    /**
     * create a L piece at location given
     * @param centerX
     * @param centerY 
     */
    void letterL(int centerX, int centerY) {
        
        square1.moveToTetrisLocation(centerX, centerY);
        square2.setRelativeLocation(-1, -1, square1);
        square3.setRelativeLocation(0, -1, square1);
        square4.setRelativeLocation(0, 1, square1);
        
        
    }
    /**
     * create a backwards L piece at location given
     * @param centerX
     * @param centerY 
     */
    void reverseLetterL(int centerX, int centerY) {
        
        square1.moveToTetrisLocation(centerX, centerY);
        
        square2.setRelativeLocation(0, -1, square1);
        square3.setRelativeLocation(1, -1, square1);
        square4.setRelativeLocation(0, 1, square1);
       
    }
    
    /**
     * create Z piece at location given
     * @param centerX
     * @param centerY 
     */
    void letterZ(int centerX, int centerY) {
        
        square1.moveToTetrisLocation(centerY, centerY);
        
        square2.setRelativeLocation(-1, -1, square1);
        square3.setRelativeLocation(0, -1, square1);
        square4.setRelativeLocation(1, 0, square1);
        
    }
    /**
     * create backwards Z at location given
     * @param centerX
     * @param centerY 
     */
    void reverseLetterZ(int centerX, int centerY) {
        
        square1.moveToTetrisLocation(centerY, centerY);
        
        square2.setRelativeLocation(-1, 0, square1);
        square3.setRelativeLocation(0, -1, square1);
        square4.setRelativeLocation(1, -1, square1);
        
    }
    
    /**
     * create line piece at location given
     * @param centerX
     * @param centerY 
     */
    void line(int centerX, int centerY) {
        
        square1.moveToTetrisLocation(centerY, centerY);
        
        square2.setRelativeLocation(0, -1, square1);
        square3.setRelativeLocation(0, 1, square1);
        square4.setRelativeLocation(0, 2, square1);
      
    }
    
    /**
     * create plus sign piece at location given
     * @param centerX
     * @param centerY 
     */
    void plusSign(int centerX, int centerY) {
        
        square1.moveToTetrisLocation(centerY, centerY);
        
        square2.setRelativeLocation(-1, 0, square1);
        square3.setRelativeLocation(1, 0, square1);
        square4.setRelativeLocation(0, -1, square1);
       
        
    }
      
       
    }
    

