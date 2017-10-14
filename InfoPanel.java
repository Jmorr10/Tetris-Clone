import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	static JLabel score = new JLabel();
	static JLabel lines = new JLabel();
	static PreviewBox preview = new PreviewBox(pieceTypes.None);
	
	public InfoPanel() {
		
		setPreferredSize(new Dimension(200,600));
		setLayout(null);
		setBounds(375,0,200,600);
		TitledBorder tb;
		Border line = BorderFactory.createLoweredBevelBorder();
		tb = BorderFactory.createTitledBorder(line, "TETRIS");
		tb.setTitleFont(new Font("Impact", Font.BOLD, 30));
		tb.setTitleColor(Color.white);
		tb.setTitleJustification(TitledBorder.CENTER);
		setBorder(tb);
		setBackground(Color.black);
		
		BufferedImage arrow;
		try {
			arrow = ImageIO.read(getClass().getResource("arrow.png"));
			ImageIcon up = new ImageIcon(arrow);
			JLabel upKey = new JLabel(up);
			upKey.setFont(new Font("Impact", Font.BOLD, 15));
			upKey.setForeground(Color.white);
			upKey.setText("Rotate");
			upKey.setHorizontalTextPosition(JLabel.CENTER);
			upKey.setVerticalTextPosition(JLabel.TOP);
			
			AffineTransform at = new AffineTransform();
			at = AffineTransform.getRotateInstance(Math.toRadians(-90), up.getIconWidth()/2.0, up.getIconHeight()/2.0);
			AffineTransformOp ao = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			BufferedImage arrow2 = ao.filter(arrow, null);
			ImageIcon left = new ImageIcon(arrow2);
			JLabel leftKey = new JLabel(left);
			leftKey.setFont(new Font("Impact", Font.BOLD, 15));
			leftKey.setForeground(Color.white);
			leftKey.setText("Left");
			leftKey.setHorizontalTextPosition(JLabel.LEFT);
			leftKey.setVerticalTextPosition(JLabel.CENTER);
			
			at = AffineTransform.getRotateInstance(Math.toRadians(-180), up.getIconWidth()/2.0, up.getIconHeight()/2.0);
			ao = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			BufferedImage arrow3 = ao.filter(arrow, null);
			ImageIcon down = new ImageIcon(arrow3);
			JLabel downKey = new JLabel(down);
			downKey.setFont(new Font("Impact", Font.BOLD, 15));
			downKey.setForeground(Color.white);
			downKey.setText("Down");
			downKey.setHorizontalTextPosition(JLabel.CENTER);
			downKey.setVerticalTextPosition(JLabel.BOTTOM);
			
			at = AffineTransform.getRotateInstance(Math.toRadians(-270), up.getIconWidth()/2.0, up.getIconHeight()/2.0);
			ao = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			BufferedImage arrow4 = ao.filter(arrow, null);
			ImageIcon right = new ImageIcon(arrow4);
			JLabel rightKey = new JLabel(right);
			rightKey.setFont(new Font("Impact", Font.BOLD, 15));
			rightKey.setForeground(Color.white);
			rightKey.setText("Right");
			rightKey.setHorizontalTextPosition(JLabel.RIGHT);
			rightKey.setVerticalTextPosition(JLabel.CENTER);
			
			BufferedImage spacebar = ImageIO.read(getClass().getResource("spacebar.png"));
			ImageIcon space = new ImageIcon(spacebar);
			JLabel spaceKey = new JLabel(space);
			spaceKey.setFont(new Font("Impact", Font.BOLD, 15));
			spaceKey.setForeground(Color.white);
			spaceKey.setText("Drop Piece");
			spaceKey.setHorizontalTextPosition(JLabel.CENTER);
			spaceKey.setVerticalTextPosition(JLabel.BOTTOM);
			
			this.add(preview);
			
			upKey.setBounds(75,310, 50,50);
			this.add(upKey);
			leftKey.setBounds(0,350, 100,50);
			this.add(leftKey);
			downKey.setBounds(75,337,50,100);
			this.add(downKey);
			rightKey.setBounds(105,350,100,50);
			this.add(rightKey);
			spaceKey.setBounds(50,400,100,100);
			this.add(spaceKey);
			
			
			score = new JLabel("Score: " + Tetris.userScore);
			score.setFont(new Font("Impact", Font.BOLD, 20));
			score.setForeground(Color.white);
			lines = new JLabel("Lines cleared: " + PieceContainer.linesCleared);
			lines.setFont(new Font("Impact", Font.BOLD, 20));
			lines.setForeground(Color.white);
			score.setBounds(25,50,200,50);
			lines.setBounds(25,80,200,50);
			
			this.add(score);
			this.add(lines);
			
			BufferedImage z = ImageIO.read(getClass().getResource("z.png"));
			ImageIcon zcon = new ImageIcon(z);
			JLabel zKey = new JLabel(zcon);
			zKey.setFont(new Font("Impact", Font.BOLD, 15));
			zKey.setForeground(Color.white);
			zKey.setText("Change Music");
			zKey.setHorizontalTextPosition(JLabel.RIGHT);
			zKey.setVerticalTextPosition(JLabel.CENTER);
			
			zKey.setBounds(25,535, 150, 50);
			this.add(zKey);
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		preview.setBounds(25,140,150,150);
		preview.repaint();
		
	}
}
