package Client;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import ServerExecutable.*;

public class Pregame extends VBox {
	
	private ListView<GameSession> aAvailableGames = new ListView<GameSession>();
	private final String aUsername;
	private final String aPassword;
	private String accessToken;
	private String refreshToken;
	private final ServerRequests aServerRequests = new ServerRequests();

	public Pregame(String pUsername, String pPassword) 
	{
		aUsername = pUsername;
		aPassword = pPassword;
		Label aLabel = new Label("Welcome back " + aUsername + "! Nice to see you again!");
		aLabel.setStyle("-fx-font-weight: bold");
		
    	getChildren().add(aLabel);
		getChildren().add(aAvailableGames);
	}
	
	public String refreshToken()
	{
		String[] tokens = aServerRequests.login(refreshToken);
		
		if (tokens != null)
		{
			accessToken = tokens[0];
			refreshToken = tokens[1];
			return tokens[0];
		}
		
		else
			return null;
		
	}
	
	public String[] login()
	{	
		String[] tokens = new String[2];
		
		tokens = aServerRequests.login(aUsername, aPassword);
		if(tokens != null)
		{	
			//set the tokens and display the available games
			accessToken = tokens[0];
			refreshToken = tokens[1];
			refreshAvailableGames();
				
		}
		
		return tokens;
	}
	
	public void refreshAvailableGames() 
	{	
		aAvailableGames.getItems().clear();
		aServerRequests.getActiveSessions(accessToken, this).forEach(gamesession -> aAvailableGames.getItems().add(gamesession));
	}
	
	public String createGameSession()  
	{	
		return aServerRequests.createSession(accessToken, this);
	}
	
	public boolean joinGameSession(GameSession pGameSession)  
	{	
		if (pGameSession == null)
			return false;
		else
			return aServerRequests.joinSession(pGameSession.getid(), accessToken, this);
	}
	
	public GameSession getGameLobby(String pSessionID)
	{
		return aServerRequests.getSessionInfo(pSessionID, accessToken, this);
	}
	
	public void leaveGame(String pSessionID)
	{
		aServerRequests.leaveSession(pSessionID, accessToken, this);
	}
	
	public boolean startGame(String pSessionID)
	{
		return aServerRequests.launchSession(pSessionID, accessToken, this);
	}
	
	public boolean launchGame(String pSessionID)
	{	
		GameSession theGameSession = aServerRequests.getSessionInfo(pSessionID, accessToken, this);
		if(theGameSession.isLaunched())
		{
			aServerRequests.launchSession(pSessionID, accessToken, this);
			return true;
		}
		return false;
	}
}
