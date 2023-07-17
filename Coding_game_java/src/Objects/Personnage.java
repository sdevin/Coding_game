package Objects;

import java.io.IOException;


public class Personnage extends MovingObject{
	
	private String name;
	private String imageBaseName;

	public Personnage(String name, String imageName, String imageBaseName, int x, int y, int width, int angle, boolean isObstacle) throws IOException {
		super(imageName, x, y, width, angle, isObstacle);
		this.imageBaseName = imageBaseName;
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageBaseName() {
		return imageBaseName;
	}

	public void setImageBaseName(String imageBaseName) {
		this.imageBaseName = imageBaseName;
	}
	
	

}
