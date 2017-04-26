
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**w
 *
 * @author AlexBeard
 */
public class InsertButton {
    
    Button button = new Button();
    
    int index;
    Direction side;
    gui gui;
    Board board;
    
    InsertButton(gui gui, Board board, CustomPane pane, int index, Direction side, String styleString) {
                
        if (side == Direction.up || side == Direction.down) {
            button.setMaxWidth(35);
            button.setMaxHeight(60);
        
        }
        
        else {
            button.setMaxWidth(60);
            button.setMaxHeight(35);
        }
        button.setStyle(styleString);
        pane.getChildren().add(button);
        
        this.index = index;
        this.side = side;
        this.gui = gui;
        this.board = board;

        
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
    
    public Button getButton() {
        return button;
    }
    
    private void handleTop() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
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
    
    private void handleRight() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
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
    
    private void handleBottom() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
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
    
    private void handleLeft() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == side) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
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
