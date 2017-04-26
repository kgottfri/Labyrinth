
import javafx.animation.*;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class gui extends Application {
    /*
    gui handles all the game initialization functions, information display, event handlers, and dictates the game flow.
     */

    /*
    Initialize all objects required to play the game and variables to keep track of the game state.
     */
    protected Board labyrinthBoard;
    public Player player1;
    public Player player2;
    public Player currentPlayer;
    public ArrayList<Integer> reachableTiles;
    public GameState state;

    public int disabledRowColumn;
    public Direction disabledDirection;
    private Deck treasureDeck;
    int gameMode;

    CustomPane boardPane;
    Scene scene;
    Stage primaryStage;

    public void start(Stage primaryStage) {
        /*
        This is main entry point into the application.  It is called when the application is launched and
        sets up two buttons so that the player can choose to play against another human or a computer.  When a mode is
        chosen, the game is created.
         */
        Button humanMode = new Button("Human Mode");
        Button computerMode = new Button("Computer Mode");

        //arrange buttons on the pane (at start)
        BorderPane pane = new BorderPane();

        scene = new Scene(pane);

        pane.setRight(humanMode);
        pane.setLeft(computerMode);

        primaryStage.setTitle("Choose a Game Mode!");

        primaryStage.setScene(scene);

        primaryStage.show();

        /*
        Game mode button event handlers
         */
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
        /*
        createGame is called after the game mode is chosen.  It sets up the main GUI and initializes the game by
        creating a game board, a treasure deck, and players.  Listeners are defined so that appropriate actions
        are taken when a button or tile is selected.
         */
        this.primaryStage = primaryStage;
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Labyrinth");

        // Set up game board and player panes.
        Group root = new Group();
        labyrinthBoard = new Board();
        Scene scene = new Scene(root, 1300, labyrinthBoard.getHeight());
        BorderPane pane = new BorderPane();

        player1 = new Player(1, labyrinthBoard);
        player1.setStyle("-fx-background-color: lightgrey;");
        player2 = new Player(2, labyrinthBoard);
                player2.setStyle("-fx-background-color: lightgrey;");
        player1.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(4))));
        player2.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(4))));

        boardPane = new CustomPane(this, labyrinthBoard);

        pane.setLeft(player1);
        pane.setRight(player2);
        pane.setCenter(boardPane);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        root.getChildren().add(pane);

        // Shuffle and deal cards from the deck of treasure cards.
        treasureDeck = new Deck();
        treasureDeck.shuffle();
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
        player1.setTreasureImage(player1, player1.getTreasure());
        player2.setTreasureImage(player2, player2.getTreasure());

        // Set initial player positions
        labyrinthBoard.addPlayer(player1, new int[]{0, 0});
        labyrinthBoard.addPlayer(player2, new int[]{6, 6});

        // Initialize game state.
        state = GameState.insertTile;

        // Player 1 goes first.
        currentPlayer = player1;
        player2.otherPlayer();
        reachableTiles = findReachableTilesFor(currentPlayer);

        // Allow player to move to a tile by clicking on it.
        boardPane.getBoard().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                boolean reachable = false;
                if (state == GameState.movePiece) {
                    /*
                     Find the tile that was clicked and determine if it is reachable.  All reachable tiles should
                     already be discovered when a tile is inserted.
                      */
                    int[] tileCoordinates = labyrinthBoard.getTileCoordinates(t.getX(), t.getY());
                    int tileIndex = Path.getTileIndex(tileCoordinates, labyrinthBoard.getX_DIM());
                    for (int reachableTile : reachableTiles) {
                        if (tileIndex == reachableTile) {
                            reachable = true;
                        }
                    }

                    if (reachable) {
                        moveCurrentPlayer(tileCoordinates);

                        // Switch to the next players turn and update state.
                        currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
                        state = GameState.insertTile;

                        // If the player is a computer, let the computer take their turn.
                        if (gameMode == 0 && currentPlayer.player_number == 2) {
                            computerTakeTurn();
                        }

                        //update currentTile
                        player1.extraBoard.updateExtraTileBoard();
                        player2.extraBoard.updateExtraTileBoard();

                        // GUI updates required when switching turns.
                        if (currentPlayer.equals(player1)) {
                            player2.otherPlayer();
                            boardPane.getPassButton().setTextFill(Color.BLUE);
                        } else {
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
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You must insert a tile first.", ButtonType.OK);
                    alert.showAndWait();
                }

            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ArrayList<Integer> findReachableTilesFor(Player player) {
        /*
        findReachableTilesFor returns a list of tile indices representing all the tiles that are reachable from the
        player's location.
         */
        int[] playerLocation = labyrinthBoard.getPlayerLocation(player);
        ArrayList<Integer> reachableTiles = Path.getReachableTiles(labyrinthBoard, playerLocation);

        return reachableTiles;
    }

    public void moveCurrentPlayer(int[] tileCoordinates) {
        /*
        Move player to the new tile specified in tileCoordinates.  If the player lands on the goal treasure, increase
        the score and turn over the next treasure card.  If there are no more treasures, end the game.
         */
        labyrinthBoard.removePlayer(currentPlayer);

        // addPlayer returns true if the treasure on the new tile matches the players goal treasure.
        if (labyrinthBoard.addPlayer(currentPlayer, tileCoordinates)) {

            labyrinthBoard.removeTreasure(tileCoordinates);

            if (currentPlayer.player_number == 1) {
                if (!player1.emptyHand()) {
                    player1.upturnCard();
                    player1.setTreasureImage(player1, player1.getTreasure());
                    player1.treasuresGotten_1 += 1;
                    player1.compTreasureNum.setText(Integer.toString(player1.treasuresGotten_1));
                } else {
                    endGame(player1);
                }
            } else {
                if (!player2.emptyHand()) {
                    player2.upturnCard();
                    player2.setTreasureImage(player2, player2.getTreasure());
                    player2.treasuresGotten_2 += 1;
                    player2.compTreasureNum.setText(Integer.toString(player2.treasuresGotten_2));
                } else {
                    endGame(player2);
                }
            }
        }
    }

    public void computerTakeTurn() {
        /*
        The computer chooses a random location to insert a tile (unless the chosen location is disabled since it is on
        the opposite side of the last move).  The computer then tries to move to the tile that contains its goal
        treasure if it can.  Otherwise it moves to a random reachable tile.
         */

        // Graphic to indicate that it is the computers turn
        boardPane.computerWaitPane.setVisible(true);
        Timeline computerThinking = createComputerTimeline(boardPane.computerWaitPane);
        computerThinking.setOnFinished(event -> boardPane.computerWaitPane.setVisible(false));
        computerThinking.play();

        // Insert the extra maze tile randomly
        Random randInt = new Random();
        int buttonInt = randInt.nextInt(12);
        InsertButton chosenButton = boardPane.buttonsList.get(buttonInt);

        // Prevent the computer from re-inserting the tile illegally
        if (chosenButton.index == disabledRowColumn && chosenButton.side == disabledDirection) {
            chosenButton = boardPane.buttonsList.get((buttonInt + 1) % 11);
        }
        chosenButton.button.fire();

        // Move to the tile with the goal treasure or a random tile.
        boolean found = false;
        for (int reachableTile : reachableTiles) {
            int[] tempTileCoordinates = Path.getTileCoordinates(reachableTile, labyrinthBoard.getX_DIM());
            Tile tempTile = labyrinthBoard.tiles[tempTileCoordinates[0]][tempTileCoordinates[1]];
            if (tempTile.treasure == currentPlayer.getTreasure().getValueAsString().toCharArray()[0]) {
                moveCurrentPlayer(tempTileCoordinates);
                found = true;
            }
            if (!found) {
                int randomTile = randInt.nextInt(reachableTiles.size());
                moveCurrentPlayer(Path.getTileCoordinates(reachableTiles.get(randomTile), labyrinthBoard.getX_DIM()));
            }
        }

        // Computer is done, now it is player1's turn.
        currentPlayer = player1;
        state = GameState.insertTile;
    }

    private Timeline createComputerTimeline(Node node) {
        // Computer animation
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

    private void endGame(Player player) {
        /*
        Allow the player to quit the game or start a new game once somebody has won.
         */
        ButtonType quit = new ButtonType("Quit");
        ButtonType restart = new ButtonType("New Game");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! Player " + currentPlayer.player_number + " has won!", quit, restart);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == quit) {
            System.exit(0);
        } else if (result.isPresent() && result.get() == restart) {
            primaryStage.close();
            Stage newStage = new Stage();
            start(newStage);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

class CustomPane extends StackPane {
    /*
    CustomPane organizes the game board and the tile insertion buttons.  It also displays a graphic for when
    the computer player is taking its turn.
     */
    public ArrayList<InsertButton> buttonsList = new ArrayList<InsertButton>();
    private Button btn_pass;
    public StackPane computerWaitPane;
    private Label computerWaitLabel;

    public CustomPane(gui gui, Board labyrinthBoard) {

        labyrinthBoard.setPadding(new Insets(90, 90, 90, 90));
        getChildren().add(labyrinthBoard);
        setPadding(new Insets(90, 90, 90, 90));

        // Create insertion buttons and add them to a button list (so that the computer player can choose from the list)
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

        // Put the buttons in their proper positions
        setAlignment(btn_1_1.getButton(), Pos.TOP_LEFT);
        setAlignment(btn_1_3.getButton(), Pos.TOP_CENTER);
        setAlignment(btn_1_5.getButton(), Pos.TOP_RIGHT);

        setMargin(btn_1_1.getButton(), new Insets(-80, 0, 0, 90));
        setMargin(btn_1_3.getButton(), new Insets(-80, 0, 0, 0));
        setMargin(btn_1_5.getButton(), new Insets(-80, 90, 0, 0));

        setAlignment(btn_2_1.getButton(), Pos.BOTTOM_LEFT);
        setAlignment(btn_2_3.getButton(), Pos.BOTTOM_CENTER);
        setAlignment(btn_2_5.getButton(), Pos.BOTTOM_RIGHT);

        setMargin(btn_2_1.getButton(), new Insets(0, 0, -50, 90));
        setMargin(btn_2_3.getButton(), new Insets(0, 0, -50, 0));
        setMargin(btn_2_5.getButton(), new Insets(0, 90, -50, 0));

        setAlignment(btn_3_1.getButton(), Pos.CENTER_LEFT);
        setAlignment(btn_3_3.getButton(), Pos.CENTER_LEFT);
        setAlignment(btn_3_5.getButton(), Pos.CENTER_LEFT);

        setMargin(btn_3_1.getButton(), new Insets(-300, 0, 0, -80));
        setMargin(btn_3_3.getButton(), new Insets(-15, 0, 0, -80));
        setMargin(btn_3_5.getButton(), new Insets(270, 0, 0, -80));

        setAlignment(btn_4_1.getButton(), Pos.CENTER_RIGHT);
        setAlignment(btn_4_3.getButton(), Pos.CENTER_RIGHT);
        setAlignment(btn_4_5.getButton(), Pos.CENTER_RIGHT);

        setMargin(btn_4_1.getButton(), new Insets(-300, -80, 0, 0));
        setMargin(btn_4_3.getButton(), new Insets(-15, -80, 0, 0));
        setMargin(btn_4_5.getButton(), new Insets(270, -80, 0, 0));

        /*
         Create a button to allow the player to pass (not move).  This button is also used to tell the player to first
         insert a tile.  This is accomplished by disabling the button and changing the label to "Insert Tile".
          */
        btn_pass = new Button("Insert Tile");
        btn_pass.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        btn_pass.setTextFill(Color.BLUE);
        btn_pass.setMaxWidth(200);
        btn_pass.setMaxHeight(25);
        btn_pass.setDisable(true);
        setAlignment(btn_pass, Pos.BOTTOM_CENTER);
        setMargin(btn_pass, new Insets(0, 0, -85, 0));
        getChildren().add(btn_pass);

        btn_pass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                gui.currentPlayer = gui.currentPlayer.player_number == 1 ? gui.player2 : gui.player1;
                gui.state = GameState.insertTile;
                if (gui.gameMode == 0 && gui.currentPlayer.player_number == 2) {
                    gui.computerTakeTurn();
                } else {
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

        // Computer Wait graphic (invisible unless the computer is taking its turn)
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
    }

    public Node getBoard() {
        Node boardNode = getChildren().get(0);

        return boardNode;
    }

    public Button getPassButton() {
        return btn_pass;
    }

    public void setPassButtonText(String text) {
        if (text == "") {
            btn_pass.setText("Skip Move Phase");
        } else {
            btn_pass.setText(text);
        }

    }
}
