
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class gui extends Application {

    protected Board labyrinthBoard;
    public Player player1;
    public Player player2;
    public Player currentPlayer;
    public ArrayList<Integer> reachableTiles;
    private LabGame game;
    public GameState state;
    private Label statusLabel;
    public int disabledRowColumn;
    public Direction disabledDirection;
    private Timeline animation;
    private Deck treasureDeck;
    private static final double MILLISEC = 200;
    PlayerPane leftPane;
    PlayerPane rightPane;
    CustomPane boardPane;
    int gameMode;

    public void start(Stage primaryStage) {
        
        Button humanMode = new Button("Human Mode");
        Button computerMode = new Button("Computer Mode");

        //arrange buttons on the pane (at start)
        BorderPane pane = new BorderPane();
        
        Scene scene = new Scene(pane);

        
        pane.setRight(humanMode);
        pane.setLeft(computerMode);
      
        primaryStage.setTitle("Choose a Game Mode!");

        primaryStage.setScene(scene);

        primaryStage.show();

        computerMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

	    	gameMode = 0;                
                
                
                //hide buttons
                humanMode.toBack();
                computerMode.toBack();
                
                createGame(primaryStage);
            }
        });

        humanMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

	    	gameMode = 1;                

                //hide buttons
                humanMode.toBack();
                computerMode.toBack();
                
                createGame(primaryStage);

            }
        });
    }
        
        public void createGame(Stage primaryStage) {
         
        //primaryStage.setHeight(700);
        //primaryStage.setWidth(700);
        primaryStage.setMaximized(true);
        
        treasureDeck = new Deck();
        treasureDeck.shuffle();
        labyrinthBoard = new Board();
        player1 = new HumanPlayer(1);
        player2 = new HumanPlayer(2);
        leftPane = new PlayerPane(player1, 1);
        rightPane = new PlayerPane(player2, 2);
        boardPane = new CustomPane(this, labyrinthBoard);
        currentPlayer = player1;
        int dealInd = 0; //index for dealing
        while (!treasureDeck.isEmpty()) {
            Card cur = treasureDeck.dealCard();
            if (dealInd % 2 == 0) {
                player1.addCard(cur);
            } else {
                player2.addCard(cur);
            }
            dealInd++;
        }
        player1.upturnCard();
        player2.upturnCard();

        // Set initial player positions
        labyrinthBoard.addPlayer(player1, new int[]{0, 0});
        labyrinthBoard.addPlayer(player2, new int[]{6, 6});
        //labyrinthBoard.setStyle("-fx-padding: 60px 10px 10px 10px");
        // Initialize game state.
        state = GameState.insertTile;

        // Find reachable tiles for each player
        reachableTiles = findReachableTilesFor(currentPlayer);

        player1.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(4))));
        player2.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(4))));
        //player1.setStyle("-fx-padding: 10px 10px 10px 10px");

        primaryStage.setTitle("Labyrinth");
        Group root = new Group();
        Scene scene = new Scene(root, 1620, labyrinthBoard.getHeight());

        BorderPane pane = new BorderPane();
        
        Button humanMode = new Button("Human Mode");
        Button computerMode = new Button("Computer Mode");

//arrange buttons on the pane (at start)
pane.setCenter(humanMode);
pane.setCenter(computerMode);

humanMode.toFront();
computerMode.toFront();

        computerMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

	    	gameMode = 0;                

                //hide buttons
                humanMode.toBack();
                computerMode.toBack();
            }
        });

        humanMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

	    	gameMode = 1;                

                //hide buttons
                humanMode.toBack();
                computerMode.toBack();
            }
        });
        
        

    

        //pane.setLeft(player1);
        //pane.setRight(player2);
        pane.setLeft(leftPane);
        pane.setRight(rightPane);
        pane.setCenter(boardPane);
        leftPane.setTreasureImage(player1, player1.getTreasure());
        rightPane.setTreasureImage(player2, player2.getTreasure());
        //pane.setPadding(new Insets(10,10,10,10));
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label stateLabel = new Label(state.toString());
        stateLabel.textProperty().bind(new SimpleStringProperty(state.toString()));
        pane.setBottom(stateLabel);

        root.getChildren().add(pane);

        boardPane.getBoard().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                boolean reachable = false;
                if (state == GameState.movePiece) {
                    int[] tileCoordinates = labyrinthBoard.getTileCoordinates(t.getX(), t.getY());
                    int tileIndex = Path.getTileIndex(tileCoordinates, labyrinthBoard.getX_DIM());
                    for (int reachableTile : reachableTiles) {
                        if (tileIndex == reachableTile) {
                            reachable = true;
                        }
                    }

                    if (reachable) {
                        labyrinthBoard.removePlayer(currentPlayer);
                        if (labyrinthBoard.addPlayer(currentPlayer, tileCoordinates)) {
                            labyrinthBoard.removeTreasure(tileCoordinates);
                            currentPlayer.upturnCard();
                            if (currentPlayer.player_number == 1) {
                                if(!player1.emptyHand())
                                    leftPane.setTreasureImage(player1, player1.getTreasure());
                                else
                                    endGame(player1);
                            } else {
                                if(!player2.emptyHand())
                                    rightPane.setTreasureImage(player2, player2.getTreasure());
                                else
                                    endGame(player2);
                            }
                            System.out.println("Success");
                        }
//                        labyrinthBoard.checkIsTreasure(currentPlayer,tileCoordinates);
                        currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
                        state = GameState.insertTile;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Player " + currentPlayer.player_number + " can't move to this tile.", ButtonType.OK);
                        alert.showAndWait();
                    }
                    //reachableTiles = findReachableTilesFor(currentPlayer);

                    //labyrinthBoard.tileAt(t.getX(), t.getY()).rotateRight(1);
                    //currentShape = new Shape(canvas, currentType, currentColor, t.getX(), t.getY());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You must insert a tile first.", ButtonType.OK);
                    alert.showAndWait();
                }

            }
        });
        //game = new LabGame(this, labyrinthBoard);
        //setUpAnimation();

        //setUpKeyPresses();
        primaryStage.setScene(scene);
        primaryStage.show();
        
        }


    

    public ArrayList<Integer> findReachableTilesFor(Player player) {
        int[] playerLocation = labyrinthBoard.getPlayerLocation(player);
        ArrayList<Integer> reachableTiles = Path.getReachableTiles(labyrinthBoard, playerLocation);

        return reachableTiles;
    }

    private void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {

            this.pause();
            game.update();
            this.resume();
        };
        // Create an animation for alternating text
        animation = new Timeline(new KeyFrame(Duration.millis(MILLISEC), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

    }

    private void pause() {
        animation.pause();
    }
    private void endGame(Player player){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! Player " + currentPlayer.player_number + " has won!", ButtonType.OK);

    }
    /**
     * The action handler method that is called when an insert button is
     * pressed.
     *
     * @param insert A string that identifies which insert method to invoke
     * @param index An int that identifies the location on the board that the
     * tile will be inserted
     */
//    private void handler(String insert, int index) {
//        if (state == GameState.insertTile) {
//            switch (insert) {
//                case "Top":
//                    labyrinthBoard.insertTileTop(index);
//                    System.out.println("this");
//                case "Bottom":
//                    labyrinthBoard.insertTileBottom(index);
//                case "Right":
//                    labyrinthBoard.insertTileRight(index);
//                case "Left":
//                    labyrinthBoard.insertTileLeft(index);
//            }
//            reachableTiles = findReachableTilesFor(currentPlayer);
//            state = GameState.movePiece;
////                    btn_1_1.setText("Player " + currentPlayer.player_number + " pass (Don't Move)");
//        } else if (state == GameState.movePiece) {
//            currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
//
//            state = GameState.insertTile;
////                    btn_1_1.setText("Insert into 1,1");
//        }
//
//    }

    private void setUpKeyPresses() {
        labyrinthBoard.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    game.left();
                    break;
                case RIGHT:
                    game.right();
                    break;
                case DOWN:
                    game.rotateLeft();
                    break;
                case UP:
                    game.rotateRight();
                    break;
                case SPACE:
                    game.drop();
                    break;

            }
        });
        labyrinthBoard.requestFocus(); // board is focused to receive key input

    }

    /**
     * Resumes the animation.
     */
    private void resume() {
        animation.play();
    }

    public static void main(String[] args) {
        Application.launch(args);

        //LabSquare test = new LabPiece(board.rows/2,1,1,board);
        //f.show();
    }
}

class CustomPane extends StackPane {

    public CustomPane(gui gui, Board labyrinthBoard) {
        
        labyrinthBoard.setPadding(new Insets(90, 90, 90, 90));
        getChildren().add(labyrinthBoard);
        setPadding(new Insets(90, 90, 90, 90));
                
                InsertButton btn_1_1 = new InsertButton(gui, labyrinthBoard, this, 1, 1, "-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
                InsertButton btn_1_3 = new InsertButton(gui, labyrinthBoard, this, 3, 1, "-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
		
		InsertButton btn_1_5 = new InsertButton(gui, labyrinthBoard, this, 5, 1, "-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
                
                InsertButton btn_2_1 = new InsertButton(gui, labyrinthBoard, this, 1, 3, "-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
                
                InsertButton btn_2_3 = new InsertButton(gui, labyrinthBoard, this, 3, 3, "-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

                InsertButton btn_2_5 = new InsertButton(gui, labyrinthBoard, this, 5, 3, "-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
		
                InsertButton btn_3_1 = new InsertButton(gui, labyrinthBoard, this, 1, 4, "-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

                InsertButton btn_3_3 = new InsertButton(gui, labyrinthBoard, this, 3, 4, "-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

		InsertButton btn_3_5 = new InsertButton(gui, labyrinthBoard, this, 5, 4, "-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

                InsertButton btn_4_1 = new InsertButton(gui, labyrinthBoard, this, 1, 2, "-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

		InsertButton btn_4_3 = new InsertButton(gui, labyrinthBoard, this, 3, 2, "-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

                InsertButton btn_4_5 = new InsertButton(gui, labyrinthBoard, this, 5, 2, "-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");

		
		setAlignment(btn_1_1.getButton(), Pos.TOP_LEFT);
		setAlignment(btn_1_3.getButton(), Pos.TOP_CENTER);
		setAlignment(btn_1_5.getButton(), Pos.TOP_RIGHT);
		
		setMargin(btn_1_1.getButton(),new Insets(-80,0,0,90));
		setMargin(btn_1_3.getButton(),new Insets(-80,0,0,0));
		setMargin(btn_1_5.getButton(),new Insets(-80,90,0,0));
		
		setAlignment(btn_2_1.getButton(), Pos.BOTTOM_LEFT);
		setAlignment(btn_2_3.getButton(), Pos.BOTTOM_CENTER);
		setAlignment(btn_2_5.getButton(), Pos.BOTTOM_RIGHT);
		
		setMargin(btn_2_1.getButton(),new Insets(0,0,-50,90));
		setMargin(btn_2_3.getButton(),new Insets(0,0,-50,0));
		setMargin(btn_2_5.getButton(),new Insets(0,90,-50,0));
		
		setAlignment(btn_3_1.getButton(), Pos.CENTER_LEFT);
		setAlignment(btn_3_3.getButton(), Pos.CENTER_LEFT);
		setAlignment(btn_3_5.getButton(), Pos.CENTER_LEFT);
		
		setMargin(btn_3_1.getButton(),new Insets(-300,0,0,-80));
		setMargin(btn_3_3.getButton(),new Insets(-15,0,0,-80));
		setMargin(btn_3_5.getButton(),new Insets(270,0,0,-80));
		
		setAlignment(btn_4_1.getButton(), Pos.CENTER_RIGHT);
		setAlignment(btn_4_3.getButton(), Pos.CENTER_RIGHT);
		setAlignment(btn_4_5.getButton(), Pos.CENTER_RIGHT);
		
		setMargin(btn_4_1.getButton(),new Insets(-300,-80,0,0));
		setMargin(btn_4_3.getButton(),new Insets(-15,-80,0,0));
		setMargin(btn_4_5.getButton(),new Insets(270,-80,0,0));
//        Button btn_1_1 = new Button();
//        btn_1_1.setMaxWidth(35);
//        btn_1_1.setMaxHeight(60);
//        btn_1_1.setStyle("-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_1_1);
//
//        Button btn_1_3 = new Button();
//        btn_1_3.setMaxWidth(35);
//        btn_1_3.setMaxHeight(60);
//        btn_1_3.setStyle("-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_1_3);
//
//        Button btn_1_5 = new Button();
//        btn_1_5.setMaxWidth(35);
//        btn_1_5.setMaxHeight(60);
//        btn_1_5.setStyle("-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_1_5);
//
//        Button btn_2_1 = new Button();
//        btn_2_1.setMaxWidth(35);
//        btn_2_1.setMaxHeight(60);
//        btn_2_1.setStyle("-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_2_1);
//
//        Button btn_2_3 = new Button();
//        btn_2_3.setMaxWidth(35);
//        btn_2_3.setMaxHeight(60);
//        btn_2_3.setStyle("-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_2_3);
//
//        Button btn_2_5 = new Button();
//        btn_2_5.setMaxWidth(35);
//        btn_2_5.setMaxHeight(60);
//        btn_2_5.setStyle("-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_2_5);
//
//        Button btn_3_1 = new Button();
//        btn_3_1.setMaxWidth(60);
//        btn_3_1.setMaxHeight(35);
//        btn_3_1.setStyle("-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_3_1);
//
//        Button btn_3_3 = new Button();
//        btn_3_3.setMaxWidth(60);
//        btn_3_3.setMaxHeight(35);
//        btn_3_3.setStyle("-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_3_3);
//
//        Button btn_3_5 = new Button();
//        btn_3_5.setMaxWidth(60);
//        btn_3_5.setMaxHeight(35);
//        btn_3_5.setStyle("-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_3_5);
//
//        Button btn_4_1 = new Button();
//        btn_4_1.setMaxWidth(60);
//        btn_4_1.setMaxHeight(35);
//        btn_4_1.setStyle("-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_4_1);
//
//        Button btn_4_3 = new Button();
//        btn_4_3.setMaxWidth(60);
//        btn_4_3.setMaxHeight(35);
//        btn_4_3.setStyle("-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_4_3);
//
//        Button btn_4_5 = new Button();
//        btn_4_5.setMaxWidth(60);
//        btn_4_5.setMaxHeight(35);
//        btn_4_5.setStyle("-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
//        getChildren().add(btn_4_5);
//
//        setAlignment(btn_1_1, Pos.TOP_LEFT);
//        setAlignment(btn_1_3, Pos.TOP_CENTER);
//        setAlignment(btn_1_5, Pos.TOP_RIGHT);
//
//        setMargin(btn_1_1, new Insets(-80, 0, 0, 90));
//        setMargin(btn_1_3, new Insets(-80, 0, 0, 0));
//        setMargin(btn_1_5, new Insets(-80, 90, 0, 0));
//
//        setAlignment(btn_2_1, Pos.BOTTOM_LEFT);
//        setAlignment(btn_2_3, Pos.BOTTOM_CENTER);
//        setAlignment(btn_2_5, Pos.BOTTOM_RIGHT);
//
//        setMargin(btn_2_1, new Insets(0, 0, -50, 90));
//        setMargin(btn_2_3, new Insets(0, 0, -50, 0));
//        setMargin(btn_2_5, new Insets(0, 90, -50, 0));
//
//        setAlignment(btn_3_1, Pos.CENTER_LEFT);
//        setAlignment(btn_3_3, Pos.CENTER_LEFT);
//        setAlignment(btn_3_5, Pos.CENTER_LEFT);
//
//        setMargin(btn_3_1, new Insets(-300, 0, 0, -80));
//        setMargin(btn_3_3, new Insets(-15, 0, 0, -80));
//        setMargin(btn_3_5, new Insets(270, 0, 0, -80));
//
//        setAlignment(btn_4_1, Pos.CENTER_RIGHT);
//        setAlignment(btn_4_3, Pos.CENTER_RIGHT);
//        setAlignment(btn_4_5, Pos.CENTER_RIGHT);
//
//        setMargin(btn_4_1, new Insets(-300, -80, 0, 0));
//        setMargin(btn_4_3, new Insets(-15, -80, 0, 0));
//        setMargin(btn_4_5, new Insets(270, -80, 0, 0));


    }

    public Node getBoard() {
        Node boardNode = getChildren().get(0);

        return boardNode;
    }
}

class PlayerPane extends Pane {
    //private Label player2=new Label();

    public PlayerPane(Player player, int playerNum) {
        getChildren().add(player);
        Label play = new Label("Player " + playerNum);
        play.setPadding(new Insets(20, 0, 50, 175));
        play.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        if (playerNum == 1) {
            play.setTextFill(Color.BLUE);
        } else {
            play.setTextFill(Color.GREEN);
        }
        getChildren().add(play);
        Label curPiece = new Label("Current Piece");
        curPiece.setPadding(new Insets(55, 0, 50, 190));
        getChildren().add(curPiece);
        Label curTreasure = new Label("Current Treasure Card");
        curTreasure.setPadding(new Insets(220, 0, 50, 165));
        getChildren().add(curTreasure);
        Label compTreasure = new Label("# Of Completed Cards");
        compTreasure.setPadding(new Insets(450, 0, 50, 160));
        getChildren().add(compTreasure);
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
}
