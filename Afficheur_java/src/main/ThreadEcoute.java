package main;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThreadEcoute extends Thread{
	
	//adresse de la fifo pour le lien avec le code C
	private final String fifoAdresse = "/tmp/cmd";
	
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
				
			}
			System.out.println("Fin d'écoute");
			buf.close();
			
		}catch(IOException e) {
			System.err.println("erreur fifo  : " + e.toString());
		}
	}
}
