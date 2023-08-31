package Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FichConf4 extends FichConf{
	
/*
 * Fichier de conf contient la liste des produits commendable
 */
	
	//parametres récupérés du fichier de conf
	private ArrayList<String> menu;
	
	//paramètres par défaut
	private static ArrayList<String> defaultMenu= new ArrayList<String>(Arrays.asList("biere", "jus", "soda", "vin", "tapas", "burger", "frites", "salade"));;

	public FichConf4() throws IOException{
			
			super();
			nomFicConf = "menu.txt";
			
			//test si le fichier de conf existe
			File f = new File(nomFicConf);
			if(f.exists())
			{ 
				menu = new ArrayList<String>();
				//si oui, le lit
			    FileReader fr = new FileReader(f);  
			    // Créer l'objet BufferedReader        
			    BufferedReader br = new BufferedReader(fr);  
			    String line;
			    //lecture du flag bonus
			    line = br.readLine();
			    while(line != null) {
			    	menu.add(line);
			    	line = br.readLine();
			     }
			     fr.close();    
			}else {
				//si non prend les paramètre par défaut
				menu = defaultMenu;
			}
	}

	public ArrayList<String> getMenu() {
		return menu;
	}

}
