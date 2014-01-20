
public class Story {
	
	private static Story story = null;
	private boolean bTruthUnfold = true;
	private int iPrincessDoorNumber = 1;
	private boolean bKingFound = false;
	private boolean bAtTheSkirtsOfTheMountain = false;
	private boolean bClimbing = false;
	private boolean bAtTheTopOfTheMountain = false;
	private boolean bPrincessDoorFound = false;
	private boolean bPrincessFound = false;
	private boolean bReadyToClimbDown = false;
	private boolean bClimbedDown = false;
	
	private Story() {}
	
	public static Story getStory() {
		if(story == null) {
			story = new Story();
		}
		
		return story;
	}
	
	public boolean isTruthUnfold() {
		return bTruthUnfold;
	}
	
	public void theTruthShallUnfold(int doorNumber) {
		bTruthUnfold = true;
		iPrincessDoorNumber = doorNumber;
	}
	
	public int princessDoorNumber() {
		return iPrincessDoorNumber;
	}
	
	public boolean isKingFound() {
		return bKingFound;
	}
	
	public void heilToTheKing() {
		bKingFound = true;
	}
	
	public boolean isAtTheSkirtsOfTheMountain() {
		return bAtTheSkirtsOfTheMountain;
	}
	
	public void reachedToTheSkirtsOfTheMountain() {
		bAtTheSkirtsOfTheMountain = true;
	}
	
	public boolean isClimbing() {
		return bClimbing;
	}
	
	public void startedToClimb() {
		bAtTheSkirtsOfTheMountain = false;
		bClimbing = true;
	}
	
	public boolean isAtTheTopOfTheMountain() {
		return bAtTheTopOfTheMountain;
	}
	
	public void reachedToTheTopOfTheMountain() {
		bClimbing = false;
		bAtTheTopOfTheMountain = true;
	}
	
	public boolean isPrincessDoorFound() {
		return bPrincessDoorFound;
	}
	
	public void knockKnock() {
		bPrincessDoorFound = true;
	}
	
	public boolean isPrincessFound() {
		return bPrincessFound;
	}
	
	public void happyEnding() {
		bPrincessFound = true;
	}
	
	public boolean isReadyToClimbDown() {
		return bReadyToClimbDown;
	}
	
	public void readyToClimbDown() {
		bReadyToClimbDown = true;
	}
	
	public boolean isClimbedDown() {
		return bClimbedDown;
	}
	
	public void climbedDown() {
		bReadyToClimbDown = false;
		bClimbedDown = true;
	}
}
