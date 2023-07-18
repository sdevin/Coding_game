package Objects;

import java.io.IOException;
import java.util.Random;

import main.Main;


public class Personnage extends MovingObject{
	
	private String name;
	private String imageBaseName;

	private final String baseNameImg = "/PersoHaut/PersoHaut"; 
	private static String defaultImgName = "/PersoHaut/PersoHaut11.png"; 
	private final int nbImgPersos = 7;

	public Personnage(String name, int x, int y, int width, int angle, boolean isObstacle) throws IOException {
		//obligatoire en premier, image par défaut
		super(defaultImgName, x, y, width, angle, isObstacle);
		this.name = name;
		
		//tirage d'une image pour le personnage
		Random r = new Random();
		int numPerso = r.nextInt(nbImgPersos)+1;
		this.imageBaseName = baseNameImg + numPerso;
		String imageName = imageBaseName + "1.png";
		
		//remplacement de l'image
		Main.view.changeImg(this, imageName);
		
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
