package heritage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Class for creating each individual square of the sphere
 * Credit goes to Blayne and Francis for their implemention of conway of life
 */

public class Square{
	RectPoint3D []vertices = new RectPoint3D[4];
	RectPoint3D[] transformedvertices = new RectPoint3D[4];
	int[] xPoints = new int[4];
	int[] yPoints = new int[4];
	Polygon p;
	boolean clicked = false;
	boolean display = false;
	private BufferedImage img;

	//stores the original vertices of square and only uses transformed vertices for graphics
	public Square(RectPoint3D a, RectPoint3D b, RectPoint3D c, RectPoint3D d){
		vertices[0] = a;
		vertices[1] = b;
		vertices[2] = c;
		vertices[3] = d;
        transformedvertices[0] = new RectPoint3D(a.x,a.y,a.z);
        transformedvertices[1] = new RectPoint3D(b.x,b.y,b.z);
        transformedvertices[2] = new RectPoint3D(c.x,c.y,c.z);
        transformedvertices[3] = new RectPoint3D(d.x,d.y,d.z);
		for (int i = 0; i < 4; i++) {
	            xPoints[i] = (int) transformedvertices[i].x;
	            yPoints[i] = (int) transformedvertices[i].y;
	        }
	        p = new Polygon (xPoints, yPoints, 4);
	}

	public String toString(){
	return transformedvertices[0].toString() + " \n" + transformedvertices[1].toString() + " \n" + transformedvertices[2].toString() + " \n" + transformedvertices[3].toString() + " \n" + "------------";
	}

	//shifts the x,y,z coordinates of each vertices based on the user's mouse drag 
	public void rotate (double angleX, double angleY, int w, int h){
		for (int i = 0; i < 4; i ++){
			RectPoint3D rotated = vertices[i].rotateY(vertices[i],angleY);
			RectPoint3D rotatedX2 = rotated.rotateX(rotated, angleX);
			//double scale = 300 / (rotatedX2.z + 400); // perspective (bigger in the front, smaller in the back)
			int x = (int) ( rotatedX2.x + w);
			int y = (int) ( -1* rotatedX2.y + h);
			transformedvertices[i] = new RectPoint3D(x, y,rotatedX2.z);
		}
		// int[] xPoints = new int[4];
	        // int[] yPoints = new int[4];

		for (int i = 0; i < 4; i++) {
		      	xPoints[i] = (int) transformedvertices[i].x;
			yPoints[i] = (int) transformedvertices[i].y;
	        }
	}

	public double avgZ() {
	    return (transformedvertices[0].z +transformedvertices[1].z +transformedvertices[2].z + transformedvertices[3].z) / 4.0;
	}

	public boolean contains(Point e){
	        return p.contains(e);

	}

	public void setImage(BufferedImage img) {
	    this.img = img;
	}

	//draws the square based on its x and y coordinates of each transformed vertices
	public void drawSquare (Graphics2D g2, int w, int h){
		p = new Polygon(xPoints, yPoints, 4);
			if(display){
				//image observer is null, painting the images on the square
				Shape old = g2.getClip();
				Rectangle bounds = p.getBounds();
				g2.setClip(p);
				
				if (bounds.width > 0 && bounds.height > 0 && img != null) {
					g2.drawImage(img, bounds.x, bounds.y, bounds.width, bounds.height, null);
				}

				// g2.drawPolygon(p);
				g2.setClip(old);
			}
	}
}