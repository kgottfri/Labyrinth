/**
 * 
 * @author AlexBeard
 *
 *This is an instance of board - stores tile array, sets tiles initial positions
 *
 */
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Random;

public class Board extends Pane{

    public static final int rows = 7;
	public static final int cols = 7;
	public static final int boardSize = 500;
    public static final double squareSize = boardSize/rows;
        
    public LabSquare[][] filledSquares = new LabSquare[cols + 1][rows + 1];
    LabSquare[] boardName;
    private int score;

	//constants
	static final int CORNER_TILE = 16;
	static final int TEE_TILE = 6;
	static final int LINE_TILE = 12;
	
	//array of tiles
	Tile[][] tiles;
	Tile extra_tile;
	
	//constructor (no args needed)
	public Board() {
		tiles = new Tile[cols][rows];
		this.setPrefHeight(boardSize);
		this.setPrefWidth(boardSize);

		// Create movable tile pool:
        ArrayList<Tile> tilePool = new ArrayList<Tile>();
        for (int i = 0; i < CORNER_TILE; i++){ tilePool.add(new Tile(this, 0, 0, false, true, true, false, true));}
        for (int i = 0; i < TEE_TILE; i++){ tilePool.add(new Tile(this, 0, 0, true, true, true, false, true));}
        for (int i = 0; i < LINE_TILE; i++){ tilePool.add(new Tile(this, 0, 0, true, false, true, false, true));}
        Random rand = new Random();

        int upperLeftX = 0;
        int upperLeftY = 0;
		//all tiles that can move, with all 4 directions 
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
                // non-movable tiles
                if (i == 0 && j ==0) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, false, false); }
                else if (i == 0 && j ==2) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, true, false); }
                else if (i == 0 && j ==4) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, true, false); }
                else if (i == 0 && j ==6) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, false, true, true, false); }
                else if (i == 2 && j ==0) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, false, false); }
                else if (i == 2 && j ==2) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, false, false); }
                else if (i == 2 && j ==4) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, true, false); }
                else if (i == 2 && j ==6) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, true, true, false); }
                else if (i == 4 && j ==0) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, false, false); }
                else if (i == 4 && j ==2) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, true, false); }
                else if (i == 4 && j ==4) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, true, true, false); }
                else if (i == 4 && j ==6) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, true, true, false); }
                else if (i == 6 && j ==0) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, false, false); }
                else if (i == 6 && j ==2) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, true, false);  }
                else if (i == 6 && j ==4) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, true, false);  }
                else if (i == 6 && j ==6) { tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, false, true, false); }

                // movable tiles - pick a random tile from the tilepool
                else {
                    tiles[i][j] = tilePool.remove(rand.nextInt(tilePool.size()));

                    // Update position
                    tiles[i][j].setPosition(upperLeftX, upperLeftY);

                    // Random orientation
                    tiles[i][j].rotateRight(rand.nextInt(4));
                }
                               
                //increment X component
                upperLeftX += squareSize;
			}
            //implement Y component
            upperLeftY += squareSize;
                        
            //reset X component
            upperLeftX = 0;
                        
		}
		// The only tile left is the extra maze tile
		extra_tile = tilePool.remove(0);


	}
        
    Tile tileAt(double x, double y) {

            int roundX = (int) Math.round(x);
            int roundY = (int) Math.round(y);
            int roundSquareSize = (int) Math.round(squareSize);

            System.out.println("x: " + roundX);
            System.out.println("y: " + roundY);
            System.out.println("squaresize: " + roundSquareSize);

            System.out.println("Yindex: " + roundY % roundSquareSize);
            System.out.println("Xindex: " + roundX % roundSquareSize);

            System.out.println("XTile: " + (roundX - (roundX % roundSquareSize))/ roundSquareSize);
            System.out.println("YTile: " + (roundY - (roundY % roundSquareSize))/ roundSquareSize);


            return tiles[(roundY - (roundY % roundSquareSize))/ roundSquareSize][(roundX - (roundX % roundSquareSize))/ roundSquareSize];
    }

    public int[] getTileCoordinates(double x, double y) {

        int roundX = (int) Math.round(x);
        int roundY = (int) Math.round(y);
        int roundSquareSize = (int) Math.round(squareSize);

        System.out.println("x: " + roundX);
        System.out.println("y: " + roundY);
        System.out.println("squaresize: " + roundSquareSize);

        System.out.println("Yindex: " + roundY % roundSquareSize);
        System.out.println("Xindex: " + roundX % roundSquareSize);

        System.out.println("XTile: " + (roundX - (roundX % roundSquareSize)) / roundSquareSize);
        System.out.println("YTile: " + (roundY - (roundY % roundSquareSize)) / roundSquareSize);


        return new int[]{(roundY - (roundY % roundSquareSize)) / roundSquareSize, (roundX - (roundX % roundSquareSize)) / roundSquareSize};
    }

    public int[] getPlayerLocation(Player player){
	    int i = 0;
	    int j = 0;
	    int[] location = new int[2];
	    for(Tile[] trow:tiles){
	        for(Tile t:trow){
	            if(t.hasPlayer(player)){
                    location[0] = i;
                    location[1] = j;
                }
                j++;
            }
            j = 0;
            i++;
        }
        return location;
    }

    public void addPlayer(Player player, int[] locTile){
	    tiles[locTile[0]][locTile[1]].setPlayer(player);
    }

    public void removePlayer(Player player){
        int[] tileCoordinates = getPlayerLocation(player);
        tiles[tileCoordinates[0]][tileCoordinates[1]].removePlayer(player);
    }
        /*
	LabSquare shapeAt(int x, int y){
		return boardName[(y*rows)+x];
	}
        */

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
	 * //@param index (can currently only be 1, 3, 5)
	 * //@param tile to be inserted
	 */

	public void insertTileTop(int index) {
	
		//move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
		//if we need to do soemthing with the table that is pushed off it could be returned by this method

        Tile tempLastTile = new Tile(tiles[rows-1][index]);

		for (int i = rows - 1; i > 0; i--) {
			tiles[i][index] = new Tile(tiles[i - 1][index]);
            tiles[i][index].setPosition(tiles[i][index].upperLeftX, tiles[i][index].upperLeftY + squareSize);
		}
		
		//insert new tile
		tiles[0][index] = new Tile(extra_tile);
        tiles[0][index].setPosition(tiles[1][index].upperLeftX, 0.0);
		extra_tile = tempLastTile;
	}

        
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
