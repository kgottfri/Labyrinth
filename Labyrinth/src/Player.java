// CS_205 Go Fish

import java.util.Collections;
import java.util.LinkedList;

/**
 * Player superclass for both human and computer players
 * @author Fabian Gaspero-Beckstrom
 */
public class Player {
    
    protected LinkedList<Card> hand;
    protected int score = 0;
    protected int successful_request = 0;
    protected int total_request = 0;
    protected int cards_gotten = 0;
    
    /**
     * Constructor instantiates new LinkedList of Card objects as hand
     */
    public Player() {
        
        hand = new LinkedList<Card>();
    }
    /**
     * The getHand method returns the LinkedList hand containing Card objects
     * which represent the players current hand
     * @return LinkedList The players hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }
    /**
     * The addCard method ands a card object to the LinkedList hand
     * @param c The Card object to be added to the players hand
     */
    public void addCard(Card c) {
        if(c != null)
            hand.add(c);
    }
     /**
     * The removeCard method removes a card object from the LinkedList hand
     * @param c The Card object to be removed from players hand
     */
    public void removeCard(Card c) {
        hand.remove(c);
    }
    /**
     * The askforCards method searches hand for a card specified by the opponent
     * @param c The card sought after by opponent
     * @return cards Array of 0-3 Card objects to be given to opponent
     */
    public Card[] askforCards(Card c){
        Card[] cards = new Card[3];
        int j = 0;
        for(int i = hand.size()-1; i >= 0;i--){
            if(hand.get(i).equals(c)){
                hand.remove(i);
                cards[j] = c;
                j++;
                cards_gotten++;
            }
        }
        return cards;
    }
    /**
     * The checkForSets method searches through players hand for sets of 4
     * If a set is found, those cards are removed from the hand and the players
     * score is increased
     */
    public void checkForSets() {
        int count = 0;
        boolean flag = false;
        int i = 1;
        LinkedList<Card> edit_hand = new LinkedList<>();
        for (Card c : hand) {      
            for (int j = i; j < hand.size();j++) {
                if (c.equals(hand.get(j))) {count++;}
            }
            i++;
            if (count == 3) {
                flag = true;
                edit_hand.add(c);
                System.out.println("A set of " + c + "s were removed from the hand.");
                score++;
            }
            count = 0;
        }
        if(flag){
            for (Card c : edit_hand) {
                for(int k = 0; k < 4;k++){
                    hand.remove(c);
                }  
            }
        }
        
    }
    /**
     * The sort method sorts players hand
     */
    public void sort(){
        Collections.sort(hand);
    }
    /**
     * The isHandEmpty method checks whether or not the players hand is empty
     * @return Either true or false
     */
    public boolean isHandEmpty() {
        if (!hand.isEmpty()) { return false;} else {
            return true;
        }
    
    }
    
    public void takeTurn(Player p1){
        
    }
    /**
     * The getScore method returns the players score
     * @return score The players score
     */
    public int getScore(){
        
        return score;
    }
    /**
     * The getSuccessfulRequests method returns the number successful requests
     * by the player
     * @return successful_request Number of successful requests made
     */
    public int getSuccessfulRequests(){
        return successful_request;
    }
    /**
     * The getTotalRequests method returns total number of requests by the player
     * @return total_request Total number requests made
     */
    public int getTotalRequests(){
        return total_request;
    }
    /**
     * The getCardsGotten method returns number of cards received by player
     * @return cards_gotten Number of cards received by player
     */
    public int getCardsGotten(){
        return cards_gotten;
    }
 
    


    
}
    
    
    
    
    

