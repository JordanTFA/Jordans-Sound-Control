import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application{
	
	Button button;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Jordan\'s Sound Control");
		
		Rectangle group = new Rectangle();
		group.setX(10);
		group.setY(10);
		group.setWidth(150);
		group.setHeight(150);
		group.setFill(Color.ALICEBLUE);
		
		Circle soundArea = new Circle();
		soundArea.maxWidth(150);
		soundArea.maxHeight(150);
		soundArea.setFill(Color.BLUE);
		
		Button add = new Button();
		add.setText("Add Node");
		add.setOnAction(e -> NodeWindow.display());
		
		Button delete = new Button();
		delete.setText("Delete Node");
		
		delete.setOnAction(e->{
			if(1<3){
				// Node is selected -> Remove node
				System.out.println("Deleted <node>");
				
			} else{
				
				// TODO: Make this graphical
				System.out.println("Select a node!");
				
			}
				
		});
		
		VBox layout = new VBox();
		layout.getChildren().addAll(group, soundArea, add, delete);
		
		Scene scene = new Scene(layout, 400, 250);
		primaryStage.setMinWidth(400);
		primaryStage.setMinHeight(250);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public static void broadcastToUser(){
		
	}

}
