import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AudioTracks {
	
	private static Label output;
	private static String track = "";
	private static Node selectedNode;
	
	static MediaPlayer player;

	// Draw main window
	public static void draw(ArrayList<Node> oldNodes){
		
		ArrayList<Node> newNodes = cloneNodes(oldNodes);
		
		Stage stage = new Stage();
		stage.setTitle("Select a track");
		
		VBox layout = new VBox(5);
		// TODO: Fix this
		layout.setPadding(new Insets(10, 50, 50, 50));
		
		final HBox buttons = new HBox(5);
		
		ArrayList<String> tracks = getTracks();
		ComboBox<String> combo = fillOutTracks(tracks);
		
		Button goButton = new Button("Go");
		goButton.setOnAction(e ->{
			try{
				playTracks(newNodes);
			}catch(MediaException ex){
				output.setText("Select a track");
			}

		});
		
		Button setButton = new Button("Set");
		setButton.setOnAction(e -> {
			
			Node selectedNode = getSelectedNode();
			selectedNode.setTrack(getTrack());
			
			System.out.println(selectedNode.name + " = " + selectedNode.track);
		});
		
		buttons.getChildren().addAll(goButton, setButton);
		
		output = new Label();
		layout.getChildren().addAll(drawCircles(newNodes), combo, output, buttons);
		
		output.setText("Select a track");

		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public static ArrayList<Node> cloneNodes(ArrayList<Node> oldNodes){
		
		ArrayList<Node> newNodes = new ArrayList<Node>();
		
		for(Node node : oldNodes){
			newNodes.add(node);
		}
		return newNodes;
	}
	
	// Make the ComboBox for the tracks and populate it
	public static ComboBox<String> fillOutTracks(ArrayList<String> tracks){
		
		ComboBox<String> combo = new ComboBox<String>();
		
		combo.setPromptText("Select a Track:");
		
		for(String track : tracks){
			combo.getItems().add(track);
		}
		
		combo.valueProperty().addListener(new ChangeListener<String>() {

			// Update the label
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	        	setTrack(combo.getValue());
	        }
	    });
		return combo;
	}
	
	public static void playTracks(ArrayList<Node> nodes){
		
		for(Node node : nodes){
			
			String path = "tracks/" + node.track;
			Media media = new Media(Node.class.getResource(path).toExternalForm());
			player = new MediaPlayer(media);
			player.play();
		}
	}
	
	// Draw each of the nodes
	// Need to make them selectable
	public static HBox drawCircles(ArrayList<Node> nodes){
		
		final HBox circleLayout = new HBox(2);
		
		double radius = 15;
		
		for(Node node : nodes){
			Circle circle = new Circle(radius, node.colour);
			Label label = new Label(node.name);
			label.setStyle("-fx-font:14 arial;");
			
			node.getChildren().addAll(circle, label);
			circleLayout.getChildren().add(node);
			
			node.setOnMouseClicked(circleOnMousePressedEventHandler);
		}
		return circleLayout;	
	}
	
	static EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
		@Override
		public void handle(MouseEvent t) {
			
			setSelectedNode((Node) t.getSource());
	  	}
	};
	
	// Get all of the tracks from the res/tracks folder
	public static ArrayList<String> getTracks(){
		
		ArrayList<String> tracks = new ArrayList<String>();
		String path = "res//tracks//";
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		for(File file : listOfFiles){
			tracks.add(file.getName());
		}
		return tracks;
	}
	
	public static void updatePreviewLabel(){
		
		output.setText(getSelectedNode().name + " - " + getTrack());
	}
	
	public static String getTrack() {
		return track;
	}

	public static void setTrack(String track) {
		AudioTracks.track = track;
		updatePreviewLabel();
	}

	public static Node getSelectedNode() {
		return selectedNode;
	}

	public static void setSelectedNode(Node selectedNode) {
		AudioTracks.selectedNode = selectedNode;
		updatePreviewLabel();
	}
}
