package main;

import java.io.*;

public class EcritureFicher {
	
	private static String nomFicEcriture = "/tmp/default";
	private FileWriter fw;
	private FileWriter fw_toto;
	private FileWriter fw_tata;
	
	
	public EcritureFicher(){
		if(Main.Sce == 1 || Main.Sce == 2) {
			nomFicEcriture = "/tmp/posPerso";
		}else if(Main.Sce == 3) {
			nomFicEcriture = "/tmp/checks";
		}else if(Main.Sce == 4) {
			nomFicEcriture = "/tmp/barman";
		}else if(Main.Sce == 5) {
			nomFicEcriture = "/tmp/takeoff";
		}
		
	    try {
	    	if(Main.Sce == 3) {
	    		fw_toto = new FileWriter("/tmp/check_toto");
	    		fw_tata = new FileWriter("/tmp/check_tata");
	    	}else {
	    		fw = new FileWriter(nomFicEcriture);
	    	}
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public void ecrireFichier(String line) throws IOException {

        fw.write(line);
        fw.flush();
	}
	
	public void ecrireFichier(String perso, String line) throws IOException {

		if(perso.compareTo("toto") == 0) {
	        fw_toto.write(line);
	        fw_toto.flush();
		}else if(perso.compareTo("tata") == 0){
	        fw_tata.write(line);
	        fw_tata.flush();
		}else {
			System.out.println("Mauvais nom perso !");
		}
	}

	public void closeFichier() throws IOException {
    	if(Main.Sce == 3) {
            fw_toto.close();
            fw_tata.close();
    	}else {
            fw.close();
    	}
	}
}
