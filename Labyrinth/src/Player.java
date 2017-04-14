// CS_205 Labyrinth

import java.util.Collections;
import java.util.LinkedList;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Player superclass for both human and computer players
 * @author Kevin Gottfried
 */
public class Player extends Pane{
    
    protected LinkedList<Card> treasuresHand;
    private char currentTreasureCard;
    public boolean activePlayer = false;
    protected int score = 0;
    protected char successfulTreasure;
    protected int totalRequest = 0;
    protected int treasuresGotten = 0;
    public static final int Prows = 7;
    public static final int Pcols = 1;
    public static final int squareWidth=450;
    public static final int squareHeight=100;
    public Color color;
    public int player_number;
    
    public Player(){
        this.setPrefHeight(Prows*squareHeight);
	this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
    }
    public Player(int playerType){
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
	
    public int getX_DIM(){
        return Prows;
    }
    public int getY_DIM(){
        return Pcols;
    }
    /**
     * Constructor instantiates new LinkedList of Card objects as hand
     */
   
    /**
     * The getHand method returns the LinkedList hand containing Card objects
     * which represent the players current hand
     * @return LinkedList The players hand
     */
    public LinkedList<Card> getTreasuresList() {
        return treasuresHand;
    }
    
    /**
     * The addCard method ands a card object to the LinkedList hand
     * @param c The Card object to be added to the players hand
     */
    public void addCard(Card c) {
        if(c != null)
            treasuresHand.add(c);
    }
    public void removeCard(){
        treasuresHand.remove();
    }
     /**
     * The getCard method removes a card object from the LinkedList hand
     */
    public Card getCard() {
        return treasuresHand.peek();
    }
    public char getTreasure(){
        
        return getCard().getValueAsString().charAt(0);
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

        if(flag){
            for (Card c : edit_hand) {
                for(int k = 0; k < 4;k++){
                    treasuresHand.remove(c);
                }  
            }
        }
        
    }
    /**
     * The sort method sorts players hand
     */
    public void sort(){
//        Collections.sort(treasuresHand);
    }
    /**
     * The isHandEmpty method checks whether or not the players hand is empty
     * @return Either true or false
     */
    public boolean isHandEmpty() {
        if (!treasuresHand.isEmpty()) { return false;} else {
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
    public char getSuccessfulTreasures(){
        return successfulTreasure;
    }
  
}
    
    
    
    
    

