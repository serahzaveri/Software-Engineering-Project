package Client;

import ServerExecutable.GameSession;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GameLobby extends VBox {
	
	private ListView<String> aPlayers = new ListView<String>();
	private final GameSession aGameSession;
	private final String aCreator;
	private final Label aLabel;
	private final Pregame aPregame;
	
	public GameLobby(GameSession pGameSession, Pregame pPregame)
	{	
		aGameSession = pGameSession;
		aCreator = pGameSession.getCreator();
		aPregame = pPregame;
		
		aLabel = new Label(aCreator + "'s Game");
		aLabel.setStyle("-fx-font-weight: bold");
		refreshListOfPlayers();		
		getChildren().add(aLabel);
		getChildren().add(aPlayers);
		
	}
	
	public String getGameSessionID() 
	{
		return aGameSession.getid();
	}
	
	public void refreshListOfPlayers()
	{
		aPlayers.getItems().clear();
		aPregame.getGameLobby(aGameSession.getid()).getPlayers().forEach(player -> aPlayers.getItems().add(player));
	}
}
