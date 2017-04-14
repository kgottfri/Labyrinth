
import java.util.LinkedList;
import javafx.scene.paint.Color;


/**
 * HumanPlayer class
 * Handles gameplay for human user
 * @author kevingottfried
 */
public class HumanPlayer extends Player {
    
    public HumanPlayer(int playerType){
                player_number = playerType;
        if (playerType == 1){
            this.color = Color.BLUE;
        }
        else
            this.color = Color.GREEN;
        
        this.setPrefHeight(Prows*squareHeight);
	this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
    }
    /**
     *
     */
    
    /**
     * The takeTurn method displays users hand and prompts for a card to ask
     * the opponent for
     * @param gm GameManager
     * @param p1 opponent
     */
    
    public void takeTurn(Player p1){
      }

    

}
