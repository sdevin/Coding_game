package Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.Main;

public class FichConf2 extends FichConf{

/*
 * Fichier de conf sous la forme :
 * nomPerso (String) : nom du premier personnage
 * nomPerso (String) : nom du second personnage
 * randPos (boolean) : positions initiale aléatoire ou non
 * Si randPos = false :
 * posePerso1X (int) : position X initiale du personnage 1
 * posePerso1Y (int) : position Y initiale du personnage 1
 * posePerso2X (int) : position X initiale du personnage 2
 * posePerso2Y (int) : position Y initiale du personnage 2
 */
	
	private String nomPerso;
	private int posPersoX;
	private int posPersoY;
	private boolean randPos;
	private String nomPerso2;
	private int posPerso2X;
	private int posPerso2Y;
	
	private static String nomPersoDefault = "toto";
	private static int posPersoXDefault = 3;
	private static int posPersoYDefault = 4;
	private static String nomPerso2Default = "tata";
	private static int posPerso2XDefault = 5;
	private static int posPerso2YDefault = 6;
	private static boolean randPosDefault = false;
	
	
	public FichConf2() throws IOException{
		
		super();
		nomFicConf  = "confS2.txt";
		
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
		    	  nomPerso2 = nomPerso2Default;
		    	  posPerso2X = posPerso2XDefault;
		    	  posPerso2Y = posPerso2YDefault;
		      }
	    	  //lecture nom du deuxième perso
		      line = br.readLine();
		      if(line != null && conti) {
		    	  nomPerso2 = line;
		      }else {
		    	  conti = false;
		    	  System.err.println("Fichier de configuration : pas de nom de personnage 2!");
		    	  nomPerso2 = nomPerso2Default;
		    	  randPos = randPosDefault;
		    	  posPersoX = posPersoXDefault;
		    	  posPersoY = posPersoYDefault;
		    	  posPerso2X = posPerso2XDefault;
		    	  posPerso2Y = posPerso2YDefault;
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
		    	  posPerso2X = posPerso2XDefault;
		    	  posPerso2Y = posPerso2YDefault;
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
			    	  posPerso2X = posPerso2XDefault;
			    	  posPerso2Y = posPerso2YDefault;
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
			    	  posPerso2X = posPerso2XDefault;
			    	  posPerso2Y = posPerso2YDefault;
			      }
		    	//lecture de la position x 
			      line = br.readLine();
			      if(line != null && conti) {
			    	  int x;
			    	  try {
			    		  x = Integer.parseInt(line);
				    	  if(x <= Main.view.getQuadrillage().getCaseMaxX() && x > 0) {
					    	  posPerso2X = posPerso2XDefault;
				    	  }else {
					    	  System.err.println("Fichier de configuration : position initiale hors limite (X)!");
					    	  posPerso2X = posPerso2XDefault;
				    	  }
			    	  }
			    	  catch (NumberFormatException e) {
				    	  System.err.println("Fichier de configuration : position initiale mauvais format (X)!");
				    	  posPerso2X = posPerso2XDefault;
			    	  }
			      }else {
			    	  System.err.println("Fichier de configuration : position initiale non définie (X)!");
			    	  conti = false;
			    	  posPerso2X = posPerso2XDefault;
			    	  posPerso2Y = posPerso2YDefault;
			      }
		    	  //lecture de la position y
			      line = br.readLine();
			      if(line != null && conti) {
			    	  int y;
			    	  try {
			    		  y = Integer.parseInt(line);
				    	  if(y <= Main.view.getQuadrillage().getCaseMaxY() && y > 0) {
					    	  posPerso2Y = posPerso2YDefault;
				    	  }else {
					    	  System.err.println("Fichier de configuration : position initiale hors limite (Y)!");
					    	  posPerso2Y = posPerso2YDefault;
				    	  }
			    	  }
			    	  catch (NumberFormatException e) {
				    	  System.err.println("Fichier de configuration : position initiale mauvais format (Y)!");
				    	  posPerso2Y = posPerso2YDefault;
			    	  }
			      }else {
			    	  System.err.println("Fichier de configuration : position initiale non définie (Y)!");
			    	  conti = false;
			    	  posPerso2Y = posPerso2YDefault;
			      }
		      }
		      fr.close();    
		}else {
			//si non prend les paramètre par défaut
	    	  nomPerso = nomPersoDefault;
	    	  nomPerso2 = nomPerso2Default;
	    	  randPos = randPosDefault;
	    	  posPersoX = posPersoXDefault;
	    	  posPersoY = posPersoYDefault;
	    	  posPerso2X = posPerso2XDefault;
	    	  posPerso2Y = posPerso2YDefault;
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

	public String getNomPerso2() {
		return nomPerso2;
	}

	public int getPosPerso2X() {
		return posPerso2X;
	}

	public int getPosPerso2Y() {
		return posPerso2Y;
	}

	public boolean isRandPos() {
		return randPos;
	}


}