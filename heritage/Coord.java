package heritage;

import java.util.*;
import java.io.*;

public class Coord{

	private String longt;
	private String lat;

	public Coord(String longt, String lat){
		this.longt = longt;
		this.lat = lat;
	}

	@Override
	//Returns the site's information in a readable format
	public String toString() {
		return "Longitude " + longt + "\n" + "Latitude: " + lat; 
	}

	public Double getLatitude(){
		return Double.parseDouble(lat); 
	}

	public Double getLongitude() {
		return Double.parseDouble(longt);
	}

	@Override
	public int hashCode() {
        return Objects.hash(this.getLatitude(), this.getLongitude());
    }

    @Override
	public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null || getClass() != obj.getClass())
        return false;
    Coord other = (Coord) obj;
    return Double.compare(this.getLatitude(), other.getLatitude()) == 0 && Double.compare(this.getLongitude(), other.getLongitude()) == 0;
	}

}