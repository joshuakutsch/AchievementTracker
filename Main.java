//Joshua Kutsch
//CSCE 315-502
//1-30-15
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

	public static void main(String[] args) {
		//List of implemented variables used in the main method
		int gameID, playerID, achievementID, achievementPoints, totalGamerScore;
		String command, playerName, gameName, achievementName, tempIGN;
		HashMap<Integer, Player> players = new HashMap<Integer, Player>(); //Player DataBase
		HashMap<Integer, Game> games = new HashMap<Integer, Game>(); //Game DataBase
		int[] friendID = new int[2];
		Player[] compPlayer = new Player[2];

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			command = scanner.next();
			
			if (command.equals("AddPlayer")) {
				playerID = scanner.nextInt();
				//findInLine with regex brings in everything in between quotes including random characters
				playerName = scanner.findInLine("(\"[a-zA-Z0-9[_$&+,:;=?@#|'<>.^*()%!-]\\s]+\")").replace("\"", "");
				Player tempPlayer = new Player(playerID, playerName); //Creates a new Player object
				players.put(playerID, tempPlayer); //Adds player with ID as it's key to Player Database
			} 
			
			else if (command.equals("AddGame")) {
				gameID = scanner.nextInt();
				gameName = scanner.findInLine("(\"[a-zA-Z0-9[_$&+,:;=?@#|'<>.^*()%!-]\\s]+\")").replace("\"", "");
				Game tempGame = new Game(gameID, gameName); //Creates a new Game object
				games.put(gameID, tempGame); //Adds game with ID as it's key to Game DataBase
			}
			
			else if (command.equals("AddAchievement")) {
				gameID = scanner.nextInt();
				achievementID = scanner.nextInt();
				achievementName = scanner.findInLine("(\"[a-zA-Z0-9[_$&+,:;=?@#|'<>.^*()%!-]\\s]+\")").replace("\"",
						"");
				achievementPoints = scanner.nextInt();
				if (games.containsKey(gameID)) { //Checks if the game exists in the DataBase
					Achievement tempAchievement = new Achievement(gameID, achievementID, achievementName,
							achievementPoints); //Creates a new Achievement object
					games.get(gameID).addAchievement(tempAchievement); //Adds achievement to the specified game's achievement DataBase
				} else {
					System.out.println("Error: Game not found in database.");
				}
			} 
			
			else if (command.equals("Plays")) {
				playerID = scanner.nextInt();
				gameID = scanner.nextInt();
				tempIGN = scanner.findInLine("(\"[a-zA-Z0-9[_$&+,:;=/?@#|'<>.^*()%!-]\\s]+\")").replace("\"", "");
				if (games.containsKey(gameID)) {
					gameName = games.get(gameID).getGameName();
					Game tempGame = new Game(games.get(gameID)); //Creates a new instance of an existing game for the player
					players.get(playerID).plays(gameID, tempGame, tempIGN); //Calls the plays function inside of the Player class
				} else {
					System.out.println("Error: Game not found in database.");
				}
			} 
			
			else if (command.equals("AddFriends")) {
				friendID[0] = scanner.nextInt();
				friendID[1] = scanner.nextInt();
				//First checks that each Player ID is already in the player DataBase
				if (players.containsKey(friendID[0]) && players.containsKey(friendID[1])) {
					//Then makes sure they aren't already friends
					if(!players.get(friendID[0]).getFriends().containsKey(friendID[1])) {
						//Finally adds each friend to each other's friend DataBase in the Player object
						players.get(friendID[0]).addFriend(friendID[1], players.get(friendID[1]));
						players.get(friendID[1]).addFriend(friendID[0], players.get(friendID[0]));
					}
				} else {
					System.out.println("Error: Player ID not found in database.");
				}
			} 
			
			else if (command.equals("Achieve")) {
				playerID = scanner.nextInt();
				gameID = scanner.nextInt();
				achievementID = scanner.nextInt();
				//First checks that the achievement exists in the game's achievement DataBase
				if(games.get(gameID).achievements.containsKey(achievementID)) {
					players.get(playerID).achieve(gameID, achievementID); //Calls achieve in the Player Class
				}
			} 
			
			else if (command.equals("FriendsWhoPlay")) {
				playerID = scanner.nextInt();
				gameID = scanner.nextInt();
				Player tempPlayer = players.get(playerID);
				System.out.println(
						"//////////////////////////////////////////////////////////////////////////////////////////");
				System.out.println(
						"Friends of " + tempPlayer.getName() + " who play " + games.get(gameID).getGameName() + ":");
				HashMap<Integer, Player> friendList = tempPlayer.getFriends(); //Gets the player's list of friends
				for (int i : friendList.keySet()) {
					//Checks if the friend plays the specific game
					if (friendList.get(i).getPlayedGames().containsKey(gameID)) {
						System.out.println(friendList.get(i).getName());
					}
				}
			} 
			
			else if (command.equals("ComparePlayers")) {
				friendID[0] = scanner.nextInt();
				friendID[1] = scanner.nextInt();
				compPlayer[0] = players.get(friendID[0]);
				compPlayer[1] = players.get(friendID[1]);
				gameID = scanner.nextInt();
				System.out.println(
						"//////////////////////////////////////////////////////////////////////////////////////////");
				System.out.println("Compare Players in: " + games.get(gameID).getGameName());
				System.out.printf("   %-22s %-20s %-20s\n", "Name", "Achievements", "GamerScore");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				for (int i = 0; i < 2; i++) { //For loop that outputs both players' points and achievement completion 
					int playerPoints = compPlayer[i].getPlayedGames().get(gameID).getGamePoints();
					int totalAchievements = compPlayer[i].getPlayedGames().get(gameID).getNumAchievements();
					int completedAchievements = compPlayer[i].getPlayedGames().get(gameID).getCompletedAchievements();
					String progress = completedAchievements + "/" + totalAchievements;
					System.out.printf("   %-25s %-20s %-20s\n", compPlayer[i].getName(), progress, playerPoints);
				}
			} 
			
			else if (command.equals("SummarizePlayer")) {
				playerID = scanner.nextInt();
				Player tempPlayer = players.get(playerID);
				playerName = tempPlayer.getName();
				totalGamerScore = tempPlayer.getGamerScore();
				System.out.println(
						"//////////////////////////////////////////////////////////////////////////////////////////");
				System.out.println("Player Summary: " + playerName);
				System.out.println("Total GamerScore: " + totalGamerScore + " pts");
				System.out.printf("   %-22s %-20s %-20s %-3s\n", "Game", "Achievements", "GamerScore", "IGN");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				if(tempPlayer.getNumGamesPlayed() == 0) { //Checks if the player has any played games
					System.out.println("None");
				}
				else { //For Loop that outputs the player's games, achievement progress, game points and in-game name
				for (int i = 1; i <= tempPlayer.getNumGamesPlayed(); i++) {
					gameName = tempPlayer.getGameName(i);
					int totalAchievements = tempPlayer.getNumAchievements(i);
					int completedAchievements = tempPlayer.getCompletedAchievements(i);
					String progress = completedAchievements + "/" + totalAchievements;
					int gamePoints = tempPlayer.getGamePoints(i);
					String inGameName = tempPlayer.getIGN(i);
					System.out.printf("%d. %-25s %-20s %-18s", i, gameName, progress, gamePoints);
					System.out.printf("%-30s", inGameName);
					System.out.print("\n");
				}
				}
				System.out.printf("   %-22s %-20s \n", "Friends", "GamerScore");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				if(tempPlayer.getNumFriends() == 0) { //Checks if player has any friends
					System.out.println("None");
				}
				else {
					for (int i = 1; i <= tempPlayer.getNumFriends(); i++) { //Loop that prints out friends and their total gamer score
						String friendName = tempPlayer.getFriendName(i);
						int friendScore = tempPlayer.getFriendScore(i);
						System.out.printf(" %-25s %-20s", friendName, friendScore);
						System.out.print("\n");
					}
				}
			} 
			
			else if (command.equals("SummarizeGame")) {
				gameID = scanner.nextInt();
				gameName = games.get(gameID).getGameName();
				HashMap<Integer, Achievement> gameAchievements = games.get(gameID).getAchievements();
				HashMap<Integer, Integer> timesAchieved = new HashMap<Integer, Integer>();
				System.out.println(
						"//////////////////////////////////////////////////////////////////////////////////////////");
				System.out.println("Game Summary: " + gameName);
				System.out.println(
						"------------------------------------------------------------------------------------------");
				System.out.println("Player(s): ");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				//Prints out the players of the game and gets the number of times each achievement has been completed
				for (int a : players.keySet()) { 
					if (players.get(a).getPlayedGames().containsKey(gameID)) {
						System.out.println(players.get(a).getName());
						HashMap<Integer, Achievement> playerAchievements = players.get(a).getPlayedGames().get(gameID)
								.getAchievements();
						for (int c : playerAchievements.keySet()) {
							if (playerAchievements.get(c).getStatus()) {
								if (timesAchieved.containsKey(c)) {
									timesAchieved.put(c, timesAchieved.get(c) + 1);
								} else {
									timesAchieved.put(c, 1);
								}
							}
						}
					}
				}
				System.out.println(
						"------------------------------------------------------------------------------------------");
				System.out.printf("%-30s %-20s\n", "Achievement(s)", "Number of Times Completed");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				for (int b : gameAchievements.keySet()) { //Loops through each achievement and prints out the times it has been achieved
					System.out.printf("%-30s %-20s\n", gameAchievements.get(b).getAchieveName(), timesAchieved.get(b));
				}
			} 
			
			else if (command.equals("SummarizeAchievement")) {
				gameID = scanner.nextInt();
				achievementID = scanner.nextInt();
				int totalPlayers = 0;
				int numPlayersAchieved = 0;
				System.out.println("//////////////////////////////////////////////////////////////////////////////////////////");
				System.out.println("Achievement Summary: " + games.get(gameID).achievements.get(achievementID).getAchieveName());
				System.out.println(
						"------------------------------------------------------------------------------------------");
				System.out.println("Players Completed:");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				for (int a : players.keySet()) { //Loops through the players of the game determining if achievement is completed
					if (players.get(a).getPlayedGames().containsKey(gameID)) {
						totalPlayers++;
						if(players.get(a).getPlayedGames().get(gameID).getAchievements().get(achievementID).getStatus()) {
							numPlayersAchieved++;
							System.out.println(players.get(a).getName());
						}
					}
				}
				System.out.println(
						"------------------------------------------------------------------------------------------");
				System.out.print("Percent Completed: ");
				System.out.println((numPlayersAchieved/totalPlayers)*100 + "%");
			} 
			
			else if (command.equals("AchievementRanking")) {
				ConcurrentHashMap<Integer, Player> playerList = new ConcurrentHashMap<Integer, Player>();
				playerList.putAll(players);//Copies players from player DataBase into an editable Database
				int i = 0;
				System.out.println(
						"//////////////////////////////////////////////////////////////////////////////////////////");
				System.out.println("Achievement Ranking");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				System.out.printf("%-30s %-20s\n", "   Player", "GamerScore");
				System.out.println(
						"------------------------------------------------------------------------------------------");
				while (playerList.size() > 0) { //Loops through all players
					for (int a : playerList.keySet()) {
						if(playerList.get(a) != null) {
							Player aPlayer = playerList.get(a);
							for (int b : playerList.keySet()) {
								Player bPlayer = playerList.get(b);
								if (bPlayer.getGamerScore() > aPlayer.getGamerScore()) {//Checks if new player has higher GamerScore
									aPlayer = bPlayer; //Swaps out current highest GamerScore
								}
							}
							i++;
							System.out.printf("%-30s %-20s\n", i + ". " + aPlayer.getName(), aPlayer.getGamerScore());//Outputs gamer with highest GamerScore
							playerList.remove(aPlayer.getPlayerID());//Removes player with highest GamerScore and continues through the player list
						}
					}
				}

			} 
			
			else {
				System.out.println("Command doesn't exist.");
			}
		}
		scanner.close();
		System.out.println("End of Program");
	}
}