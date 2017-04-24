
import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

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
        player1 = new Player(1, labyrinthBoard);
        player2 = new Player(2, labyrinthBoard);
        player2.otherPlayer();
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
        Scene scene = new Scene(root, 1300, labyrinthBoard.getHeight());

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
        pane.setLeft(player1);
        pane.setRight(player2);
        pane.setCenter(boardPane);
        player1.setTreasureImage(player1, player1.getTreasure());
        player2.setTreasureImage(player2, player2.getTreasure());
        //pane.setPadding(new Insets(10,10,10,10));
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        root.getChildren().add(pane);

        boardPane.getBoard().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                
                //currentPlayer.currentPlayer();
                
                boolean reachable = false;
                if (state == GameState.movePiece) {
                    int[] tileCoordinates = labyrinthBoard.getTileCoordinates(t.getX(), t.getY());
                    int tileIndex = Path.getTileIndex(tileCoordinates, labyrinthBoard.getX_DIM());
                    //player1.UpdateTurn(currentPlayer);
                    for (int reachableTile : reachableTiles) {
                        if (tileIndex == reachableTile) {
                            reachable = true;
                        }
                    }

                    if (reachable) {
                        System.out.println("yes");
                            moveCurrentPlayer(tileCoordinates);
//                        labyrinthBoard.checkIsTreasure(currentPlayer,tileCoordinates);
                            currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
                            state = GameState.insertTile;
                            // If the player is a computer, let the computer take their turn.
                            if (gameMode==0 && currentPlayer.player_number == 2){
                                //boardPane.computerWaitPane.setVisible(true);
                                computerTakeTurn();
                            }
                            
                            //update currentTile
                            player1.extraBoard.updateExtraTileBoard();
                            player2.extraBoard.updateExtraTileBoard();

                            System.out.print("extra called");
                        
                        if(currentPlayer.equals(player1)){
                            player2.otherPlayer();
                            boardPane.getPassButton().setTextFill(Color.BLUE);
                        }
                        else{
                            player1.otherPlayer();
                            boardPane.getPassButton().setTextFill(Color.GREEN);
                        }
                        boardPane.setPassButtonText("Insert Tile");
                        boardPane.getPassButton().setDisable(true);
                        currentPlayer.resetColor();

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

    public void moveCurrentPlayer(int[] tileCoordinates){
        labyrinthBoard.removePlayer(currentPlayer);
        if (labyrinthBoard.addPlayer(currentPlayer, tileCoordinates)) {
//            if(currentPlayer.player_number==1){
//                currentPlayer.treasuresGotten+=1;
//            }
//            else{
//                currentPlayer.treasuresGotten+=1;
//            }
            labyrinthBoard.removeTreasure(tileCoordinates);
            currentPlayer.upturnCard();
            if (currentPlayer.player_number == 1) {
                if(!player1.emptyHand()){
                    player1.setTreasureImage(player1, player1.getTreasure());
                    player1.treasuresGotten_1+=1;
                    player1.compTreasureNum.setText(Integer.toString(player1.treasuresGotten_1));
                }
                else
                    endGame(player1);
            } else {
                if(!player2.emptyHand()){
                    player2.setTreasureImage(player2, player2.getTreasure());
                    player2.treasuresGotten_2+=1;
                    player2.compTreasureNum.setText(Integer.toString(player2.treasuresGotten_2));
                }
                else
                    endGame(player2);
            }
            System.out.println("Success");
        }
    }

    public void computerTakeTurn(){
        boardPane.computerWaitPane.setVisible(true);
        Timeline computerThinking = createComputerTimeline(boardPane.computerWaitPane);
        computerThinking.setOnFinished(event -> boardPane.computerWaitPane.setVisible(false));
        computerThinking.play();

        // Insert the extra maze tile randomly
        Random randInt = new Random();
        int buttonInt = randInt.nextInt(12);
        InsertButton chosenButton = boardPane.buttonsList.get(buttonInt);

        // Prevent the computer from re-sinserting the tile illegally
        if (chosenButton.index == disabledRowColumn && chosenButton.side == disabledDirection){
            chosenButton = boardPane.buttonsList.get((buttonInt + 1) % 11);
        }
        chosenButton.button.fire();

        boolean found = false;
        for (int reachableTile:reachableTiles){
            int[] tempTileCoordinates = Path.getTileCoordinates(reachableTile, labyrinthBoard.getX_DIM());
            Tile tempTile = labyrinthBoard.tiles[tempTileCoordinates[0]][tempTileCoordinates[1]];
            if (tempTile.treasure == currentPlayer.getTreasure().getValueAsString().toCharArray()[0]){
                moveCurrentPlayer(tempTileCoordinates);
                found = true;
            }
            if (!found){
                int randomTile = randInt.nextInt(reachableTiles.size());
                moveCurrentPlayer(Path.getTileCoordinates(reachableTiles.get(randomTile), labyrinthBoard.getX_DIM()));
            }
        }
        currentPlayer = player1;
        state = GameState.insertTile;
    }

    private Timeline createComputerTimeline(Node node) {
        Timeline computerThink = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                node.opacityProperty(),
                                1,
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(
                                node.opacityProperty(),
                                1,
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                node.opacityProperty(),
                                1,
                                Interpolator.DISCRETE
                        )
                )
        );
        computerThink.setCycleCount(1);

        return computerThink;
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

    public ArrayList<InsertButton> buttonsList = new ArrayList<InsertButton>();
    private Button btn_pass;
    public StackPane computerWaitPane;
    private Label computerWaitLabel;

    public CustomPane(gui gui, Board labyrinthBoard) {

        labyrinthBoard.setPadding(new Insets(90, 90, 90, 90));
        getChildren().add(labyrinthBoard);
        setPadding(new Insets(90, 90, 90, 90));

        InsertButton btn_1_1 = new InsertButton(gui, labyrinthBoard, this, 1, Direction.down, "-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_1_1);
        InsertButton btn_1_3 = new InsertButton(gui, labyrinthBoard, this, 3, Direction.down, "-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_1_3);
        InsertButton btn_1_5 = new InsertButton(gui, labyrinthBoard, this, 5, Direction.down, "-fx-background-image: url('/arrows/downArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_1_5);
        InsertButton btn_2_1 = new InsertButton(gui, labyrinthBoard, this, 1, Direction.up, "-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_2_1);
        InsertButton btn_2_3 = new InsertButton(gui, labyrinthBoard, this, 3, Direction.up, "-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_2_3);
        InsertButton btn_2_5 = new InsertButton(gui, labyrinthBoard, this, 5, Direction.up, "-fx-background-image: url('/arrows/upArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_2_5);
        InsertButton btn_3_1 = new InsertButton(gui, labyrinthBoard, this, 1, Direction.right, "-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_3_1);
        InsertButton btn_3_3 = new InsertButton(gui, labyrinthBoard, this, 3, Direction.right, "-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_3_3);
        InsertButton btn_3_5 = new InsertButton(gui, labyrinthBoard, this, 5, Direction.right, "-fx-background-image: url('/arrows/rightArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_3_5);
        InsertButton btn_4_1 = new InsertButton(gui, labyrinthBoard, this, 1, Direction.left, "-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_4_1);
        InsertButton btn_4_3 = new InsertButton(gui, labyrinthBoard, this, 3, Direction.left, "-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_4_3);
        InsertButton btn_4_5 = new InsertButton(gui, labyrinthBoard, this, 5, Direction.left, "-fx-background-image: url('/arrows/leftArrow.png'); -fx-background-position:center center; -fx-background-size: cover;");
        buttonsList.add(btn_4_5);



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

        btn_pass = new Button("Insert Tile");
        btn_pass.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        btn_pass.setTextFill(Color.BLUE);
        btn_pass.setMaxWidth(200);
        btn_pass.setMaxHeight(25);
        btn_pass.setDisable(true);
        setAlignment(btn_pass, Pos.BOTTOM_CENTER);
        setMargin(btn_pass,new Insets(0,0,-85,0));
        getChildren().add(btn_pass);

        btn_pass.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                gui.currentPlayer = gui.currentPlayer.player_number == 1 ? gui.player2 : gui.player1;
                gui.state = GameState.insertTile;
                if (gui.gameMode==0 && gui.currentPlayer.player_number == 2){
                    gui.computerTakeTurn();
                }
                else {
                    if (gui.currentPlayer.equals(gui.player1)) {
                        gui.player2.otherPlayer();
                        btn_pass.setTextFill(Color.BLUE);
                    } else {
                        gui.player1.otherPlayer();
                        btn_pass.setTextFill(Color.GREEN);
                    }
                }
                btn_pass.setText("Insert Tile");
                btn_pass.setDisable(true);
                gui.currentPlayer.resetColor();
                gui.player1.extraBoard.updateExtraTileBoard();
                gui.player2.extraBoard.updateExtraTileBoard();
                

                
            }
        });
        computerWaitPane = new StackPane();
        computerWaitPane.setVisible(false);
        Rectangle computerWaitRect = new Rectangle(0, 0, 300, 100);
        computerWaitRect.setFill(Color.WHITE);
        computerWaitRect.setArcHeight(15);
        computerWaitRect.setArcWidth(15);
        computerWaitRect.setStroke(Color.GREEN);
        computerWaitRect.setStrokeWidth(6);

        computerWaitLabel = new Label("Computer Taking Turn...");
        computerWaitLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        computerWaitPane.getChildren().addAll(computerWaitRect, computerWaitLabel);
        this.getChildren().add(computerWaitPane);
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

    public Button getPassButton(){
        return btn_pass;
    }

    public void setPassButtonText(String text){
        if (text == ""){
            btn_pass.setText("Skip Move Phase");
        }
        else{
            btn_pass.setText(text);
        }

    }

    public void displayComputerWait() throws InterruptedException {

        int i = 0;
        int wait = 5;
        this.computerWaitLabel.setText("Computer Thinking");
        while (i < wait) {
            Thread.sleep(333);
            incrementWaitLabel();
            i += 1;
        }
        this.computerWaitPane.setVisible(false);
    }
    private void incrementWaitLabel(){
        String currentText = this.computerWaitLabel.getText();
        currentText += ".";
        this.computerWaitLabel.setText(currentText);
    }
}

