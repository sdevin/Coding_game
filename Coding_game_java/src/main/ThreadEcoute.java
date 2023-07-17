package main;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThreadEcoute extends Thread{
	
	//adresse de la fifo pour le lien avec le code C
	private final String fifoAdresse = "/tmp/cmd";
	
	private String persoName;
	private String direction;
	private String texteDefaite;
	
	Runnable movePerso = new Runnable() {
		public void run() {
			Main.jeu.movePerso(persoName, direction);
		}
	};

	Runnable movePersoToCheck = new Runnable() {
		public void run() {
			Main.jeu.movePersoToCheck(persoName);
		}
	};

	Runnable movePersoOut = new Runnable() {
		public void run() {
			Main.jeu.movePersoOut(persoName);
		}
	};
	
	Runnable victoire = new Runnable() {
		public void run() {
			try {
				Main.jeu.victoire();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	Runnable defaite = new Runnable() {
		public void run() {
			Main.jeu.defaite(texteDefaite);
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
				System.out.println("Commande reçue : " + instruction);
				String ins[] = instruction.split("\\s");
				if(ins.length == 1 && ins[0].equals("end")) {
					run = false;
				}else {
					if(ins.length != 2) {
						System.err.println("Commande invalide : " + instruction);	
					}else{
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
						default : 
							System.err.println("Instruction invalide : " + ins[1]);
							run = false;
						}
					}
					try {
						Thread.sleep(1200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Main.jeu.getConflit()) {
						run = false;
						texteDefaite = Main.jeu.getTextConflit();
						Platform.runLater(defaite);
					}
				}
			}
			System.out.println("Fin d'écoute");
			buf.close();
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
