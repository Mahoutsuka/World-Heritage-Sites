package heritage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

/**
 * Class for creating rendering the sphere and tracking the uer's mouse movements
 * Credit goes to Blayne and Francis for their implemention of conway of life
 */

public class SphereRender extends JPanel implements MouseListener, MouseMotionListener{
	public Sphere sphere;
	public Graphics2D g2;
	public HashMap<Coord, Site> sites;
    public double angleX = 0; //stores the cumulative mouse drag in x, in order to preserve the original x,y,z points of both square and heritage sites
    public double angleY = 0; //stores the cumulative mouse drag in y for similar reasons
    public boolean dragging = false;
    public int prevX;
    public int prevY;
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    public int w;
    public int h;
    private JLabel siteLabel;



    
	public SphereRender(Sphere sphere){
		this.sphere = sphere;
		addMouseListener(this);
		addMouseMotionListener(this);
		siteLabel = new JLabel("Click on a site!");
		siteLabel.setForeground(Color.WHITE); // Make text visible on black background
        siteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.setLayout(new BorderLayout());
		this.add(siteLabel, BorderLayout.SOUTH);
	}


	@Override public void mousePressed (MouseEvent e){
		dragging = true;
		prevX = e.getX();
		prevY = e.getY();
	}

	@Override public void mouseReleased(MouseEvent e){
		dragging = false;
	}

	//checks to see if mouse clicked on a heritage site
	@Override public void mouseClicked(MouseEvent e) {
		// sphere.updateSquare(e.getPoint());
		sphere.updatePoint(e.getPoint());
		repaint();
	}

	//as the user drags their mouse, rotate the sphere based on the direction they dragged in
	@Override public void mouseDragged (MouseEvent e){
		int newX = e.getX();
		int newY = e.getY();
		int deltaX = newX - prevX;
		int deltaY = newY - prevY;
		angleX += deltaY * 0.001;
		angleY += deltaX * 0.001;
		prevX = e.getX();
		prevY = e.getY();
		sphere.rotate(angleX,angleY, w, h);
		repaint();
	}

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}


    //renders the sphere
	public void paint (Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		this.g2 = g2;
		g2.setColor(Color.BLACK); 
        g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(1));
		w = getWidth()/2;
		h = getHeight()/2;
		sphere.drawSphere(g2,w,h);
		sphere.drawCoordPoints(g2,w,h);
	}

	public static void main(String[] args){
		JFrame frame = new JFrame("Sphere");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		Injest inj = new Injest();
		inj.injestCoords();

		Sphere sp = new Sphere(inj.getUnesco());
		sp.initializeSphere();
		SphereRender spRender= new SphereRender(sp);

		frame.add(spRender);
		frame.setVisible(true);

	}
}