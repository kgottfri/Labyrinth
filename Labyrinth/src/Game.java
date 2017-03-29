import java.util.Scanner;

/**
 * 
 * @author AlexBeard
 * Main file - this has game logic/user input
 *
 */
public class Game {
	//instance of board object
	static Board board;
	static boolean keepPlaying = true;
	static Tile currentTile;
		
	
	public static void main(String args[]) {
		
		board = new Board();
		
		//should generate random type
		currentTile = new Tile(false, true, false, true, true);
		
		//create scanner
		Scanner s = new Scanner(System.in);
		
		while (keepPlaying) {
			board.printBoard();
			System.out.println("\n Current tile: " + currentTile.stringRepresentation());
			
			System.out.println("\nHow many times would you like to rotate tile right?");
			
			currentTile.rotateRight(s.nextInt());
			System.out.println("\nCurrent tile: " + currentTile.stringRepresentation());
			
			System.out.println("Where would you like to insert tile? (1, 3, or 5): ");
			
			board.insertTile(s.nextInt(), currentTile);
			
			//new current tile (should be random generator)
			currentTile = new Tile(false, true, true, true, true);
			
		}
        }
}

		
	


