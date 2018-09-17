import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AudioTracks {
	
	private static Label output;
	
	// Draw main window
	public static void draw(ArrayList<Node> nodes){
		
		Stage stage = new Stage();
		stage.setTitle("Select a track");
		
		VBox layout = new VBox(5);
		// TODO: Fix this
		layout.setPadding(new Insets(10, 50, 50, 50));
		
		ArrayList<String> tracks = getTracks();
		ComboBox<String> combo = fillOutTracks(tracks);
		
		Button goButton = new Button("Go");
		goButton.setOnAction(e ->{
			
		});
		
		output = new Label();
		layout.getChildren().addAll(drawCircles(nodes), combo, output, goButton);

		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	// Make the combobox for the tracks and populate it
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
	        	output.setText(combo.getValue());
	        }
	    });
		return combo;
	}
	
	public static void playTracks(ArrayList<Node> nodes){
		
		for(Node node : nodes){
			Node.playTrack(node.track);
		}
	}
	
	// Draw each of the nodes
	// Need to make them selectable
	public static HBox drawCircles(ArrayList<Node> nodes){
		
		HBox circleLayout = new HBox(2);
		
		double radius = 15;
		
		for(Node node : nodes){
			Circle circle = new Circle(radius, node.colour);
			Label label = new Label(node.name);
			label.setStyle("-fx-font:14 arial;");
			
			// TODO: Set an ID
			
			StackPane stack = new StackPane();
			stack.getChildren().addAll(circle, label);
			circleLayout.getChildren().add(stack);
		}
		
		return circleLayout;	
	}
	
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
}
