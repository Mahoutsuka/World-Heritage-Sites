package heritage;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Points{

	private int x;
	private int y;

	public Points(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	//Returns the site's information in a readable format
	public String toString() {
		return "X: " + x + "\n" + "Y: " + y; 
	}

    //converting long and lat to x and y for the graph.
    public int getX() {
    	return x;
    }

    public int getY() {
    	return y;
    }
}