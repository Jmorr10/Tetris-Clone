import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {
	
	public Board() {
		setBounds(0,0,350,600);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Rectangle boardContainer = new Rectangle(0,0,350,600);
		Rectangle boardCutOut = new Rectangle(50,50,250,500);
		Area full = new Area(boardContainer);
		Area cutOut = new Area(boardCutOut);
		full.subtract(cutOut);
		Rectangle block = new Rectangle();
		block.x = 0;
		block.y = 0;
		block.height = 50;
		block.width = 50;
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

		
	}
	
	
	
	
	
	
}
