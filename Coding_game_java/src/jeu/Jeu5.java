package jeu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Objects.Plane;
import main.Main;

public class Jeu5 extends Jeu{

	private ArrayList<Plane> planes; //liste de tous les avions
	
	static private int queueSize = 5; //taille max de la file d'attente
	private Plane [] queue; //avions en attente de décollage
	private int nbPlaneQueue; //nb avions actuellement dans la file
	
	private boolean takeoff; //true si un avion utilise la piste de décollage, false si la piste est libre
	
	//position des avions avant arrivée
	private static int posStartX = -2*Main.tailleQuadrillage; 
	private static int posStartY = 5*Main.tailleQuadrillage; 
	//positions des avions dans la file
	private static int[] posQueueX = {6*Main.tailleQuadrillage, 5*Main.tailleQuadrillage, 4*Main.tailleQuadrillage, 3*Main.tailleQuadrillage, 2*Main.tailleQuadrillage};
	private static int[] posQueueY = {5*Main.tailleQuadrillage, 5*Main.tailleQuadrillage, 5*Main.tailleQuadrillage, 5*Main.tailleQuadrillage, 5*Main.tailleQuadrillage};
	//points de passage pour le décollage des avions
	private static int nbPointTakeOff = 7;
	private static int[] takeOffX = {7*Main.tailleQuadrillage, 8*Main.tailleQuadrillage, 7*Main.tailleQuadrillage, 6*Main.tailleQuadrillage, 4*Main.tailleQuadrillage, 1*Main.tailleQuadrillage, -5*Main.tailleQuadrillage};
	private static int[] takeOffY = {4*Main.tailleQuadrillage, 3*Main.tailleQuadrillage, 2*Main.tailleQuadrillage, 2*Main.tailleQuadrillage, 2*Main.tailleQuadrillage, 2*Main.tailleQuadrillage, 2*Main.tailleQuadrillage};
	
	
	public Jeu5(int caseMaxX, int caseMaxY) throws IOException {	
		super(caseMaxX, caseMaxY);
		planes = new ArrayList<Plane>();
		textDefaite = "Tous les avions n'ont pas décollé !";
		queue = new Plane[queueSize];
		nbPlaneQueue = 0;
		takeoff = false;
	}
	
	public void addPlane(String refPlane) throws IOException {
		//création d'un nouvel avion
		Plane plane = new Plane(refPlane, posStartX, posStartY, Main.tailleQuadrillage, 0, true);
		Main.view.addToListObjectsAndDisplay(plane);
		planes.add(plane);
		
		//verification si la file n'est pas pleine
		if(nbPlaneQueue == queueSize) {
			//conflit : file pleine
			conflit = true;
			textConflit = " File d'attente pleine !";
		}else {
			//deplacement de l'avion à la dernière place libre
			Main.view.movePlaneTo(plane, posQueueX[nbPlaneQueue], posQueueY[nbPlaneQueue]);
			
			//ajout dans la file
			queue[nbPlaneQueue] = plane;
			nbPlaneQueue++;
		}
	}
	
	public void takeOff(String refPlane) {
		//verification de la disponibilité de la piste
		if(takeoff == true) {
			//conflit : piste occupée
			conflit = true;
			textConflit = " Piste de décollage occupée !";
		}else {
			//verifier si l'avion est premier dans la file
			if(nbPlaneQueue == 0) {
				conflit = true;
				textConflit = " Pas d'avion dans la file !";
			}else if(!queue[0].getRef().equals(refPlane)){
				conflit = true;
				textConflit = refPlane + " n'est pas en tête de file !";
			}else {
				Plane plane = queue[0];
				//deplacement avion décollage
				takeoff = true;
				Main.view.movePlaneFromPoints(plane, nbPointTakeOff, takeOffX, takeOffY);
				
				//décalage des avions dans la file
				for(int i = 1; i < nbPlaneQueue; i++) {
					Main.view.movePlaneTo(queue[i], posQueueX[i-1], posQueueY[i-1]);
					queue[i-1] = queue[i];
				}
				nbPlaneQueue --;

				//simulation temps de décollage
				TimerTask taskAfterTakeOff = new TimerTask() {
			        public void run() {
			        	takeoff = false;
			    		//envoie signal decolage ok
			        	try {
			            	String line = refPlane + "\n";
			    			Main.ficEcr.ecrireFichier(line);
			    		} catch (IOException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    		}
			        }
				};
				Timer timer = new Timer("Timer move");
				timer.schedule(taskAfterTakeOff, (long)(Main.view.getTimeMove()*nbPointTakeOff));
				
			}
		}
	}
	
	//renvoie true si tous les avions en jeu sont partis
    public boolean gagner() {
		boolean res = true;
		for(Plane p : planes) {
			if(p.getX() != takeOffX[nbPointTakeOff-1] || p.getY() != takeOffY[nbPointTakeOff-1]){
				System.out.println(p.getRef() + " n'a pas décollé ");
				res =  false;
			}
		}
		return res;
	}
	
	public Plane getPlaneByRef(String ref) {
		for(Plane p : planes) {
			if(p.getRef().equals(ref)) {
				return p;
			}
		}
		return null;
	}

	public ArrayList<Plane> getPlanes() {
		return planes;
	}

	public Plane [] getQueue() {
		return queue;
	}


}
