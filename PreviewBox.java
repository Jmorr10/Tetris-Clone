import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PreviewBox extends JPanel {

	pieceTypes display;
	Pieces dBlock = new Pieces(pieceTypes.None, new Point2D.Double(75,25), 1);

	public PreviewBox(pieceTypes display) {
		this.display = display;
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Rectangle boardContainer = new Rectangle(0,0,150,150);
		Rectangle boardCutOut = new Rectangle(25,25,100,100);
		Area full = new Area(boardContainer);
		Area cutOut = new Area(boardCutOut);
		full.subtract(cutOut);
		Rectangle2D block = new Rectangle();
		block.setRect(0, 0, 25, 25);
		BufferedImage img;
		try {
			img = ImageIO.read(getClass().getResource("border.png"));
			TexturePaint tex = new TexturePaint(img, block);
			g2d.setPaint(tex);
			g2d.fill(full);
			g2d.setColor(Color.black);
			g2d.setPaint(null);
			g2d.fill(cutOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		BufferedImage img2;
			
		try {
			img2 = ImageIO.read(getClass().getResource("border.png"));	
			switch (display) {
			
			case Line:
				img2 = ImageIO.read(getClass().getResource("cyan.png"));
				dBlock = new Pieces(display, new Point2D.Double(62.5,25), 1);
				block.setRect(62.5,25,25,25);
				break;
			case Square:
				img2 = ImageIO.read(getClass().getResource("yellow.png"));
				dBlock = new Pieces(display, new Point2D.Double(50,50), 1);
				break;
			case L:
				img2 = ImageIO.read(getClass().getResource("orange.png"));
				dBlock = new Pieces(display, new Point2D.Double(50,37.5), 1);
				block.setRect(50,37.5,25,25);
				break;
			case RL:
				img2 = ImageIO.read(getClass().getResource("blue.png"));
				dBlock = new Pieces(display, new Point2D.Double(75,37.5), 1);
				block.setRect(50,37.5,25,25);
				break;
			case S:
				img2 = ImageIO.read(getClass().getResource("red.png"));
				dBlock = new Pieces(display, new Point2D.Double(75,37.5), 1);
				block.setRect(75,37.5,25,25);
				break;
			case Z:
				img2 = ImageIO.read(getClass().getResource("green.png"));
				dBlock = new Pieces(display, new Point2D.Double(50,37.5), 1);
				block.setRect(50,37.5,25,25);
				break;
			case T:
				img2 = ImageIO.read(getClass().getResource("purple.png"));
				dBlock = new Pieces(display, new Point2D.Double(62.5,50), 1);
				block.setRect(62.5,50,25,25);
				break;
			}
			dBlock.midAreas.clear();
			dBlock.setFormPoints();

			TexturePaint tex = new TexturePaint(img2, block);
			g2d.setPaint(tex);
			for (Area a : dBlock.midAreas) {
				g2d.fill(new Rectangle2D.Double(a.getBounds2D().getX()-4.0,a.getBounds2D().getY()-4.0, 25.0,25.0));
			}
		} catch (Exception e) {
				e.printStackTrace();
		}	
			
	}
		
}

