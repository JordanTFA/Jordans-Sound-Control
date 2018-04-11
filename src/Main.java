import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Stage primaryStage;
	public static Scene scene;
	public static Rectangle group;
	public static VBox layout;
	
	Button button;
	static ArrayList<Node> nodes; // Maybe unnecessary

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unused")
	@Override
	public void start(Stage s) throws Exception {
		
		primaryStage = s;
		nodes = new ArrayList<Node>();
		
		primaryStage.setTitle("Jordan\'s Sound Control");
		
		group = new Rectangle();
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
		
		layout = new VBox();
		layout.getChildren().addAll(group, soundArea, add, delete);
		
		scene = new Scene(layout, 400, 250);
		primaryStage.setMinWidth(400);
		primaryStage.setMinHeight(250);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public static void addNode(Node node){
		nodes.add(node);
		
		Circle crc = new Circle();
		crc.setFill(node.colour);
		crc.setRadius(15);
		crc.setCenterX(node.x);
		crc.setCenterY(node.y);
		
		layout.getChildren().add(crc);
		System.out.println(node.name + " added with colour: " + node.colour);
	}
	
	public static void broadcastToUser(){
		
	}
	
}
