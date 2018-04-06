

public class Node {
	
	public String name;
	public javafx.scene.paint.Color colour;
	public Double volume;

	public Node(String name, javafx.scene.paint.Color red, Double volume) {
		this.name = name;
		this.colour = red;
		this.volume = volume;
	}
	
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}
}
