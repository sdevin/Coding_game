package scene;

import java.io.IOException;
import java.util.ArrayList;

import Objects.Object;
import Objects.StaticObject;
import main.Main;

public class Background {
	
	private ArrayList<Object> listObjects;
	
	public Background() {
		listObjects = new ArrayList<Object>();

		try {
			switch(Main.Sce) {
			case 1:
			case 2:
				setBGSce1();
				break;
			case 3:
				setBGSce3();
				break;
			case 4:
				setBGSce4();
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//background pour les scénarios 1 et 2
	private void setBGSce1() throws IOException {
		//ajout comptoirs d'enregistrement
		Object compt1 = new StaticObject("/background/comtoir_bas.png", Main.xmaxQuad, 0, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(compt1);
		Object compt2 = new StaticObject("/background/comtoir_haut.png", Main.xmaxQuad, Main.tailleQuadrillage*3, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(compt2);
		Object compt3 = new StaticObject("/background/comtoir_bas.png", Main.xmaxQuad, Main.tailleQuadrillage*5, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(compt3);
		Object compt4 = new StaticObject("/background/comtoir_haut.png", Main.xmaxQuad, Main.tailleQuadrillage*8, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(compt4);
		
		//ajouts tapis bagages
		Object tapis1 = new StaticObject("/background/tapis.png", Main.xmaxQuad, Main.tailleQuadrillage*2, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(tapis1);
		Object tapis2 = new StaticObject("/background/tapis.png", Main.xmaxQuad, Main.tailleQuadrillage*7, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(tapis2);
		
		//ajout mur entrée
		Object mur = new StaticObject("/background/mur.png", 0, 0, Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(mur);
		
		//ajout banc
		Object banc = new StaticObject("/background/banc.png", Main.tailleQuadrillage/2, Main.tailleQuadrillage*6, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(banc);
		
		//ajout plantes
		Object plante = new StaticObject("/background/plante.png", Main.tailleQuadrillage/2, 0, Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(plante);
		Object plante2 = new StaticObject("/background/plante.png", Main.tailleQuadrillage/2, Main.tailleQuadrillage, Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(plante2);
	}
	
	//background pour lee scénario 3
	private void setBGSce3() throws IOException {

		//ajout mur sortie
		Object murS = new StaticObject("/background/mur.png", Main.sceneX - Main.tailleQuadrillage, 0, Main.tailleQuadrillage, 180, false);
		Main.view.addToListObjects(murS);
		//ajout murs entrée
		Object murE1 = new StaticObject("/background/mur250.png", 0, 0, Main.tailleQuadrillage/2, 180, false);
		Main.view.addToListObjects(murE1);
		Object murE2 = new StaticObject("/background/mur100.png", 0, 9*Main.tailleQuadrillage/2, Main.tailleQuadrillage/2, 180, false);
		Main.view.addToListObjects(murE2);
		Object murE3 = new StaticObject("/background/mur250.png", 0, 15*Main.tailleQuadrillage/2, Main.tailleQuadrillage/2, 180, false);
		Main.view.addToListObjects(murE3);
		

		//ajout bancs
		Object banc = new StaticObject("/background/banc.png", Main.tailleQuadrillage*3, -Main.tailleQuadrillage, 2*Main.tailleQuadrillage, 90, false);
		Main.view.addToListObjects(banc);
		Object banc2 = new StaticObject("/background/banc.png", Main.tailleQuadrillage*8, -Main.tailleQuadrillage, 2*Main.tailleQuadrillage, 90, false);
		Main.view.addToListObjects(banc2);
		Object banc3 = new StaticObject("/background/banc.png", Main.tailleQuadrillage*3, Main.tailleQuadrillage*7, 2*Main.tailleQuadrillage, 270, false);
		Main.view.addToListObjects(banc3);
		Object banc4 = new StaticObject("/background/banc.png", Main.tailleQuadrillage*8, Main.tailleQuadrillage*7, 2*Main.tailleQuadrillage, 270, false);
		Main.view.addToListObjects(banc4);

		//ajout plantes
		Object plante = new StaticObject("/background/plante.png", Main.tailleQuadrillage*6, 0, Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(plante);
		Object plante2 = new StaticObject("/background/plante.png", Main.tailleQuadrillage*6, Main.tailleQuadrillage*9, Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(plante2);
		
	}
	
	private void setBGSce4() throws IOException {
		
		//ajout mur
		Object mur = new StaticObject("/background/mur.png", 0, 0, Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(mur);
		
		//ajout bar
		Object bar = new StaticObject("/background/bar.png", Main.xmaxQuad, 0, 2*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(bar);
		
		//ajout menu
		Object menu = new StaticObject("/background/menu.png", 7*Main.tailleQuadrillage, 5*Main.tailleQuadrillage, 6*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(menu);
		
		//ajout deco bar
		Object table = new StaticObject("/background/table.png", 2*Main.tailleQuadrillage, 6*Main.tailleQuadrillage, 3*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(table);
		Object table2 = new StaticObject("/background/table.png", 4*Main.tailleQuadrillage, 0, 3*Main.tailleQuadrillage, 0, false);
		Main.view.addToListObjects(table2);
	}
	
	public ArrayList<Object> getListObjects(){
		return listObjects;
	}
	
	public void addObject(Object object) {
		listObjects.add(object);
	}

}
