import java.util.ArrayList;

/**
 * 
 * @author AlexBeard
 *
 *One individual tile with all attributes included. Includes rotate, flip, etc
 *
 */
public class Tile {
	
	//whether tile has a road in/out of that direction of the card
	boolean up;
	boolean right;
	boolean down;
	boolean left;
	
	boolean canMove;
	
	//not implemented yet (Z = no treasure)
	char treasure = 'Z';
	
	Tile(boolean up, boolean right, boolean down, boolean left, boolean canMove) {
		
			this.up = up;
			this.right = right;
			this.down = down;
			this.left = left;
			this.canMove = canMove;	


	}
	
	/**
	 * 
	 * Only needed for command line testing
	 */
	public String stringRepresentation() {
		
		ArrayList<String> s = new ArrayList<String>();
		
		if (left) { s.add("L"); }
		if (up) { s.add("U"); }
		if (down) { s.add("D"); }
		if (right) { s.add("R"); }
		if (canMove) { s.add("-"); }
		
		return String.join("",s);

		
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
	
	
	

}
