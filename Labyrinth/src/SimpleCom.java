// CS_205 Go Fish



import java.util.*;

/**
 * SimpleCom class
 * Handles simple computer decisions
 * @author kevingottfried
 */
public class SimpleCom extends Player{

    
    
    public SimpleCom() {

    }
    /**
     * Constructor instantiates local UI object
     */
    
    /**
     * The takeTurn method generates a random card choice to ask opponent for
     * @param gm GameManager
     * @param p1 Opponent
     */
    public void takeTurn(Player p1){
		if(hand.isEmpty())
		{

		}
		else
		{
			
			//ui.print(hand.toString());
			String [] new_hand = new String[hand.size()];
			Random num = new Random();
			int card;
			
			int hand_size = hand.size();
			card = num.nextInt(hand_size);
			Card rand_card = hand.get(card);
			Card [] opp_hand = p1.askforCards(rand_card);
			if(opp_hand[0] != null){
				for(int i = 0; i < 3; i++){
					if (opp_hand[i] !=null){
						addCard(opp_hand[i]);
					}
				}
				successful_request++;
				
			}
			else{
				
			}
			total_request++;
			if(hand.size() > 1)
				sort();
			checkForSets();
		}
    }
    
    
}