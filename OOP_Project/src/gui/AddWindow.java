package gui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Customer;
import objects.Phone;
import objects.Product;
import objects.TV;

public class AddWindow {
	
	private GridPane grid;
	
	//ArrayList will contain all the textfield values in the order they appear
	private ArrayList<String> values = new ArrayList<String>();
	private Product p;
	private Customer cus;

	/*
	 * Creates a new Window with a gridPane and the title passed through
	 */
	public AddWindow(GridPane grid, String title) {
		
		// setting up stage and its actions
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		
		Group group = new Group();
		Scene scene = new Scene( group, 400, 400, Color.WHITE );
		    
		this.grid = grid;
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setStyle("-fx-background-color: #8e8e8e;");
		grid.setAlignment(Pos.TOP_CENTER);
		grid.prefHeightProperty().bind(scene.heightProperty());
		grid.prefWidthProperty().bind(scene.widthProperty());

		//Get the last row number of the gridpane
		int maxIndex = grid.getChildren().stream().mapToInt(n -> {
			
			Integer row = GridPane.getRowIndex(n);
			Integer rowSpan = GridPane.getRowSpan(n);

		    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
		    
		}).max().orElse(-1);
		
		// With the action we check to see if all the inputs in text field are valid and add it to an arrayList.
		Button add = new Button( "Add" );
		add.setOnAction(e ->{
			
			ObservableList<Node> childrens = grid.getChildren();
			int count = 0;// count to check that there is a product or customer. if count does not match values.size will set product and customer to null
			int choice = 0; // choice is to call the correct if. choice 1 = phone while 3 = customer
			
		
			if(title.equals("New Phone")) {
				
				count = phoneInfo(count, childrens);
				choice = 1;
				
			}
			else if(title.equals("New TV")){
				
				count = tvInfo(count, childrens);
				choice = 2;
				
			}
			else {
				
				count = customerInfo(count, childrens);
				choice = 3;
				
			}
			
			//check values size == count
			if(values.size() == count) {
				
				// if choice 1 set product p to a phone product and adds vales
				if(choice == 1) {
					
					p = (Phone) new Phone(0, values.get(0), values.get(1), Double.parseDouble(values.get(2)), values.get(3));
					
				}//choice 2 for tv
				else if(choice == 2) {//choice 2 for tv
					
					boolean bool = false;
					
					if(values.get(5).equals("Yes")) {
						System.out.println("dsdddsad");
						bool = true;
						
					}
					
					p = (TV) new TV(0, values.get(0), values.get(1), Double.parseDouble(values.get(2)), Double.parseDouble(values.get(3)), values.get(4), bool);
					
				}
				else if(choice == 3) {// choice 3 for customer
					
					cus = new Customer(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
					
				}
				
				stage.close();
				
			}
			else {
				
				p = null;
				cus = null;
				values.clear();// clear so any previous values wont be there adding again. If you enter one value and click add. It will fail but that one value will be in the values list. this removes that value
				
			}
			
		});
		grid.addRow( maxIndex + 1, add ); // adds the button after the last row in gridpane
		
		group.getChildren().add(grid);
		stage.setScene( scene );
		stage.showAndWait();
		
	}
	
	
	public Product getP() {
		return p;
	}

	public Customer getCus() {
		return cus;
	}
	
	public GridPane getGrid() {
		
		return this.grid;
		
	}
	
	public ArrayList<String> getValues() {
		
		return this.values;
		
	}
	
	/*
	 * Check input is contains only letters
	 */
	private boolean checkValidAlpha(String check) {
    	
    	return check.matches("[a-zA-Z]+");	
    	
    }
	
	/*
	 * check input only contains numbers
	 */
	private boolean checkValidNum(String check) {
		
		return check.matches("[.0-9]+");
	}
	
	/*
	 * check string is at least 1 in length
	 */
	private boolean checkStringLength(String check) {
		
		
		return check.length() > 0;
		
	}
	
	/*
	 * get a node based on row and column value
	 */
	private Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
		
	    Node result = null;
	    
	    ObservableList<Node> childrens = gridPane.getChildren();

	    for (Node node : childrens) {
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	/*
	 * Gets the phone information and creates a phone object if information valid
	 */
	private int phoneInfo(int count, ObservableList<Node> childrens) {
		
		int row = 0;
		
		// go through the nodes on a grid pane
		for(Node node : childrens) {
			 
			//if node is a textfield will check it
			if(node instanceof TextField) {
				
				TextField textField = ( TextField )node;
				
				Label lab = (Label)getNodeByRowColumnIndex(row, 0, grid);
				row++;

				if(checkValidAlpha(textField.getText()) && lab.getText().equals("Price:") == false && lab.getText().equals("Storage:") == false && lab.getText().trim().equals("Model:") == false) {
		
					values.add(textField.getText());
					count++;
							
				}
				else if(checkValidNum(textField.getText()) && lab.getText().equals("Price:")) {

					values.add(textField.getText());
					count++;
					
				}
				else if(checkValidNum(textField.getText()) && lab.getText().equals("Storage:")) {

					values.add(textField.getText());
					count++;
					
				}
				else if(checkStringLength(textField.getText())) {
					
					values.add(textField.getText());
					count++;
					
				}
				else{// display an alertbox informing user that information inputted not valied
					
					AlertBox.display( "Not Valid", "Not a Valid Input" );
					count++;
					break;
					
				}
				
			}
		}
	
		return count;
	}
	
	/*
	 * Gets the TV information and creates a TV object if information valid
	 */
	private int tvInfo(int count, ObservableList<Node> childrens ) {
		
		// Works the same way as phoneinfo
		
		int row = 0;
		
		for(Node node : childrens) {
			
			if(node instanceof TextField) {
				
				TextField textField = ( TextField )node;
				
				Label lab = (Label)getNodeByRowColumnIndex(row, 0, grid);
				row++;

				if(checkValidAlpha(textField.getText()) && lab.getText().equals("Price:") == false && lab.getText().equals("Screen Size:") == false && lab.getText().trim().equals("Model:") == false) {
		
					values.add(textField.getText());
					count++;
							
				}
				else if(checkValidNum(textField.getText()) && lab.getText().equals("Price:")) {
				
					values.add(textField.getText());
					count++;
					
				}
				else if(checkValidNum(textField.getText()) && lab.getText().equals("Screen Size:")) {
				
					values.add(textField.getText());
					count++;
					
				}
				else if(checkStringLength(textField.getText())) {
					
					values.add(textField.getText());
					count++;
					
				}
				else{
					
					AlertBox.display( "Not Valid", "Not a Valid Input" );
					count++;
					break;
					
				}
				
			}
			else if(node instanceof ComboBox) { //Contains a combobox to select yes or no for 3d capable
				
				count++;
				
				@SuppressWarnings({ "rawtypes", "unchecked" })
				ComboBox<String> combo = (ComboBox)node;
				String value = combo.getValue();
				values.add(value);
				
			}
			
		}
		
		return count;
		
	}
	
	/*
	 * Gets the Customers information and creates a customer object if information valid
	 */
	private int customerInfo(int count, ObservableList<Node> childrens) {
		
		int row = 0;
		
		for(Node node : childrens) {
			
			if(node instanceof TextField) {
				
				TextField textField = ( TextField )node;
				
				Label lab = (Label)getNodeByRowColumnIndex(row, 0, grid);
				row++;
				
				if(checkValidAlpha(textField.getText()) && lab.getText().equals("Street:") == false) {
		
					values.add(textField.getText());
					count++;
							
				}
				else if(checkStringLength(textField.getText()) && lab.getText().equals("Street:")) {
					
					values.add(textField.getText());
					count++;
					
				}
				else{
					
					AlertBox.display( "Not Valid", "Not a Valid Input" );
					count++;
					break;
					
				}
				
			}
		}
		
		return count;
	}
	
}
