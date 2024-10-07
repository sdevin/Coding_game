package scene; 

import main.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.scene.control.Button;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objects.Light;
import Objects.Object;
import Objects.Personnage;
import Objects.Plane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.Collection;


public class View extends Pane{
	
	private GraphicsContext gc;
	private Canvas canvas;
	
	private Quadrillage quad;
	
	private final Timeline tm;
	Collection<KeyFrame> frames;
	
	Text textPrincipal; //gagné ou perdu
	Text textSecondaire; //raison defaite
	
	private final double timeMove = 1000; //temps de déplacement d'un personnage ou avion (d'un point à un autre)
	private final int nbImgMove = 4; //nombre d'image constituant un personnage en déplacement
	private final int nbMovePlane = 10; //nombre de frame lors du déplacement d'un avion
	
	

	public View() {
		super();
		this.tm = new Timeline();
		frames = tm.getKeyFrames();
		canvas=new Canvas(Main.sceneX,Main.sceneY);
		gc = canvas.getGraphicsContext2D();
		quad = new Quadrillage(Main.tailleQuadrillage, Main.xminQuad, Main.yminQuad, Main.xmaxQuad, Main.ymaxQuad);
		//background transparent
		this.setBackground(new javafx.scene.layout.Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		
		
	}

	@SuppressWarnings("unchecked")
	public void launchView() throws IOException {
		
		
		//ajout du fond
		for(Object o : Main.jeu.getBackground().getListObjects()) {
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
		for(Object o : Main.jeu.getStaticObjects()) {
			this.getChildren().add(o.getView());
		}
		
		displayAllMoving();
		
		//ajout bouton reset
		Button buttonReset = new Button("Relancer partie");
		this.getChildren().add(buttonReset);
		buttonReset.setOnAction (handlerButton);

	}
	
	private void displayAllMoving() {
		for(Object o : Main.jeu.getMovingObjects()) {
			this.getChildren().add(o.getView());
		}
		for(Object o : Main.jeu.getPersos()) {
			this.getChildren().add(o.getView());
		}
		
		if(Main.Sce == 3) {
			this.getChildren().add(Main.jeu.getPortiqueLight().getCircle());
		}
	}
	
	private void removeAllMoving() {
		for(Object o : Main.jeu.getMovingObjects()) {
			this.getChildren().remove(o.getView());
		}
		for(Object o : Main.jeu.getPersos()) {
			this.getChildren().remove(o.getView());
		}
		
		if(Main.Sce == 3) {
			this.getChildren().remove(Main.jeu.getPortiqueLight().getCircle());
		}else if(Main.Sce == 5) {
			for(Object o : Main.jeu.getPlanes()) {
				this.getChildren().remove(o.getView());
			}
		}
		
		
	}
	
	private void removeText() {
		this.getChildren().remove(textPrincipal);
		this.getChildren().remove(textSecondaire);
		
	}
	
	
	@SuppressWarnings("rawtypes")
	EventHandler handlerButton = new EventHandler<ActionEvent>() {
	public void handle(ActionEvent event) {
		// on a cliqué sur le bouton
		 System.out.println("Reset demandé !");
		removeAllMoving();
		removeText();
		 //reset de tout le context du jeu
		 try {
			Main.jeu.setMovingContext();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //mis a jour vue
		displayAllMoving();
		 //relance thread ecoute
		 Main.jeu.setGameOn(true);
		 // on signifie à JavaFX que rien d’autre n’a besoin de cet événement
		 event.consume();
		 }
		};

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
	
	//déplace un avion à un point x, y
	public void movePlaneTo(Plane plane, int x, int y) {
		Duration currentTime = tm.getCurrentTime();
		double time = currentTime.toMillis();
		int xInit = plane.getX();
		int yInit = plane.getY();
		
		//determination de l'angle de direction
		double deltaX = x-xInit;
		double deltaY = y-yInit;
		double angleRad = Math.atan2(deltaX,-deltaY);
		double angleDeg = Math.toDegrees(angleRad);
		plane.turnObject((int)angleDeg);
		
		for(int i = 1; i <= nbMovePlane; i++) {
			int newX= xInit + ((x - xInit)*i)/(nbMovePlane);
			int newY = yInit + ((y - yInit)*i)/(nbMovePlane);

			KeyFrame kfDep = new KeyFrame(Duration.millis(time), actionEvent-> {
				plane.moveObject(newX, newY);
			});
			frames.add(kfDep);
			time = time + timeMove/nbMovePlane;
		}
		tm.playFrom(currentTime);
		
	}
	
	//déplace un avion en pasant par plusieurs points
	public void movePlaneFromPoints(Plane plane, int nbPoints, int[] pointX, int[] pointY) {
		Duration currentTime = tm.getCurrentTime();
		double time = currentTime.toMillis();
		int xInit = plane.getX();
		int yInit = plane.getY();
		
		for(int i = 0; i < nbPoints; i++) {
			//determination de l'angle de direction
			double deltaX = pointX[i]-xInit;
			double deltaY = pointY[i]-yInit;
			double angleRad = Math.atan2(deltaX,-deltaY);
			double angleDeg = Math.toDegrees(angleRad);
			
			KeyFrame kfTurn = new KeyFrame(Duration.millis(time), actionEvent-> {
				plane.turnObject((int)angleDeg);
			});
			frames.add(kfTurn);
			
			for(int j = 1; j <= nbMovePlane; j++) {
				int newX= xInit + ((pointX[i] - xInit)*j)/(nbMovePlane);
				int newY = yInit + ((pointY[i] - yInit)*j)/(nbMovePlane);
	
				KeyFrame kfDep = new KeyFrame(Duration.millis(time), actionEvent-> {
					plane.moveObject(newX, newY);
				});
				frames.add(kfDep);
				time = time + timeMove/nbMovePlane;
			}
			xInit = pointX[i];
			yInit = pointY[i];
		}
		tm.playFrom(currentTime);
		
	}
	
	public void removeObjectView(Object object) {
		this.getChildren().remove(object.getView());
	}

	public void addObjectView(Object object) {
		this.getChildren().add(object.getView());
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
		
		textPrincipal = new Text("C'est gagné !");
		textPrincipal.setFont(new Font("Arial", Main.tailleQuadrillage));
		textPrincipal.setFill(Color.BLACK);
		textPrincipal.setX(Main.sceneX/5);
		textPrincipal.setY(Main.sceneY/2);
		textPrincipal.setTextAlignment(TextAlignment.CENTER);
		this.getChildren().add(textPrincipal);
	}
	
	//affichage du texte en cas de defaite
	public void displayDefaite(String texteDefaite) {

		//affichage perdu
		textPrincipal = new Text("Perdu...");
		textPrincipal.setFont(new Font("Arial", Main.tailleQuadrillage));
		textPrincipal.setTextAlignment(TextAlignment.CENTER);
		textPrincipal.setFill(Color.BLACK);
		textPrincipal.setX(Main.sceneX/3);
		textPrincipal.setY(Main.sceneY/2-Main.tailleQuadrillage);
		this.getChildren().add(textPrincipal);
		System.out.println("Perdu...");
		//affichage raison defaite
		textSecondaire = new Text(texteDefaite);
		textSecondaire.setFont(new Font("Arial", Main.tailleQuadrillage/2));
		textSecondaire.setTextAlignment(TextAlignment.CENTER);
		textSecondaire.setFill(Color.BLACK);
		textSecondaire.setX(Main.sceneX/4);
		textSecondaire.setY(Main.sceneY/2 + Main.tailleQuadrillage);
		this.getChildren().add(textSecondaire);
	}

	public Quadrillage getQuadrillage() {
		return quad;
	}

	public double getTimeMove() {
		return timeMove;
	}

}
