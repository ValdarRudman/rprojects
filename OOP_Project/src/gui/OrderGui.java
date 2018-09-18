package gui;

import java.util.ArrayList;

import DB.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Order;
import objects.Phone;
import objects.Product;
import objects.TV;

public class OrderGui {
	
	private Stage stage;
	private Order order;
	private GridPane grid;
	
	/*
	 * Gui to add to an order for a customer.
	 */
	public OrderGui(DBConnect connect) {

		stage = new Stage();
		stage.setTitle("Order");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.setOnCloseRequest(e-> closeProgram());
		
		Group group = new Group();
		Scene scene = new Scene( group, 450, 400, Color.WHITE );
		    
		grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setStyle("-fx-background-color: #8e8e8e;");
		grid.prefHeightProperty().bind(scene.heightProperty());
		grid.prefWidthProperty().bind(scene.widthProperty());
		grid.setAlignment(Pos.TOP_LEFT);
		
		// get all products
		ArrayList<Product> products = connect.getAllProducts();
		// add to grid pane 
		
		if(products.size() != 0) {
			for( int i = 0; i < products.size(); i ++) {
				
				Product pro = products.get(i);
				
				// adds all information for tv product to gridpane
				if(pro instanceof TV) {
					
					addTVDetailsToGridPane(pro);
					
				}
				else {// all information for phone product
					
					addPhoneDetailsToGridPane(pro);
					
				}
	
			}
			
			// get las row added to
			int maxIndex = grid.getChildren().stream().mapToInt(n -> {
				
				Integer row = GridPane.getRowIndex(n);
				Integer rowSpan = GridPane.getRowSpan(n);
	
			    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
			    
			}).max().orElse(-1);
			
			//Add submit order button at the bottom
			Button add = new Button("Sumbit Order");
			add.setOnAction(e->{
				
				order = new Order();
			
				ObservableList<Node> childrens = grid.getChildren();
				
				//goes through the nodes of gridpane
			    for (Node node : childrens) {
			    	
			    	//if node is a instance of combo box
			        if(node instanceof ComboBox) {
			        
			        	@SuppressWarnings("rawtypes")
						ComboBox com = (ComboBox)node;
			        	int amount = (int)com.getSelectionModel().getSelectedItem();
			        	
			        	// checks to see if amount is 1 or more. if so addes to order
			        	if(amount != 0) {
			        		
				        	int row = GridPane.getRowIndex(node);
				        	
				        	// get node next to combo box. will be the product id
				        	Node nodeId = getNodeByRowColumnIndex(row, 1, grid);
				        	
				        	Label labelId = (Label)nodeId;
				      
				        	int valueId = Integer.parseInt(labelId.getText());
				      
				        	// goes through products 
				        	for(int i = 0; i < products.size(); i++) {
				        		
				        		// if product id ==  value from the label will add it to orders
				        		if(valueId == products.get(i).getId()) {
				        		
				        			order.add(products.get(i), amount);				
				        			
				        		}// end if inner if
				        		
				        	}// end of inner for
				        	
			        	}// end of middle if
			        	
			        }// end of outer if
			        
			    }// end of outer for loop
			    
			    stage.close();
			    
			});
			GridPane.setConstraints( add, 1, maxIndex + 1 );
			
			grid.getChildren().add(add);
			
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
			
			AlertBox.display("No Products", "No Products in database");
			
		}
		
	}
	
	/*
	 * Method runs when user click the x button
	 */
	private void closeProgram() {

		order = null;
		stage.close();
		
	}

	public Order getOrder() {
		
		return order;
		
	}

	/*
	 * Creates a combo box with values between 0 - 100
	 */
	private ComboBox<Integer> createComboBox() {
		
		ObservableList<Integer> options = FXCollections.observableArrayList();

		for(int i = 0; i < 101; i++) {
			
			options.add(i);
			
		}
		
		ComboBox<Integer> combo = new ComboBox<Integer>(options);
		
		combo.getSelectionModel().selectFirst();
		
		return combo;
		
	}
	
	/*
	 * returns node from gridpane based on row and column index
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
	 * Adds Tv product details to gridpane
	 */
	private void addTVDetailsToGridPane(Product p) {
		
		int maxIndex = grid.getChildren().stream().mapToInt(n -> {
			
			Integer row = GridPane.getRowIndex(n);
			Integer rowSpan = GridPane.getRowSpan(n);

		    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
		    
		}).max().orElse(-1);
		
		TV tv = (TV)p;
		
		Label tvLabel = new Label("TV ID:");
		tvLabel.setFont(Font.font("Helvetica", 18));
		tvLabel.setTextFill(Color.BLACK);
		GridPane.setConstraints( tvLabel, 0, maxIndex + 1);
		
		Label tvID = new Label(Integer.toString(tv.getId()));
		tvID.setFont(Font.font("Helvetica", 18));
		tvID.setTextFill(Color.BLACK);
		GridPane.setConstraints( tvID, 1, maxIndex + 1);			
		
		ComboBox<Integer> count = createComboBox();
		GridPane.setConstraints( count, 2, maxIndex + 1 );
		
		Label make = new Label("Make:");
		make.setFont(Font.font("Helvetica", 14));
		make.setTextFill(Color.BLACK);
		GridPane.setConstraints( make, 1, maxIndex + 2 );
		
		Label makeValue = new Label(tv.getMake());
		makeValue.setFont(Font.font("Helvetica", 14));
		makeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( makeValue, 2, maxIndex + 2 );
		
		Label model = new Label("Model:");
		model.setFont(Font.font("Helvetica", 14));
		model.setTextFill(Color.BLACK);
		GridPane.setConstraints( model, 1, maxIndex + 3 );
		
		Label modelValue = new Label(tv.getModel());
		modelValue.setFont(Font.font("Helvetica", 14));
		modelValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( modelValue, 2, maxIndex + 3 );
		
		Label price = new Label("Price:");
		price.setFont(Font.font("Helvetica", 14));
		price.setTextFill(Color.BLACK);
		GridPane.setConstraints( price, 1, maxIndex + 4 );
		
		Label priceValue = new Label(Double.toString(tv.getPrice()));
		priceValue.setFont(Font.font("Helvetica", 14));
		priceValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( priceValue, 2, maxIndex + 4 );
		
		Label screenSize = new Label("SreenSize(IN):");
		screenSize.setFont(Font.font("Helvetica", 14));
		screenSize.setTextFill(Color.BLACK);
		GridPane.setConstraints( screenSize, 1, maxIndex + 5 );
		
		Label screenSizeValue = new Label(Double.toString(tv.getScreenSize()));
		screenSizeValue.setFont(Font.font("Helvetica", 14));
		screenSizeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( screenSizeValue, 2, maxIndex + 5 );
		
		Label type = new Label("Type:");
		type.setFont(Font.font("Helvetica", 14));
		type.setTextFill(Color.BLACK);
		GridPane.setConstraints( type, 1, maxIndex + 6 );
		
		Label typeValue = new Label(tv.getType());
		typeValue.setFont(Font.font("Helvetica", 14));
		typeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( typeValue, 2, maxIndex + 6 );
		
		Label threeD = new Label("3D:");
		threeD.setFont(Font.font("Helvetica", 14));
		threeD.setTextFill(Color.BLACK);
		GridPane.setConstraints( threeD, 1, maxIndex + 7 );
		
		String bool = "No";
		
		if(tv.getCapable3D()) {
			
			bool = "Yes";
			
		}
		
		Label threeDValue = new Label(bool);
		threeDValue.setFont(Font.font("Helvetica", 14));
		threeDValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( threeDValue, 2, maxIndex + 7 );
		
		Label space = new Label("");
		GridPane.setConstraints( space, 0, maxIndex + 8 );
		
		
		grid.getChildren().addAll( tvLabel, tvID, count, make, makeValue, model, modelValue, price, priceValue, screenSize, screenSizeValue, type, typeValue, threeD, threeDValue, space);
		
	}
	
	/*
	 * Add phone details to gripane
	 */
	private void addPhoneDetailsToGridPane(Product p) {
		
		Phone phone = (Phone)p;
		
		int maxIndex = grid.getChildren().stream().mapToInt(n -> {
			
			Integer row = GridPane.getRowIndex(n);
			Integer rowSpan = GridPane.getRowSpan(n);

		    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
		    
		}).max().orElse(-1);
		
		Label phoneLabel = new Label("Phone ID:");
		phoneLabel.setFont(Font.font("Helvetica", 18));
		phoneLabel.setTextFill(Color.BLACK);
		GridPane.setConstraints( phoneLabel, 0, maxIndex + 1);
		
		Label phoneID = new Label(Integer.toString(phone.getId()));
		phoneID.setFont(Font.font("Helvetica", 18));
		phoneID.setTextFill(Color.BLACK);
		GridPane.setConstraints( phoneID, 1, maxIndex + 1);			
		
		ComboBox<Integer> count = createComboBox();
		GridPane.setConstraints( count, 2, maxIndex + 1 );
		
		Label make = new Label("Make:");
		make.setFont(Font.font("Helvetica", 14));
		make.setTextFill(Color.BLACK);
		GridPane.setConstraints( make, 1, maxIndex + 2 );
		
		Label makeValue = new Label(phone.getMake());
		makeValue.setFont(Font.font("Helvetica", 14));
		makeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( makeValue, 2, maxIndex + 2 );
		
		Label model = new Label("Model:");
		model.setFont(Font.font("Helvetica", 14));
		model.setTextFill(Color.BLACK);
		GridPane.setConstraints( model, 1, maxIndex + 3 );
		
		Label modelValue = new Label(phone.getModel());
		modelValue.setFont(Font.font("Helvetica", 14));
		modelValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( modelValue, 2, maxIndex + 3 );
		
		Label price = new Label("Price:");
		price.setFont(Font.font("Helvetica", 14));
		price.setTextFill(Color.BLACK);
		GridPane.setConstraints( price, 1, maxIndex + 4 );
		
		Label priceValue = new Label(Double.toString(phone.getPrice()));
		priceValue.setFont(Font.font("Helvetica", 14));
		priceValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( priceValue, 2, maxIndex + 4 );
		
		Label storage = new Label("Storage(GB):");
		storage.setFont(Font.font("Helvetica", 14));
		storage.setTextFill(Color.BLACK);
		GridPane.setConstraints( storage, 1, maxIndex + 5 );
		
		Label storageValue = new Label(phone.getStorage());
		storageValue.setFont(Font.font("Helvetica", 14));
		storageValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( storageValue, 2, maxIndex + 5 );
		
		Label space = new Label("");
		GridPane.setConstraints( space, 0, maxIndex + 6 );
		
		grid.getChildren().addAll( phoneLabel, phoneID, count, make, makeValue, model, modelValue, price, priceValue, storage, storageValue, space);
		
	}
	
}
