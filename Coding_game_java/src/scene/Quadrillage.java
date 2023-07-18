package scene;

import java.util.ArrayList;
import javafx.scene.shape.Line;

public class Quadrillage {

	private int tailleCase; //taille en pixel d'une case (carré)
	private ArrayList<Line> lignes; //lignes constituant le quadrillage
	//nombre de case du quadrillage
	private int caseMaxX = -1; 
	private int caseMaxY = -1;
	
	public Quadrillage(int tailleCase, int xmin, int ymin, int xmax, int ymax) {
		this.tailleCase = tailleCase;
		lignes = new ArrayList<Line>();
		
		//création des lignes verticales
		int x = xmin;
		while(x <= xmax) {
			Line l = new Line(x, ymin, x, ymax);
			lignes.add(l);
			x = x + tailleCase;
			caseMaxX++;
		}
		//création des lignes horizontales
		int y = ymin;
		while(y <= ymax) {
			Line l = new Line(xmin, y, xmax, y);
			lignes.add(l);
			y = y + tailleCase;
			caseMaxY++;
		}
	}

	public int getTailleCase() {
		return tailleCase;
	}
	
	public ArrayList<Line> getLignes(){
		return lignes;
	}

	public int getCaseMaxX() {
		return caseMaxX;
	}

	public int getCaseMaxY() {
		return caseMaxY;
	}
	
}
