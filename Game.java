import java.util.*;
public class Game {
	//List of a game's private variables
	private int gameID;
	private String gameName;
	private int numAchievements;
	private int numAchievementsCompleted;
	private int gamePoints;
	HashMap<Integer, Achievement> achievements = new HashMap<Integer, Achievement>();
	
	public Game(int tempGameID, String tempGameName) { //Constructor
		this.gameID = tempGameID;
		this.gameName = tempGameName;
		this.numAchievements = 0;
		this.numAchievementsCompleted = 0;
		this.gamePoints = 0;
	}
	
	public Game(Game tempGame) { //Constructor using an existing game
		this.gameID = tempGame.gameID;
		this.gameName = tempGame.gameName;
		this.gamePoints = tempGame.gamePoints;
		this.numAchievements = tempGame.numAchievements;
		this.numAchievementsCompleted = tempGame.numAchievementsCompleted;
		this.achievements = tempGame.achievements;
	}
	
	public void addAchievement(Achievement tempAchievement) { //Adds an achievement to the game's achievement DataBase
		numAchievements++;
		achievements.put(tempAchievement.getAchieveID(), tempAchievement);
	}
	
	public void achieve(int tempAchieveID) { //Adds points and info for the achievement as well as sets the achievement as completed
		numAchievementsCompleted++;
		gamePoints += achievements.get(tempAchieveID).getPoints();
		achievements.get(tempAchieveID).completedAchieve();
	}
	//Many get statements for accessing the game's private variables
	public String getGameName() {
		return gameName;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public int getGamePoints() {
		return gamePoints;
	}
	
	public int getNumAchievements() {
		return numAchievements;
	}
	
	public int getCompletedAchievements() {
		return numAchievementsCompleted;
	}
	
	public HashMap<Integer, Achievement> getAchievements() {
		return achievements;
	}
}