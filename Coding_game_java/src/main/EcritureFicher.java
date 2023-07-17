package main;

import java.io.*;

public class EcritureFicher {
	
	private static String nomFicEcriture = "/tmp/default";
	private FileWriter fw;
	
	EcritureFicher(){
		if(Main.Sce == 1 || Main.Sce == 2) {
			nomFicEcriture = "/tmp/posPerso";
		}else {
			nomFicEcriture = "/tmp/checks";
		}
		
	    try {
	        fw = new FileWriter(nomFicEcriture);
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public void ecrireFichier(String line) throws IOException {

        fw.write(line);
        fw.flush();
	}

	public void closeFichier() throws IOException {
        fw.close();
	}
}
