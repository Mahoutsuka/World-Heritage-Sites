package heritage;

public class Site{

	private String name;
	private String desc;
	private String longt;
	private String lat;
	private String country;
	private String region;

	public Site(String name, String desc, String longt, String lat, String country, String region){
		this.name = name;
		this.desc = desc;
		this.longt = longt;
		this.lat = lat;
		this.country = country;
		this.region = region;
	}

	@Override
	//Returns the site's information in a readable format
	public String toString() {
		return "Site: " + name + "\n" + "Country: " + country + "\n" + "Region: " + region + "\n" + "Description: " + desc; 
	}

	public Double getLat(){
		return Double.parseDouble(lat); 
	}
	public Double getLongt() {
		return Double.parseDouble(longt);
	}

	public String getName() {
		return name;
	}

	public static void main(String[] args) {
		Site williams = new Site("Williams College", "It's a college", "-98.989764", "0.987955", "USA", "America");

		System.out.println(williams);
		System.out.println("Latitude: " + williams.getLongt());
	}
}