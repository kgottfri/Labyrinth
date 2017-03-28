import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Tetris Application, which contains the board and a message label.
 * Also contains some code for interaction between the 2 boards.
 *
 * @author pipWolfe/Alex Beard
 */
public class Tetris extends Application {

    //initialize new label: will be changed when game type is decided
    private Label statusLabel = new Label("  ");

    //create both tetris games
    private TetrisGame game;
    private TetrisGame game2;

    //create both tetris boards
    private TetrisBoard tetrisBoard;
    private TetrisBoard tetrisBoard2;

    private Timeline animation;
    private Timeline animation2;

    private Stage primaryStage;

    //constants for use with gameType
    private final int BATTLEMODE = 0;
    private final int SCOREMODE = 1;
    private final int PUZZLEMODE = 2;

    //message in the middle of the 2 games on screen
    public String statusLabelMessage;
    
    //to make sure update/key presses aren't functional after game ends
    boolean keepGoing = true;

    /**
     * Launches the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Sets up the tetris board and game, as well as a status label that can be
     * used to display scores and messages.
     *
     * Enables key events for the arrow keys and space bar, as well as an
     * animation.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane);

        //choosing a button changes the gameType variable
        //this will be passed through to tetrisBoard
        //create battle mode and score mode buttons and put on the screen
        Button battleModeButton = new Button("Battle Mode");
        Button scoreModeButton = new Button("Score Mode");
        Button puzzleModeButton = new Button("Puzzle Mode");

        //arrange buttons on the pane (at start)
        pane.setRight(battleModeButton);
        pane.setLeft(scoreModeButton);
        pane.setCenter(puzzleModeButton);
        

        //score mode constructor
        scoreModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                System.out.println("Score Mode");

                //call createTetrisGames - pass in game type
                createTetrisGames(SCOREMODE);

                //hide buttons
                scoreModeButton.toBack();
                battleModeButton.toBack();
                puzzleModeButton.toBack();
            }
        });

        //battle mode constructor
        battleModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                System.out.println("Battle Mode");

                //call createTetrisGames - pass in game type
                createTetrisGames(BATTLEMODE);

                //hide buttons
                scoreModeButton.toBack();
                battleModeButton.toBack();
                puzzleModeButton.toBack();

            }
        });

        //puzzle mode
        puzzleModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                System.out.println("Puzzle Mode");

                //call createTetrisGames - pass in game type
                createTetrisGames(PUZZLEMODE);

                //hide buttons
                scoreModeButton.toBack();
                battleModeButton.toBack();
                puzzleModeButton.toBack();
            }
        });

        primaryStage.setTitle("Tetris Start Screen");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * creates 2 tetris boards and games - sets up gameplay
     * @param gameType type of game to create
     */
    private void createTetrisGames(int gameType) {

        //create both tetrisBoards
        tetrisBoard = new TetrisBoard();
        tetrisBoard2 = new TetrisBoard();

        //create games
        game2 = new TetrisGame(this, tetrisBoard2, gameType);
        game = new TetrisGame(this, tetrisBoard, gameType);

        //set up both animations
        setUpAnimation2();
        setUpAnimation();

        statusLabelMessage = "Game has started!";

        //initialize labels as blank
        Label player1 = new Label(" ");
        Label player2 = new Label(" ");

        //only need scoreLabels if it is score mode
        if (gameType == SCOREMODE) {

            player1.setText("Player 1 Score:");
            player2.setText("Player 2 Score:");

        }

        //null labels for spacing purposes
        //I probably could have done this somehow by formatting my vbox
        Label nullLabel = new Label(" ");
        Label nullLabel2 = new Label(" ");

        //set up vbox (middle of screen)
        VBox scoreboard = new VBox(5);
        scoreboard.getChildren().addAll(player1, game.scoreLabel, nullLabel, player2, game2.scoreLabel, nullLabel2, statusLabel);
        BorderPane pane = new BorderPane();
        
        //set up formatting
        pane.setRight(tetrisBoard);
        pane.setLeft(tetrisBoard2);
        pane.setCenter(scoreboard);

        //show them on the screen
        Scene scene = new Scene(pane);

        
        setUpKeyPresses(scene);

        primaryStage.setTitle("Tetris GamePlay");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * Changes the message in the status label in the scoreboard
     *
     * @param message
     */
    public void setMessage(String message) {
        statusLabel.setText(message);

    }

    
    /**
     * 
     * Animation for board
     * Sets up an animation timeline that calls update on the game every
     * board.millisec milliseconds.
     */
    private void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            this.pause();

            if (keepGoing) {

                game.update();
                this.resume();
            }

        };
        // Create an animation for alternating text
        animation = new Timeline(new KeyFrame(Duration.millis(game.millisec), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

    }

    /**
     * Animation for board2
     * Sets up an animation timeline that calls update on the game every
     * board2.millisec milliseconds.
     */
    private void setUpAnimation2() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            this.pause();

            if (keepGoing) {
                game2.update();
                this.resume();

            }
        };
        // Create an animation for alternating text
        animation2 = new Timeline(new KeyFrame(Duration.millis(game2.millisec), eventHandler));
        animation2.setCycleCount(Timeline.INDEFINITE);
        animation2.play();


    }

   /**
    * 
    * Sets up key events for the arrow keys and space bar, as well as AWSD and X (game2). All keys send
    * messages to the game, which should react appropriately.
    * @param scene -scene that key presses must be entered on
    */
    private void setUpKeyPresses(Scene scene) {

        scene.setOnKeyPressed(e -> {

            if (keepGoing) {
                switch (e.getCode()) {

                    //Start up screen controls:
                    //Right board controls
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

                    //left board controls
                    case A:
                        game2.left();
                        break;
                    case D:
                        game2.right();
                        break;
                    case S:
                        game2.rotateLeft();
                        break;
                    case W:
                        game2.rotateRight();
                        break;
                    case X:
                        game2.drop();
                        break;

                }

            }

        });

        tetrisBoard.requestFocus(); // board is focused to receive key input

    }

    /**
     * Pauses the animation.
     */
    private void pause() {
        animation.pause();
    }

    /**
     * Resumes the animation.
     */
    private void resume() {
        animation.play();
    }

    /**
     * end game 
     * if game is ended in a way where someone has won - acheived some goal
     */
    public void endGame() {

        if (game.gameOver == true) {

            statusLabel.setText("Player 1 wins!");

        } else {
            statusLabel.setText("Player 2 wins!");
        }

        keepGoing = false;

    }
    /**
     * end game - if game has ended by someone's screen being too full of pieces
     * (someone has "lost" instead of someone has "won"
     */
    public void endGameFilled() {

        if (game.gameOver == true) {

            statusLabel.setText("Player 2 wins!");

        } else {
            statusLabel.setText("Player 1 wins!");
        }

        keepGoing = false;

    }

}