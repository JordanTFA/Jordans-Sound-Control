import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// TODO: Add new Vbox for nodes to go.

public class Main extends Application{
	
	public static Stage primaryStage;
	public static Scene scene;
	
	public static Rectangle muteArea;
	public static Circle soundArea;
	
	public static VBox controls;
	public static HBox circles;
	public static VBox pen;
	
    static double orgSceneX;
	static double orgSceneY;
    static double orgTranslateX, orgTranslateY;
    
    static Line line;
	
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
		
		line = new Line();
		
		primaryStage.setTitle("Jordan\'s Sound Control");
		
		muteArea = new Rectangle();
		muteArea.setX(10);
		muteArea.setY(10);
		muteArea.setWidth(500);
		muteArea.setHeight(500);
		muteArea.setFill(Color.POWDERBLUE);
		
		soundArea = new Circle();
		soundArea.setRadius(250);
		soundArea.setFill(Color.DODGERBLUE);
		
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
		
		StackPane me = addMeNode();
		StackPane stack = new StackPane();
		stack.getChildren().addAll(muteArea, soundArea, line, me);
		
		circles = new HBox(5);
		circles.getChildren().add(stack);
		
		controls = new VBox(10);
		controls.getChildren().addAll(add, delete);
		
		pen = new VBox();
		// TODO: This
		
		/* ******************************
		 * *                 *          *
		 * *                 * controls *
		 * *                 *          *
		 * *     circles     * **********
		 * *                 *          *
		 * *                 *    pen   *
		 * *                 *          *
		 * ******************************
		 */
		
		
		HBox layout = new HBox(10);
		layout.getChildren().addAll(circles, controls);
		
		scene = new Scene(layout, 800, 500);
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(500);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		s.widthProperty().addListener((obs, oldVal, newVal) -> {

			muteArea.setWidth((double) oldVal - 0);

		});

		s.heightProperty().addListener((obs, oldVal, newVal) -> {

			muteArea.setHeight((double) oldVal - 0 );
		});
		
	}
	
	public static void addNode(Node node){
		nodes.add(node);
		
		StackPane stack = new StackPane();
		Random r = new Random();
		
		Circle crc = new Circle();
		crc.setFill(node.colour);
		crc.setRadius(15);
		crc.setCenterX(node.x);
		crc.setCenterY(node.y);

		Label lbl = new Label(node.name);
		lbl.setStyle("-fx-font:14 arial;");
		
		stack.getChildren().addAll(crc, lbl);
		crc.setCursor(Cursor.MOVE);
		stack.setOnMousePressed(circleOnMousePressedEventHandler);
        stack.setOnMouseDragged(circleOnMouseDraggedEventHandler);

		circles.getChildren().add(stack);
		stack.toFront();
		
		System.out.println(node.name + " added with colour: " + node.colour);
	}
	
	public static StackPane addMeNode(){
		
		Node me = new Node("me", Color.RED, 0.0, 0.0 ,0.0);
		Circle crc = new Circle();
		crc.setFill(me.colour);
		crc.setRadius(15);
		crc.setCenterX(me.x);
		crc.setCenterY(me.y);
		
		Label lblme = new Label(me.name);
		lblme.setStyle("-fx-font:14 arial;");
		StackPane Me = new StackPane();
		
		Me.getChildren().addAll(crc, lblme);
		
		return Me;
		
	}
	
	
	
	static EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
	        @Override
	        public void handle(MouseEvent t) {
	            orgSceneX = t.getSceneX();
	            orgSceneY = t.getSceneY();
	            orgTranslateX = ((StackPane)(t.getSource())).getTranslateX();
	            orgTranslateY = ((StackPane)(t.getSource())).getTranslateY();
	        }
	    };
	     
	    static EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
	        @Override
	        public void handle(MouseEvent t) {
	            double offsetX = t.getSceneX() - orgSceneX;
	            double offsetY = t.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX + offsetX;
	            double newTranslateY = orgTranslateY + offsetY;

	            /*if(newTranslateX < muteArea.getX()){
	            	((StackPane)(t.getSource())).setTranslateX(muteArea.getX());
	            }//else if(newTranslateX > muteArea.getWidth()){
	            	//((StackPane)(t.getSource())).setTranslateX(muteArea.getX() + muteArea.getWidth());
	            //}
	            else{
		            ((StackPane)(t.getSource())).setTranslateX(newTranslateX);
	            }
	            if(newTranslateY < muteArea.getY()){
	            	
	            	((StackPane)(t.getSource())).setTranslateY(muteArea.getY());
	            }//else if(newTranslateY > muteArea.getY() + muteArea.getHeight()){
	            	//((StackPane)(t.getSource())).setTranslateY(muteArea.getY() + muteArea.getHeight());
	            //}
	            else{
		            ((StackPane)(t.getSource())).setTranslateY(newTranslateY);
	            }*/

	            ((StackPane)(t.getSource())).setTranslateX(newTranslateX);
	            ((StackPane)(t.getSource())).setTranslateY(newTranslateY);
	            //System.out.println(t.getSceneX() + " " + t.getSceneY());

	            line.setEndX(t.getSceneX());
	            line.setEndY(t.getSceneY());
	            
	            line.setStartX(123); // Mid point
	            line.setStartY(125);
	            
	            double length = Math.sqrt(Math.pow( line.getStartX() - line.getEndX() , 2) + 
	            		( Math.pow(line.getStartY() - line.getEndY(), 2)));
	               
	            if(length > soundArea.getRadius()){
	            	// Set volume of node to 0
	            	System.out.println("Muted");
	            }else{
	            	// Normalise and set volume of node
		            System.out.println(length);
	            }
	        }
	    };
	
	public static void broadcastToUser(){
		
	}
	
}
