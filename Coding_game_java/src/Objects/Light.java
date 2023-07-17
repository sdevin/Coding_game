package Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;

public class Light{

	private Circle circle;
	
	private Color color;
	protected int x; 
	protected int y;
	protected int width;


	public Light(Color color, int x, int y, int width) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.circle = new Circle();
		
		Main.view.displayLight(this);
	}

	public Color getColor() {
		return color;
	}

	public void changeColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}
}
