import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AudioTracks {
	
	public static void draw(ArrayList<Node> nodes){
		
		Stage stage = new Stage();
		stage.setTitle("Select a track");
		
		VBox layout = new VBox(20);
		
		ArrayList<String> tracks = getTracks();
		ComboBox<String> combo = fillOutTracks(tracks);

		layout.getChildren().addAll(drawCircles(nodes), combo);
		
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
		return combo;
	}
	
	public static HBox drawCircles(ArrayList<Node> nodes){
		
		HBox circleLayout = new HBox(5);
		
		double radius = 15;
		
		for(Node node : nodes){
			Circle circle = new Circle(15, node.colour);
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
			System.out.println(file.getName());
			tracks.add(file.getName());
		}
		return tracks;
	}
}
