/**
 * 
 * @author AlexBeard
 *
 *This is an instance of board - stores tile array, sets tiles initial positions
 *
 */
import javafx.scene.layout.*;

public class Board extends Pane{

        public static final int rows = 7;
	public static final int cols = 7;
	public static final int boardSize = 500;
        public static final double squareSize = boardSize/rows;
        
        public LabSquare[][] filledSquares = new LabSquare[cols + 1][rows + 1];
        LabSquare[] boardName;
        private int score;

	//constants
	static final int CORNER_TILE = 0;
	static final int TEE_TILE = 1;
	static final int LINE_TILE = 2;
	
	//array of tiles
	Tile[][] tiles;
	
	//constructor (no args needed)
	public Board() {
		tiles = new Tile[cols][rows];
		this.setPrefHeight(boardSize);
		this.setPrefWidth(boardSize);
                
                int upperLeftX = 0;
                int upperLeftY = 0;
		//all tiles that can move, with all 4 directions 
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
                            
                                //4 corner tiles
                                if (i == 0 && j ==0) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, false, false); }
                                else if (i == 0 && j ==6) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, false, true, true, false); }
                                else if (i == 6 && j ==0) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, false, false); }
                                else if (i == 6 && j ==6) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, false, true, false); }

                                //non-corner tiles
                                else { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, true, true); }
                               
                                //increment X component
                                upperLeftX += squareSize;
			}
                        //implement Y component
                        upperLeftY += squareSize;
                        
                        //reset X component
                        upperLeftX = 0;
                        
		}
		
		//SET IMMOVABLE TILES
		
		//4 corner tiles: 
		//tiles[0][0] = new Tile(false, true, true, false, false);
		//tiles[0][6] = new Tile(false, false, true, true, false);
		//tiles[6][0] = new Tile(true, false, false, true, false);
		//tiles[6][6] = new Tile(true, true, false, false, false);
		
		//set random tiles (right now, just all 4 selected)
		//Can actually be any combo of 2 or 3 directions
		
	}
        
	LabSquare shapeAt(int x, int y){
		return boardName[(y*rows)+x];
	}

        public void checkRows() {

       
        
        int count=0;
        for (int row = 0; row < filledSquares.length; row++) {
            count = 0;
            
            for (int col = 0; col < filledSquares[row].length; col++) {

                if (filledSquares[row][col] == null) {
                    count++;
                }
            }
            
            if (count == 0) {
                RemoveRows(row,filledSquares);
                score+=1;
                
            }
            
        }
    }
            public void RemoveRows(int row, LabSquare[][] squares){           
        for(int i=0; i<rows; i++){
            squares[row][i].removeFromDrawing();
            squares[row][i] = null;
            
          }
        
        
        for(int j=row; j>0;j--){
            
            for(int k=0; k<squares[j-1].length; k++){
                if (squares[j][k] == null && squares[j-1][k] != null) {
                
            
            squares[j-1][k].moveToLabLocation(k, j);
            squares[j][k]=squares[j-1][k];
            squares[j-1][k]=null;
                }
        }
        }

       
       

    }
    public int getScore(){
    	return score;
    }
	/**
	 * prints board w string representation of tiles
	 * only needed for command line testing
	 */
    /*
	public void printBoard() {
		//prints indexes on top of board
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			if (i%2 != 0) { System.out.printf("%6s", i + "  "); }
			else { System.out.printf("%6s", " "); }
		}
		System.out.println("\n---------------------------------------------");
		
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				System.out.printf("%6s", tiles[i][j].stringRepresentation());
			}
			System.out.println("\n");
		}
		
		
	}
    */
	/**
	 * 
	 * @param index (can currently only be 1, 3, 5)
	 * @param tile to be inserted
	 */
    /*
	public void insertTile(int index, Tile newTile) {
	
		//move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
		//if we need to do soemthing with the table that is pushed off it could be returned by this method
		for (int i = BOARD_HEIGHT - 1; i > 0; i--) {
			tiles[i][index] = tiles[i - 1][index]; 
		}
		
		//insert new tile
		tiles[0][index] = newTile;
	}
        */
        
        public int getX_DIM(){
            return rows;
	}
	public int getY_DIM(){
		return cols;
	}
        
        public void addSquare(LabSquare square) {
            filledSquares[square.getY()][square.getX()] = square;
        } 
        public boolean checkLocation(int sqY, int sqX) {
            return filledSquares[sqY][sqX] == null;
        }
	

}
