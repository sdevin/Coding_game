package jeu;

import java.io.IOException;
import java.util.Random;

import Objects.Personnage;
import Objects.StaticObject;
import main.Main;

public class Jeu2 extends Jeu{
	private StaticObject goal;
	
	public Jeu2(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		textDefaite = "Arrivée non atteinte pour tous";

		//ajout de l'arrivée
		goal = new StaticObject("/arrivee.png", Main.convertCasetoPosX(8), Main.convertCasetoPosY(7), Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(goal);
		
		//si position du personnage aléatoire, tirage de cette position
		int posPersoX, posPersoY,posPerso2X, posPerso2Y;
		if(Main.conf.isRandPos()) { 
			Random r = new Random();
			posPersoX = r.nextInt(caseMaxX)+1;
			posPersoY = r.nextInt(caseMaxY)+1;
			//envoie de la position dans le fichier de dialogue
			String line = Main.conf.getNomPerso() + " " + posPersoX + " " + posPersoY;
			Main.ficEcr.ecrireFichier(line);
			posPerso2X = r.nextInt(caseMaxX)+1;
			posPerso2Y = r.nextInt(caseMaxY)+1;
			//envoie de la position dans le fichier de dialogue
			line = Main.conf.getNomPerso2() + " " + posPerso2X + " " + posPerso2Y;
			Main.ficEcr.ecrireFichier(line);
		}else {
			posPersoX = Main.conf.getPosPersoX();
			posPersoY = Main.conf.getPosPersoY();
			posPerso2X = Main.conf.getPosPerso2X();
			posPerso2Y = Main.conf.getPosPerso2Y();
		}
		
		//ajout du perso dans les objets
		Personnage perso = new Personnage(Main.conf.getNomPerso(), "/PersoHaut/PersoHaut3_1.png", "/PersoHaut/PersoHaut3_", Main.convertCasetoPosX(posPersoX), Main.convertCasetoPosY(posPersoY), Main.tailleQuadrillage, 90, true);
		Main.view.addToListObjects(perso);
		persos.add(perso);
		Personnage perso2 = new Personnage(Main.conf.getNomPerso2(), "/PersoHaut/PersoHaut2_1.png", "/PersoHaut/PersoHaut2_", Main.convertCasetoPosX(posPerso2X), Main.convertCasetoPosY(posPerso2Y), Main.tailleQuadrillage, 90, true);
		Main.view.addToListObjects(perso2);
		persos.add(perso2);
	}

	public void checkConflicts(int newX, int newY) {
		int caseX = Main.convertPostoCaseX(newX);
		int caseY = Main.convertPostoCaseY(newY);
		//en dehors de la scene
		if(caseX < 1 || caseY < 1 || caseX > caseMaxX || caseY > caseMaxY) {
			conflit = true;
			textConflit = "Deplacement en dehors de la scène";
			System.out.println("Case demandée : " + caseX + " " + caseY);
		}
	}
	
	public boolean gagner() {
		boolean res = true;
		for(Personnage p : persos) {
			if((p.getX() != goal.getxInit()) || (p.getY() != goal.getyInit())){
				System.out.println(p.getName() + " n'est pas arrivé !");
				res =  false;
			}
		}
		return res;
	}

	public StaticObject getGoal() {
		return goal;
	}

	public void setGoal(StaticObject goal) {
		this.goal = goal;
	}
}
