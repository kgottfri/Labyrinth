/**
 * 
 * @author AlexBeard
 *
 *This is an instance of board - stores tile array, sets tiles initial positions 
 *
 */
public class Board {
	//can change to change size
	final int BOARD_WIDTH = 7;
	final int BOARD_HEIGHT = 7;
	
	//constants
	static final int CORNER_TILE = 0;
	static final int TEE_TILE = 1;
	static final int LINE_TILE = 2;
	
	//array of tiles
	Tile[][] tiles;
	
	//constructor (no args needed)
	public Board() {
		tiles = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
		
		//all tiles that can move, with all 4 directions 
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				tiles[i][j] = new Tile(true, true, true, true, true);
			}
		}
		
		//SET IMMOVABLE TILES
		
		//4 corner tiles: 
		tiles[0][0] = new Tile(false, true, true, false, false);
		tiles[0][6] = new Tile(false, false, true, true, false);
		tiles[6][0] = new Tile(true, false, false, true, false);
		tiles[6][6] = new Tile(true, true, false, false, false);
		
		//set random tiles (right now, just all 4 selected)
		//Can actually be any combo of 2 or 3 directions
		
	}
	
	/**
	 * prints board w string representation of tiles
	 * only needed for command line testing
	 */
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
	/**
	 * 
	 * @param index (can currently only be 1, 3, 5)
	 * @param tile to be inserted
	 */
	public void insertTile(int index, Tile newTile) {
	
		//move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
		//if we need to do soemthing with the table that is pushed off it could be returned by this method
		for (int i = BOARD_HEIGHT - 1; i > 0; i--) {
			tiles[i][index] = tiles[i - 1][index]; 
		}
		
		//insert new tile
		tiles[0][index] = newTile;
	}
	

}
