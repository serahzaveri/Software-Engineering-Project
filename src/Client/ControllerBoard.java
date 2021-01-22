package Client;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControllerBoard {

	   @FXML
	    private GridPane grid;

	    @FXML
	    private Pane loco;

	    @FXML
	    private Pane firstWagon;

	    @FXML
	    private Pane secondWagon;

	    @FXML
	    private ImageView Django;

	    @FXML
	    private Pane thirdWagon;

	    @FXML
	    private Pane caleche;

	    @FXML
	    private ImageView Belle;
	   
	    
	    @FXML
	    private GridPane cornerGrid;

	    @FXML
	    private Button Pause;

	    @FXML
	    private Button lootBoutton;
	    

	    @FXML
	    private ImageView gem;
	   

	    @FXML
	    private VBox box;
	    
	    @FXML
	    void BelleClicked(MouseEvent event) {
	    	Button button0 = new Button("Go here");
	    	Button button1 = new Button("Go here");
	    	Button button2 = new Button("Go here");
	    	Button button3 = new Button("Go here");
	    	Button goDownButton = new Button("Go down");
	    	
	    	if(grid.getRowIndex(Belle)==null||grid.getRowIndex(Belle)==0) {
	    	
	    		if(grid.getColumnIndex(Belle) == null || grid.getColumnIndex(Belle) == 0) {
	    			grid.add(button1, 1, 0);
	    			grid.add(button2, 2, 0);
	    			grid.add(button3, 3, 0);
	    			grid.add(goDownButton, 0, 1);
	    		}
	    		else if (grid.getColumnIndex(Belle) == 1){
	    			grid.add(button0, 0, 0);
	    			grid.add(button2, 2, 0);
	    			grid.add(button3, 3, 0);
	    			grid.add(goDownButton, 1, 1);
	    	
	    		}
	    		else if(grid.getColumnIndex(Belle) == 2) {
	    			grid.add(button0, 0, 0);
	    			grid.add(button2, 1, 0);
	    			grid.add(button3, 3, 0);
	    			grid.add(goDownButton, 2, 1);
		  
	    		}
	    		else
	    		{
	    			grid.add(button0, 0, 0);
	    			grid.add(button2, 1, 0);
	    			grid.add(button3, 2, 0);
	    			grid.add(goDownButton, 3, 1);
		    	
	    		}
	    	}
	    	else {
	    		grid.add(button0, 0, 0);
	    		grid.add(button1, 1,1);
	    	}
	    	
	    	buttonEventHandling(button0,button1, button2, button3, goDownButton);
	    	
	    }

	    @FXML
	    void DjangoClicked(MouseEvent event) {
	    	
	    	
	    }
	    
	   
	
	   
	    
	  
	    
	    @FXML
	    void buttonEventHandling(Button button0, Button button1,Button button2, Button button3, Button goDownButton) {

	    	button0.setOnAction(event ->
	    	{
	    		grid.getChildren().remove(button0);
	    		grid.getChildren().remove(button1);
	    		grid.getChildren().remove(button2);
	    		grid.getChildren().remove(button3);
	    		grid.getChildren().remove(goDownButton);
	    		grid.getChildren().remove(Belle);
	    		grid.add(Belle, 0, 0);
	    		
	    	});
	    	
	    	button1.setOnAction(event ->
	    	{
	    		grid.getChildren().remove(button0);
	    		grid.getChildren().remove(button1);
	    		grid.getChildren().remove(button2);
	    		grid.getChildren().remove(button3);
	    		grid.getChildren().remove(goDownButton);
	    		
	    		if(grid.getRowIndex(Belle) == null || grid.getRowIndex(Belle) == 0) {
	    			grid.getChildren().remove(Belle);
		    		grid.add(Belle, 1, 0);
	    		}
	    	else {
	    			grid.getChildren().remove(Belle);
	    			grid.add(Belle,1, 1);
	    		}
	    		
	    	});
	    	button2.setOnAction(event ->
	    	{
	    		grid.getChildren().remove(button0);
	    		grid.getChildren().remove(button1);
	    		grid.getChildren().remove(button2);
	    		grid.getChildren().remove(button3);
	    		grid.getChildren().remove(goDownButton);
	    		grid.getChildren().remove(Belle);
	    		grid.add(Belle, 2, 0);
	    		
	    	});
	    	button3.setOnAction(event ->
	    	{
	    		grid.getChildren().remove(button0);
	    		grid.getChildren().remove(button1);
	    		grid.getChildren().remove(button2);
	    		grid.getChildren().remove(button3);
	    		grid.getChildren().remove(goDownButton);
	    		grid.getChildren().remove(Belle);
	    		grid.add(Belle, 3, 0);
	    		
	    	});
	    	goDownButton.setOnAction(event ->
	    	{
	    		grid.getChildren().remove(button0);
	    		grid.getChildren().remove(button1);
	    		grid.getChildren().remove(button2);
	    		grid.getChildren().remove(button3);
	    		grid.getChildren().remove(goDownButton);
	    		grid.getChildren().remove(Belle);
	    		
	    		if(grid.getColumnIndex(Belle) == null || grid.getColumnIndex(Belle) == 0) {
	    			grid.add(Belle, 0, 1);
	    		}
	    		
	    		else if(grid.getColumnIndex(Belle) == 1) {
	    			grid.add(Belle, 1, 1);
	    		}
	    		
	    		else if(grid.getColumnIndex(Belle) == 2) {
	    			grid.add(Belle, 2, 1);
	    		}
	    		else if(grid.getColumnIndex(Belle) == 3) {
	    			grid.add(Belle, 3, 1);
	    		}
	    	});
	    }
	 
	    @FXML
	    void lootBouttonClicked(ActionEvent event) {
	    	grid.getChildren().remove(gem);
	    	cornerGrid.add(gem, 1, 0);
	    }
	   

	}