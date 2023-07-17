package Objects;

import java.io.IOException;

import javafx.scene.image.ImageView;
import main.Main;

public class Object {

	protected ImageView view;
	
	protected String imageName; //fichier de l'image de l'objet
	//position et taille de l'objet
	protected int xInit; 
	protected int yInit;
	protected int width;
	protected int angle;
	
	protected boolean isObstacle;
	
	public Object(String imageName, int x, int y, int width, int angle, boolean isObstacle) throws IOException {
		this.imageName = imageName;
		this.xInit = x;
		this.yInit = y;
		this.width = width;
		this.angle = angle;
		this.isObstacle = isObstacle;
		
		//affichage de l'objet
		Main.view.displayObject(this);
	}
	

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}


	public ImageView getView() {
		return view;
	}

	public void setView(ImageView view) {
		this.view = view;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public double getxInit() {
		return xInit;
	}

	public void setxInit(int xInit) {
		this.xInit = xInit;
	}

	public int getyInit() {
		return yInit;
	}

	public void setyInit(int yInit) {
		this.yInit = yInit;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isObstacle() {
		return isObstacle;
	}

	public void setObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}
	
	

}
