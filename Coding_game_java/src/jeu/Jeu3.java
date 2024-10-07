package jeu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TimerTask;

import Config.FichConf3;

import java.util.ArrayList;
import java.util.Timer;

import Objects.Personnage;
import Objects.StaticObject;
import javafx.scene.paint.Color;
import Objects.Light;
import main.Main;

public class Jeu3 extends Jeu{

	private StaticObject portique; //portique de sécurité
	private Light portiqueLight; //lumière associée au portique
	private ArrayList<String> file1; //liste des personnages dans la file d'attente 1
	private ArrayList<String> file2; //liste des personnages dans la file d'attente 2
	
	private static String nomFicFile1 = "listeFile1.txt"; //nom du fichier contenant les personnage de la file 1
	private static String nomFicFile2 = "listeFile2.txt"; //nom du fichier contenant les personnage de la file 2
	
	private boolean checkInProgress = false; //true quand un contrôle est en cours dans le portique
	private static long timeCheck = 2000; // temps de contrôle au portique (en milliseconde)
	
	//position des persos dans les files d'attente (hors ecran)
	private static int posAtt1X = -Main.tailleQuadrillage; 
	private static int posAtt1Y = 3*Main.tailleQuadrillage;
	private static int posAtt2X = -Main.tailleQuadrillage;
	private static int posAtt2Y = 6*Main.tailleQuadrillage;
	//position des persos premier de file d'attente
	private static int posFile1X = Main.tailleQuadrillage; 
	private static int posFile1Y = 3*Main.tailleQuadrillage;
	private static int posFile2X = Main.tailleQuadrillage;
	private static int posFile2Y = 6*Main.tailleQuadrillage;
	//position du portique
	private static int posPortiqueX = 6*Main.tailleQuadrillage;
	private static int posPortiqueY = 4*Main.tailleQuadrillage;
	//position de la sortie (après portique, hors ecran)
	private static int posOutX = Main.sceneX + Main.tailleQuadrillage;
	private static int posOutY = 5*Main.tailleQuadrillage;
	
	public Jeu3(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		conf = new FichConf3(); 
		textDefaite = "Tous les personnages \n ne sont pas passés";
		
		//ajout du portique
		portique = new StaticObject("/portique.png", posPortiqueX, posPortiqueY-Main.tailleQuadrillage/2, Main.tailleQuadrillage, 0, false);
		listStaticObjects.add(portique);

		setMovingContext();
	}
	
	public void setMovingContext() throws IOException {
		super.setMovingContext();
		//ajout des personnages
		if(!conf.getBonus()) { 
			//pas de bonus, uniquement un personnage dans chaque file
			Personnage perso1 = new Personnage(conf.getNomPerso1(), posFile1X, posFile1Y, Main.tailleQuadrillage, 90, true);
			persos.add(perso1);
			Personnage perso2 = new Personnage(conf.getNomPerso2(), posFile2X, posFile2Y, Main.tailleQuadrillage, 90, true);
			persos.add(perso2);
		}else {
			//bonus, la liste des personnage est récupérer des fichiers
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
			    	  Personnage perso1 = new Personnage(line, posFile1X, posFile1Y, Main.tailleQuadrillage, 90, true);
					  persos.add(perso1);
					  file1.add(line);
			      }else {
			    	  System.err.println("Fichier " + nomFicFile1 + " vide !");
			      }
			      //lecture des persos depuis fichier et mise dans les files
			      line = br.readLine();
			      while(line != null) {
			    	  Personnage perso = new Personnage(line, posAtt1X, posAtt1Y, Main.tailleQuadrillage, 90, true);
					  persos.add(perso);
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
			    	  Personnage perso2 = new Personnage(line, posFile2X, posFile2Y, Main.tailleQuadrillage, 90, true);
					  persos.add(perso2);
					  file2.add(line);
			      }else {
			    	  System.err.println("Fichier " + nomFicFile2 + " vide !");
			      }
			      //lecture des persos depuis fichier et mise dans les files
			      line = br.readLine();
			      while(line != null) {
			    	  Personnage perso = new Personnage(line, posAtt2X, posAtt2Y, Main.tailleQuadrillage, 90, true);
					  persos.add(perso);
					  file2.add(line);
				      line = br.readLine();
			      }
			      fr.close();
			}else {
				System.err.println("Fichier " + nomFicFile2 + " introuvable !");
			}   	  
			
		}
		
		//ajout de la lumière du portique
		portiqueLight = new Light(Color.GREEN, (posPortiqueX + Main.tailleQuadrillage/2), (posPortiqueY + Main.tailleQuadrillage/2), Main.tailleQuadrillage/2);
		
				
	}
	
	//déplace un personnage au portique et commence le controle
	public void movePersoToCheck(String persoName) {
		
		//déplacer le perso au portique
		Main.view.movePersoTo(persoName, posPortiqueX, posPortiqueY);
		
		
		//attente de l'arrivée au portique (basé sur le temps de déplacement)
		TimerTask taskAfterMove = new TimerTask() {
	        public void run() {
	        	//si bonus : avance du prochain dans la liste
	    		if(conf.getBonus()) {
	    			//recuperation du numéro de file du personnage
	    			if(file1.size() > 0 && persoName.equals(file1.get(0))){//perso dans la premiere file
	    				file1.remove(0);
	    				//apparition du suivant
	    				if(file1.size() > 0) {
	    					Main.view.movePersoTo(file1.get(0), posFile1X, posFile1Y);
	    				}
	    			}else if(file2.size() > 0 &&  persoName.equals(file2.get(0))){//perso dans la premiere file
	    				file2.remove(0);
	    				//apparition du suivant
	    				if(file2.size() > 0) {
	    					Main.view.movePersoTo(file2.get(0), posFile2X, posFile2Y);
	    				}
	    			}else {
	    				System.err.println("Personnage non trouvé dans les files !");
	    			}
	    			
	    		}
	        	checkSecu(persoName);
	        }
	    };
	    
	   Timer timer = new Timer("Timer move");
	   timer.schedule(taskAfterMove, (long)Main.view.getTimeMove());
		
	}
	
	//un personnage par du portique
	public void movePersoOut(String persoName) {
		//déplacer le perso a la sortie
		Main.view.movePersoTo(persoName, posOutX, posOutY);

		//check conflit : pas dans portique ou check non terminé
		checkConflictOutPortique(persoName);
	}
	
	//simule le contrôle dans le portique
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
							ficEcr.ecrireFichier(persoName, line);
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
	
	//verifie les conflit quand un personnage entre dans le portique
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

	//verifie les conflit quand un personnage sort du portique
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
    
    //renvoie true si tous les personnages en jeu sont partis (et donc sont passé au contrôle)
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
    

    public Light getPortiqueLight() {
    	return portiqueLight;
    }
}
