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
	
	private static Stage primaryStage;
	private static Scene scene;
	
	private static Rectangle muteArea;
	private static Circle soundArea;
	
	private static VBox controls;
	private static HBox circles;
	private static VBox pen;
	
    static double orgSceneX;
	static double orgSceneY;
    static double orgTranslateX, orgTranslateY;
    
    private Node me;
 
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
		
		me = new Node("Me", Color.RED, 0.0, soundArea.getCenterX(), soundArea.getCenterY());
		StackPane Me = addMeNode(me);
		StackPane stack = new StackPane();
		stack.getChildren().addAll(muteArea, soundArea, line, Me);
		
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
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		s.widthProperty().addListener((obs, oldVal, newVal) -> {

			muteArea.setWidth((double) oldVal - ((double)oldVal / 6));
			soundArea.setCenterX(((double) oldVal / 2.0));
			soundArea.setCenterY(((double) oldVal / 2.0));

		});

		s.heightProperty().addListener((obs, oldVal, newVal) -> {

			muteArea.setHeight((double) oldVal - 10 );
			soundArea.setCenterX(((double) oldVal / 2.0));
			soundArea.setCenterY(((double) oldVal / 2.0));
			soundArea.setRadius(((double) oldVal / 2.0) - 40);
		});
		
	}
	
	public static void drawLine(){
		
	}
	
	public static void addNode(Node node){
		nodes.add(node);
		
		StackPane stack = new StackPane();
		Random rand = new Random();
		
		// Set x coord of node to a random spot
		int randWidth = rand.nextInt((int)muteArea.getWidth());
		
		// Set y coord of node to a random spot
		int randHeight = rand.nextInt((int)muteArea.getHeight());

		Circle crc = new Circle();
		crc.setFill(node.colour);
		crc.setRadius(15);
		crc.setCenterX(randWidth);
		crc.setCenterY(randHeight);

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
	
	public static StackPane addMeNode(Node me){
		
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
	            
	            line.setStartX(muteArea.getWidth() / 2); // Mid point
	            line.setStartY(muteArea.getHeight() / 2);
	            
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
	
    public Node getMe() {
		return me;
	}

	public void setMe(Node me) {
		this.me = me;
	}
	
}
