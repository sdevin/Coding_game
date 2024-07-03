package jeu;

import java.io.IOException;

import Config.FichConf4;
import Objects.Object;
import Objects.Personnage;
import Objects.StaticObject;
import main.Main;

public class Jeu4 extends Jeu{

	private int timeService = 500; //simulation du temps de service du barman (en milliseconde)
	//position des persos avant entrée (hors ecran)
	private static int posAttX = -Main.tailleQuadrillage; 
	private static int posAttY = 5*Main.tailleQuadrillage;
	//position des persos au bar
	private static int posBar1X = Main.xmaxQuad - Main.tailleQuadrillage; 
	private static int posBar1Y = 2*Main.tailleQuadrillage;
	private static int posBar2X = Main.xmaxQuad - Main.tailleQuadrillage;
	private static int posBar2Y = 4*Main.tailleQuadrillage;
	//position des objets au bar
	private static int posObjBarX = Main.xmaxQuad; 
	private static int posObjBarY = 3*Main.tailleQuadrillage;
	//position des perso après sortie (hors ecran)
	private static int posOutX = -2*Main.tailleQuadrillage; 
	private static int posOutY = 5*Main.tailleQuadrillage;
	//position de bar libre
	private boolean bar1free = true;
	private boolean bar2free = true;
	//objet sur le bar
	private boolean objectOnBar = false;
	private Object onBar;
	
	public Jeu4(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		conf = new FichConf4(); 
		textDefaite = "Tous les personnages n'ont pas eu leur commande !";
		
	}
	
	//un client entre dans le bar
	public void enterPerso(String persoName) throws IOException {
		//creation du personnage et ajout à la liste
		Personnage perso = new Personnage(persoName, posAttX, posAttY, Main.tailleQuadrillage, 90, true);
		persos.add(perso);
		Main.view.addObjectView(perso);
		
		
		//trouver place libre au bar et deplacement au bar
		if(bar1free) {
			Main.view.movePersoTo(persoName, posBar1X, posBar1Y);
			bar1free = false;
		}else if(bar2free) {
			Main.view.movePersoTo(persoName, posBar2X, posBar2Y);
			bar2free = false;
		}else {
			System.err.println("Pas de place libre au bar !");
		}
		
	}

	//un client est content de sa commande et laisse un pourboir
	public void tipPerso(String persoName) throws IOException {
		//faire disparaitre le produit du bar
		if(objectOnBar) {
			listMovingObjects.remove(onBar);
			Main.view.removeObjectView(onBar);
		}
		
		//mettre de l'argent sur le bar
		StaticObject tip = new StaticObject("/tip.png", posObjBarX, posObjBarY, Main.tailleQuadrillage/2, 0, false);
		listMovingObjects.add(tip);
		Main.view.addObjectView(tip);
		objectOnBar = true;
		onBar = tip;
		
		//faire partir le personnage
		Main.view.movePersoTo(persoName, posOutX, posOutY);
		if(getPersoByName(persoName).getY() == posBar1Y) {
			bar1free = true;
		}else if(getPersoByName(persoName).getY() == posBar2Y) {
			bar2free = true;
		}
	}
	
	//un client n'est pas content de sa commande
	public void noTipPerso(String persoName) throws IOException {
		//faire partir le personnage
		Main.view.movePersoTo(persoName, posOutX, posOutY);
		if(getPersoByName(persoName).getY() == posBar1Y) {
			bar1free = true;
		}else if(getPersoByName(persoName).getY() == posBar2Y) {
			bar2free = true;
		}
		
		//game over
		conflit = true;
		textConflit = persoName + " n'est pas content de sa commande !";
	}
	
	//le barman sert le produit
	public void barman(String produit) throws IOException, InterruptedException  {
		//si quelque chose sur le bar, le fait disparaitre (ancien tip)
		if(objectOnBar) {
			listMovingObjects.remove(onBar);
			Main.view.removeObjectView(onBar);
		}		
		
		//attente (simulation service)
		Thread.sleep(timeService);
		
		//verifie que le produit est au menu
		if(conf.getMenu().contains(produit)) {
			//faire apparaitre le produit sur le bar
			String productImg = "/produits/" + produit + ".png";
			StaticObject prod = new StaticObject(productImg, posObjBarX, posObjBarY, Main.tailleQuadrillage/2, 0, false);
			listMovingObjects.add(prod);
			Main.view.addObjectView(prod);
			objectOnBar = true;
			onBar = prod;
			
			//ecrire dans le fichier de comm que le produit est servit
	    	try {
	        	String line = produit + "\n";
				ficEcr.ecrireFichier(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.err.println(produit + " n'est pas au menu !");
		}
		
	}
    
    //renvoie true si tous les personnages en jeu sont partis (et ont donc eu leur commande)
    public boolean gagner() {
		boolean res = true;
		for(Personnage p : persos) {
			if(p.getX() != posOutX || p.getY() != posOutY){
				System.out.println(p.getName() + " n'a pas eu sa commande !");
				res =  false;
			}
		}
		return res;
	}

}
