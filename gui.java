import javafx.scene.layout.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class gui extends Application{
	private board labyrinthBoard;
	private player player1;
	private player player2;
	
	public void start(Stage primaryStage){
		
		labyrinthBoard=new board();
		player1= new player();
		player2=new player();
		
		primaryStage.setTitle("Labyrinth");
		Group root = new Group();
		Scene scene = new Scene(root,1600,labyrinthBoard.getHeight());
		//BorderPane player1Pane=new BorderPane();
		
		//BorderPane player2Pane=new BorderPane();
		
		
		
		BorderPane pane=new BorderPane();
		pane.setLeft(player1);
		pane.setRight(player2);
		pane.setCenter(labyrinthBoard);
		//pane.setStyle("-fx-background-color: black");
		
		root.getChildren().add(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		
//		Button slideTileDown=new Button();
//		slideTileDown.setText("Down");
//		slideTileDown.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				System.out.println("Moved Tile Down");
//			}
//		});;
		
		
	}
	
		
	public static void main(String[] args){
		Application.launch(args);
		//f.show();
	}
}
