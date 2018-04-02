import java.awt.Color;

public class Node {
	
	public String name;
	public Color colour;
	public Double volume;

	public Node(String name, Color colour, Double volume) {
		this.name = name;
		this.colour = colour;
		this.volume = volume;
	}
	
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}
}
