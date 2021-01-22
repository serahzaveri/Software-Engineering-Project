package Client;

import java.io.File;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXMLLoader;

public class ColtExpressBoard extends GridPane {
	
	private final String aBackground = "resources/boardComponents/background.png";
	private final String aTrain = "resources/boardComponents/trainReSized.png";
	private final String aStageCoach = "resources/boardComponents/stageCoach.png";
	private final String aWagon = "resources/boardComponents/wagonReSized.png";
	private final String aAngryShotGun = "resources/characters/AngryShotgun.png";
	private final int aNumberOfPlayers;
	private final int aNumberOfTiles;
	private final static int WIDTH = 200;
	private final static int HEIGHT = 75;
	/*private final String aGem = "";
	*private final String aPurse = "";
	*
	*/

	public ColtExpressBoard(int pNumberOfPlayers)
	{	
		aNumberOfPlayers = pNumberOfPlayers ;
		aNumberOfTiles = pNumberOfPlayers + 1;
		createBackground(fileToImage(aBackground));
		createGameBoardTiles();
		setBoard();
	}
	
	private void createGameBoardTiles()
	{	
		createWagons();
		createTrain();
		createEmptyTiles();
		
	}
	
	/**Method that creates the tiles necessary for wagons --> the interior tile and roof tile
	 */
	private void createWagons()
	{
		for(int i = 0; i < 2; i++)
		{	
			for(int j = 0; j < aNumberOfPlayers; j++)
			{
				if(i == 0)				{
					Rectangle aTile = new Rectangle(WIDTH, HEIGHT);
					aTile.setFill(Color.TRANSPARENT);
					add(new StackPane(aTile), j, i);
				}
				
				else {
					 
					Rectangle aTile = new Rectangle(WIDTH, HEIGHT);
					Image aImage = fileToImage(aWagon);
					aTile.setFill(new ImagePattern(aImage));;
					add(new StackPane(aTile), j, i);
				}
			}
		}
	}
	
	/**Method that creates the tiles necessary for Trains --> the interior tile and roof tile
	 */
	private void createTrain()
	{
		for(int i = 0; i < 2; i++)
		{	
			//The train is found at the position equal to the number of players so the number of Tiles
			//This is because we have the same number of wagons as the number of players
			//And the positioning in a gridpane starts at the index 0
			int yPosition = aNumberOfPlayers;
			
			if(i == 0)
			{
				Rectangle aTile = new Rectangle(WIDTH, HEIGHT);
				aTile.setFill(Color.TRANSPARENT);
				add(new StackPane(aTile), yPosition, i);
			}
			
			else {
				 
				Rectangle aTile = new Rectangle(WIDTH, HEIGHT);
				Image aImage = fileToImage(aTrain);
				aTile.setFill(new ImagePattern(aImage));
				add(new StackPane(aTile), yPosition, i);
			}
			
			
		}
	}
	
	/**Method that creates the empty tiles necessary for the StageCoach --> the interior tile and roof tile
	 * and the empty tiles for the horse riding
	 */
	private void createEmptyTiles() 
	{
			//the tiles for the stageCoach (interior and roof) are alwayss at index 3 and 4
			for(int i = 2; i < 5; i++)  
			{	
				for(int j = 0; j <= aNumberOfPlayers; j++)
				{
					Rectangle aTile = new Rectangle(WIDTH, HEIGHT);
					aTile.setFill(Color.TRANSPARENT);
					add(new StackPane(aTile), j, i);
				}
			}
	}
	
	private Image fileToImage(String pString)
	{
		File aFile = new File(pString);
		Image aImage = new Image(aFile.toURI().toString());
		return aImage;
	}
	
	private void createBackground(Image pImage)
	{
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background theBackground = new Background(new BackgroundImage(pImage, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, 
				backgroundSize));
		setBackground(theBackground);
	}
	
	private HBox createBoardComponent(String pString)
	{	
		Image aImage = fileToImage(pString);
		HBox aHBox = new HBox();
		ImageView aImageView = new ImageView();
		aImageView.setImage(aImage);
		aHBox.getChildren().add(aImageView);
		return aHBox;
	}
	
	private void setBoard()
	{	
		//the items to place on the board
		Image theStageCoach = fileToImage(aStageCoach);
		Image theAngryShotgun = fileToImage(aAngryShotGun);
		
		//Setting the board with coordinates
		Rectangle roofOfLastTile = (Rectangle) (((StackPane) getChildren().get(aNumberOfTiles * 4 - 1)).getChildren().get(0));
		Rectangle lastTile = (Rectangle) (((StackPane) getChildren().get(aNumberOfTiles * 5 - 1)).getChildren().get(0));
		
		roofOfLastTile.setFill(new ImagePattern(theAngryShotgun));
		lastTile.setFill(new ImagePattern(theStageCoach));
	
	}
}
