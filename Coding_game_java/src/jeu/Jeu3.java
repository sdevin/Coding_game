package jeu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Timer;

import Objects.Personnage;
import Objects.StaticObject;
import javafx.scene.paint.Color;
import Objects.Light;
import main.Main;

public class Jeu3 extends Jeu{

	private StaticObject portique;
	private Light portiqueLight;
	private ArrayList<String> file1;
	private ArrayList<String> file2;
	
	private static String nomFicFile1 = "listeFile1.txt";
	private static String nomFicFile2 = "listeFile2.txt";
	
	private boolean checkInProgress = false;
	private static long timeCheck = 2000; // en milliseconde
	
	private static int posFile1X = 100;
	private static int posFile1Y = 300;
	private static int posFile2X = 100;
	private static int posFile2Y = 600;
	private static int posPortiqueX = 600;
	private static int posPortiqueY = 400;
	private static int posOutX = Main.sceneX + 100;
	private static int posOutY = 400;
	
	public Jeu3(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		textDefaite = "Tous les personnages ne sont pas passés";
		
		//ajout des personnages
		if(!Main.conf.getBonus()) { 
			//ajout des perso dans les objets
			Personnage perso1 = new Personnage(Main.conf.getNomPerso1(), posFile1X, posFile1Y, 100, 90, true);
			Main.view.addToListObjects(perso1);
			persos.add(perso1);
			Personnage perso2 = new Personnage(Main.conf.getNomPerso2(), posFile2X, posFile2Y, 100, 90, true);
			Main.view.addToListObjects(perso2);
			persos.add(perso2);
		}else {
			file1 = new ArrayList<String>();
			file2 = new ArrayList<String>();
			//lecture de la premiere file
			File f1 = new File(nomFicFile1);
			if(f1.exists())//test si le fichier de conf existe
			{ 
			      FileReader fr = new FileReader(f1);  
			      BufferedReader br = new BufferedReader(fr);  
			      String line;
			      //affichage du premier perso
			      line = br.readLine();
			      if(line != null) {
			    	  Personnage perso1 = new Personnage(line, posFile1X, posFile1Y, 100, 90, true);
					  Main.view.addToListObjects(perso1);
					  persos.add(perso1);
					  file1.add(line);
			      }else {
			    	  System.err.println("Fichier " + nomFicFile1 + " vide !");
			      }
			      //lecture des persos depuis fichier et mise dans les files
			      line = br.readLine();
			      while(line != null) {
					  file1.add(line);
				      line = br.readLine();
			      }
			      fr.close();
			}else {
				System.err.println("Fichier " + nomFicFile1 + " introuvable !");
			}
			//lecture de la premiere file
			File f2 = new File(nomFicFile2);
			if(f2.exists())//test si le fichier de conf existe
			{ 
			      FileReader fr = new FileReader(f2);  
			      BufferedReader br = new BufferedReader(fr);  
			      String line;
			      //affichage du premier perso
			      line = br.readLine();
			      if(line != null) {
			    	  Personnage perso2 = new Personnage(line, posFile2X, posFile2Y, 100, 90, true);
					  Main.view.addToListObjects(perso2);
					  persos.add(perso2);
					  file2.add(line);
			      }else {
			    	  System.err.println("Fichier " + nomFicFile2 + " vide !");
			      }
			      //lecture des persos depuis fichier et mise dans les files
			      line = br.readLine();
			      while(line != null) {
					  file2.add(line);
				      line = br.readLine();
			      }
			      fr.close();
			}else {
				System.err.println("Fichier " + nomFicFile2 + " introuvable !");
			}   	  
			
		}
		
		//ajout du portique
		portique = new StaticObject("/arrivee.png", posPortiqueX, posPortiqueY, 100, 0, false);
		Main.view.addToListObjects(portique);
		//ajout de la lumière du portique
		portiqueLight = new Light(Color.GREEN, (posPortiqueX + 50), (posPortiqueY - 50), 50);
		
		
	}
	

	public void movePersoToCheck(String persoName) {
		
		//déplacer le perso au portique
		Main.view.movePersoTo(persoName, posPortiqueX, posPortiqueY);
		
		//si bonus : ajout du prochain dans la liste
		if(Main.conf.getBonus()) {
			//recuperation du numéro de file du personnage
			if(file1.size() > 0 && persoName.equals(file1.get(0))){//perso dans la premiere file
				file1.remove(0);
				//apparition du suivant
				if(file1.size() > 0) {
					try {
						Personnage perso = new Personnage(file1.get(0), posFile1X, posFile1Y, 100, 90, true);
						Main.view.addToListObjects(perso);
						Main.view.getChildren().add(perso.getView());
						persos.add(perso);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(file2.size() > 0 &&  persoName.equals(file2.get(0))){//perso dans la premiere file
				file2.remove(0);
				//apparition du suivant
				if(file2.size() > 0) {
					try {
						Personnage perso = new Personnage(file2.get(0), posFile2X, posFile2Y, 100, 90, true);
						Main.view.addToListObjects(perso);
						Main.view.getChildren().add(perso.getView());
						persos.add(perso);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else {
				System.err.println("Personnage non trouvé dans les files !");
			}
			
		}
		
		//attente de l'arrivée au portique (basé sur le temps de déplacement
		TimerTask taskAfterMove = new TimerTask() {
	        public void run() {
	        	checkSecu(persoName);
	        	
	        	if(Main.conf.getBonus()) {
	        		//Affichage du personnage suivant (si existe)
	        	}
	        }
	    };
	    
	   Timer timer = new Timer("Timer move");
	   timer.schedule(taskAfterMove, (long)Main.view.getTimeMove());
		
	}
	

	public void movePersoOut(String persoName) {
		//déplacer le perso a la sortie
		Main.view.movePersoTo(persoName, posOutX, posOutY);

		//check conflit : pas dans portique ou check non terminé
		checkConflictOutPortique(persoName);
	}
	
	public void checkSecu(String persoName) {
		//check conflit : autre personne déjà au portique
		checkConflictGoPortique(persoName);
		if(!conflit) {
			//lumière passe à orange
			checkInProgress = true;
			Main.view.changeLightColor(portiqueLight, Color.ORANGE);
			//attente, check fini : lumière à vert
			TimerTask taskCheckSecu = new TimerTask() {
		        public void run() {
		        	checkInProgress = false;
		        	if(!conflit) {
		        		Main.view.changeLightColor(portiqueLight, Color.GREEN);
			        	//envoie signal de fin de check
			        	try {
				        	String line = persoName + "\n";
							Main.ficEcr.ecrireFichier(line);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}
		        }
		    };
		    
		    Timer timer = new Timer("Timer check");
		    timer.schedule(taskCheckSecu, timeCheck);
		}
	    
	}
	
    public void checkConflictGoPortique(String persoName) {
    	for(Personnage p : persos) {
			if(p.getX() == posPortiqueX && p.getY() == posPortiqueY && !p.getName().equals(persoName)) {
				//quelqu'un déjà dans le portique
				conflit = true;
				textConflit = "Deux personnes dans le portique !";
				Main.view.changeLightColor(portiqueLight, Color.RED);
			}
		}
    }
    
    public void checkConflictOutPortique(String persoName) {
    	Personnage p = getPersoByName(persoName);
    	//le perso doit être dans le portique
    	if(p.getX() != posPortiqueX || p.getY() != posPortiqueY) {
			conflit = true;
			textConflit = persoName + " n'est pas passé au contrôle !";
    	}else if(checkInProgress){//le contrôle doit être terminé
			conflit = true;
			textConflit = "Contrôle non terminé !";
			Main.view.changeLightColor(portiqueLight, Color.RED);
    	}
    }
    
    public boolean gagner() {
		boolean res = true;
		for(Personnage p : persos) {
			if(p.getX() != posOutX || p.getY() != posOutY){
				System.out.println(p.getName() + " n'a pas fini son contrôle !");
				res =  false;
			}
		}
		return res;
	}
    

}
