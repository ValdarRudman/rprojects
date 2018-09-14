package gui;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import games.GuessNumber;
import games.LottoCure;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import track.Score;

public class Gui extends Application {
	
	private static GuessNumber guessNumber;
	private static LottoCure lotto;
	
	private static Score score;
	
	private static Tab guessNumberTab;
	private static Tab lottoCure;
	
	// Size is the 'size' of the buttons. Have buttons layout neatly
	private static int SIZE = 10;
	// length and width used for the for loop for generating buttons on the gui
	private static int length = SIZE;
	private static int width = SIZE;
	// Start number that appears on the buttons generated for games.
	private static int butttonNumber = 1;
	
	// ArrayList of buttons for guessNumber game
	private static ArrayList<Button> guessNumberButtons = new ArrayList();
	// ArrayList of buttons for lotto game
	private static ArrayList<Button> lottoButtons = new ArrayList();
	
	private static Tab prizeTab;
	
  public static void main(String[] args) {
	  
    Application.launch(args);
    
  }
  
  @Override
  public void start(Stage primaryStage) {
	 
	 // create games and score object
	 score = new Score();
	 guessNumber = new GuessNumber(score.getMinScore());
	 lotto = new LottoCure(score.getMinScore());
	 
	 
    primaryStage.setTitle("Tabs");
    Group root = new Group();
    Scene scene = new Scene(root, 400, 400, Color.WHITE);
    TabPane tabPane = new TabPane();
    BorderPane borderPane = new BorderPane();
    
    guessNumber(tabPane);

    lottoCure(tabPane);
    
    borderPane.prefHeightProperty().bind(scene.heightProperty());
    borderPane.prefWidthProperty().bind(scene.widthProperty());

    borderPane.setCenter(tabPane);
    root.getChildren().add(borderPane);
    primaryStage.setScene(scene);
    primaryStage.show();
    
  }
  
  /*
   * Generated the guessNumber tab with the number of buttons needed to reflect the game. Has a quit and reset button
   */
  public static void guessNumber(TabPane tabPane) {
	  
	  guessNumberTab = new Tab();
	  guessNumberTab.setText("Guess Number");
	  
	  BorderPane mainPane = new BorderPane();  
	  GridPane buttonsPane = new GridPane();  
	   
	// Loop to create all the buttons needed for the game and adds to the correct arraylist
	  for(int i = 0; i < length; i++){
	    	
		  for(int j = 0; j < width; j++){

			// create button with number and set size and increment button number
			  Button button = new Button(Integer.toString(butttonNumber));
	          butttonNumber++;
	          button.setPrefHeight(50);
	          button.setPrefWidth(50);
	          button.setAlignment(Pos.CENTER);

	       // set button onto grid pane and layout so buttons appear in order and neatly
	          GridPane.setRowIndex(button,i);
	          GridPane.setColumnIndex(button,j);    
	          buttonsPane.getChildren().add(button);
	          guessNumberButtons.add(button);
	          
	       // Action each button will perform
	          button.setOnAction(e -> {
	            
	        	// Get source of the button and disable button
	        	  Button action = (Button) e.getSource();
	        	  action.setDisable(true);
	            
	        	//get the text of the button which will be a number. Use the number to get users guess
	        	  boolean correct = guessNumber.checkGuess(Integer.parseInt(action.getText()));
	            	
	        	  // checks if user guessed the number and if all attempts are used.
	        	  if(correct == false && guessNumber.checkAttempts()) {
	            	
	        		  //if all attempts are used informs user and disables buttons
	        		  AlertBox.display("SORRY", "All attempts used");
	            	
	        		  for(int k = 0; k < guessNumberButtons.size(); k++) {
	        			  
	        			  Button disable = (Button)guessNumberButtons.get(k);
	        			  disable.setDisable(true);
	        		  }

	        	  }
	        	  else if(correct) {
	            		
	        		  	// if user guess number, informs user and adds to the score
	            		AlertBox.display("Winner", "You have guessed the number");
	            		score.addToScore(guessNumber.getScore());

	            		// will display prize tab for user to choose prize
	            		if(prizeTab != null) {
	            			
	            			tabPane.getTabs().remove(2);
	            			prizeTab = null;
	            		}
	            			
						prizes(tabPane);
	            		
	            	}
	            	else {
	            		
	            		// Informs the user if the correct number is higher or lower than the number guessed
	            		String ans = guessNumber.checkHighOrLow(Integer.parseInt(action.getText()));
	            		
	            		AlertBox.display("Helpful", ans);
	            		
	            	}
	            	
	            });
	        }
	    }
	    
	  	// add hbox with quit button
	    HBox guessHBox = hbox();

	    // add reset button
	    Button buttonReset = new Button("Reset");
	    buttonReset.setOnAction(e ->{
	    	
	    	if(guessNumber.checkAttempts()) {
	    		
	    		AlertBox.display("Sorry", "Cannot reset if all attempts have been used");
	    		
	    	}
	    	else {
	    		
		    	guessNumber = null;
		    	
		    	guessNumber = new GuessNumber(score.getMinScore());
		    	
		    	for(int i = 0; i < guessNumberButtons.size(); i++) {
		    		
		    		guessNumberButtons.get(i).setDisable(false);
		    		
		    	}
		    	
	    	}
	    	
	    	if(prizeTab != null) {
    			
    			tabPane.getTabs().remove(2);
    			prizeTab = null;
    		}
	    	
	    });
	    buttonReset.setPrefSize(100, 20);

	    guessHBox.getChildren().add(buttonReset);
	    
	    mainPane.setTop(guessHBox);
	    mainPane.setCenter(buttonsPane);
	    guessNumberTab.setContent(mainPane);
	    tabPane.getTabs().add(guessNumberTab);
	  
  }
  
  /*
   * Generated the lottoCure tab with the number of buttons needed to reflect the game. Has a quit and reset button
   */
  public static void lottoCure(TabPane tabPane) {
	  
	    lottoCure = new Tab();
	    lottoCure.setText("Lotto Cure");
	    
	    BorderPane mainPane = new BorderPane();  
	    GridPane buttonsPane = new GridPane();  
	    
	    butttonNumber = 1;
	     
	    // Loop to create all the buttons needed for the game and adds to the correct arraylist
	    for(int i = 0; i < length / 2; i++){
	    	
	        for(int j = 0; j < width; j++){

	        	// create button with number and set size and increment button number
	            Button button = new Button(Integer.toString(butttonNumber));
	            butttonNumber++;
	            button.setPrefHeight(50);
	            button.setPrefWidth(50);
	            button.setAlignment(Pos.CENTER);

	            // set button onto grid pane and layout so buttons appear in order and neatly
	            GridPane.setRowIndex(button,i);
	            GridPane.setColumnIndex(button,j);    
	            buttonsPane.getChildren().add(button);
	            lottoButtons.add(button);
	            
	            // Action each button will perform
	            button.setOnAction(e -> {
	            	
	            	// Get source of the button and disable button
	            	Button action = (Button) e.getSource();
	            	action.setDisable(true);
	            	
	            	//get the text of the button which will be a number. Use the number to get users guess
	            	lotto.numberGuess(Integer.parseInt(action.getText()));
	            	
	            	boolean done = lotto.allGuesses();
	            	
	            	// checks to see if guess used and if any guess are right
	            	if(done && lotto.checkNumbers()) {
	            		
	            		score.addToScore(lotto.getScore());
	            		
	            		for(int k = 0; k < lottoButtons.size(); k++) {
	            			
	            			lottoButtons.get(k).setDisable(true);
	            			
	            		}

	            		// Shows prize tab based on user score
	            		if(prizeTab != null) {
	            			
	            			tabPane.getTabs().remove(3);
	            			prizeTab = null;
	            		}
	            		
						prizes(tabPane);
						

	            	}// of not right resets buttons and the index in the guessNumber array for the user. Allows infinite attempts
	            	else if(done && lotto.checkNumbers() == false) {
	            		
	            		lotto.resetCurrentIndex();
	            		
	            		for(int k = 0; k < lottoButtons.size(); k++) {
	            			
	            			Button enable = (Button) lottoButtons.get(k);
	            			enable.setDisable(false);
	            			
	            		}
	            		
	            	}
	            	
	            });
	        }
	    }
	    
	    HBox hboxLotto = hbox();
	
	    // add reset button to hbox
	    Button buttonResetLotto = new Button("Reset");
	    buttonResetLotto.setOnAction(e ->{
	    	
	    	lotto = null;
	    	
	    	lotto = new LottoCure(score.getMinScore());
	    	
	    	for(int i = 0; i < lottoButtons.size(); i++) {
	    		
	    		lottoButtons.get(i).setDisable(false);
	    		
	    	}
	    	
	    	if(prizeTab != null) {
    			
    			tabPane.getTabs().remove(2);
    			prizeTab = null;
    		}
	    	
	    });
	    
	    buttonResetLotto.setPrefSize(100, 20);
	    hboxLotto.getChildren().add(buttonResetLotto);
	    
	    mainPane.setTop(hboxLotto);
	    mainPane.setCenter(buttonsPane);
	    
	    lottoCure.setContent(mainPane);
	    tabPane.getTabs().add(lottoCure);
	  
	  
  }
  
  /*
   * prize tab that is generated when min socre is met. Contains the hashmap. Disables game tabs when prize us selected
   */
  public static void prizes(TabPane tabPane) {
	  
	  prizeTab = new Tab();
	  prizeTab.setText("Prizes");
	  VBox mainPane = new VBox();
	  
	  //Hashmap containg all the prizes and key words.
	  HashMap<String, String> prizesHM = new HashMap<>();
	  String key1 = "Apple";
	  String key2 = "Grape";
	  String key3 = "Pear";
	  String key4 = "Banana";
	  
	  prizesHM.put(key1, "\u20ac1,000");
	  prizesHM.put(key2, "\u20ac10,000");
	  prizesHM.put(key3, "a trip to Donegal");
	  prizesHM.put(key4, "a trip to Caribbean");
	  
	  ComboBox comboBox;
	  
	  // creates an observablelist based on the user score.
	  if(score.getScore() == 4) {
		  
		  ObservableList<String> options = FXCollections.observableArrayList(
													key1,
													key3);
	 
		  comboBox = new ComboBox(options);
		  
	  }
	  else if(score.getScore() == 5) {
		  
		  ObservableList<String> options = FXCollections.observableArrayList(
					key1,
					key2,
					key3);

		  comboBox = new ComboBox(options);
		  
	  }
	  else {
		  
		  ObservableList<String> options = FXCollections.observableArrayList(
					key1,
					key2,
					key3,
					key4);

		  comboBox = new ComboBox(options);
		  
	  }
		
	  
	  comboBox.getSelectionModel().selectFirst();
	  
	  //Creates a file and prints prizes. 
	  try {
		  
		  PrintWriter file = new PrintWriter("LabOneTextFile.txt", "UTF-8");
		  
		  file.println("Key:\tValue:");
		  file.println(key1+"\t"+prizesHM.get(key1));
		  file.println(key2+"\t"+prizesHM.get(key2));
		  file.println(key3+"\t"+prizesHM.get(key3));
		  file.println(key4+"\t"+prizesHM.get(key4));
		  
		  file.close();
		  
	  }catch(Exception e){
		
		  System.out.println("Failed to create File + " + e);
		  
	  }

	  
	  Button selectPrizeButton = new Button("Select Prize");
	  Label userPrize = new Label("");
	  selectPrizeButton.setOnAction(e -> {
		  
			  userPrize.setText("You win: "+(String) prizesHM.get(comboBox.getValue()) + ".");
			  selectPrizeButton.setDisable(true); // you can only select one prize and it disables
			  
			  // Disables game tabs once user has select prize
			  guessNumberTab.setDisable(true);
			  lottoCure.setDisable(true);
		  
	  });
		
	  mainPane.setPadding((new Insets(10)));
	  mainPane.getChildren().addAll(comboBox, selectPrizeButton, userPrize);
	  prizeTab.setContent(mainPane);
 
	  tabPane.getTabs().add(prizeTab);
	  
  }
  
  /*
   *  Creates a hbox with a quit button on it.
   */
  private static HBox hbox() {
	  
	  HBox hbox = new HBox();
	  hbox.setPadding(new Insets(15, 15, 15, 15));
	  hbox.setSpacing(10);
	  hbox.setStyle("-fx-background-color: #338899;");
	    
	  Button buttonQuit = new Button("Quit");
	  buttonQuit.setOnAction(e ->{
	    	
	    System.exit(0);
	    	
	  });
	  buttonQuit.setPrefSize(100, 20);
	    
	  hbox.getChildren().add(buttonQuit);
	    
	  return hbox;
  }
  
}