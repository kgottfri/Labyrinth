
import java.util.LinkedList;
import javafx.scene.paint.Color;
/**
 * SimpleCom class
 * Handles simple computer decisions
 * @author kevingottfried
 */
public class CompPlayer extends Player{

    
    public CompPlayer() {
        this.color = Color.GREEN;
        this.setPrefHeight(Prows*squareHeight);
	this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
        
    }
    /**
     * Constructor instantiates local UI object
     */
    
    /**
     * The takeTurn method generates a random card choice to ask opponent for
     * @param gm GameManager
     * @param p1 Opponent
     */
    /*
    public void takeTurn(Player p1){
		if(treasuresHand.isEmpty())
		{

		}
		else
		{
			
			//ui.print(hand.toString());
			String [] new_hand = new String[treasuresHand.size()];
			Random num = new Random();
			int card;
			
			int hand_size = treasuresHand.size();
			card = num.nextInt(hand_size);
			Card rand_card = treasuresHand.get(card);
			Card [] opp_hand = p1.askforCards(rand_card);
			if(opp_hand[0] != null){
				for(int i = 0; i < 3; i++){
					if (opp_hand[i] !=null){
						addCard(opp_hand[i]);
					}
				}
				successfulTreasure++;
				
			}
			else{
				
			}
			totalRequest++;
			if(treasuresHand.size() > 1)
				sort();
			checkForSets();
		}

    }
 */   
    
}
