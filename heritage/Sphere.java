/**
 * Class for creating the 3d sphere
 * Credit goes to Blayne and Francis for their implemention of conway of life
 */
package heritage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Sphere{

	public HashMap<CoordPoint, Site> sites = new HashMap<CoordPoint, Site>(); 
	public HashMap<Coord, Site> unesco = new HashMap<Coord,Site>();

	public static final int LAT = 120;
	public static final int LONG = 150;
	public static final int RADIUS = 300;

	private BufferedImage map;
	private BufferedImage[][] sections;
 
	public RectPoint3D [][] vertices = new RectPoint3D[LAT + 1 ][LONG];
	public ArrayList<Square> arrayofSquare = new ArrayList<Square>();
	public Square [][] squares = new Square[LAT + 1][LONG];

	public Sphere (HashMap<Coord,Site> unesco){
		this.unesco = unesco;
	}

	public void initializeSphere(){
		//finds the x,y,z coordinates of every point based on their spherical coordinates
		double theta, phi, x, y, z;
		for (int i = 0 ; i <= LAT ; i ++){
			phi = Math.PI * i / LAT;
			for (int j = 0; j < LONG; j ++){
				theta = 2*Math.PI * j / LONG;
				z = RADIUS * Math.sin(phi)*Math.cos(theta);
				x = RADIUS * Math.sin(phi)*Math.sin(theta);
				y = RADIUS * Math.cos(phi);
				vertices[i][j] = new RectPoint3D(x,y,z);
			}
		}

		//finds the x,y,z coordinates of every world-heritage site based on their longitutde and latitude
		for (Coord coordinate: unesco){
			phi = (90.0 - coordinate.getLatitude()) * (Math.PI / 180.0);
			theta = (coordinate.getLongitude() + 180.0) * (Math.PI / 180.0);

			z = RADIUS * Math.sin(phi)*Math.cos(theta);
			x = RADIUS * Math.sin(phi)*Math.sin(theta);
			y = RADIUS * Math.cos(phi);

			CoordPoint coord = new CoordPoint(x,y,z);
			sites.put(coord, unesco.get(coordinate));
			//System.out.println(sites.get(coord));
		}


		

		// creates a square with every 4 adjacent points
		RectPoint3D point1, point2, point3, point4;
		int count = 0;
		for (int i = 0; i < LAT; i ++){
			for (int j = 0; j < LONG; j ++){
				point1 = vertices[i][j];
				point2 = vertices[i][(j+1)%LONG]; //wraps around the globe
				point3 = vertices[(i+1)][(j+1)%LONG]; //shouldn't wrap around i as it looks like there is a hole through the earth
				point4 = vertices[(i+1)][j];
				Square tempSquare = new Square (point1,point2,point3,point4);
				arrayofSquare.add(tempSquare);
				count ++;
				squares [i][j] = tempSquare;
				// System.out.println(tempSquare.toString());
				// System.out.println(squares[i][j].toString());

			}
		}

		//setting those map sections on the squares
		try{
			map = ImageIO.read(new File("heritage/eqqq.jpeg"));
		}

		catch (IOException e) {
			System.err.println("File not found: " + e.getMessage());
		}
		
		int w = map.getWidth() / LONG;
    	int h = map.getHeight() / LAT;

    	// creating map sections
    	sections = new BufferedImage[LAT][LONG];

		for (int i = 0; i < LAT; i ++){
			for (int j = 0; j < LONG; j ++){
				sections[i][j] = map.getSubimage(j*w, i*h, w, h);
				squares[i][j].setImage(sections[i][j]);
			}
		}
	}

	//rotates the sphere by updating the graphical representation of each square and world-heritage site
	//only show the squares that are in the front
	public void rotate(double angleX, double angleY, int w, int h){
		for (Square square: arrayofSquare){
			square.display = false;
			square.rotate(angleX, angleY, w, h);
			if(square.avgZ() < 0){
				square.display = true;
			}
		}
		for (CoordPoint coord: sites){
			coord.display = false;
			coord.rotate(angleX, angleY, w, h);
			if (coord.transformedCoord[2] < 0){
				coord.display = true;
			}
		}
	}


	// test for clicking/interacting with an individual square
	// public void updateSquare (Point e) {
	// 	ArrayList<Square> SquareContains = new ArrayList<Square>();
	// 	for (Square square: arrayofSquare){
	// 		if(square.contains(e)){
	// 			SquareContains.add(square);
	// 		}
			
	// 	}
	// 	if (SquareContains.size() > 0){ //size = 0 when clicking outside of globe, size = 2 if clicked inside of globe
	// 		if (SquareContains.get(0).transformedvertices[0].z < SquareContains.get(1).transformedvertices[0].z){ //make a comparator
	// 			SquareContains.get(0).clicked = true;
	// 		}
	// 		else{
	// 			SquareContains.get(1).clicked = true;
	// 		}
	// 	}
	// }

	//when a world-heritage site is clicked, display information of the site
	public void updatePoint(Point e){
		for (CoordPoint point : sites){
			point.clicked = false;
		}
		ArrayList<CoordPoint> PointContains = new ArrayList<CoordPoint>();
		for (CoordPoint point : sites){
			if (point.transformedCoord[2] < RADIUS * 0.8) { //to consider points at the front
				if (point.contains(e)){
					PointContains.add(point);
					System.out.println(sites.get(point));
				}
			}
		}
		if (PointContains.size() == 0) return;
		if (PointContains.size() == 1){
			PointContains.get(0).clicked = true;
		// 	System.out.println(PointContains.get(0));
		// 	System.out.println(sites.get(PointContains.get(0)));
		}


		// if (PointContains.size() == 2){
		// 	if (PointContains.get(0).transformedCoord[2] < PointContains.get(1).transformedCoord[2]){
		// 		PointContains.get(0).clicked = true;
		// 		//System.out.println(sites.get(PointContains.get(0)));
		// 	}
		// 	else{
		// 		PointContains.get(1).clicked = true;
		// 		//System.out.println(sites.get(PointContains.get(1)));
		// 	}
		// }
	}

	// displays the world-heritage site
	public void drawCoordPoints (Graphics2D g2, int w, int h){
		for (CoordPoint coord: sites){
			coord.drawPoint(g2,w,h);
		}
	}

	//draws the sphere
	public void drawSphere (Graphics2D g2, int w, int h){
		for (Square square : arrayofSquare){
			square.drawSquare(g2,w,h);
		}
	}

}