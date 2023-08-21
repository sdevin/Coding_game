package main;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThreadEcoute extends Thread{
	
	//adresse de la fifo pour le lien avec le code C
	private final String fifoAdresse = "/tmp/cmd";
	
	//champs pour passer des données (par de param dans un runnable)
	private String persoName;
	private String direction;
	private String produit;
	private String texteDefaite;
	
	//déplace un personnage dans une direction sur le quadrillage
	Runnable movePerso = new Runnable() {
		public void run() {
			Main.jeu.movePerso(persoName, direction);
		}
	};

	//déplace un personnage au portique de sécurité (sce 3)
	Runnable movePersoToCheck = new Runnable() {
		public void run() {
			Main.jeu.movePersoToCheck(persoName);
		}
	};

	//fait sortir un personnage du portique de sécurité (sce 3)
	Runnable movePersoOut = new Runnable() {
		public void run() {
			Main.jeu.movePersoOut(persoName);
		}
	};

	//fait entrer un personnage dans le bar (sce 4)
	Runnable enterPerso = new Runnable() {
		public void run() {
			try {
				Main.jeu.enterPerso(persoName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	//un personnage laisse un pourboire et part (sce 4)
	Runnable tipPerso = new Runnable() {
		public void run() {
			try {
				Main.jeu.tipPerso(persoName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	//un personnage laisse un pourboire et part (sce 4)
	Runnable noTipPerso = new Runnable() {
		public void run() {
			try {
				Main.jeu.noTipPerso(persoName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	//le barman sert le produit (sce 4)
	Runnable barman = new Runnable() {
		public void run() {
			try {
				Main.jeu.barman(produit);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	//la partie est gagnée
	Runnable victoire = new Runnable() {
		public void run() {
			Main.view.displayVictoire();
		}
	};
	
	//la partie est perdue
	Runnable defaite = new Runnable() {
		public void run() {
			Main.view.displayDefaite(texteDefaite);
		}
	};
	
	public void run() {
		try {
			
			//connection a la fifo avec le code C
			System.out.println("Attente d'instruction");
			FileReader fifo = new FileReader(fifoAdresse);
			BufferedReader buf = new BufferedReader(fifo);
			
			boolean run = true;
			while(run) {
				String instruction = buf.readLine();
				while(instruction == null) {
					instruction = buf.readLine();
				}
				System.out.println("Commande reçue : " + instruction);
				String ins[] = instruction.split("\\s");
				if(ins.length == 1 && ins[0].equals("end")) { //signal de fin d'écoute)
					run = false;
				}else if(ins.length == 2) {
					if(ins[0].equals("barman")) {
						produit = ins[1];
						Platform.runLater(barman);
					}else {
						persoName = ins[0];
						switch(ins[1]) {
						case "r":
						case "l":
						case "u":
						case "d":
							direction = ins[1];
							Platform.runLater(movePerso);
							break;
						case "goCheck":
							Platform.runLater(movePersoToCheck);
							break;
						case "outCheck":
							Platform.runLater(movePersoOut);
							break;
						case "enter":
							Platform.runLater(enterPerso);
							break;
						case "tip":
							Platform.runLater(tipPerso);
							break;
						case "notip":
							Platform.runLater(noTipPerso);
							break;
						default : 
							System.err.println("Instruction invalide : " + ins[1]);
							run = false;
						}
					}
				}else{
					System.err.println("Commande invalide : " + instruction);	
				}
				//temps d'attente entre chaque instruction (pour des raisons de synchro)
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//vérifie si la précédente commande n'a pas générer de conflit (et donc de defaite)
				if(Main.jeu.getConflit()) {
					run = false;
					texteDefaite = Main.jeu.getTextConflit();
					Platform.runLater(defaite);
				}
			}
			System.out.println("Fin d'écoute");
			buf.close();
			//fin de la partie check si gagner ou perdu
			if(!Main.jeu.getConflit()) {
				if(Main.jeu.gagner()) {
					Platform.runLater(victoire);
				}else {
					texteDefaite = Main.jeu.getTextDefaite();
					Platform.runLater(defaite);
				}
			}
			
		}catch(IOException e) {
			System.err.println("erreur fifo  : " + e.toString());
		}
	}
}
