package main;

import jeu.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scene.View;

public class Main extends Application {
	

	private Scene scene;
	public static Jeu jeu;
	public static View view;
	public static boolean run = true;
	
	//format de la fenêtre et du quadrillage
	public static final int tailleQuadrillage = 50;
	public static final int sceneX = 13*tailleQuadrillage;
	public static final int sceneY = 10*tailleQuadrillage;
	public static final int xminQuad = tailleQuadrillage;
	public static final int yminQuad = 0;
	public static final int xmaxQuad = 11*tailleQuadrillage;
	public static final int ymaxQuad = 10*tailleQuadrillage;
	
	
	/* version du scénario 
	* 1 : un perso à amener à l'arrivée
	* 2 : deux persos (multi-thread)
	* 3 : passage de la securité (mutex)
	* 4 : bar (client-serveur)
	* 5 : piste de décollage (producteur-cosommateur)
	*/
	public static int Sce = 5;

	//couleur du background
	private int bgR = 185;
	private int bgG = 216;
	private int bgB = 219;

	
	public void start(Stage stage) throws Exception { 
		stage.setTitle("Coding Game V1"); 

		view = new View();
		switch(Sce) {
		case 1 :
			jeu = new Jeu1(view.getQuadrillage().getCaseMaxX(), view.getQuadrillage().getCaseMaxY());
			break;
		case 2 :
			jeu = new Jeu2(view.getQuadrillage().getCaseMaxX(), view.getQuadrillage().getCaseMaxY());
			break;
		case 3 :
			jeu = new Jeu3(view.getQuadrillage().getCaseMaxX(), view.getQuadrillage().getCaseMaxY());
			break;
		case 4 :
			jeu = new Jeu4(view.getQuadrillage().getCaseMaxX(), view.getQuadrillage().getCaseMaxY());
			break;
		case 5 :
			jeu = new Jeu5(view.getQuadrillage().getCaseMaxX(), view.getQuadrillage().getCaseMaxY());
			break;
		default :
			jeu = new Jeu(view.getQuadrillage().getCaseMaxX(), view.getQuadrillage().getCaseMaxY());
		}
		
		view.launchView();
		

		//parametrage couleur fond
		//bg different sce 5
		if(Sce == 5) {
			bgR = 230;
			bgG = 255;
			bgB = 230;
		}
		Color bgColor = Color.rgb(bgR, bgG, bgB);
		
		scene = new Scene(view, sceneX, sceneY, bgColor); 
		stage.setScene(scene); 
		
		stage.show();
		
	}

	public static void main(String[] args) throws IOException {


		//lancement du thread d'ecoute du code C
		ThreadEcoute tEc = new ThreadEcoute();
		tEc.start();
		
		launch(args); 	
		run = false;
	}
	

	public static int convertCasetoPosX(int numCase) {
		return (numCase-1)*Main.tailleQuadrillage + xminQuad;
	}
	
	public static int convertCasetoPosY(int numCase) {
		return (numCase-1)*Main.tailleQuadrillage + yminQuad;
	}
	
	public static int convertPostoCaseX(int pos) {
		return (int)(((pos-xminQuad)/Main.tailleQuadrillage) + 1);
	}

	public static int convertPostoCaseY(int pos) {
		return (int)(((pos-yminQuad)/Main.tailleQuadrillage) + 1);
	}

}
