import java.util.ArrayList;


/**
 * The bottom left of the map is Coordinate(0, 0)
 * */
public class Coordinate {
	private int x;
	private int y;
	private int g;
	private int f;
	
	public Coordinate() {
		x = 0;
		y = 0;
		g = 0;
		f = 0;
	}
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		g = 0;
		f = 0;
	}
	
	public Coordinate(int x, int y, int g, int f) {
		this.x = x;
		this.y = y;
		this.g = g;
		this.f = f;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public int getF() {
		return f;
	}
	
	public void setF(int f) {
		this.f = f;
	}
	
	public void goFront(int currentDirection) {
		switch(currentDirection) {
			case Direction.NORTH:
				y++;
				break;
				
			case Direction.EAST:
				x++;
				break;
			
			case Direction.SOUTH:
				y--;
				break;
				
			case Direction.WEST:
				x--;
				break;
		}
	}
	
	public ArrayList<Coordinate> neighbors() {
		ArrayList<Coordinate> neighborList = new ArrayList<Coordinate>();
		
		if(x != 0) {
			neighborList.add(new Coordinate(x - 1, y));
		}
		
		if(x != 3) {
			neighborList.add(new Coordinate(x + 1, y));
		}
		
		if(y != 0) {
			neighborList.add(new Coordinate(x, y - 1));
		}
		
		if(y != 10) {
			neighborList.add(new Coordinate(x, y + 1));
		}
		
		return neighborList;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
