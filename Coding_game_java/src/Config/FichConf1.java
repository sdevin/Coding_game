package Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.Main;

public class FichConf1 extends FichConf{

/*
 * Fichier de conf sous la forme :
 * Scenario 1 : 
 * nomPerso (String) : nom du personnage à déplacer
 * randPos (boolean) : position initiale aléatoire ou non
 * Si randPos = false :
 * posePersoX (int) : position X initiale du personnage
 * posePersoY (int) : position Y initiale du personnage
 */
	
	//parametre récupérés du fichier de conf
	private String nomPerso;
	private int posPersoX;
	private int posPersoY;
	private boolean randPos;
	
	//paramètres par défaut
	private static String nomPersoDefault = "toto";
	private static int posPersoXDefault = 3;
	private static int posPersoYDefault = 4;
	private static boolean randPosDefault = false;
	
	
	public FichConf1() throws IOException{
		
		super();
		nomFicConf = "confS1.txt";
		
		boolean conti = true;
		
		//test si le fichier de conf existe
		File f = new File(nomFicConf);
		if(f.exists())
		{ 
			//si oui, le lit
		      FileReader fr = new FileReader(f);  
		      // Créer l'objet BufferedReader        
		      BufferedReader br = new BufferedReader(fr);  
		      String line;
		      //lecture nom du perso
		      line = br.readLine();
		      if(line != null) {
		    	  nomPerso = line;
		      }else {
		    	  System.err.println("Fichier de configuration : pas de nom de personnage !");
		    	  conti = false;
		    	  nomPerso = nomPersoDefault;
		    	  randPos = randPosDefault;
		    	  posPersoX = posPersoXDefault;
		    	  posPersoY = posPersoYDefault;
		      }
		      //position initiale aléatoire ou non ?
		      line = br.readLine();
		      if(line != null && conti) {
		    	  randPos = Boolean.parseBoolean(line);
		      }else {
		    	  conti = false;
		    	  System.err.println("Fichier de configuration : position initiale non définie (randPos)!");
		    	  randPos = randPosDefault;
		    	  posPersoX = posPersoXDefault;
		    	  posPersoY = posPersoYDefault;
		      }
		      //si position de départ fixe, lecture x et y
		      if(!randPos && conti) {
		    	  //lecture de la position x
			      line = br.readLine();
			      if(line != null) {
			    	  int x;
			    	  try {
			    		  x = Integer.parseInt(line);
				    	  if(x <= Main.view.getQuadrillage().getCaseMaxX() && x > 0) {
					    	  posPersoX = posPersoXDefault;
				    	  }else {
					    	  System.err.println("Fichier de configuration : position initiale hors limite (X)!");
					    	  posPersoX = posPersoXDefault;
				    	  }
			    	  }
			    	  catch (NumberFormatException e) {
				    	  System.err.println("Fichier de configuration : position initiale mauvais format (X)!");
				    	  posPersoX = posPersoXDefault;
			    	  }
			      }else {
			    	  System.err.println("Fichier de configuration : position initiale non définie (X)!");
			    	  conti = false;
			    	  posPersoX = posPersoXDefault;
			    	  posPersoY = posPersoYDefault;
			      }
		    	  //lecture de la position y
			      line = br.readLine();
			      if(line != null && conti) {
			    	  int y;
			    	  try {
			    		  y = Integer.parseInt(line);
				    	  if(y <= Main.view.getQuadrillage().getCaseMaxY() && y > 0) {
					    	  posPersoY = posPersoYDefault;
				    	  }else {
					    	  System.err.println("Fichier de configuration : position initiale hors limite (Y)!");
					    	  posPersoY = posPersoYDefault;
				    	  }
			    	  }
			    	  catch (NumberFormatException e) {
				    	  System.err.println("Fichier de configuration : position initiale mauvais format (Y)!");
				    	  posPersoY = posPersoYDefault;
			    	  }
			      }else {
			    	  System.err.println("Fichier de configuration : position initiale non définie (Y)!");
			    	  conti = false;
			    	  posPersoY = posPersoYDefault;
			      }
		      }
		      fr.close();    
		}else {
			//si non prend les paramètre par défaut
	    	  nomPerso = nomPersoDefault;
	    	  randPos = randPosDefault;
	    	  posPersoX = posPersoXDefault;
	    	  posPersoY = posPersoYDefault;
		}
	}


	public String getNomPerso() {
		return nomPerso;
	}

	public int getPosPersoX() {
		return posPersoX;
	}

	public int getPosPersoY() {
		return posPersoY;
	}

	public boolean isRandPos() {
		return randPos;
	}


}