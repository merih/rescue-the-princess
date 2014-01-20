import java.util.ArrayList;

import lejos.nxt.LCD;

public class Village {

	private static Village village = null;
	
	static final char VILLAGER_DARK = 'D';
	static final char VILLAGER_LIGHT = 'L';
	static final char KING = 'K';
	static final char VISITED = 'V';
	static final char WALL = 'W';
	
	static final int POPULATION = 10;
	int darkVillagerCount = 0;
	int lightVillagerCount = 0;
	
	private int PRINCESS = 0;
	private Coordinate ironThrone;
	
	private char[][] villageMap;
	private int[][] walls;
	private int[][] visitDirections;
	
	private ArrayList<Coordinate> dungeonPath;
	
	Story story;
	Prince prince;
	
	private Village() {
		villageMap = new char[4][11];
		walls = new int[4][11];
		visitDirections = new int[4][11];
		
		dungeonPath = new ArrayList<Coordinate>();
		
		prince = Prince.getPrince();
		story = Story.getStory();
	}
	
	public static Village getVillage() {
		if(village == null) {
			village = new Village();
		}
		
		return village;
	}
	
	public ArrayList<Coordinate> getReturnPath() {
		return dungeonPath;
	}
	
	public void thereIsAWallHere() {
		walls[prince.getX()][prince.getY()] |= prince.getCurrentDirection();		
	}
	
	public boolean isThereAWall(int x, int y, int direction) {
		return (walls[x][y] & direction) != 0;
	}
	
	// returns if the pricess talked with the villager previously or not
	public boolean talkWithVillager(int villagerColor) {
		int x = prince.getX();
		int y = prince.getY();
		
		int maxX = prince.getMaxX();
		int maxY = prince.getMaxY();
		
		char personType = getPersonType(villagerColor);
		
		if(x > 3) {
			// prince did not started with direction = NORTH
			// this may happen only once
			// rotate 90deg
			char[][] tempMap = new char[4][11];
			for(int i = 0; i <= maxX; i++) {
				for(int j = 0; j <= maxY; j++) {
					tempMap[maxY - j][i] = villageMap[i][j];
					
					 int rotatedVisits = visitDirections[i][j] << 1;
					 if(rotatedVisits > 15) {
						 rotatedVisits &= 15;
						 rotatedVisits |= 1;
					 }
					 visitDirections[maxY - j][i] = rotatedVisits;
					 
					 int rotatedWalls = walls[i][j] << 1;
					 if(rotatedWalls > 15) {
						 rotatedWalls &= 15;
						 rotatedWalls |= 1;
					 }
					 walls[maxY - j][i] = rotatedWalls;
				}
			}
			
			prince.rotateMapLeft();
			maxX = prince.getMaxX();
			maxY = prince.getMaxY();
			
			for(int i = 0; i <= maxX; i++) {
				for(int j = 0; j <= maxY; j++) {
					villageMap[i][j] = tempMap[i][j];
				}
			}
		} else if(x < 0 && maxX == 3) {
			// prince did not started with direction = NORTH
			// this may happen only once
			char[][] tempMap = new char[4][11];
			for(int i = 0; i <= maxX; i++) {
				for(int j = 0; j <= maxY; j++) {
					tempMap[j][maxX - i] = villageMap[i][j];
					
					 boolean northVisited = (visitDirections[i][j] & Direction.NORTH) != 0;
					 int rotatedVisits = visitDirections[i][j] >> 1;
					 if(northVisited) {
						 rotatedVisits |= Direction.WEST;
					 }
					 visitDirections[j][maxX - i] = rotatedVisits;
					 
					 boolean theWall = (walls[i][j] & Direction.NORTH) != 0;
					 int rotatedWalls = walls[i][j] >> 1;
					 if(theWall) {
						 rotatedWalls |= Direction.WEST;
					 }
					 walls[j][maxX - i] = rotatedWalls;
				}
			}
			
			prince.rotateMapRight();
			maxX = prince.getMaxX();
			maxY = prince.getMaxY();
			
			for(int i = 0; i <= maxX; i++) {
				for(int j = 0; j <= maxY; j++) {
					villageMap[i][j] = tempMap[i][j];
				}
			}
		} else if(x < 0) {
			// prince did not started at x = 0
			// this may happen many times
			for(int i = maxX; i >= 0; i--) {
				for(int j = 0; j <= maxY; j++) {
					villageMap[i + 1][j] = villageMap[i][j];
				}
			}
			
			prince.panMapRight();
			
		} else if(y < 0 && maxX > 0) {
			// prince may have started with direction = SOUTH
			// if not first condition will correct this later
			char[][] tempMap = new char[4][11];
			for(int i = 0; i <= maxX; i++) {
				for(int j = 0; j <= maxY; j++) {
					tempMap[maxY - j][i] = villageMap[i][j];
					
					 int rotatedVisits = visitDirections[i][j] << 1;
					 if(rotatedVisits > 15) {
						 rotatedVisits &= 15;
						 rotatedVisits |= 1;
					 }
					 visitDirections[maxY - j][i] = rotatedVisits;
					 
					 int rotatedWalls = walls[i][j] << 1;
					 if(rotatedWalls > 15) {
						 rotatedWalls &= 15;
						 rotatedWalls |= 1;
					 }
					 walls[maxY - j][i] = rotatedWalls;
				}
			}
			
			prince.rotateMapLeft();
			maxX = prince.getMaxX();
			maxY = prince.getMaxY();
			
			for(int i = 0; i <= maxX; i++) {
				for(int j = 0; j <= maxY; j++) {
					villageMap[i][j] = tempMap[i][j];
				}
			}
		}
		
		x = prince.getX();
		y = prince.getY();
		int direction = prince.getCurrentDirection();		
		int previouslyVisitedDirections = visitDirections[x][y];
		
		// do not talk again!
		if(villageMap[x][y] == 0 || (previouslyVisitedDirections & direction) == 0) {
			if(personType == VILLAGER_DARK) {
				darkVillagerCount++;
			}
			else if(personType == VILLAGER_LIGHT) {
				lightVillagerCount++;
			}
			
			LCD.clear();
			LCD.drawString("Location: " + prince.getCurrentLocation(), 0, 0);
			LCD.drawString("Direction: " + Direction.toString(direction), 0, 1);
			LCD.drawString("Dark Count: " + Integer.toString(darkVillagerCount), 0, 2);
			LCD.drawString("Light Count: " + Integer.toString(lightVillagerCount), 0, 3);
	
			// update map
			villageMap[x][y] = personType;
			
			if(personType == 'K') {
				story.heilToTheKing();
				ironThrone = prince.getCurrentLocation();
			}
			
			// update visited directions
			visitDirections[x][y] |=  direction;
			
			if(lightVillagerCount > POPULATION / 10 * 4) {
				PRINCESS = VILLAGER_LIGHT;
				story.theTruthShallUnfold(1);
			} else if(darkVillagerCount > POPULATION / 10 * 4) {
				PRINCESS = VILLAGER_DARK;
				story.theTruthShallUnfold(2);
			}
			
			return false;
		} else {
			return true;
		}
	}
	
	public Coordinate westeros() {
		return ironThrone;
	}
	
	public char villagerType(int x, int y) {
		return villageMap[x][y];
	}
	
	public boolean searchPrincess(int princessColor) {
		dungeonPath.add(0, prince.getCurrentLocation());
		
		if(princessColor < 50) {
			return true;
		} else {
			return false;
		}
	}
	
	public char getPersonType(int villagerColor) {
		if(villagerColor > 28 && villagerColor < 32) {
			// black
			return VILLAGER_DARK;
		} else if(villagerColor > 42 && villagerColor < 46) {
			// blue
			return VILLAGER_LIGHT;
		} else if(villagerColor > 48 && villagerColor < 52) {
			// pink
			return KING;
		} else {
			// white
			return VISITED;
		}
	}
	
	public boolean isPrincessHere(int doorColor) {
		return doorColor == PRINCESS;
	}
}
