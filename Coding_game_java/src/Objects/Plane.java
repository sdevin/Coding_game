package Objects;

import java.io.IOException;


public class Plane extends MovingObject{
	
	private static String defaultImgName = "/plane.png";  //image par défaut

	private String ref;
	
	public Plane(String ref, int x, int y, int width, int angle, boolean isObstacle) throws IOException {
		super(defaultImgName, x, y, width, angle, isObstacle);
		this.setRef(ref);
		
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
}
