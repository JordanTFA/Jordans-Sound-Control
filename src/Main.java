import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// TODO: Add new Vbox for nodes to go.

public class Main extends Application{
	
	private static Scene scene;
	
	private static Rectangle muteArea;
	private static Circle soundArea;
	
	private static VBox controls;
	private static Pane circles;
	
    static double orgSceneX;
	static double orgSceneY;
    static double orgTranslateX, orgTranslateY;
    
    private Node me;
    public final static double NODE_RADIUS = 15;
 
	static Line line;
	
	Button button;
	static ArrayList<Node> nodes; // Maybe unnecessary

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unused")
	@Override
	public void start(Stage stage) throws Exception {

		nodes = new ArrayList<Node>();
		line = new Line();
		
		stage.setTitle("Jordan\'s Sound Control");
		
		muteArea = new Rectangle(500, 500, Color.POWDERBLUE);
		soundArea = new Circle(250, Color.DODGERBLUE);
		soundArea.setCenterX(muteArea.getWidth() / 2);
		soundArea.setCenterY(muteArea.getHeight() / 2);
		
		Button add = new Button("Add Node");
		add.setOnAction(e -> NodeWindow.display());
		
		Button delete = new Button("Delete Node");
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
		Me.setLayoutX(me.x - NODE_RADIUS);
		Me.setLayoutY(me.y - NODE_RADIUS);

		circles = new Pane();//HBox(0);
		circles.getChildren().addAll(muteArea, soundArea, line, Me);
		
		controls = new VBox(30);
		controls.getChildren().addAll(add, delete);

		// TODO: This
		
		/* HBox layout
		 * 
		 * ******************************
		 * *                 *          *
		 * *                 *  		*
		 * *                 *          *
		 * *     circles     * controls	*
		 * *                 *          *
		 * *                 *          *
		 * *                 *          *
		 * ******************************
		 */	
		
		HBox layout = new HBox(10);
		layout.getChildren().addAll(circles, controls);
		
		scene = new Scene(layout, 800, 500);
		stage.setScene(scene);
		stage.show();
		
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {

			muteArea.setWidth((double) oldVal - ((double)oldVal / 6));
			soundArea.setCenterX(((double) oldVal / 2.0));
			soundArea.setCenterY(((double) oldVal / 2.0));

		});

		stage.heightProperty().addListener((obs, oldVal, newVal) -> {

			muteArea.setHeight((double) oldVal - 10 );
			soundArea.setCenterX(((double) oldVal / 2.0));
			soundArea.setCenterY(((double) oldVal / 2.0));
			soundArea.setRadius(((double) oldVal / 2.0) - 40);
		});
		
	}
	
	public static void addNode(Node node){
		nodes.add(node);
		
		StackPane stack = new StackPane();
		Random rand = new Random();
		
		// Set x coordinate of node to a random spot
		double randWidth = rand.nextInt((int)muteArea.getWidth() - 50);
		node.setX(randWidth);
		
		// Set y coordinate of node to a random spot
		double randHeight = rand.nextInt((int)muteArea.getHeight() - 50);
		node.setY(randHeight);

		Circle crc = new Circle(NODE_RADIUS, node.colour);

		Label lbl = new Label(node.name);
		lbl.setStyle("-fx-font:14 arial;");
		
		stack.getChildren().addAll(crc, lbl);
		crc.setCursor(Cursor.MOVE);
		stack.setOnMousePressed(circleOnMousePressedEventHandler);
        stack.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        stack.relocate(randWidth, randHeight);
		circles.getChildren().add(stack);
		stack.toFront();
		
		System.out.println(node.name + " added with colour: " + node.colour);
	}
	
	public static StackPane addMeNode(Node me){
		
		Circle crc = new Circle(NODE_RADIUS, me.colour);
		
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
			
			translateSelectedNode(t);
			updateLine(t);
			adjustVolume(t);

		}
	};
	
	public static void translateSelectedNode(MouseEvent t){
		
		double offsetX = t.getSceneX() - orgSceneX;
		double offsetY = t.getSceneY() - orgSceneY;
		double newTranslateX = orgTranslateX + offsetX;
		double newTranslateY = orgTranslateY + offsetY;

		((StackPane)(t.getSource())).setTranslateX(newTranslateX);
		((StackPane)(t.getSource())).setTranslateY(newTranslateY);
		
	}
	
	public static void updateLine(MouseEvent t){
		
		line.setStartX(muteArea.getWidth() / 2); // Mid point
		line.setStartY(muteArea.getHeight() / 2);
            
		line.setEndX(t.getSceneX());
		line.setEndY(t.getSceneY());
		
	}
	
	public static void adjustVolume(MouseEvent t){
		
		// Length of line
		double length = Math.sqrt(Math.pow( line.getStartX() - line.getEndX() , 2) + 
				( Math.pow(line.getStartY() - line.getEndY(), 2)));
                          
		if(length > soundArea.getRadius()){
			// Set volume of node to 0
			System.out.println(t.getSource().toString());
		}else{
			// Normalise and set volume of node
			int volume = (int)normaliseVolume(soundArea.getRadius(), length);
			System.out.println(volume + "%");
		}
		
	}
	
	public static double normaliseVolume(double maxVol, double currentVol){
	    	
		return 100 - ((currentVol / maxVol) * 100);
	}
	
    public Node getMe() {
		return me;
	}

	public void setMe(Node me) {
		this.me = me;
	}
	
}
