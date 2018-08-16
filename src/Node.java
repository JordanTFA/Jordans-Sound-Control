public class Node {
	
	private String name;
	private javafx.scene.paint.Color colour;
	private Double volume;
	private Double x, y;

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

	public double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public void setX(Double x) {
		this.x = x;
	}
}

