package scene;

import java.util.ArrayList;

import Objects.Object;

public class Background {
	
	private ArrayList<Object> listObjects;
	
	public Background() {
		listObjects = new ArrayList<Object>();
		
		//ajout comptoirs d'enregistrement
		
		//ajouts tapis bagages
		
		//ajout mur entrée
		
		//ajout porte
		
		//ajout banc
		
		//ajout plante
		
	}
	
	public ArrayList<Object> getListObjects(){
		return listObjects;
	}
	
	public void addObject(Object object) {
		listObjects.add(object);
	}

}
