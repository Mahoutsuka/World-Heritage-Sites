package heritage;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;


/**things to do:
 * Somehow setup a lat and longitude over this map - we do this by mapping latitude and longitude mathematicallY
 * Somehow align the points on the map
 * Somehow put those points as buttons on the map
 * Somehow link those to the final site
 */


public class WorldMap extends JPanel{
	
	private Image map;
	private boolean added = false;

	//TEST!!!!
	// public Coord a = new Coord ("3.946111111", "49.0775");
	// public Coord[] coords = {a};
	public HashMap<Coord, Site> sites;
	public ArrayList<Points> points = new ArrayList<>();
	public ArrayList<JButton> buttons = new ArrayList<>();


	//Constructor
	public WorldMap(Injest inj) {
		this.sites = inj.getUnesco();
		setLayout(null);
		setSize(1200,715);
		// if (added == false) {
		// 	addtoList();
		// 	added = true;
		// }
		addButtons();
		map = Toolkit.getDefaultToolkit().getImage("heritage/eqqq.jpeg");
	}

	private Points convertCtoP(double longt, double lat) {
		int width = this.getWidth();
		int height = this.getHeight();

		double longtX = (longt + 180) / 360;
		double latY = (90 - lat) / 180;

		int x = (int)(longtX * width);
		int y = (int)(latY * height);

		return new Points(x, y);
	}

	//test !
	// public void addtoList() {
	// 	for (Coord key : sites) {
	// 		double longt = key.getLongitude();
	// 		double lat = key.getLatitude();
	// 		Point p = convertCtoP(longt, lat);
	// 		points.add(p);
	// 	}
	// }

	public void addButtons() {
		ImageIcon pin = new ImageIcon("heritage/pin.png");
		        // System.out.println("Icon width: " + pin.getIconWidth());
		for (Coord key : sites) {
			Site s = sites.get(key);
			double longt = key.getLongitude();
			double lat = key.getLatitude();
			Points p = convertCtoP(longt, lat);

			JButton butt = new JButton(pin);
			butt.setBounds(p.getX() - 5, p.getY() - 5, 20, 20);
		
			butt.setContentAreaFilled(false);
			butt.setBorderPainted(false);
			butt.setFocusPainted(false);
			butt.setOpaque(false);

			butt.addActionListener(e -> {
			    JFrame fr = new JFrame(s.getName());
			    fr.setSize(500, 300);
			    fr.setLocationRelativeTo(null);

			    JTextArea text = new JTextArea(s.toString());
			    text.setEditable(false);
			    text.setLineWrap(true);


			    fr.add(text);
			    fr.setVisible(true);
			});

			buttons.add(butt);
		}
		System.out.println("# of buttons: " + buttons.size());
	}

	@Override
	public void paintComponent(Graphics g) {
		if (map != null) {
            g.drawImage(map, 0, 0, getWidth(), getHeight(), this);
        }

        // g.setColor(Color.RED);
        // for (Point p : points) {
        //     g.fillOval(p.getX() - 3, p.getY() - 3, 6, 6); //  red dot
        // }
	}


	public static void main(String[] args) {
		Injest inj = new Injest();
		inj.injestCoords();
		JFrame frame = new JFrame("World Map");
		WorldMap panel = new WorldMap(inj);
		frame.setLayout(null);
		frame.add(panel);
		frame.setSize(panel.getWidth(), panel.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //add buttons one by one
        for (JButton b: panel.buttons) {
        	panel.add(b);
        }
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

	}
}
