package Objects;

import java.io.IOException;

public class MovingObject extends Object{
	
	//current position of the object
	private int x;
	private int y;
	
	public MovingObject(String imageName, int x, int y, int width, int angle, boolean isObstacle) throws IOException {
		super(imageName, x, y, width, angle, isObstacle);
		this.x = this.xInit;
		this.y = this.yInit;
	}

	//deplace l'object à une nouvelle position (et met à jour l'affichage)
	public void moveObject(int newX, int newY) {
		this.x = newX;
		this.y = newY;
		view.relocate(this.x, this.y);
	}
	
	//tourne l'objet et met à jour l'affichage ('angle' = nouvelle orientation)
	public void turnObject(int angle) {
		this.angle = angle;
		view.setRotate(angle);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}