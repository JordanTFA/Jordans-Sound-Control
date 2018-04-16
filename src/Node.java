public class Node {
	
	public String name;
	public javafx.scene.paint.Color colour;
	private Double volume;
	public Double x, y;

	public Node(String name, javafx.scene.paint.Color colour, Double volume, Double x, Double y) {
		this.name = name;
		this.colour = colour;
		this.volume = volume;
		this.x = x;
		this.y = y;
	}
	
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}
}

