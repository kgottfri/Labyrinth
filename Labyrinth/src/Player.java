// CS_205 Labyrinth

import java.util.Collections;
import java.util.LinkedList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Player superclass for both human and computer players
 * @author Kevin Gottfried
 */
public class Player extends Pane{
    
    protected LinkedList<Card> treasuresHand;
    private Card currentTreasureCard;
    public boolean activePlayer = false;
    protected int score = 0;
    protected char successfulTreasure;
    protected int totalRequest = 0;
    public int treasuresGotten_1 = 0;
    public int treasuresGotten_2 = 0;
    public static final int Prows = 7;
    public static final int Pcols = 1;
    public static final int squareWidth=450;
    public static final int squareHeight=100;
    public Color color;
    public int player_number;
    Label play;
    Label compTreasureNum;
    
    public Player(){
        this.setPrefHeight(Prows*squareHeight);
	this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
        
        
    }
    public Player(int playerType){
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f,0.4f,0.4f));
        player_number = playerType;
        if (playerType == 1){
            this.color = Color.BLUE;
        }
        else
            this.color = Color.GREEN;
        System.out.println(playerType);
        this.setPrefHeight(Prows*squareHeight);
	    this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
        play = new Label("Player " + player_number);
        play.setPadding(new Insets(20, 0, 50, 180));
        play.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        play.setEffect(ds);
        if (player_number == 1) {
            play.setTextFill(Color.BLUE);
        } else {
            play.setTextFill(Color.GREEN);
        }
        getChildren().add(play);
        Label curPiece = new Label("Current Piece");
        curPiece.setPadding(new Insets(55, 0, 50, 200));
        getChildren().add(curPiece);
        Label curTreasure = new Label("Current Treasure Card");
        curTreasure.setPadding(new Insets(220, 0, 50, 175));
        getChildren().add(curTreasure);
        Label compTreasure = new Label("# Of Completed Cards");
        compTreasure.setPadding(new Insets(450, 0, 50, 170));
        getChildren().add(compTreasure);
        compTreasureNum=new Label();
        if(player_number==1){
            compTreasureNum.setText(Integer.toString(treasuresGotten_1));
            compTreasureNum.setTextFill(Color.BLUE);
        }
        else{
            compTreasureNum.setText(Integer.toString(treasuresGotten_2));
            compTreasureNum.setTextFill(Color.GREEN);
        }
        compTreasureNum.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        compTreasureNum.setPadding(new Insets(480,0,50,230));
        getChildren().add(compTreasureNum);
        this.toFront();
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
        return treasuresHand.pollFirst();
    }
    public Card getTreasure(){
        return this.currentTreasureCard;
    }

    public void upturnCard(){
        this.currentTreasureCard = getCard();
    }
    public boolean emptyHand(){
        if (treasuresHand.peek() != null)
            return false;
        else
            return true;
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
   
    public void setTreasureImage(Player p, Card card) {
//            String testString = new String("/treasureCards/A.jpg");

        String cardString = new String("/treasureCards/" + card.getValueAsString() + ".jpg");
        Image image = new Image(cardString);
        ImageView treasureCard = new ImageView(image);
        treasureCard.setFitWidth(110);
        treasureCard.setPreserveRatio(true);
        treasureCard.relocate(180, 260);
        p.getChildren().add(treasureCard);
    }
    
    public void currentPlayer(){
        play.setTextFill(null);
    }
    public void otherPlayer(){
        play.setTextFill(Color.GREY);
        this.setVisible(false);
    }
    public void resetColor(){
        play.setTextFill(color);
        this.setVisible(true);
    }
}
    
    
    
    
    

