package jeu;

import java.io.IOException;
import java.util.Random;

import Config.FichConf1;
import Objects.Personnage;
import Objects.StaticObject;
import main.Main;

public class Jeu1 extends Jeu{
	
	private StaticObject goal; //objet que le personnage doit atteindre
	
	public Jeu1(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		conf = new FichConf1(); 
		textDefaite = "Arrivée non atteinte";

		//ajout de l'arrivée (comptoir 3, case 10-7)
		goal = new StaticObject("/vide.png", Main.convertCasetoPosX(10), Main.convertCasetoPosY(7), Main.tailleQuadrillage, 0, false);
		listStaticObjects.add(goal);

		setMovingContext();
	}

	public void setMovingContext() throws IOException {
		super.setMovingContext();

		//si position du personnage aléatoire, tirage de cette position
		int posPersoX, posPersoY;
		if(conf.isRandPos()) { 
			Random r = new Random();
			posPersoX = r.nextInt(caseMaxX)+1;
			posPersoY = r.nextInt(caseMaxY)+1;
			//envoie de la position dans le fichier de dialogue
			String line = conf.getNomPerso() + " " + posPersoX + " " + posPersoY;
			ficEcr.ecrireFichier(line);
		}else { //sinon on recupère du fichier de conf
			posPersoX = conf.getPosPersoX();
			posPersoY = conf.getPosPersoY();
		}
		
		//ajout du personnage
		Personnage perso = new Personnage(conf.getNomPerso(), Main.convertCasetoPosX(posPersoX), Main.convertCasetoPosY(posPersoY), Main.tailleQuadrillage, 90, true);
		persos.add(perso);
		
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
	
	//return true si l'objectif est atteint (personnage à l'arrivée)
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
