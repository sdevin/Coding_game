package main; 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;



public class View extends Pane{
	
	private Text displayedChars[];

	private static final String bgName = "images/afficheur.png";
	private static final int yText = 112;
	private static final int xStartText = 5;
	public static final int nbCharText = 45;
	public static final int deltaXText = Main.tailleX/nbCharText;
	
	
	public View() {
		super();
		
	}

	public void launchView() throws IOException {
		
		//ajout background
		BufferedImage bufImg;
		try {
			bufImg = ImageIO.read(new File(bgName));
			Image bgimage = SwingFXUtils.toFXImage(bufImg, null);
			ImageView bgview = new ImageView(bgimage);
			bgview.setFitWidth(Main.tailleX);
			bgview.setPreserveRatio(true);
			this.getChildren().add(bgview);
		} catch (IOException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
		

		displayedChars = new Text[nbCharText];
		for(int i = 0; i < nbCharText; i ++) {
			displayedChars[i] = new Text(String.valueOf(' '));
			displayedChars[i].setFont(new Font("Arial", deltaXText));
			displayedChars[i].setFill(Color.YELLOW);
			displayedChars[i].setX(xStartText + i*deltaXText);
			displayedChars[i].setY(yText);
			displayedChars[i].setTextAlignment(TextAlignment.CENTER);
			this.getChildren().add(displayedChars[i]);
		}
	}
	
	public void printFlight(Flight f) {
		clear();
		char text[] = f.flightToText();
		for(int i = 0; i < nbCharText; i++) {
			System.out.print(text[i]);
			printChar(text[i], i);
		}
	}
	
	//affichage d'un caractère dans une case de l'afficheur
	private void printChar(char c, int caseX) {
		

		displayedChars[caseX] = new Text(String.valueOf(c));
		displayedChars[caseX].setFont(new Font("Arial", deltaXText));
		displayedChars[caseX].setFill(Color.YELLOW);
		displayedChars[caseX].setX(xStartText + caseX*deltaXText);
		displayedChars[caseX].setY(yText);
		displayedChars[caseX].setTextAlignment(TextAlignment.CENTER);
		this.getChildren().add(displayedChars[caseX]);

	}
	
	public void clear() {
		for(Text t : displayedChars) {
			t.relocate(-50, -50);
		}
	}
}
