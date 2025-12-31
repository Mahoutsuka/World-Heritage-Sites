package heritage;

/**
 * Rectangular coordinate form of each point on the sphere
 */

public class RectPoint3D{
	double x;
	double y;
	double z;

	public RectPoint3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String toString(){
		return Double.toString(x) + ", " + Double.toString(y) + ", " + Double.toString(z);
	}

	// Holds the x-axis constant and rotate points around it
	public static RectPoint3D rotateX(RectPoint3D p, double angle){
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double newY = p.y*cos - p.z*sin;
		double newZ = p.z*cos + p.y*sin;
		return new RectPoint3D(p.x,newY,newZ);
	}

	// Holds the y-axis constant and rotate points around it
	public static RectPoint3D rotateY (RectPoint3D p, double angle){
		double sin = Math.sin(angle);
		double cos = Math.cos (angle);
		double newX = p.x*cos + p.z*sin; //may need to swap
		double newZ = p.z*cos - p.x*sin;
		return new RectPoint3D(newX, p.y, newZ);
	}

}