import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Pieces {
	
	pieceTypes pieceType;
	ArrayList<Point2D> bPoints = new ArrayList<Point2D>();
	GeneralPath finalBlock = new GeneralPath();
	ArrayList<Area> midAreas = new ArrayList<Area>();
	Rectangle block = new Rectangle(0,0,25,25);
	Point2D bPos = new Point2D.Double();
	int rPos = 1; 
	
	public Pieces(pieceTypes type, Point2D position, int rotate)
	{
	
		this.pieceType = type;
		this.bPos = position;
		this.rPos = rotate;
		this.setFormPoints();
	}
	
	public void setFormPoints() {
		midAreas.clear();
	
		if (this.pieceType == pieceTypes.Line) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+block.height+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height*2)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height*3)+4,19,19)));
	
		}
		if (this.pieceType == pieceTypes.Square) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+block.width+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+block.width+4, bPos.getY()+(block.height)+4,19,19)));
	
		}
		
		if (this.pieceType == pieceTypes.L) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height*2)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+block.width+4, bPos.getY()+(block.height*2)+4,19,19)));
	
		}
		
		if (this.pieceType == pieceTypes.RL) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height*2)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()-block.width+4, bPos.getY()+(block.height*2)+4,19,19)));
	
		}
		
		if (this.pieceType == pieceTypes.S) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()-block.width+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()-block.width+4, bPos.getY()+(block.height*2)+4,19,19)));
	
		}
		
		if (this.pieceType == pieceTypes.Z) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+block.width+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+block.width+4, bPos.getY()+(block.height*2)+4,19,19)));
	
		}
		
		if (this.pieceType == pieceTypes.T) {
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()-block.width+4, bPos.getY()+(block.height)+4,19,19)));
			midAreas.add(new Area(new Rectangle2D.Double(bPos.getX()+block.width+4, bPos.getY()+(block.height)+4,19,19)));
	
		}	
	}

}
