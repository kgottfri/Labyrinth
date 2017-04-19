
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
    int side;
    gui gui;
    Board board;
    
    InsertButton(gui gui, Board board, CustomPane pane, int index, int side, String styleString) {
                
        if (side == 1 || side == 3) { 
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
                if (side == 1) {
                    handleTop();
                }
                
                if (side == 2) {
                    handleRight();
                }
                
                if (side == 3) {
                    handleBottom();
                }
                
                if (side == 4) {
                    handleLeft();
                }
    
            }


//                if (state == GameState.insertTile) {
//                    if ((disabledDirection == Direction.down) && (disabledRowColumn == 1)) {
//                        Alert alert = new Alert(Alert.AlertType.ERROR, "You can not undo the last move.", ButtonType.OK);
//                        alert.showAndWait();
//                    } else {
//                        labyrinthBoard.insertTileTop(1);
//                        reachableTiles = findReachableTilesFor(currentPlayer);
//                        state = GameState.movePiece;
//                        disabledRowColumn = 1;
//                        disabledDirection = Direction.up;
//                    }
//                } else if (state == GameState.movePiece) {
//                    currentPlayer = currentPlayer.player_number == 1 ? player2 : player1;
//                    state = GameState.insertTile;
////                    btn_1_1.setText("Insert into 1,1");
//                }
               

            
        });
    }
    
    public Button getButton() {
        return button;
    }
    
    private void handleTop() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == Direction.down) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                else {
                    board.insertTileTop(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.up;
                }
            } else if (gui.state == GameState.movePiece) {
                    gui.currentPlayer = gui.currentPlayer.player_number == 1 ? gui.player2 : gui.player1;
                    gui.state = GameState.insertTile;
                
                    }
    }
    
    private void handleRight() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == Direction.left) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                else {
                    board.insertTileRight(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.right;
                }
            } else if (gui.state == GameState.movePiece) {
                    gui.currentPlayer = gui.currentPlayer.player_number == 1 ? gui.player2 : gui.player1;
                    gui.state = GameState.insertTile;
                
                    }
    }
    
    private void handleBottom() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == Direction.up) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                else {
                    board.insertTileBottom(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.down;
                }
            } else if (gui.state == GameState.movePiece) {
                    gui.currentPlayer = gui.currentPlayer.player_number == 1 ? gui.player2 : gui.player1;
                    gui.state = GameState.insertTile;
                
                    }
    }
    
    private void handleLeft() {
            if (gui.state == GameState.insertTile) { 
                if ((gui.disabledDirection == Direction.right) && (gui.disabledRowColumn == index)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot undo the last move.", ButtonType.OK);
                    alert.showAndWait();
                }
                else {
                    board.insertTileLeft(index);
                    gui.reachableTiles = gui.findReachableTilesFor(gui.currentPlayer);
                    gui.state = GameState.movePiece;
                    gui.disabledRowColumn = index;
                    gui.disabledDirection = Direction.left;
                }
            } else if (gui.state == GameState.movePiece) {
                    gui.currentPlayer = gui.currentPlayer.player_number == 1 ? gui.player2 : gui.player1;
                    gui.state = GameState.insertTile;
                
                    }
    }
        
    
    
}
