import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")

public class PieceContainer extends JPanel {
	
	
	static ArrayList<Pieces> pieceArray = new ArrayList<Pieces>();
	static Pieces currentBlock = new Pieces(pieceTypes.None, new Point2D.Double(125,0), 1);
	
	ArrayList<Point2D> bPoints = new ArrayList<Point2D>();
	Rectangle baseBlock = new Rectangle(0,0,25,25);
	static ArrayList<Area> board = new ArrayList<Area>();
	static ArrayList<Boolean> boardSwitches = new ArrayList<Boolean>();
	static int linesCleared = 0;
	int multipleLines = 0;

	
	
	public PieceContainer() {
		
		setPreferredSize(new Dimension(250,500));
		setLayout(null);
		setBounds(50,50,250,500);
		setOpaque(false);
		for (int y = 0; y <= 475; y +=25) {
			for (int x = 0; x <= 225; x +=25) {
				board.add(new Area(new Rectangle(x,y, 25,25)));
			}
		}
		
		setupBoardPhysics();
	}
	

	public void paintComponent(final Graphics g) {
		
        super.paintComponent(g);
        Color alpha = new Color(0, 0, 0, 70); //r,g,b,alpha
        g.setColor(alpha);
        g.fillRect(0,0,250,500);
        
        
        	
 	 if (!Tetris.playing) {
	  	g.setFont(new Font("Impact", Font.BOLD, 30)); 
	  	g.setColor(Color.red);
	  	g.drawString("ENTER = START", 25,100);
 	 }
 	        
        for (Pieces x: pieceArray) {
            	drawBlock(g,x.bPos.getX(), x.bPos.getY(), x.rPos, x, false, true);        
        }
        
        if (!(currentBlock == null)) {
            	drawBlock(g,currentBlock.bPos.getX(), currentBlock.bPos.getY(), currentBlock.rPos, currentBlock, false, false);
               }
            
            refreshBoardPhysics();
        }
	
	
	public void drawBlock(Graphics g, double x, double y, int rPos, Pieces block, Boolean newBlock, Boolean inPArray) {

		if (detectBoardPhysics() && currentBlock.bPos.getX() == 125 && currentBlock.bPos.getY() == 0) {
			Tetris.gameOver();
			return;
		}
		
		baseBlock.x = (int) x;
		baseBlock.y = (int) y;

		Graphics2D g2d = (Graphics2D)g;
		
		if (newBlock) {
			Pieces newB = new Pieces(pieceTypes.Line, new Point(125,0), 1);
			block = newB;
			currentBlock = newB;
		}
		
		currentBlock.midAreas.clear();
		currentBlock.setFormPoints();
		
		BufferedImage img2;
		
		
		try {
			img2 = ImageIO.read(getClass().getResource("border.png"));
			switch (block.pieceType) {
			
			case Line:
				img2 = ImageIO.read(getClass().getResource("cyan.png"));
				break;
			case Square:
				img2 = ImageIO.read(getClass().getResource("yellow.png"));
				break;
			case L:
				img2 = ImageIO.read(getClass().getResource("orange.png"));
				break;
			case RL:
				img2 = ImageIO.read(getClass().getResource("blue.png"));
				break;
			case S:
				img2 = ImageIO.read(getClass().getResource("red.png"));
				break;
			case Z:
				img2 = ImageIO.read(getClass().getResource("green.png"));
				break;
			case T:
				img2 = ImageIO.read(getClass().getResource("purple.png"));
				break;
			}
			TexturePaint tex = new TexturePaint(img2, baseBlock);
			g2d.setPaint(tex);
			
			if (!(block.pieceType == pieceTypes.Square)) {
				if (rPos == 2) {
					AffineTransform at = new AffineTransform();
					if (!inPArray) {
						at = AffineTransform.getRotateInstance(Math.toRadians(90), currentBlock.midAreas.get(1).getBounds2D().getCenterX(),currentBlock.midAreas.get(1).getBounds2D().getCenterY());
						for (Area a : currentBlock.midAreas) {
							a.transform(at);
						}
					}
				}
				if (rPos == 3) {
					AffineTransform at = new AffineTransform();
					if (!inPArray) {
						at = AffineTransform.getRotateInstance(Math.toRadians(180), currentBlock.midAreas.get(1).getBounds2D().getCenterX(),currentBlock.midAreas.get(1).getBounds2D().getCenterY());
						for (Area a : currentBlock.midAreas) {
							a.transform(at);
						}
					}
				}
				if (rPos == 4) {
					AffineTransform at = new AffineTransform();
					if (!inPArray) {
						at = AffineTransform.getRotateInstance(Math.toRadians(270), currentBlock.midAreas.get(1).getBounds2D().getCenterX(),currentBlock.midAreas.get(1).getBounds2D().getCenterY());
						for (Area a : currentBlock.midAreas) {
							a.transform(at);
						}
					}
				}	
			}
			
			if (inPArray) {
				for (Area a : block.midAreas) {
					g2d.fill(new Rectangle2D.Double(a.getBounds2D().getX()-4.0,a.getBounds2D().getY()-4.0, 25.0,25.0));
				}
			} else {
				for (Area a : currentBlock.midAreas) {
					g2d.fill(new Rectangle2D.Double(a.getBounds2D().getX()-4.0,a.getBounds2D().getY()-4.0, 25.0,25.0));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void moveBlockLeft() {
				
		AffineTransform at = new AffineTransform();
		at = AffineTransform.getTranslateInstance(-25, 0);
		
		for (Area a : currentBlock.midAreas) {
			a.transform(at);
		}
		
		if (detectBoardPhysics()) {
			
		    at = new AffineTransform();
		    at = AffineTransform.getTranslateInstance(-25, 0);
			for (Area a : currentBlock.midAreas) {
				a.transform(at);
			}
			this.repaint();
			return;
			
		}
		
		double minX = currentBlock.midAreas.get(0).getBounds2D().getCenterX();
		for (Area a : currentBlock.midAreas) {
			
			if (a.getBounds2D().getCenterX() < minX) {
				 minX = a.getBounds2D().getCenterX();
			}
			
		}		
		Boolean bHit = false;
		if (minX < 0) {
			bHit = true;
		}
		if (bHit) {
			
			at = new AffineTransform();
			at = AffineTransform.getTranslateInstance(25, 0);
			for (Area a : currentBlock.midAreas) {
				a.transform(at);
			}
			
		} else {
			currentBlock.bPos.setLocation(currentBlock.bPos.getX()-25.0, currentBlock.bPos.getY());
		}
		this.repaint();
			
	}
			
	public void moveBlockRight() {
							
			AffineTransform at = new AffineTransform();
			at = AffineTransform.getTranslateInstance(25, 0);
				
			for (Area a : currentBlock.midAreas) {
				a.transform(at);
			}
			
			if (detectBoardPhysics()) {
				
			    at = new AffineTransform();
			    at = AffineTransform.getTranslateInstance(-25, 0);
				for (Area a : currentBlock.midAreas) {
					a.transform(at);
				}
				this.repaint();
				return;
				
			}
			
			
			double maxX = currentBlock.midAreas.get(3).getBounds2D().getCenterX();
			for (Area a : currentBlock.midAreas) {
				
				if (a.getBounds2D().getCenterX() > maxX) {
					 maxX = a.getBounds2D().getCenterX();
				}
				
			}		
			Boolean bHit = false;
			if (maxX > 250) {
				bHit = true;
			}
			if (bHit) {
				
			    at = new AffineTransform();
			    at = AffineTransform.getTranslateInstance(-25, 0);
				for (Area a : currentBlock.midAreas) {
					a.transform(at);
				}
			   
			} else {
				currentBlock.bPos.setLocation(currentBlock.bPos.getX()+25.0, currentBlock.bPos.getY());
			   }
			this.repaint();
				
	}	

	public Boolean moveBlockDown() {
		
		if (!(currentBlock.pieceType == pieceTypes.None)) {
				
			AffineTransform at = new AffineTransform();
			at = AffineTransform.getTranslateInstance(0, 25);
			
			for (Area a : currentBlock.midAreas) {
				a.transform(at);
			}
			
			if (detectBoardPhysics()) {
				at = AffineTransform.getTranslateInstance(0, -25);
				
				for (Area a : currentBlock.midAreas) {
					a.transform(at);
				}
				Tetris.pieceFalling = false;
				pieceArray.add(currentBlock);
				refreshBoardPhysics();
				currentBlock = new Pieces(pieceTypes.None, new Point2D.Double(125,0), 1);
				refreshBoardPhysics();
				lineClearCheck(0);
				this.repaint();
				return true;
			}
			
			
			Boolean bHit = false;
			double maxY = currentBlock.midAreas.get(0).getBounds2D().getCenterY();
			for (Area a : currentBlock.midAreas) {
				
				if (a.getBounds2D().getCenterY() > maxY) {
					 maxY = a.getBounds2D().getCenterY();
				}
				
			}
			if (maxY > 500) {
				bHit = true;
			} 
			if (bHit) {
				
				at = new AffineTransform();
				at = AffineTransform.getTranslateInstance(0, -25);
				for (Area a : currentBlock.midAreas) {
					a.transform(at);
				}
				Tetris.pieceFalling = false;
				pieceArray.add(currentBlock);
				currentBlock = new Pieces(pieceTypes.None, new Point2D.Double(125.0, 0), 1);
				refreshBoardPhysics();
				lineClearCheck(0);
				this.repaint();
				return true;
			} else {
				currentBlock.bPos.setLocation(currentBlock.bPos.getX(), currentBlock.bPos.getY()+25.0);
				
			}
			this.repaint();
		}
		return false;		
	}
	
	public void dropBlock() {
		
		while(!moveBlockDown()) {
		}	
	}
	
	public Boolean rotateBlock() {
		
		if (currentBlock.rPos == 4) {
			if (!canRotate()) {
			
			return false;
			}
			currentBlock.rPos = 1;
			
			this.repaint();
			return true;
		} else {
			if (!canRotate()) {
				
				return false;
			}
			currentBlock.rPos++;
			
			this.repaint();
			return true;
		}
	}
	
	public Boolean canRotate() {
		
		
			Boolean allowed = true;
			
			AffineTransform at = new AffineTransform();
			
			at = AffineTransform.getRotateInstance(Math.toRadians(-90), currentBlock.bPos.getX()+12.5, currentBlock.bPos.getY()+37.5);
			for (Area a :currentBlock.midAreas) {
				a.transform(at);
			}

			double maxX = currentBlock.midAreas.get(3).getBounds2D().getCenterX();
			for (Area a : currentBlock.midAreas) {
				
				if (a.getBounds2D().getCenterX() > maxX) {
					 maxX = a.getBounds2D().getCenterX();
				}
				
			}	
			double minX = currentBlock.midAreas.get(0).getBounds2D().getCenterX();
			for (Area a : currentBlock.midAreas) {
				
				if (a.getBounds2D().getCenterX() < minX) {
					 minX = a.getBounds2D().getCenterX();
				}
				
			}	
			double maxY = currentBlock.midAreas.get(0).getBounds2D().getCenterY();
			for (Area a : currentBlock.midAreas) {
				
				if (a.getBounds2D().getCenterY() > maxY) {
					 maxY = a.getBounds2D().getCenterY();
				}
				
			}
			double minY = currentBlock.midAreas.get(0).getBounds2D().getCenterY();
			for (Area a : currentBlock.midAreas) {
				
				if (a.getBounds2D().getCenterY() < minY) {
					 minY = a.getBounds2D().getCenterY();
				}
				
			}
			
			if (maxX > 250 || minX < 0 || maxY > 500 || minY < 0) {
				allowed = false;
			}	
			
			at = AffineTransform.getRotateInstance(Math.toRadians(90), currentBlock.bPos.getX()+12.5, currentBlock.bPos.getY()+37.5);
			for (Area a :currentBlock.midAreas) {
				a.transform(at);
			}
			
		return allowed;
	}
	
//	public Boolean CollisionDetectionDown() {
//		
//		ArrayList<Point2D> ignore = new ArrayList<Point2D>();
//		
//		for (Pieces x : pieceArray) {
//		
//			if (currentBlock.rPos == 2 || currentBlock.rPos == 4) {
//				ignore.addAll(currentBlock.hBoundaries);
//			}
//			
//			if (currentBlock.rPos == 1 || currentBlock.rPos == 3) {
//				ignore.addAll(currentBlock.vBoundaries);
//			}
//			
//			
//			for (Point2D y : x.bPoints) {
//				if (currentBlock.bPoints.contains(y) && (!ignore.contains(y))) {
//						return true;
//				}
//			}
//		}
//		return false;
//	}
//	
//public Boolean CollisionDetectionSide() {
//		
//		ArrayList<Point2D> ignore = new ArrayList<Point2D>();
//		
//		for (Pieces x : pieceArray) {
//		
//			if (currentBlock.rPos == 2 || currentBlock.rPos == 4) {
//				ignore.addAll(currentBlock.vBoundaries);
//			}
//			
//			if (currentBlock.rPos == 1 || currentBlock.rPos == 3) {
//				ignore.addAll(currentBlock.hBoundaries);
//			}
//			
//			
//			for (Point2D y : (x.rPos == 2 || x.rPos == 4)? x.vBoundaries : x.hBoundaries) {
//				if (currentBlock.bPoints.contains(y)) {
//						return true;
//				}
//			}
//		}
//		return false;
//	}

	@SuppressWarnings("unused")
	public void setupBoardPhysics() {
		boardSwitches.clear();
		for (Area a : board) {
				boardSwitches.add(false);
			}
	}
	
	
	
	
	public void refreshBoardPhysics() {
		
		boardSwitches.clear();
		
		for (Area a : board) {
			Boolean contained = false;
			
			for (Pieces p : pieceArray) {
				for (Area mid : p.midAreas) {
					
					if (a.contains(mid.getBounds2D())) {
						contained = true;
					}
					
				}
			}
			
			if (contained) {
				boardSwitches.add(true);
			} else {
				boardSwitches.add(false);
			}
		}
	}

	public Boolean detectBoardPhysics() {
		int count = 0;
		
		for (Area a : board) {
			for (Area mid : currentBlock.midAreas) {
				if (a.contains(mid.getBounds2D()) && boardSwitches.get(count)) {
					
					return true;
					
				}
				
			}
			count++;
		}
		
		return false;
		
	}
	
	public void lineClearCheck(int multiLines) {
		
		for (int i = 0; i <= 19; i++) {
			int count = 0;
			for (int j = 0 + (10*i); j <= (10*i)+9; j++) {
//				
				if (boardSwitches.get(j)) count++;
			}
			if (count == 10) {
				clearLine(i, multiLines);
				return;
			} 
			
		}
		
	}
	
	public void clearLine(int line, int multiLines) {
		linesCleared++;
		InfoPanel.lines.setText("Lines cleared: " + PieceContainer.linesCleared);
		multiLines++;
		ArrayList<Pieces> removePieces = new ArrayList<Pieces>();
		for (Pieces x : pieceArray) {
			ArrayList<Area> removeAreas = new ArrayList<Area>();
			for (Area a : x.midAreas) {
				for (int i = 0 + (10*line); i<= (10*line)+9; i++) {
					if (board.get(i).contains(a.getBounds2D())) {
						removeAreas.add(a);
					}
				}
				
			}
			if (removeAreas.size() == 4) {
				removePieces.add(x);
			} else {
				x.midAreas.removeAll(removeAreas);
			}
			
		}
		pieceArray.removeAll(removePieces);
		
		AffineTransform at = new AffineTransform();
		at = AffineTransform.getTranslateInstance(0, 25);
		for (Pieces x : pieceArray) {
			for (Area a : x.midAreas) {
				if (a.getBounds2D().getY() < board.get(10*line).getBounds2D().getY()) {
					a.transform(at);	
				}
			}
		}
		if (multiLines > 1) {
			if (multiLines >= 4) {
				Tetris.move = new Timer(Tetris.speed-75,Tetris.down);
				Tetris.move.start();
			}
			Tetris.userScore += (1200 * (multiLines * .5));
			InfoPanel.score.setText("Score: " + Tetris.userScore);
		} else {
		if (linesCleared >= 20) {
			Tetris.move = new Timer(Tetris.speed-50,Tetris.down);
			Tetris.move.start();
		}
		Tetris.userScore += 100;
		InfoPanel.score.setText("Score: " + Tetris.userScore);
		}
		refreshBoardPhysics();
		cleanPieceArray();
		lineClearCheck(multiLines);
		this.repaint();
		
		
	}
	
	public void cleanPieceArray() {
		ArrayList<Pieces> remove = new ArrayList<Pieces>();
		for (Pieces x : pieceArray) {
			if (x.midAreas.size() == 0) {
				remove.add(x);
			}
		}
		pieceArray.removeAll(remove);
	}
	
}
