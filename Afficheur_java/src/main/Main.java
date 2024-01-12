package main;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	private Scene scene;
	public static View view;
	
	public static final int tailleX = 500, tailleY = 150;
	
	public void start(Stage stage) throws Exception { 
		stage.setTitle("Coding Game afficheur"); 

		view = new View();
		view.launchView();
		
		Color bgColor = Color.BLACK;
		scene = new Scene(view, tailleX, tailleY, bgColor); 
		stage.setScene(scene); 
		
		stage.show();
		
	}
	
	public static void main(String[] args) {
		
		//lancement du thread d'ecoute du code C
		ThreadEcoute tEc = new ThreadEcoute();
		tEc.start();
		
		launch(args); 	

	}
	
}
