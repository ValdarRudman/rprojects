package gameOfLife;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameOfLife extends Application implements Runnable{
	
	private int mainSizeX = 800; // size of x-axis for canvas
	private int boxSize = 10; // size boxes on canvas
	private int mainSizeY = 800; // size of y-axis for canvas
	private Box[][] boxes; // array of all the boxes on the canvas
	private Thread thread;
	private boolean running = false;
	
	private final long MAX_TICK = 100000000;
	private int tickRate = 0;

	public static void main(String[] args) {
		
		launch(args);
		
	}
	/*
	 * Start the game and the thread
	 */
	public void start() {
		 
		running = true;
		thread = new Thread(this, "Game of Life");
		thread.start();

	}
	/*
	 * Create a Canvas of x and y size and create x number of boxes
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		boxes = new Box[mainSizeX / boxSize][mainSizeX / boxSize]; // creates x number of boxes

		for(int i = 0; i < mainSizeX / boxSize; i++) {
			
			for(int j =0; j < mainSizeY / boxSize; j++) {
				
				Box box = new Box();
				box.value = (int) Math.round(Math.random()); // assign a random number to the box and then add it to the array
				
				boxes[i][j] = box;
				
			}
			
		}
		
		
		stage.setScene(new Scene(createMainScene()));
		stage.show();
		
		start();
		
	}
	
	public void stop(){
		
	}
	
	@Override
	public void run() {
		
		while(running) {
			
			tick();
		}
		
		
	}
	
	public void tick() {
		
		if(tickRate < MAX_TICK) { // increments tick value
			
			tickRate++;
			
			
		}
		else {
			updateBoxes(); // when tick value equals max tick calls updateBoxes
			tickRate = 0;
		}
		
	}
	
	private Parent createMainScene() {
		
		Pane root = new Pane();
		root.setPrefSize(mainSizeX, mainSizeY);
		
		for(int i = 0; i < boxes.length; i++) {
		
			for(int j = 0; j < boxes[i].length; j++) {
				
				Box box = boxes[i][j];
				box.setTranslateX(i * boxSize);
				box.setTranslateY(j * boxSize);
				box.setBackground();
				
				root.getChildren().add(box);
	
			}
			
		}
		
		return root;
		
	}
	
	private void updateBoxes(){
		
		Box[][] temp = boxes.clone();
		int state;
		int neighbours;
		
		// go through all boxes.
		for(int i = 0; i < temp.length; i++) {
			
			for(int j = 0; j < temp[i].length; j++) {
				
				state = temp[i][j].value;
	
				neighbours = count(temp, i, j);

				// set value of current box selected based on its state and neighbouring boxes
				if(state == 0 && neighbours == 3) {
					
					this.boxes[i][j].value = 1;
					this.boxes[i][j].setBackground();
					
					
				}
				else if(state == 1 && (neighbours < 2 || neighbours > 3)) { // sets value of box to zero if conditions are met
					
					this.boxes[i][j].value = 0;
					
				}
				else {
					
					this.boxes[i][j].value = state;
					
				}
				
			}
			
		}
		
		for(int i = 0; i < boxes.length; i++) {
			
			for(int j = 0; j < boxes[i].length; j++) {
				
				boxes[i][j].setBackground();
				
			}
		}
		
	}
	
	/*
	 * Checks to see what value the boxes are around the selected box
	 */
	private int count(Box[][] boxes, int x, int y) {
		
		int sum = 0;
		int cols = boxes.length;
		
		for(int i = -1; i < 3; i++) {
			
			for(int j = -1; j < 3; j++) {
				
				sum+= boxes[(i + x + cols) % cols][(j + y + cols) % cols].value;
				
			}
			
		}
			
		return sum;
		
	}
	 
	/*
	 * Create a box containing a value of 1 or 0
	 */
	private class Box extends StackPane{
		
		private int value;
		private Rectangle box;
		
		public Box() {
			
			box = new Rectangle(boxSize, boxSize);
			box.setFill(Color.BLUE);
			box.setStroke(Color.BLACK);
			
			this.setAlignment(Pos.CENTER);
			this.getChildren().addAll(box);
			
		}
				
		public void setBackground() {
		
			if(value == 0) {
				
				box.setFill(Color.BLUE);
				//this.setStyle("-fx-background-color: #FFFFFF;");
				
			}
			else if(value == 1) {
				
				box.setFill(Color.RED);
				//this.setStyle("-fx-background-color: #000000;");;
				
			}
			
		}
		
	}

}
