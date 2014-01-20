
public final class Direction {
	private static final int LOST = 0;
	public static final int NORTH = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 4;
	public static final int EAST = 8;
	
	public static int turnLeft(int direction) {
		switch(direction) {
			case NORTH:
				return WEST;
				
			case WEST:
				return SOUTH;
				
			case SOUTH:
				return EAST;
			
			case EAST:
				return NORTH;
				
			default:
				return LOST;
		}
	}
	
	public static int turnRight(int direction) {
		switch(direction) {
			case NORTH:
				return EAST;
			
			case EAST:
				return SOUTH;
				
			case SOUTH:
				return WEST;
				
			case WEST:
				return NORTH;
				
			default:
				return LOST;
		}
	}
	
	public static int turnBack(int direction) {
		switch(direction) {
			case NORTH:
				return SOUTH;
			
			case SOUTH:
				return NORTH;
				
			case WEST:
				return EAST;
				
			case EAST:
				return WEST;
				
			default:
				return LOST;
		}
	}
	
	public static int toDirection(Coordinate source, Coordinate destination) {
		if(destination.getX() < source.getX()) {
			return Direction.WEST;
		} else if(destination.getX() > source.getX()) {
			return Direction.EAST; 
		} else if(destination.getY() < source.getY()) {
			return Direction.SOUTH; 
		} else if(destination.getY() > source.getY()) {
			return Direction.NORTH; 
		} else {
			return Direction.LOST;
		}
	}
	
	public static String toString(int direction) {
		switch(direction) {
			case NORTH:
				return "NORTH";
				
			case WEST:
				return "WEST";
				
			case SOUTH:
				return "SOUTH";
			
			case EAST:
				return "EAST";
				
			default:
				return "LOST";
		}
	}
}
