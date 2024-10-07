package jeu;

import java.io.IOException;
import java.util.ArrayList;

import Config.FichConf;
import Objects.Light;
import Objects.Object;
import Objects.Personnage;
import Objects.Plane;
import main.EcritureFicher;
import main.Main;
import scene.Background;

public class Jeu {

	protected boolean conflit;  //indique si il y a eu conflit suite à une commande
	protected String textConflit; //texte à indiquer en cas de defaite suite à un conflit
	protected String textDefaite; // texte à indiquer en cas de defaite (but non atteint)
	protected int caseMaxX; //nb de case du quadrillage (axe x)
	protected int caseMaxY; //nb de case du quadrillage (axe y)
	protected Background bg;
	protected EcritureFicher ficEcr;
	protected boolean gameOn;

	protected ArrayList<Personnage> persos; //liste des personnages impliqués dans le scenario
	protected ArrayList<Object> listMovingObjects;//liste des objets dynamiques impliqués dans le scenario
	protected ArrayList<Object> listStaticObjects;//liste des objets statiques impliqués dans le scenario

	protected FichConf conf;
	
	public Jeu(int caseMaxX, int caseMaxY) {
		bg = new Background(); 	
		ficEcr = new EcritureFicher();
		this.caseMaxX = caseMaxX;
		this.caseMaxY = caseMaxY;
		listStaticObjects = new ArrayList<Object>();

	}
	
	//met en place les paramètres du jeu qui vont évoluer dans le temps
	public void setMovingContext() throws IOException {
		persos = new ArrayList<Personnage>();
		listMovingObjects = new ArrayList<Object>();
		this.conflit = false;
		this.gameOn = true;
	}
	
	//déplace un personnage (nommé 'persoName') d'une case dans la direction 'direction'
	public void movePerso(String persoName, String direction) {
		//verifie que le personnage existe
		Personnage perso;
		if((perso = this.getPersoByName(persoName)) == null) {
			System.err.println("Personnage invalide : " + persoName);
		}
		//determination de la future position du personnage
		int movex = perso.getX();
		int movey = perso.getY();
		switch(direction) {
		case "r":
			movex = movex + Main.tailleQuadrillage;
			break;
		case "l":
			movex = movex - Main.tailleQuadrillage;
			break;
		case "u":
			movey = movey - Main.tailleQuadrillage;
			break;
		case "d":
			movey = movey + Main.tailleQuadrillage;
			break;
		default : 
			System.err.println("Direction invalide : " + direction);
		}
		//verifie les possibles conflits
		checkConflicts(movex, movey);
		if(!conflit) { //si ok, deplace effectivement le personnage
			Main.view.movePerso(persoName, direction);
		}
	}
	
	public Personnage getPersoByName(String name) {
		for(Personnage p : persos) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	
	public boolean getGameOn() {
		return gameOn;
	}
	
	public void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
	}
	
	public ArrayList<Personnage> getPersos() {
		return persos;
	}
	
	public ArrayList<Object> getMovingObjects() {
		return listMovingObjects;
	}
	
	public ArrayList<Object> getStaticObjects() {
		return listStaticObjects;
	}

	public void addMovingObject(Object object) {
		this.listMovingObjects.add(object);
	}
	
	public void addStaticObject(Object object) {
		this.listStaticObjects.add(object);
	}
	
	public void addMovingObjectAndDisplay(Object object) {
		this.listMovingObjects.add(object);
		Main.view.displayObject(object);
	}


	public void addPerso(Personnage perso) {
		this.persos.add(perso);
	}
	
	public boolean getConflit() {
		return conflit;
	}
	
	public String getTextConflit() {
		return textConflit;
	}
	
	public String getTextDefaite() {
		return textDefaite;
	}


	public void setCaseMaxX(int caseMaxX) {
		this.caseMaxX = caseMaxX;
	}

	public void setCaseMaxY(int caseMaxY) {
		this.caseMaxY = caseMaxY;
	}
	
	public int getCaseMaxX() {
		return caseMaxX;
	}

	public int getCaseMaxY() {
		return caseMaxY;
	}

	public Background getBackground() {
		return bg;
	}

	public EcritureFicher getFicEcr() {
		return ficEcr;
	}


	
	//super methodes
	public boolean gagner() {
		return false;
	}
	
	public void checkConflicts(int newX, int newY) {
		
	}

	public void movePersoOut(String persoName) {
	}
	
	public void movePersoToCheck(String persoName) {
		
	}
	
	public void enterPerso(String persoName) throws IOException {
		
	}
	
	public void tipPerso(String persoName) throws IOException  {
		
	}
	
	public void noTipPerso(String persoName) throws IOException  {
		
	}
	
	public void barman(String produit) throws IOException, InterruptedException  {
		
	}
	
	public void addPlane(String refPlane) throws IOException {
		
	}
	
	public void takeOff(String refPlane) {
	}
	
	public ArrayList<Plane> getPlanes() {
		return null;
	}
    public Light getPortiqueLight() {
    	return null;
    }
}
