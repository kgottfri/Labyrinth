import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class gui extends Application{
	private Board labyrinthBoard;
	private Player player1;
	private Player player2;
	
	public void start(Stage primaryStage){
		
		labyrinthBoard=new Board();
		player1= new Player();
		player2=new Player();
		
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
