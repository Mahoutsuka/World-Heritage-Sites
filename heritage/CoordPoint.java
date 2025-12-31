package heritage;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * This class helps display each world heritage site
 */

class CoordPoint extends RectPoint3D{
	boolean clicked;
	double[] transformedCoord = new double [3];
	boolean display = false;

	public CoordPoint(double x, double y, double z){
		super(x,y,z);
		this.clicked = false;
		transformedCoord[0] = x;
		transformedCoord[1] = y;
		transformedCoord[2] = z;
	}

	//rotates the x,y,z coordinates based on the movement of the user's mouse
	public void rotate (double angleX, double angleY, int w, int h){
		RectPoint3D rotated = this.rotateY(this, angleY);
		RectPoint3D rotatedX2 = rotated.rotateX(rotated, angleX);
        transformedCoord[0] =  ( rotatedX2.x + w);
        transformedCoord[1] =  ( -1*rotatedX2.y + h);
		transformedCoord[2] = rotatedX2.z;
	}

	// draws a circle on the globe representing the point
	public void drawPoint (Graphics2D g2, int w, int h){
		if (display){
			int radius = 5;
			if (clicked){
				g2.setColor(Color.RED);
			}
			else{
				g2.setColor(Color.BLUE);
			}
			g2.fillOval((int) transformedCoord[0] - radius, (int) transformedCoord[1] - radius, 3 * radius, 3 * radius);
			g2.drawOval((int) transformedCoord[0] - radius, (int) transformedCoord[1] - radius, 3* radius, 3 * radius);
			g2.setColor(Color.BLACK);
		}
	

	}

	// checks if the user's mouse click is close enough to the point
	public boolean contains (Point e){
		double x = e.getX();
		double y = e.getY();

		int distance = 16;
		if(Math.abs((x-transformedCoord[0])*(x-transformedCoord[0]) + (y-transformedCoord[1])*(y-transformedCoord[1])) < 16){
			if (transformedCoord[2] < 8 * 0.9) {
			clicked = true;
			}
		}
		return clicked; 
	}	

	@Override
	public int hashCode() {
	    return Objects.hash(Math.round(x * 1000), Math.round(y * 1000), Math.round(z * 1000));
	}

	@Override
	public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null || getClass() != obj.getClass())
        return false;
    CoordPoint other = (CoordPoint) obj;
    return Math.round(this.x * 1000) == Math.round(other.x * 1000) && Math.round(this.y * 1000) == Math.round(other.y * 1000) && Math.round(this.z * 1000) == Math.round(other.z * 1000);
	}

}