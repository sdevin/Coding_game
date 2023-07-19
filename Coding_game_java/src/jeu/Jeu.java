package jeu;

import java.io.IOException;
import java.util.ArrayList;

import Objects.Personnage;
import main.Main;

public class Jeu {

	protected boolean conflit;  //indique si il y a eu conflit suite à une commande
	protected String textConflit; //texte à indiquer en cas de defaite suite à un conflit
	protected String textDefaite; // texte à indiquer en cas de defaite (but non atteint)
	protected int caseMaxX; //nb de case du quadrillage (axe x)
	protected int caseMaxY; //nb de case du quadrillage (axe y)

	protected ArrayList<Personnage> persos; //liste des personnages impliqués dans le scenario
	
	public Jeu(int caseMaxX, int caseMaxY) {	
		this.caseMaxX = caseMaxX;
		this.caseMaxY = caseMaxY;
		persos = new ArrayList<Personnage>();
		this.conflit = false;

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
	
	public ArrayList<Personnage> getPersos() {
		return persos;
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
}
