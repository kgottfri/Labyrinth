import com.sun.xml.internal.ws.commons.xmlutil.Converter;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class gui extends Application{
	private Board labyrinthBoard;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private ArrayList<Integer> reachableTiles;
	private LabGame game;
	private GameState state;
	private Label statusLabel;
	private Timeline animation;
	private static final double MILLISEC = 200;
	
	public void start(Stage primaryStage){
		
		labyrinthBoard = new Board();
		player1 = new Player(1);
		player2 = new Player(2);
        currentPlayer = player1;

		// Set initial player positions
        labyrinthBoard.addPlayer(player1, new int[]{0, 0});
        labyrinthBoard.addPlayer(player2, new int[]{6, 6});

        // Initialize game state.
        state = GameState.insertTile;

        // Find reachable tiles for each player
        reachableTiles = findReachableTilesFor(currentPlayer);
		
		player1.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(4))));
		player2.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(4))));
		//player1.setStyle("-fx-padding: 10px 10px 10px 10px");
		
		primaryStage.setTitle("Labyrinth");
		Group root = new Group();
		Scene scene = new Scene(root,1620,labyrinthBoard.getHeight());
		
		BorderPane pane=new BorderPane();
		pane.setLeft(player1);
		pane.setRight(player2);
		pane.setCenter(labyrinthBoard);
		pane.setPadding(new Insets(10,10,10,10));
		pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		Label stateLabel = new Label(state.toString());
		stateLabel.textProperty().bind(new SimpleStringProperty(state.toString()));
		pane.setBottom(stateLabel);

		Button btn_1_1 = new Button("Insert into 1,1");
		pane.setTop(btn_1_1);
		btn_1_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (state == GameState.insertTile) {
                    labyrinthBoard.insertTileTop(1);
                    reachableTiles = findReachableTilesFor(currentPlayer);
                    state = GameState.movePiece;
                    btn_1_1.setText("Player " + currentPlayer.player_number + " pass (Don't Move)");
                }
                else if (state == GameState.movePiece){
                    currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
                    state = GameState.insertTile;
                    btn_1_1.setText("Insert into 1,1");
                }
            }
        });

		
		root.getChildren().add(pane);

        pane.getCenter().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
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

                    if (reachable){
                        labyrinthBoard.removePlayer(currentPlayer);
                        labyrinthBoard.addPlayer(currentPlayer, tileCoordinates);
                        currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
                        state = GameState.insertTile;
                        btn_1_1.setText("Insert into 1,1");
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Player " + currentPlayer.player_number + " can't move to this tile.", ButtonType.OK);
                        alert.showAndWait();
                    }
                    //reachableTiles = findReachableTilesFor(currentPlayer);

                    //labyrinthBoard.tileAt(t.getX(), t.getY()).rotateRight(1);
                    //currentShape = new Shape(canvas, currentType, currentColor, t.getX(), t.getY());
                }
                else{
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
		
		//add tile
                //Tile testTile = new Tile(labyrinthBoard, 20, 20, false, true, false, true, true);
		
		
//		Button slideTileDown=new Button();
//		slideTileDown.setText("Down");
//		slideTileDown.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				System.out.println("Moved Tile Down");
//			}
//		});;
		
		
	}

	private ArrayList<Integer> findReachableTilesFor(Player player) {
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
		
	public static void main(String[] args){
		Application.launch(args);
		
		//LabSquare test = new LabPiece(board.rows/2,1,1,board);
		//f.show();
	}
}
