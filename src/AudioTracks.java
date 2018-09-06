import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AudioTracks {
	
	public static void draw(ArrayList<Node> nodes){
		
		Stage stage = new Stage();
		stage.setTitle("Select a track");
		
		VBox layout = new VBox(20);
		Scene scene = new Scene(layout, 300, 300);
		
		ArrayList<String> tracks = getTracks();
		
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public static ArrayList<String> getTracks(){
		
		String path = "res//tracks//";
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		for(File file : listOfFiles){
			System.out.println(file.getName());
		}
		
		// TODO: Get list of all files in the folder
		return null;
	}
}
