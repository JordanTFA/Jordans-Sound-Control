import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NodeWindow {

	public static void display(){
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Node Selection");
		window.setMinWidth(250);
		window.setMinHeight(250);
		
		Label instr = new Label("Select a colour and a name for the node");
		
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		
		/*for(int i = 0; i < window.getWidth(); i++){
			for(int j = 0; j < window.getHeight(); j++){
				
				Rectangle r = new Rectangle(10 * i, 10 * j, 10, 10);
				layout.getChildren().add(r);
			}
		}*/
			
		layout.setPadding(new Insets(10,10,10,10));
		TextField chooseName = new TextField();
		
		Button ok = new Button("Okay");
		ok.setOnAction(e -> {
			System.out.println(chooseName.getText() + " added.");
			window.close();
		});
		
		layout.getChildren().addAll(instr, chooseName, ok);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
