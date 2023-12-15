package main; 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;



public class View extends Pane{
	

	private static final String bgName = "images/afficheur.png";
	
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
		

	}
	
}
