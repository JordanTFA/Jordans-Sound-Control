import java.awt.Font;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NodeWindow {

	public static void display(){
			
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Node Selection");
		window.setMinWidth(250);
		window.setMinHeight(355);
		
		final Circle preview = new Circle();
		final Label instr = new Label("Select a colour and a name for the node");
		final ColorPicker colourPicker = new ColorPicker(Color.GREEN);
		final Button ok = new Button("Okay");
		final TextField chooseName = new TextField();
		
		preview.setFill(colourPicker.getValue());
		preview.setRadius(75);
		
		VBox vbox = new VBox(preview, instr, chooseName, colourPicker, ok);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10,10,10,10));
		
		colourPicker.setOnAction( e -> preview.setFill(colourPicker.getValue()) );
		
		chooseName.setOnAction(e-> {
			
			// TODO: Make this an actionlistener
			StackPane stack = new StackPane();
			
			Text text = new Text(chooseName.getText().substring(0, 3));
			
			text.setStyle("-fx-font:56 arial;");
			text.setBoundsType(TextBoundsType.VISUAL);
			stack.getChildren().addAll(preview, text);
			
			vbox.getChildren().add(0, stack);

		});
		
		ok.setOnAction(e -> {
			// Get name & Colour then create node
			
			Node node = new Node(chooseName.getText(), colourPicker.getValue(), 0.0);
			Circle crc = new Circle();
			crc.setFill(node.colour);
			crc.setRadius(5);
			
			vbox.getChildren().add(crc);
			
			System.out.println(chooseName.getText() + " added with colour: " + colourPicker.getValue());
			window.close();
		});
		
		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
	}

}
