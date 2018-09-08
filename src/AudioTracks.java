import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AudioTracks {
	
	private static Label output;
	
	public static void draw(ArrayList<Node> nodes){
		
		Stage stage = new Stage();
		stage.setTitle("Select a track");
		
		VBox layout = new VBox(20);
		
		ArrayList<String> tracks = getTracks();
		ComboBox<String> combo = fillOutTracks(tracks);
		
		output = new Label();
		layout.getChildren().addAll(drawCircles(nodes), combo, output);

		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public static ComboBox<String> fillOutTracks(ArrayList<String> tracks){
		
		ComboBox<String> combo = new ComboBox<String>();
		
		combo.setPromptText("Select a Track:");
		
		for(String track : tracks){
			combo.getItems().add(track);
		}
		
		combo.valueProperty().addListener(new ChangeListener<String>() {

	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	        	output.setText(combo.getValue());
	        }
	    });
		return combo;
	}
	
	public static HBox drawCircles(ArrayList<Node> nodes){
		
		HBox circleLayout = new HBox(5);
		
		double radius = 15;
		
		for(Node node : nodes){
			Circle circle = new Circle(radius, node.colour);
			circleLayout.getChildren().add(circle);
		}
		
		return circleLayout;	
	}
	
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
