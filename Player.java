import java.util.*;

public class Player {
	//List of a player's private variables
	private int playerID;
	private String playerName;
	private int totalPoints;
	private ArrayList<String> gameNameList = new ArrayList<String>();
	private ArrayList<String> friendsList = new ArrayList<String>();
	private ArrayList<String> inGameNameList = new ArrayList<String>();
	private ArrayList<Integer> achieveNumList = new ArrayList<Integer>();
	private ArrayList<Integer> friendScores = new ArrayList<Integer>();
	private ArrayList<Integer> completedAchieveList = new ArrayList<Integer>();
	private ArrayList<Integer> points = new ArrayList<Integer>();
	private HashMap<Integer, Player> friends = new HashMap<Integer, Player>();
	private HashMap<Integer, Game> playedGames = new HashMap<Integer, Game>();
	private HashMap<Integer, String> inGameNames = new HashMap<Integer, String>();

	public Player(int tempID, String tempName) { //Constructor
		this.playerID = tempID;
		this.playerName = tempName;
		this.totalPoints = 0;
	}
	
	public void addFriend(int tempID, Player tempPlayer) { //Adds friend to player's friend DataBase
		friends.put(tempID, tempPlayer);
	}
	
	public void plays(int tempGameID, Game tempGame, String tempIGN) { //Adds game and IGN to the player's respective DataBases
		playedGames.put(tempGameID, tempGame);
		inGameNames.put(tempGameID, tempIGN);
	}
	
	public void achieve(int tempGameID, int tempAchieveID) { //Sets the player's achievement to completed and adds points to player's GamerScore
		if(playedGames.containsKey(tempGameID)) {
				playedGames.get(tempGameID).achieve(tempAchieveID);
				totalPoints += playedGames.get(tempGameID).achievements.get(tempAchieveID).getPoints();
		}
	}
	//Many get statements for accessing the player's private variables
	public int getPlayerID() {
		return playerID;
	}

	public String getName() {
		return playerName;
	}
	
	public int getNumFriends() {
		return friends.size();
	}
	
	public String getIGN(int i) {
		for(int a : inGameNames.keySet()) {
			inGameNameList.add(inGameNames.get(a));
		}
		return inGameNameList.get(i-1);
	}

	public String getFriendName(int i) {
		for(int a : friends.keySet()) {
			friendsList.add(friends.get(a).playerName);
		}
		return friendsList.get(i-1);
	}
	
	public int getFriendScore(int i) {
		for(int a : friends.keySet()) {
			friendScores.add(friends.get(a).totalPoints);
		}
		return friendScores.get(i-1);
	}
	
	public String getGameName(int i) {
		for(int a : playedGames.keySet()) {
			gameNameList.add(playedGames.get(a).getGameName());
		}
		return gameNameList.get(i-1);
	}
	
	public int getGamerScore() {
		return totalPoints;
	}
	
	public int getNumGamesPlayed() {
		return playedGames.size();
	}
	
	public int getGamePoints(int i) {
		for(int a : playedGames.keySet()) {
			points.add(playedGames.get(a).getGamePoints());
		}
		return points.get(i-1);
	}
	
	public int getNumAchievements(int i) {
		for(int a : playedGames.keySet()) {
			achieveNumList.add(playedGames.get(a).getNumAchievements());
		}
		return achieveNumList.get(i-1);
	}
	
	public int getCompletedAchievements(int i) {
		for(int a : playedGames.keySet()) {
			completedAchieveList.add(playedGames.get(a).getCompletedAchievements());
		}
		return completedAchieveList.get(i-1);
	}
	
	public HashMap<Integer, Game> getPlayedGames() {
		return playedGames;
	}
	
	public HashMap<Integer, Player> getFriends() {
		return friends;
	}
}