package Client;

import java.io.IOException;
import ServerExecutable.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginDisplay extends Application {
	
	private Stage aStage;
	private GridPane aLoginPage = new GridPane();
	private Scene aLoginScene;
	private Scene aGameScene;
	private Scene aPreGameScene;
	private Scene aGameLobbyScene;
	/**
	 * Launches the application.
	 * @param pArgs This program takes no argument.
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage pStage) throws IOException 
	{
		
		aStage = pStage;
		aStage.centerOnScreen(); //Make window appear in the middle of the screen
		aStage.setTitle("Colt Express");
		
		createLoginPage();
		
		aLoginScene = new Scene(aLoginPage, 400, 400);

		Parent root = FXMLLoader.load(LoginDisplay.class.getResource("3PlayerBoard.fxml"));
		root.getStylesheets().add(LoginDisplay.class.getResource("style.css").toExternalForm());
		aGameScene = new Scene(root, 1500, 700);
		
		aStage.setScene(aLoginScene);
		aStage.setResizable(false);
		centreStage();
		aStage.show();
	 
	}
	
	private void centreStage()
	{	
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		aStage.setX((screenBounds.getWidth() - aStage.getWidth()) / 2);
		aStage.setY((screenBounds.getHeight() - aStage.getHeight()) / 2);
		
	}
	
	private void createLoginPage()
	{
		aLoginPage.setAlignment(Pos.CENTER);
		aLoginPage.setHgap(10);
		aLoginPage.setVgap(10);
		aLoginPage.setPadding(new Insets(25, 25, 25, 25));
		Text aWelcomeText = new Text("Welcome");
		aWelcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		aLoginPage.add(aWelcomeText, 0, 0, 2, 1);
		
		createLabel("UserName:", 0, 1);
		TextField aUsername = createTextField(1, 1);
		createLabel("Password:", 0, 2);
		PasswordField aPassword = createPasswordField(1, 2);
		HBox buttonBox = createHBox(10, Pos.BOTTOM_RIGHT);
		Button submitButton = createButton("Sign in", buttonBox);
		aLoginPage.add(buttonBox, 1, 4);
		
		Text statusMessage = new Text();
		aLoginPage.add(statusMessage, 1, 6);
		
		buttonEventHandling(submitButton, aUsername, aPassword, statusMessage);
		
	}
	
	
	
	private Label createLabel(String pTitle, int pWidth, int pHeight)
	{
		Label aLabel = new Label(pTitle);
		aLoginPage.add(aLabel, pWidth, pHeight);
		
		return aLabel;
	}
	
	private TextField createTextField(int pWidth, int pHeight)
	{
		TextField aUsername = new TextField();
		aLoginPage.add(aUsername, pWidth, pHeight);
		return aUsername;
	}
	
	private PasswordField createPasswordField(int pWidth, int pHeight)
	{
		PasswordField aPassword = new PasswordField();
		aLoginPage.add(aPassword, pWidth, pHeight);
		return aPassword;
	}
	
	private Button createButton(String pTitle, HBox pHBox)
	{
		Button aButton = new Button(pTitle);
		pHBox.getChildren().add(aButton);
		return aButton;
	}
	
	private HBox createHBox(int pSize, Pos pPos)
	{
		HBox aHBox = new HBox(pSize);
		aHBox.setAlignment(pPos);
		
		return aHBox;
	}
	
	private void buttonEventHandling(Button pButton, TextField pUser, PasswordField pPass, Text pText) 
	{
		pButton.setOnAction( event ->
			
			 {
				String aUser = pUser.getText();
				String aPass = pPass.getText();
				
				if(aUser.equals("") && aPass.equals(""))
					pText.setText("Please input a valid Username and Password");
				
				else if(aUser.equals(""))
					pText.setText("Please input a valid Username");
				
				else if(aPass.equals(""))
					pText.setText("Please input a valid Password");
				
				else{
			
			 

				
				
					Pregame aPregame = new Pregame(aUser, aPass);
					
					//login the user
					String[] tokens = aPregame.login();
					
					
					if(tokens == null) 
					{
						pText.setText("Please input a valid Username and Password");
					}
					else 
					{
						Button createGame = new Button("Create Game");
						aPregame.getChildren().add(createGame);
						createGame.setOnAction( e1 -> 
					
								{
									//Create a GameLobby
								
									String sessionID = aPregame.createGameSession();
									if(sessionID == null)
									{
										//Session wasn't able to be created
										Stage messageStage = new Stage();
										Label label = new Label("A game session was not able to be created. Please try again later");
										Button closeButton = new Button("Close");
										VBox messagBox = new VBox(label, closeButton);
										messagBox.setAlignment(Pos.CENTER);
										Scene errorScene = new Scene(messagBox);
										
										//Click on the button and it closes the window returning to available games view
										closeButton.setOnAction(e2 -> messageStage.close());
										
										
										messageStage.setScene(errorScene);
										centreStage();
										messageStage.showAndWait();
									}
									else 
									{	
										
										GameSession createdGameSession = aPregame.getGameLobby(sessionID);
									
									
										//Create the New lobby to join
									
										GameLobby aGameLobby = new GameLobby(createdGameSession, aPregame);
										
										
										//button refresh the players in session
										Button refreshSession = new Button("Refresh");
										aGameLobby.getChildren().add(refreshSession);
										refreshSession.setOnAction(e2 -> 
											{
												aGameLobby.refreshListOfPlayers();
											});
										
										//Button for the creator to start the game
										Button startGame = new Button("Start Game");
										aGameLobby.getChildren().add(startGame);
										startGame.setOnAction(e2 -> 
											
											{	
												if(aPregame.startGame(aGameLobby.getGameSessionID()))
												{
													aStage.setScene(aGameScene);
													centreStage();
												}
												else {
													//Joined Session hasn't started yet
													Stage messageStage = new Stage();
													Label label = new Label("The game failed to launch. Please try again in a moment");
													Button closeButton = new Button("Close");
													VBox messagBox = new VBox(label, closeButton);
													messagBox.setAlignment(Pos.CENTER);
													Scene errorScene = new Scene(messagBox);
													
													//Click on the button and it closes the window returning to available games view
													closeButton.setOnAction(e3 -> messageStage.close());
													aGameLobby.refreshListOfPlayers(); //refresh the list of players after game was not launched
													messageStage.setScene(errorScene);
													centreStage();
													messageStage.showAndWait();
												}
											});
												
										//Button that deletes the session
										Button deleteSession = new Button("Delete Session");
										aGameLobby.getChildren().add(deleteSession);
										aGameLobbyScene = new Scene(aGameLobby);
										aStage.setScene(aGameLobbyScene);
										centreStage();
									}

								}
						);
					
					
						Button joinGame = new Button("Join Game");
						aPregame.getChildren().add(joinGame);
					
						joinGame.setOnAction( e1 -> 
					
							{	
								//Obtain the game lobby selected in the listView
								ObservableList<Node> aList = aPregame.getChildrenUnmodifiable();
					    		GameSession gameSessionToJoin = ((ListView<GameSession>) aList.get(1)).getSelectionModel().getSelectedItem();
					    		
					    		if(gameSessionToJoin == null)
					    		{
					    			//Created session wasn't able to be joined
									Stage messageStage = new Stage();
									Label label = new Label("Please select a game session before trying to join one");
									Button closeButton = new Button("Close");
									VBox messagBox = new VBox(label, closeButton);
									messagBox.setAlignment(Pos.CENTER);
									Scene errorScene = new Scene(messagBox);
									
									//Click on the button and it closes the window returning to available games view
									closeButton.setOnAction(e2 -> messageStage.close());
									
									messageStage.setScene(errorScene);
									centreStage();
									messageStage.showAndWait();
					    		}
					    	
					    		else if(!aPregame.joinGameSession(gameSessionToJoin))
								{
									//Created session wasn't able to be joined
									Stage messageStage = new Stage();
									Label label = new Label("The game session was not able to be joined. Please try again later");
									Button closeButton = new Button("Close");
									VBox messagBox = new VBox(label, closeButton);
									messagBox.setAlignment(Pos.CENTER);
									Scene errorScene = new Scene(messagBox);
									
									//Click on the button and it closes the window returning to available games view
									closeButton.setOnAction(e2 -> messageStage.close());
									
									messageStage.setScene(errorScene);
									centreStage();
									messageStage.showAndWait();
								}
								
								else 
								{
								
									//GameLobby stuff aka after the player joined the game and all
									GameLobby aGameLobby = new GameLobby(gameSessionToJoin, aPregame);
									
									//button refresh the players in session
									Button refreshSession = new Button("Refresh");
									aGameLobby.getChildren().add(refreshSession);
									refreshSession.setOnAction(e2 -> 
										{
											aGameLobby.refreshListOfPlayers();
										});
									
									
									//Button to join the launched session
									Button launchSession = new Button("Launch");
									aGameLobby.getChildren().add(launchSession);
									launchSession.setOnAction(e2 ->
									
										{
											if(aPregame.launchGame(aGameLobby.getGameSessionID()))
											{
												aStage.setScene(aGameScene);
												centreStage();
											}
											else {
												//Joined Session hasn't started yet
												Stage messageStage = new Stage();
												Label label = new Label("The game has not started yet. Please try again in a moment");
												Button closeButton = new Button("Close");
												VBox messagBox = new VBox(label, closeButton);
												messagBox.setAlignment(Pos.CENTER);
												Scene errorScene = new Scene(messagBox);
												
												//Click on the button and it closes the window returning to available games view
												closeButton.setOnAction(e3 -> messageStage.close());
												aGameLobby.refreshListOfPlayers(); //refresh the list of players after game was not launched
												messageStage.setScene(errorScene);
												centreStage();
												messageStage.showAndWait();
											}
										});
									
									
									//Button to exit the game before it starts
									
									Button exitGame = new Button("Exit Game");
									aGameLobby.getChildren().add(exitGame);
									
									exitGame.setOnAction(e2 ->
									
										{
											aPregame.leaveGame(aGameLobby.getGameSessionID());
											aStage.setScene(aPreGameScene);
											centreStage();
										});
											
									aGameLobbyScene = new Scene(aGameLobby);
									aStage.setScene(aGameLobbyScene);
									centreStage();
								}
							});
						
						
						Button refreshGames = new Button("Refresh");
						aPregame.getChildren().add(refreshGames);
					
						refreshGames.setOnAction( e1 -> aPregame.refreshAvailableGames());
					
						aPreGameScene = new Scene(aPregame);
						aStage.setScene(aPreGameScene);
						centreStage();
					}
				}
			 }); 
	}
}
