package main;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThreadEcoute extends Thread{
	
	//adresse de la fifo pour le lien avec le code C
	private final String fifoAdresse = "/tmp/cmd";
	//flight a afficher
	private Flight flight = new Flight();

	//déplace un personnage dans une direction sur le quadrillage
	Runnable printFlight = new Runnable() {
		public void run() {
			Main.view.printFlight(flight);
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
				if(ins.length != 5) {
					System.out.println("Mauvais nombre d'arguments !");
				}else {
					//1er argument : time int[4]
					if(ins[0].length() > 4) {
						System.out.println("Argument time trop long !");
					}else {
						flight.setTime(ins[0]);
					}
					//2eme argument : destination char[10]
					if(ins[1].length() > 10) {
						System.out.println("Argument destination trop long !");
					}else {
						flight.setDestination(ins[1]);
					}
					//3eme argument : flightRef char[7]
					if(ins[2].length() > 7) {
						System.out.println("Argument flightRef trop long !");
					}else {
						flight.setFlightRef(ins[2]);
					}
					//4eme argument : gate int[2]
					if(ins[3].length() > 2) {
						System.out.println("Argument gate trop long !");
					}else {
						flight.setGate(ins[3]);
					}
					//5eme argument : remarks char[10]
					if(ins[4].length() > 10) {
						System.out.println("Argument remarks trop long !");
					}else {
						flight.setRemark(ins[4]);
					}
					//affichage du vol
					Platform.runLater(printFlight);
				}
			}
			System.out.println("Fin d'écoute");
			buf.close();
			
		}catch(IOException e) {
			System.err.println("erreur fifo  : " + e.toString());
		}
	}
}
