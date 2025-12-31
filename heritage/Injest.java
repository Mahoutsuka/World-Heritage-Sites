package heritage;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Injest{

	public HashMap<Coord, Site> unesco; 

	public Injest(){
		this.unesco = new HashMap<Coord, Site>(); 
	}

	public void injestCoords(){
		
		String filePath =  "heritage/unescosites.csv";

		try{
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);

			//skip header line 

			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}

			while (scanner.hasNextLine()) {

				String eachSite = scanner.nextLine();

            	String[] eachSit = eachSite.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            	String name = eachSit[0];
            	String desc = eachSit[1];
            	String longitude = eachSit[2];
            	String latitude = eachSit[3];
            	String country = eachSit[4];
            	String region = eachSit[5];

            	Coord c = new Coord(longitude, latitude);
            	Site s = new Site(name, desc, longitude, latitude, country, region);

            	unesco.put(c, s);
            }
		}
		catch(FileNotFoundException e){
			 System.err.println("File not found: " + e.getMessage());
		}
		System.out.println("Injested "+this.unesco.size()+" site entries from " + filePath); 
	}

	public HashMap<Coord, Site> getUnesco() {
		return this.unesco;
	} 

	public static void main(String[] args){
		Injest inj = new Injest(); 
			
		inj.injestCoords();

	}

}