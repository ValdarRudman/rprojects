package gui;

import java.util.ArrayList;

import DB.DBConnect;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Customer;

public class SelectCustomer {
	
	private Stage stage;
	private Customer cus = null;

	/*
	 * Allows user to select a customer from the database
	 */
	public SelectCustomer(DBConnect connect) {
		
		stage = new Stage();
		stage.setTitle("Select Customer");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.setOnCloseRequest(e-> closeProgram());
		
		Group group = new Group();
		Scene scene = new Scene( group, 450, 400, Color.WHITE );
		    
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setStyle("-fx-background-color: #8e8e8e;");
		grid.prefHeightProperty().bind(scene.heightProperty());
		grid.prefWidthProperty().bind(scene.widthProperty());
		grid.setAlignment(Pos.TOP_LEFT);
		
		ArrayList<Customer> customers = connect.getCustomers();
		
		if(customers.size() != 0) {
			
			// For the length of customers. displays each customer on the gui
			for(int i = 0; i < customers.size(); i++) {
				
				Customer customer = customers.get(i);
				
				// Gets the last row index a node is at. if node at row 1. will return 1
				int maxIndex = grid.getChildren().stream().mapToInt(n -> {
					
					Integer row = GridPane.getRowIndex(n);
					Integer rowSpan = GridPane.getRowSpan(n);
	
				    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
				    
				}).max().orElse(-1);
				
				// add all customer information
				Label customerTitle = new Label("Customer ID:");
				customerTitle.setFont(Font.font("Helvetica", 15));
				customerTitle.setTextFill(Color.BLACK);
				GridPane.setConstraints( customerTitle, 0, maxIndex + 1);
				
				Label customerId = new Label(Integer.toString(customer.getId()));
				customerId.setFont(Font.font("Helvetica", 15));
				customerId.setTextFill(Color.BLACK);
				GridPane.setConstraints( customerId, 1, maxIndex + 1);
				
				Button select = new Button("Select");
				select.setOnAction(e->{
					
					Node n = (Node)e.getSource();// get nodes source
					
					int row = GridPane.getRowIndex(n); // get nodes row
					
					Label id = (Label)getNodeByRowColumnIndex(row, 1, grid);// get label next to node
					
					for(int j = 0; j < customers.size(); j++) {
						
						if(customers.get(j).getId() == Integer.parseInt(id.getText())) {// if the number form label node we got(id) matchs the id a customer in the customers list will assign that customer to the global cus variable
							
							cus = customers.get(j);
							break;
							
						}
						
					}
					
					stage.close();
					
				});
				GridPane.setConstraints( select, 2, maxIndex + 1);
				
				// Keep adding customer information
				Label name = new Label("Name:");
				name.setFont(Font.font("Helvetica", 14));
				name.setTextFill(Color.BLACK);
				GridPane.setConstraints( name, 1, maxIndex + 2);
				
				Label nameValue = new Label(customers.get(i).getFName() + " " + customers.get(i).getLName());
				nameValue.setFont(Font.font("Helvetica", 14));
				nameValue.setTextFill(Color.BLACK);
				GridPane.setConstraints( nameValue, 2, maxIndex + 2);
				
				Label street = new Label("Street");
				street.setFont(Font.font("Helvetica", 14));
				street.setTextFill(Color.BLACK);
				GridPane.setConstraints( street, 1, maxIndex + 3);
				
				Label streetValue = new Label(customers.get(i).getStreet());
				streetValue.setFont(Font.font("Helvetica", 14));
				streetValue.setTextFill(Color.BLACK);
				GridPane.setConstraints( streetValue, 2, maxIndex + 3);
				
				Label city = new Label("City");
				city.setFont(Font.font("Helvetica", 14));
				city.setTextFill(Color.BLACK);
				GridPane.setConstraints( city, 1, maxIndex + 4);
				
				Label cityValue = new Label(customers.get(i).getCity());
				cityValue.setFont(Font.font("Helvetica", 14));
				cityValue.setTextFill(Color.BLACK);
				GridPane.setConstraints( cityValue, 2, maxIndex + 4);
				
				Label county = new Label("County");
				county.setFont(Font.font("Helvetica", 14));
				county.setTextFill(Color.BLACK);
				GridPane.setConstraints( county, 1, maxIndex + 5);
				
				Label countyValue = new Label(customers.get(i).getCounty());
				countyValue.setFont(Font.font("Helvetica", 14));
				countyValue.setTextFill(Color.BLACK);
				GridPane.setConstraints( countyValue, 2, maxIndex + 5);
				
				grid.getChildren().addAll(customerTitle, customerId, select, name, nameValue, street, streetValue, city, cityValue, county, countyValue);
				
			}
			
			//Creates a scroll pane where the grid pane will be in
			ScrollPane scroll = new ScrollPane();

			scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroll.setFitToWidth(true);
			scroll.setContent(grid);
			
			BorderPane border = new BorderPane();
			border.prefHeightProperty().bind(scene.heightProperty());
	        border.prefWidthProperty().bind(scene.widthProperty());
			
			border.setCenter(scroll);
			
			group.getChildren().add(border);
			stage.setScene( scene );
			stage.showAndWait();

		}
		else {
			
			AlertBox.display("No Customer", "No Customers");
			
		}
		
	}
	
	public Customer getCus() {
		
		return cus;
	
	}

	/*
	 * get node by row and column fron gridpane
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
	 * Runs when X button clicked by user
	 */
	private void closeProgram() {
		
		stage.close();
		
	}
	
}
