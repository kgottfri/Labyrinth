
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AlexBeard
 */
public class InsertButton {
    
    Button button = new Button();
    
    int index;
    int side;
    
    InsertButton(Board board, CustomPane pane, int index, int side, String styleString) {
        
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
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (side == 1) { board.insertTileTop(index); }
                if (side == 2) { board.insertTileRight(index); }
                if (side == 3) { board.insertTileBottom(index); }
                if (side == 4) { board.insertTileLeft(index); }



               

            }
        });
    }
    
    public Button getButton() {
        return button;
    }
    
}
