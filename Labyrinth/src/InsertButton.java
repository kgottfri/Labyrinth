
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 *
 * @author AlexBeard/Eric
 * 
 * Creates one of arrow buttons used to insert tiles on board
 */
public class InsertButton {
    
    Button button = new Button();
    
    //index button corresponds to
    //if button is on top/bottom, index is xIndex
    //if button is of left/right, index is yIndex
    int index;

    Direction side;
    gui gui;
    Board board;
   
    /**
     * Constructor
     * @param gui
     * @param board
     * @param pane
     * @param index
     * @param side
     * @param styleString 
     */
    InsertButton(gui gui, Board board, CustomPane pane, int index, Direction side, String styleString) {
       
        //Set width/height (depending on if it is top/bottom or left/right
        if (side == Direction.up || side == Direction.down) {
            button.setMaxWidth(35);
            button.setMaxHeight(60);
        }
        else {
            button.setMaxWidth(60);
            button.setMaxHeight(35);
        }
        
        //apply css style
        button.setStyle(styleString);
        
        pane.getChildren().add(button);
        
        this.index = index;
        this.side = side;
        this.gui = gui;
        this.board = board;

        //handler for button - call correct method based on which side it is on
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //top
                if (side == Direction.down) {
                    handleTop();
                }

                if (side == Direction.left) {
                    handleRight();
                }

                if (side == Direction.up) {
                    handleBottom();
                }

                if (side == Direction.right) {
                    handleLeft();
                }

                // Enable the button that lets the player pass.
                gui.boardPane.getPassButton().setDisable(false);
            }
        });
    }
    
    /**
     * 
     * @return button (so it can be referenced/position can be set)
     */
    public Button getButton() {
        return button;
    }
    
    /**
     * Handle Insert from Top
     * 
     */
    private void handleTop() {
        
            //Alert if move would reverse last move
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                
                //else: insert tile from top
                else {
                    board.insertTileTop(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.boardPane.setPassButtonText("");
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.up;
                }
            }
    }
   
    /**
     * Handle Insert from Right
     */
    private void handleRight() {
        
            //Alert if move would reverse last move
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                
                //else: insert tile from right 
                else {
                    board.insertTileRight(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.boardPane.setPassButtonText("");
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.right;
                }
            }
    }
    
    /**
     * Handle Insert from Bottom
     */
    private void handleBottom() {
        
            //Alert if move would reverse last move
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                
                //else: insert tile from bottom
                else {
                    board.insertTileBottom(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.boardPane.setPassButtonText("");
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.down;
                }
            }
    }
    
    /**
     * Handle Insert from Left
     */
    private void handleLeft() {
        
            //Alert if move would reverse last move
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                
                //else: insert tile from left
                else {
                    board.insertTileLeft(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.boardPane.setPassButtonText("");
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.left;
                }
            }
    }
        
    
    
}
