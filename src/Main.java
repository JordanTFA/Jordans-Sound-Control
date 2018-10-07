import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    
    private static Node meNode;
    public final static double NODE_RADIUS = 15;
 
	static Line line;
	
	Button button;
	static ArrayList<Node> nodes;
	
	static Node selectedNode;

	static double lastSuccessfulX;
	static double lastSuccessfulY;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		nodes = new ArrayList<Node>();
		line = new Line();
		setLastSuccessfulX(50.0);
		setLastSuccessfulY(0.0);
		
		build(stage);
	}
	
	public static void build(Stage stage){
		
		buildWindow(stage);
		VBox controls = buildControls();
		Pane circles = buildCircles();
		
		HBox layout = new HBox(10);
		layout.getChildren().addAll(circles, controls);

		scene = new Scene(layout, 620, 500);
		stage.setScene(scene);
		stage.show();
		
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			
			double centreX = muteArea.getWidth() / 2;
			double centreY = muteArea.getHeight() / 2;

			muteArea.setWidth((double) oldVal - (120));
			soundArea.setCenterX(centreX);
			soundArea.setCenterY(centreY);
			
			soundArea.setRadius(getCircleRadius());
			
			meNode.relocate(centreX - NODE_RADIUS, centreY - NODE_RADIUS);
		});

		stage.heightProperty().addListener((obs, oldVal, newVal) -> {

			double centreX = muteArea.getWidth() / 2;
			double centreY = muteArea.getHeight() / 2;
			
			muteArea.setHeight((double)oldVal);
			soundArea.setCenterX(centreX);
			soundArea.setCenterY(centreY);
			
			soundArea.setRadius(getCircleRadius());
			
			meNode.relocate(centreX - NODE_RADIUS, centreY - NODE_RADIUS);

		});
	}
	
	// Return the value the radius should be (half of smallest muteArea dimension)
	public static double getCircleRadius(){
		
		double radius = muteArea.getWidth();
		if(muteArea.getHeight() < radius){
			radius = muteArea.getHeight();
		}
		
		radius /= 2;
		
		return radius;
	}
	
	public static void buildWindow(Stage stage){
		
		stage.setTitle("Jordan\'s Sound Control");
		
		muteArea = new Rectangle(500, 500, Color.POWDERBLUE);
		soundArea = new Circle(250, Color.DODGERBLUE);
		soundArea.setCenterX(muteArea.getWidth() / 2);
		soundArea.setCenterY(muteArea.getHeight() / 2);
	}
	
	public static Pane buildCircles(){
		
		meNode = new Node("Me", Color.RED, 0.0, soundArea.getCenterX(), soundArea.getCenterY(), "");
		meNode = addMeNode(meNode);
		
		circles = new Pane();
		circles.getChildren().addAll(muteArea, soundArea, line, meNode);
		
		return circles;
	}

	public static VBox buildControls(){
		
		Button add = new Button("Add Node");
		add.setOnAction(e -> NodeWindow.display());
		
		Button delete = new Button("Delete Node");
		delete.setOnAction(e -> {
			
			if(getSelectedNode() != null){
				// Node is selected -> Remove node
				circles.getChildren().removeAll(getSelectedNode(), line);
					
			} else{	
				// No node is selected -> Show user error
				showError("No nodes selected!");			
			}
		});	
		
		Button selectTrack = new Button("Assign Tracks");
		selectTrack.setOnAction(e -> {

			try{
				AudioTracks.draw(nodes);			
			}catch(IndexOutOfBoundsException ex){
				showError("Please create at least one node!");
			}

		});
		
		controls = new VBox(5);
		controls.getChildren().addAll(add, delete, selectTrack);
		
		return controls;
	}
	
	public static void showError(String message){
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
		
	}
	
	public static void addNode(Node node){
		nodes.add(node);

		Random rand = new Random();
		
		// Set x coordinate of node to a random spot
		double randWidth = rand.nextInt((int)muteArea.getWidth() - 50);
		node.setX(randWidth);
		
		// Set y coordinate of node to a random spot
		double randHeight = rand.nextInt((int)muteArea.getHeight() - 50);
		node.setY(randHeight);

		Circle crc = new Circle(NODE_RADIUS, node.colour);

		Label label = new Label(node.name);
		label.setStyle("-fx-font:14 arial;");
		
		node.getChildren().addAll(crc, label);
		crc.setCursor(Cursor.MOVE);
		node.setOnMousePressed(circleOnMousePressedEventHandler);
		node.setOnMouseDragged(circleOnMouseDraggedEventHandler);

		node.relocate(node.x, node.y);
		circles.getChildren().add(node);
		node.toFront();
		
		System.out.println(node.name + " added with colour: " + node.colour);
	}
	
	public static Node addMeNode(Node me){
		
		Circle crc = new Circle(NODE_RADIUS, me.colour);
		
		Label lblme = new Label(me.name);
		lblme.setStyle("-fx-font:14 arial;");
		
		me.relocate(me.x - NODE_RADIUS, me.y - NODE_RADIUS);
		me.getChildren().addAll(crc, lblme);
		
		return me;
		
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
			
			setSelectedNode((Node)t.getSource());
			
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

		Bounds bounds = muteArea.getBoundsInLocal();
		
		if(t.getSceneX() <= (bounds.getMinX() + NODE_RADIUS)){
			//System.out.println("Out of bounds");//newTranslateX = bounds.getMinX() + NODE_RADIUS;
			//newTranslateX = orgTranslateX;
			newTranslateX = 0;
		}
		else if(t.getSceneX() >= (bounds.getMaxX() - NODE_RADIUS)){
			//System.out.println("Out of bounds");//newTranslateY = bounds.getMaxX() + NODE_RADIUS;
			newTranslateX = 0;
		}
		else if(t.getSceneY() <= (bounds.getMinY() + NODE_RADIUS)){
			System.out.println("Out of bounds");//newTranslateY = bounds.getMinY() + NODE_RADIUS;
			newTranslateY = 0;
		}
		else if(t.getSceneY() >= (bounds.getMaxY() - NODE_RADIUS)){
			System.out.println("Out of bounds");//newTranslateY = bounds.getMaxY() + NODE_RADIUS;
			newTranslateY = 0;
		}
			
		((Node)(t.getSource())).setTranslateX(newTranslateX);
		((Node)(t.getSource())).setTranslateY(newTranslateY);
		
		setLastSuccessfulX(t.getSceneX());
		setLastSuccessfulY(t.getSceneX());
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
			((Node)(t.getSource())).setVolume(0.0);
			System.out.println("Muted");
		}else{
			// Normalise and set volume of node
			double volume = (int)normaliseVolume(soundArea.getRadius(), length);
			((Node)(t.getSource())).setVolume(volume);
			System.out.println(((Node)t.getSource()).getVolume() + "%");

		}
	}
	
	public static double normaliseVolume(double maxVol, double currentVol){
	    	
		// Convert length to percentage
		return 100 - ((currentVol / maxVol) * 100);
	}
	
	public static Node getSelectedNode() {
		return selectedNode;
	}

	public static void setSelectedNode(Node selectedNode) {
		Main.selectedNode = selectedNode;
	}
	
	public static double getLastSuccessfulX() {
		return lastSuccessfulX;
	}

	public static void setLastSuccessfulX(double lastSuccessfulX) {
		Main.lastSuccessfulX = lastSuccessfulX;
	}

	public static double getLastSuccessfulY() {
		return lastSuccessfulY;
	}

	public static void setLastSuccessfulY(double lastSuccessfulY) {
		Main.lastSuccessfulY = lastSuccessfulY;
	}
}
