package Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FichConf3 extends FichConf{
	
/*
 * Fichier de conf sous la forme :
 * bonus (boolean) : si true, liste des persos prise depuis fichier, si false 2 persos uniquement
 * nomPerso1 (String) : nom du premier personnage (bonus = false)
 * nomPerso2 (String) : nom du second personnage (bonus = false)
 */
	
	//parametres récupérés du fichier de conf
	private boolean bonus;
	private String nomPerso1;
	private String nomPerso2;
	
	//paramètres par défaut
	private static boolean defaultBonus = false;
	private static String defaultNomPerso1 = "toto";
	private static String defaultNomPerso2 = "tata";

	public FichConf3() throws IOException{
			
			super();
			nomFicConf = "confS3.txt";
			
			boolean conti = true;
			
			//test si le fichier de conf existe
			File f = new File(nomFicConf);
			if(f.exists())
			{ 
				  //si oui, le lit
			      FileReader fr = new FileReader(f);  
			      // Créer l'objet BufferedReader        
			      BufferedReader br = new BufferedReader(fr);  
			      String line;
			      //lecture du flag bonus
			      line = br.readLine();
			      if(line != null) {
			    	  bonus = Boolean.parseBoolean(line);
			      }else {
			    	  System.err.println("Fichier de configuration : bonus non definit (true or false) !");
			    	  conti = false;
			    	  bonus = defaultBonus;
			    	  nomPerso1 = defaultNomPerso1;
			    	  nomPerso2 = defaultNomPerso2;
			      }
			    //lecture nom du perso 1
			      line = br.readLine();
			      if(conti && line != null) {
			    	  nomPerso1 = line;
			      }else {
			    	  System.err.println("Fichier de configuration : pas de nom de personnage 1 !");
			    	  conti = false;
			    	  nomPerso1 = defaultNomPerso1;
			    	  nomPerso2 = defaultNomPerso2;
			      }
			    //lecture nom du perso 2
			      line = br.readLine();
			      if(conti && line != null) {
			    	  nomPerso2 = line;
			      }else {
			    	  System.err.println("Fichier de configuration : pas de nom de personnage 2 !");
			    	  conti = false;
			    	  nomPerso2 = defaultNomPerso2;
			      }
			      fr.close();    
			}else {
				//si non prend les paramètre par défaut
				bonus = defaultBonus;
				nomPerso1 = defaultNomPerso1;
				nomPerso2 = defaultNomPerso2;
			}
	}

	public boolean getBonus() {
		return bonus;
	}

	public String getNomPerso1() {
		return nomPerso1;
	}

	public String getNomPerso2() {
		return nomPerso2;
	}
}
