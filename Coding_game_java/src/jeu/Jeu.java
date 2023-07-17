package jeu;

import java.io.IOException;
import java.util.ArrayList;

import Objects.Personnage;
import main.Main;

public class Jeu {

	protected boolean conflit;
	protected String textConflit;
	protected String textDefaite;
	protected int caseMaxX;
	protected int caseMaxY;

	protected ArrayList<Personnage> persos;
	
	public Jeu(int caseMaxX, int caseMaxY) {	
		this.caseMaxX = caseMaxX;
		this.caseMaxY = caseMaxY;
		persos = new ArrayList<Personnage>();
		this.conflit = false;

	}
	
	public void victoire() throws IOException {
		Main.view.displayVictoire();
		System.out.println("C'est gagné !");
		
	}
	public void defaite(String texteDefaite) {
		Main.view.displayDefaite(texteDefaite);
		System.out.println(texteDefaite);
		
		
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

	
	public void movePerso(String persoName, String direction) {
		//verifie que le personnage existe
		Personnage perso;
		if((perso = this.getPersoByName(persoName)) == null) {
			System.err.println("Personnage invalide : " + persoName);
		}
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
		if(!conflit) {
			Main.view.movePerso(persoName, direction);
		}
	}
	
	
	
	public ArrayList<Personnage> getPersos() {
		return persos;
	}

	public Personnage getPersoByName(String name) {
		for(Personnage p : persos) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public void addPerso(Personnage perso) {
		this.persos.add(perso);
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
}
