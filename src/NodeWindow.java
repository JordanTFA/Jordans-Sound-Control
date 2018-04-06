import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NodeWindow {

	public static void display(){
			
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Node Selection");
		window.setMinWidth(250);
		window.setMinHeight(355);
		
		Label instr = new Label("Select a colour and a name for the node");
		
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);

		layout.setPadding(new Insets(10,10,10,10));
		TextField chooseName = new TextField();
		
		Button ok = new Button("Okay");
		ok.setOnAction(e -> {
			// Get name & Colour then create node
			
			Node node = new Node(chooseName.getText(), Color.RED, 0.0);
			Circle crc = new Circle();
			crc.setFill(node.colour);
			crc.setRadius(5);
			
			layout.getChildren().add(crc);
			
			System.out.println(chooseName.getText() + " added.");
			window.close();
		});
		
		GridPane grid = createColourGrid();
		
		layout.getChildren().addAll(grid, instr, chooseName, ok);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public static GridPane createColourGrid(){
		
		GridPane colourGrid = new GridPane();
		
	    Color[] colors = {
	    		Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
	    		Color.INDIGO, Color.VIOLET, Color.DARKGREEN, Color.HOTPINK, Color.CHOCOLATE,
	    		Color.FUCHSIA, Color.NAVY, Color.OLIVE, Color.SALMON, Color.ROYALBLUE,
	    		Color.TURQUOISE, Color.FIREBRICK, Color.PURPLE, Color.FORESTGREEN, Color.NAVY,
	    		Color.DARKORANGE, Color.LIME, Color.MAROON, Color.GOLD, Color.PINK
	    		};
	    
	    
		int rows = 5;
		int columns = 5;
		int noColours = colors.length;
	    int n = 0;
	    
		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){

		        Rectangle rec = new Rectangle();
		        rec.setWidth(30);
		        rec.setHeight(30);
		        rec.setFill(colors[n]);
		        GridPane.setMargin(rec, new Insets(5,5,5,5));
		        GridPane.setRowIndex(rec, i);
		        GridPane.setColumnIndex(rec, j);
		        colourGrid.getChildren().addAll(rec);
		        
		        if(n<noColours)
					n++;
			}
		}
		
		return colourGrid;
	}
}
