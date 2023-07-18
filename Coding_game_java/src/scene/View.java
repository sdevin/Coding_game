package scene; 

import main.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objects.Light;
import Objects.Object;
import Objects.Personnage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Collection;


public class View extends Pane{
	
	private GraphicsContext gc;
	private Canvas canvas;
	
	private ArrayList<Object> listObjects;
	private ArrayList<Light> listLights;
	private Quadrillage quad;
	
	private final Timeline tm;
	Collection<KeyFrame> frames;
	
	private final double timeMove = 1000; //temps de déplacement d'un personnage (d'un point à un autre)
	private final int nbImgMove = 4; //nombre d'image constituant un personnage en déplacement
	
	

	public View() {
		super();
		this.tm = new Timeline();
		frames = tm.getKeyFrames();
		listObjects = new ArrayList<Object>();
		listLights = new ArrayList<Light>();
		canvas=new Canvas(Main.sceneX,Main.sceneY);
		gc = canvas.getGraphicsContext2D();
		quad = new Quadrillage(Main.tailleQuadrillage, Main.xminQuad, Main.yminQuad, Main.xmaxQuad, Main.ymaxQuad);
		
	}

	public void launchView() throws IOException {
		
		
		//ajout du fond
		for(Object o : Main.bg.getListObjects()) {
			this.getChildren().add(o.getView());
		}
		
		//affichage du quadrillage (uniquement scenario 1 et 2)
		if(Main.Sce == 1 || Main.Sce == 2) {
			for(Line l : quad.getLignes()) {
				gc.setStroke(Color.DARKGREY);
	            gc.strokeLine(l.getStartX(), l.getStartY(), l.getEndX(), l.getEndY());
			}
			this.getChildren().add(canvas);
		}
		Main.jeu.setCaseMaxX(quad.getCaseMaxX());
		Main.jeu.setCaseMaxY(quad.getCaseMaxY());
		
		//ajout des objets à la scène
		for(Object o : listObjects) {
			this.getChildren().add(o.getView());
		}
		//ajout des lumieres à la scène
		for(Light l : listLights) {
			this.getChildren().add(l.getCircle());
		}

	}
	

	//déplace un personnage à un point x, y
	public void movePersoTo(String persoName, int x, int y) {
		frames.clear();
		Duration currentTime = tm.getCurrentTime();
		double time = currentTime.toMillis();
		//recupération du personnage
		Personnage p = Main.jeu.getPersoByName(persoName);
		int xInit = p.getX();
		int yInit = p.getY();
		String imageBaseName = p.getImageBaseName();
		
		//determination de l'angle de direction
		double deltaX = x-xInit;
		double deltaY = y-yInit;
		double angleRad = Math.atan2(deltaX,-deltaY);
		double angleDeg = Math.toDegrees(angleRad);
		p.turnObject((int)angleDeg);
		
		//determination de la distance à parcourir
		double distance = Math.sqrt(Math.pow((x-xInit),2) + Math.pow((y-yInit),2));
		int nbPas = (int)(nbImgMove*distance/Main.tailleQuadrillage);
		for(int i = 1; i <= nbPas; i++) {
			//recupération de l'image
			int nbImg = ((i-1)%nbImgMove)+1;
			String imageName = imageBaseName + nbImg + ".png";
			
			//changement de l'image
			KeyFrame kfImg = new KeyFrame(Duration.millis(time), actionEvent-> {
				try {
					changeImg(p, imageName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			frames.add(kfImg);

			int newX= xInit + ((x - xInit)*i)/(nbPas);
			int newY = yInit + ((y - yInit)*i)/(nbPas);
			
			KeyFrame kfDep = new KeyFrame(Duration.millis(time), actionEvent-> {
				p.moveObject(newX, newY);
			});
			frames.add(kfDep);
			time = time + timeMove/nbPas;
		}
		tm.playFrom(currentTime);
		
	}
	
	//déplace un personnage d'une case dans le quadrillage
	public void movePerso(String perso, String direction) {
		frames.clear();
		Duration currentTime = tm.getCurrentTime();
		double time = currentTime.toMillis();
		//recupération du personnage
		Personnage p = Main.jeu.getPersoByName(perso);
		int x = p.getX();
		int y = p.getY();
		String imageBaseName = p.getImageBaseName();
		
		//rotation image suivant la direction
		switch(direction) {
		case "r":
			p.turnObject(90);
			break;
		case "l":
			p.turnObject(270);
			break;
		case "u":
			p.turnObject(0);
			break;
		case "d":
			p.turnObject(180);
			break;
		}
		for(int i = 1; i <= nbImgMove; i++) {
			//recupération de l'image
			String imageName = imageBaseName + i + ".png";
			
			//changement de l'image
			KeyFrame kfImg = new KeyFrame(Duration.millis(time), actionEvent-> {
				try {
					changeImg(p, imageName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			frames.add(kfImg);

			//déplacement
			int newX, newY;
			switch(direction) {
			case "r":
				newX = x+(Main.tailleQuadrillage*i)/(nbImgMove);
				newY = y;
				break;
			case "l":
				newX = x-(Main.tailleQuadrillage*i)/(nbImgMove);
				newY = y;
				break;
			case "u":
				newX= x;
				newY = y-(Main.tailleQuadrillage*i)/(nbImgMove);
				break;
			case "d":
				newX= x;
				newY = y+(Main.tailleQuadrillage*i)/(nbImgMove);
				break;
			default :
				newX = x;
				newY = y;
			}
			KeyFrame kfDep = new KeyFrame(Duration.millis(time), actionEvent-> {
				p.moveObject(newX, newY);
			});
			frames.add(kfDep);
			time = time + timeMove/nbImgMove;
		}
		tm.playFrom(currentTime);

	}
	
	public void addToListObjects(Object object) {
		listObjects.add(object);
	}
	
	public void addToListLights(Light light) {
		listLights.add(light);
	}

	//configure l'image view d'un object
	public void displayObject(Object object) {

		BufferedImage bufImg;
		try {
			bufImg = ImageIO.read(object.getClass().getResource(object.getImageName()));
			Image image = SwingFXUtils.toFXImage(bufImg, null);
			ImageView view = new ImageView(image);
			object.setView(view);
			view.setFitWidth(object.getWidth());
			view.setPreserveRatio(true);
			view.setRotate(object.getAngle());
			view.relocate(object.getxInit(), object.getyInit());
		} catch (IOException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//change la couleur d'une lumière
	public void changeLightColor(Light light, Color color) {
		light.getCircle().setFill(color);
	}
	
	//change l'image associée à un objet
	public void changeImg(Object object, String newImage) throws IOException {
		object.setImageName(newImage);
		BufferedImage bufImg = ImageIO.read(object.getClass().getResource(newImage));
		Image image = SwingFXUtils.toFXImage(bufImg, null);
		object.getView().setImage(image);
		
	}
	

	//affichage du texte en cas de victoire
	public void displayVictoire() {
		
		Text text = new Text("C'est gagné !");
		text.setFont(new Font("Arial", Main.tailleQuadrillage));
		text.setFill(Color.BLACK);
		text.setX(Main.sceneX/5);
		text.setY(Main.sceneY/2);
		text.setTextAlignment(TextAlignment.CENTER);
		this.getChildren().add(text);
	}
	
	//affichage du texte en cas de defaite
	public void displayDefaite(String texteDefaite) {

		//affichage perdu
		Text text = new Text("Perdu...");
		text.setFont(new Font("Arial", Main.tailleQuadrillage));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.BLACK);
		text.setX(Main.sceneX/3);
		text.setY(Main.sceneY/2-Main.tailleQuadrillage);
		this.getChildren().add(text);
		System.out.println("Perdu...");
		//affichage raison defaite
		Text text2 = new Text(texteDefaite);
		text2.setFont(new Font("Arial", Main.tailleQuadrillage/2));
		text2.setTextAlignment(TextAlignment.CENTER);
		text2.setFill(Color.BLACK);
		text2.setX(Main.sceneX/4);
		text2.setY(Main.sceneY/2 + Main.tailleQuadrillage);
		this.getChildren().add(text2);
	}

	public Quadrillage getQuadrillage() {
		return quad;
	}

	public double getTimeMove() {
		return timeMove;
	}

}
