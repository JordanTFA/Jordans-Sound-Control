import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Node extends StackPane{
	
	public String name;
	public javafx.scene.paint.Color colour;
	public Double volume;
	public Double x, y;
	public String track;
	

	
	public Node(String name, javafx.scene.paint.Color colour, Double volume, Double x, Double y, String track) {
		this.name = name;
		this.colour = colour;
		this.volume = volume;
		this.x = x;
		this.y = y;
		this.track = track;
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
	
	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}
}

