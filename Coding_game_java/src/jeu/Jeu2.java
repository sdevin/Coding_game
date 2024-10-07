package jeu;

import java.io.IOException;
import java.util.Random;

import Config.FichConf2;
import Objects.Personnage;
import Objects.StaticObject;
import main.Main;

public class Jeu2 extends Jeu{
	private StaticObject goal;
	
	public Jeu2(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		conf = new FichConf2(); 
		textDefaite = "Arrivée non atteinte \n pour tous";

		//ajout de l'arrivée (comptoir 3, case 10-7)
		goal = new StaticObject("/vide.png", Main.convertCasetoPosX(10), Main.convertCasetoPosY(7), Main.tailleQuadrillage, 0, false);
		listStaticObjects.add(goal);

		setMovingContext();
	}
	
	public void setMovingContext() throws IOException {
		super.setMovingContext();

		//si position du personnage aléatoire, tirage de cette position
		int posPersoX, posPersoY,posPerso2X, posPerso2Y;
		if(conf.isRandPos()) { 
			Random r = new Random();
			posPersoX = r.nextInt(caseMaxX)+1;
			posPersoY = r.nextInt(caseMaxY)+1;
			//envoie de la position dans le fichier de dialogue
			String line = conf.getNomPerso() + " " + posPersoX + " " + posPersoY;
			ficEcr.ecrireFichier(line);
			posPerso2X = r.nextInt(caseMaxX)+1;
			posPerso2Y = r.nextInt(caseMaxY)+1;
			//envoie de la position dans le fichier de dialogue
			line = conf.getNomPerso2() + " " + posPerso2X + " " + posPerso2Y;
			ficEcr.ecrireFichier(line);
		}else { //sinon positions récupérées dans le fichier de conf
			posPersoX = conf.getPosPersoX();
			posPersoY = conf.getPosPersoY();
			posPerso2X = conf.getPosPerso2X();
			posPerso2Y = conf.getPosPerso2Y();
		}
		
		//ajout des personnages
		Personnage perso = new Personnage(conf.getNomPerso(), Main.convertCasetoPosX(posPersoX), Main.convertCasetoPosY(posPersoY), Main.tailleQuadrillage, 90, true);
		persos.add(perso);
		Personnage perso2 = new Personnage(conf.getNomPerso2(), Main.convertCasetoPosX(posPerso2X), Main.convertCasetoPosY(posPerso2Y), Main.tailleQuadrillage, 90, true);
		persos.add(perso2);
	}

	//verifie si un personnage peut se déplacer sur la case demandée
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

	//return true si l'objectif est atteint (tous les personnages à l'arrivée)
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
