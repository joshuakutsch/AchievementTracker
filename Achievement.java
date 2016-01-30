public class Achievement {
	//List of an achievement's private variables
	private int gameID;
	private int achieveID;
	private String achieveName;
	private int achievePoints;
	private boolean playerCompleted;
	
	public Achievement(int tempGameID, int tempAchieveID, String tempAchieveName, int tempAchievePoints) { //Constructor
		this.gameID = tempGameID;
		this.achieveID = tempAchieveID;
		this.achieveName = tempAchieveName;
		this.achievePoints = tempAchievePoints;
		this.playerCompleted = false;
	}
		
	public void completedAchieve() { //Sets the achievement as completed
		playerCompleted = true;
	}
	//Many get statements for accessing the achievement's private variables
	public int getAchieveID() {
		return achieveID;
	}
	
	public String getAchieveName() {
		return achieveName;
	}
	
	public int getPoints() {
		return achievePoints;
	}
	
	public boolean getStatus() {
		return playerCompleted;
	}
}