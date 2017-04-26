// CS_205 Labyrinth

import java.util.Collections;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import static javafx.scene.layout.StackPane.setAlignment;
import static javafx.scene.layout.StackPane.setMargin;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.BorderFactory;

/**
 * Player class for both human and computer players
 * @author Kevin Gottfried
 */
public class Player extends Pane{
    
    protected LinkedList<Card> treasuresHand;
    private Card currentTreasureCard;
    protected int score = 0;
    protected char successfulTreasure;
    public int treasuresGotten_1 = 0;
    public int treasuresGotten_2 = 0;
    public static final int Prows = 7;
    public static final int Pcols = 1;
    public static final int squareWidth=300;
    public static final int squareHeight=100;
    public Color color;
    public int player_number;
    Label play;
    Label compTreasureNum;
    Board board;
    Board extraBoard;
    
    public Player(){
        this.setPrefHeight(Prows*squareHeight);
	this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
    }

    public Player(int playerType, Board board){
        this.board = board;
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f,0.4f,0.4f));
        player_number = playerType;
        if (playerType == 1){
            this.color = Color.BLUE;
        }
        else
            this.color = Color.GREEN;
        this.setPrefHeight(Prows*squareHeight);
	    this.setPrefWidth(Pcols*squareWidth);
        treasuresHand = new LinkedList<Card>();
        play = new Label("Player " + player_number);
        play.setAlignment(Pos.TOP_CENTER);
        play.setPadding(new Insets(20, 0, 50, 95));
        play.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        play.setEffect(ds);
        if (player_number == 1) {
            play.setTextFill(Color.BLUE);
        } else {
            play.setTextFill(Color.GREEN);
        }
        getChildren().add(play);
        Label curPieceLabel = new Label("Current Piece");
        curPieceLabel.setLayoutX(105);
        curPieceLabel.setLayoutY(75);
        getChildren().add(curPieceLabel);
        

        //display extra Tile
        extraBoard = new Board(board,0,0);
        extraBoard.setLayoutX(110);
        extraBoard.setLayoutY(105);
        extraBoard.setBorder(new Border(new BorderStroke(Color.BLACK, new BorderStrokeStyle(null,null,null,10,0,null),null,null)));
        
        getChildren().add(extraBoard);
        
        Button btn_right = new Button("R");
        btn_right.setMaxHeight(20);
        btn_right.setMaxWidth(20);
        btn_right.setBorder(new Border(new BorderStroke(Color.BLACK, new BorderStrokeStyle(null,null,null,10,0,null),null,null)));
        getChildren().add(btn_right);

        btn_right.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                board.extra_tile.rotateRight(1);
                extraBoard.updateExtraTileBoard();
            }
        });
        Button btn_left = new Button("L");
        btn_left.setPrefHeight(20);
        btn_left.setPrefWidth(20);
        btn_left.setBorder(new Border(new BorderStroke(Color.BLACK, new BorderStrokeStyle(null,null,null,10,0,null),null,null)));

        btn_left.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                board.extra_tile.rotateRight(3);
                extraBoard.updateExtraTileBoard();
            }
        });        

        Label curTreasure = new Label("Current Treasure Card");
        curTreasure.setPadding(new Insets(220, 0, 50, 90));
        getChildren().add(curTreasure);
        Label compTreasure = new Label("# Of Completed Cards");
        compTreasure.setPadding(new Insets(450, 0, 50, 85));
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
        compTreasureNum.setPadding(new Insets(480,0,50,145));
        getChildren().add(compTreasureNum);
        this.toFront();
        HBox hbox= new HBox(100);
        hbox.getChildren().addAll(btn_left,btn_right);
        hbox.setLayoutY(105);
        hbox.setLayoutX(70);
        hbox.toFront();
        getChildren().add(hbox);
        hbox.toFront();
        
        Button infoButton = new Button("i");
        infoButton.setMaxHeight(35);
        infoButton.setMaxWidth(35);
        infoButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        infoButton.setTextFill(color);

        HBox hbox2 = new HBox();
        hbox2.getChildren().add(infoButton);
        hbox2.setLayoutY(10);
        hbox2.setLayoutX(10);
        hbox2.toFront();
        getChildren().add(hbox2);
        hbox2.toFront();
        
        infoButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
          
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your goal is to reach your current treasure card, and collect all of your 13 treasure cards before your opponent collects all of theirs. Rotate your current piece, insert it, and then move your player on the board. Good luck! ", ButtonType.OK);
                alert.showAndWait();
                    
            }
        });
    }
    
    /**
     * The addCard method ands a card object to the LinkedList hand
     * @param c The Card object to be added to the players hand
     */
    public void addCard(Card c) {
        if(c != null)
            treasuresHand.add(c);
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

    public void setTreasureImage(Player p, Card card) {

        String cardString = new String("/treasureCards/" + card.getValueAsString() + ".jpg");
        Image image = new Image(cardString);
        ImageView treasureCard = new ImageView(image);
        treasureCard.setFitWidth(110);
        treasureCard.setPreserveRatio(true);
        treasureCard.relocate(95, 260);
        p.getChildren().add(treasureCard);
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
    
    
    
    
    

