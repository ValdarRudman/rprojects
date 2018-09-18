package gui;

import java.util.ArrayList;
import java.util.Map;
import DB.DBConnect;
import javafx.application.Application;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Customer;
import objects.Order;
import objects.Phone;
import objects.Product;
import objects.TV;

public class Gui extends Application{
	
	private static DBConnect connect = new DBConnect();
	
	private BorderPane border;
	private GridPane grid;
	
	public static void main(String[] args) {
		  
	    Application.launch(args);
	    
	  }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group root = new Group();
		Scene scene = new Scene(root, 450, 400, Color.WHITE);
		border = new BorderPane();
		
		border.prefHeightProperty().bind(scene.heightProperty());
		border.prefWidthProperty().bind(scene.widthProperty());
		
		HBox top = hbox();
		
		border.setTop(top);
		
		grid = new GridPane();
		grid.setStyle("-fx-background-color: #8e8e8e;");
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setAlignment(Pos.TOP_CENTER);
		
		loadGrid();
		
		root.getChildren().add(border);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	/*
	 * Load the grid for the start window
	 */
	public void loadGrid() {
		
		Label search = new Label( "Search for product by ID: " );
		search.setFont(Font.font("Helvetica", 14));
		search.setTextFill(Color.BLACK);
		GridPane.setConstraints( search, 0, 0 );
		
		ComboBox<Integer> productIDs = createComboBox();
		GridPane.setConstraints( productIDs, 1, 0 );
		
		Button productIDSelect = new Button( "Select" );
		productIDSelect.setOnAction(e->{
			
			ObservableList<Node> childrens = grid.getChildren();
			
			// Goes though nodes on gridpane
			for(Node node: childrens) {
				
				// if node is a cmbo box
				if(node instanceof ComboBox) {
					
					@SuppressWarnings("rawtypes")
					ComboBox combo = (ComboBox)node;
					
					int value = (int)combo.getValue();
		
					// if value is 0 will display alertbox telling user no products in database
					if(value > 0) {
						
						new ReadWindow(connect, value);
					}
					else {
						
						AlertBox.display("No products", "No products in database");
						
					}
				}
				
			}
			
		});
		GridPane.setConstraints( productIDSelect, 2, 0 );
		
		Label allProducts = new Label( "Display All Products:" );
		allProducts.setFont(Font.font("Helvetica", 14));
		allProducts.setTextFill(Color.BLACK);
		GridPane.setConstraints( allProducts, 0, 1 );
		
		Button allPSelect = new Button("Select");
		
		// displaying all products
		allPSelect.setOnAction(e->{
			
			new ReadWindow(connect);
			
		});
		GridPane.setConstraints( allPSelect, 2, 1 );
		
		Label addOrder = new Label("Add order");
		addOrder.setFont(Font.font("Helvetica", 14));
		addOrder.setTextFill(Color.BLACK);
		GridPane.setConstraints( addOrder, 0, 3 );
		
		Button addOrderSelect = new Button("Select");
		// for adding an order
		addOrderSelect.setOnAction(e ->{
			
			SelectCustomer cusSl = new SelectCustomer(connect);
			
			Customer cus = cusSl.getCus();
			
			if(cus != null) {
				OrderGui cusOrder = new OrderGui(connect);
				
				Order order = cusOrder.getOrder();
				
				if(order != null) {
					
					Map<Integer, Product> mp = order.getOrders();
					
					connect.addOrder(cus, mp);
			
				}
			}
		});
		GridPane.setConstraints( addOrderSelect, 2, 3 );
		
		Label allCustomerOrders = new Label("Display all of customers orders");
		allCustomerOrders.setFont(Font.font("Helvetica", 14));
		allCustomerOrders.setTextFill(Color.BLACK);
		GridPane.setConstraints(allCustomerOrders, 0, 4);
		
		//show customers orders. use customer id
		Button allCusButton = new Button("Select");
		// display all of a customers orders
		allCusButton.setOnAction(e -> {
			
			SelectCustomer selCus = new SelectCustomer(connect);
			
			Customer cus = selCus.getCus();
		
			if(cus != null) {
			
				displayCustomersOrders(cus);
				
			}
		});
		GridPane.setConstraints(allCusButton, 2, 4);
		
		grid.getChildren().addAll( search, productIDs, productIDSelect, allProducts, allPSelect, addOrder, addOrderSelect, allCustomerOrders, allCusButton );
		
		border.setCenter(grid);
		
	}
	
	/*
	 *  Creates a hbox with a add customer and ad product button on it.
	 */
	private HBox hbox() {
		  
		
		  HBox hbox = new HBox();
		  hbox.setPadding(new Insets(15, 15, 15, 15));
		  hbox.setSpacing(10);
		  hbox.setStyle("-fx-background-color: #666666;"); 
		    
		  Button newCus = new Button("New Customer");
		  newCus.setOnAction(e ->{
			  GridPane grid = new GridPane();
			  grid.setPadding(new Insets(10, 10, 10, 10));
			  grid.setVgap(20);
			  grid.setHgap(50);
			  grid.setAlignment(Pos.CENTER);
			  
			  /*
			   * Add all the label and textfields need from the user
			   */
			  Label fName = new Label( "First Name:" );
			  fName.setFont(Font.font("Helvetica", 14));
			  fName.setTextFill(Color.BLACK);
			  GridPane.setConstraints( fName, 0, 0 );
			  
			  TextField fNameInput = new TextField();
			  GridPane.setConstraints( fNameInput, 1, 0 );
			  
			  Label lName = new Label( "Last Name:" );
			  lName.setFont(Font.font("Helvetica", 14));
			  lName.setTextFill(Color.BLACK);
			  GridPane.setConstraints( lName, 0, 1 );
			  
			  TextField lNameInput = new TextField();
			  GridPane.setConstraints( lNameInput, 1, 1 );

			  Label street = new Label( "Street:" );
			  street.setFont(Font.font("Helvetica", 14));
			  street.setTextFill(Color.BLACK);
			  GridPane.setConstraints( street, 0, 2 );
			  
			  TextField streetInput = new TextField();
			  GridPane.setConstraints( streetInput, 1, 2 );
			  
			  Label city = new Label( "City:" );
			  city.setFont(Font.font("Helvetica", 14));
			  city.setTextFill(Color.BLACK);
			  GridPane.setConstraints( city, 0, 3 );
			  
			  TextField cityInput = new TextField();
			  GridPane.setConstraints( cityInput, 1, 3 );

			  Label county = new Label( "County:" );
			  county.setFont(Font.font("Helvetica", 14));
			  county.setTextFill(Color.BLACK);
			  GridPane.setConstraints( county, 0, 4 );
			  
			  TextField countyInput = new TextField();
			  GridPane.setConstraints( countyInput, 1, 4 );
			  
			  //Add all nodes to gridpane
			  grid.getChildren().addAll( fName, fNameInput, lName, lNameInput, street, streetInput, city, cityInput, county, countyInput );
			  
			  AddWindow window = new AddWindow( grid, "New Customer" );

			  ArrayList<String> values = window.getValues();
			  
			  // if values size is greater than 0. Will contian information to add a customer
			  if(values.size() > 0) {
				  
				  Customer cus = new Customer(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
				  
				  connect.addCustomer(cus);
				  
			  }
			 
			  this.grid.getChildren().clear();
			  loadGrid();
			 
		  });
		  newCus.setPrefSize(100, 20);
		  
		  Button newProduct = new Button("New Product");
		  newProduct.setOnAction(e ->{
			  
			  GridPane grid = new GridPane();
			  grid.setPadding(new Insets(10, 10, 10, 10));
			  grid.setVgap(20);
			  grid.setHgap(50);
			  grid.setAlignment(Pos.TOP_CENTER);
			  
			  // value of what product you will be adding
			  String value = PickBox.display();

			  // based on value calls the correct method		  
			  if(value != null) {
				  
				  if(value.equals("Phone")) {
					
					  createPhone();
					  
				  }
				  else if(value.equals("TV")) {
					  
					  createTV();
					  
				  }
				  
			  }
			 
			  this.grid.getChildren().clear();
			  loadGrid();
			
		  });
		  newProduct.setPrefSize(100, 20);
		  
		    
		  hbox.getChildren().addAll(newCus, newProduct);
		    
		  return hbox;
		  
	 }
	
	/*
	 * Create a combo box with all ids of products. sets to 0 if no ids
	 */
	private ComboBox<Integer> createComboBox() {
		
		ObservableList<Integer> options = FXCollections.observableArrayList();
		
		ArrayList<Integer> values = connect.getProductCount();
		
		if(values.size() == 0) {
			
			options.add(0);
			
		}
		else {
			
			options.addAll(values);
			
		}
			
		ComboBox<Integer> combo = new ComboBox<Integer>(options);
		
		combo.getSelectionModel().selectFirst();
		
		return combo;
		
	}
	
	/*
	 *  Creates a window that allows the user to add the phone details in
	 */
	private static void createPhone() {
		
		GridPane grid = new GridPane();
		
		Label model = new Label( "Model:" );
		model.setFont(Font.font("Helvetica", 14));
		model.setTextFill(Color.BLACK);
		GridPane.setConstraints( model, 0, 0 );
		  
		TextField modelInput = new TextField();
		GridPane.setConstraints( modelInput, 1, 0 );
		  
		Label make = new Label( "Make:" );
		make.setFont(Font.font("Helvetica", 14));
		make.setTextFill(Color.BLACK);
		GridPane.setConstraints( make, 0, 1 );
		  
		TextField makeInput = new TextField();
		GridPane.setConstraints( makeInput, 1, 1 );
		  
		Label price = new Label( "Price:" );
		price.setFont(Font.font("Helvetica", 14));
		price.setTextFill(Color.BLACK);
		GridPane.setConstraints( price, 0, 2 );
		  
		TextField priceInput = new TextField();
		GridPane.setConstraints( priceInput, 1, 2 );
		
		Label storage = new Label( "Storage:" );
		storage.setFont(Font.font("Helvetica", 14));
		storage.setTextFill(Color.BLACK);
		GridPane.setConstraints( storage, 0, 3 );
		  
		TextField storageInput = new TextField();
		GridPane.setConstraints( storageInput, 1, 3 );
		  
		//Add all nodes to gridpane
		grid.getChildren().addAll( model, modelInput, make, makeInput, price, priceInput, storage, storageInput );
		  
		AddWindow window = new AddWindow( grid, "New Phone" );

		Product p = window.getP();
		  
		if(p != null) {
			
			connect.addProduct(p);
			
		}
		
	}
	
	/*
	 * Adds a window that allows the user to add the details for a tv
	 */
	private static void createTV() {
		
		GridPane grid = new GridPane();
		
		Label model = new Label( "Model:" );
		model.setFont(Font.font("Helvetica", 14));
		model.setTextFill(Color.BLACK);
		GridPane.setConstraints( model, 0, 0 );
		  
		TextField modelInput = new TextField();
		GridPane.setConstraints( modelInput, 1, 0 );
		  
		Label make = new Label( "Make:" );
		make.setFont(Font.font("Helvetica", 14));
		make.setTextFill(Color.BLACK);
		GridPane.setConstraints( make, 0, 1 );
		  
		TextField makeInput = new TextField();
		GridPane.setConstraints( makeInput, 1, 1 );
		  
		Label price = new Label( "Price:" );
		price.setFont(Font.font("Helvetica", 14));
		price.setTextFill(Color.BLACK);
		GridPane.setConstraints( price, 0, 2 );
		  
		TextField priceInput = new TextField();
		GridPane.setConstraints( priceInput, 1, 2 );
		
		Label screenSize = new Label( "Screen Size:" );
		screenSize.setFont(Font.font("Helvetica", 14));
		screenSize.setTextFill(Color.BLACK);
		GridPane.setConstraints( screenSize, 0, 3 );
		  
		TextField screenSizeInput = new TextField();
		GridPane.setConstraints( screenSizeInput, 1, 3 );
		
		Label type = new Label( "Type:" );
		type.setFont(Font.font("Helvetica", 14));
		type.setTextFill(Color.BLACK);
		GridPane.setConstraints( type, 0, 4 );
		  
		TextField typeInput = new TextField();
		GridPane.setConstraints( typeInput, 1, 4 );
		
		Label threeD = new Label( "3D:" );
		threeD.setFont(Font.font("Helvetica", 14));
		threeD.setTextFill(Color.BLACK);
		GridPane.setConstraints( threeD, 0, 5 );
		
		ObservableList<String> options = FXCollections.observableArrayList("Yes", "No");
		
		ComboBox<String> combo = new ComboBox<String>(options);
		
		combo.getSelectionModel().selectFirst();
		GridPane.setConstraints( combo, 1, 5 );
		  
		//Add all nodes to gridpane
		grid.getChildren().addAll( model, modelInput, make, makeInput, price, priceInput, screenSize, screenSizeInput, type, typeInput, threeD, combo );
		  
		AddWindow window = new AddWindow( grid, "New TV" );
		
		Product p = window.getP();
		  
		if(p != null) {
			
			connect.addProduct(p);
			
		}
		
	}
	
	/*
	 * Display orders of a selected customer
	 */
	private static void displayCustomersOrders(Customer cus) {
		
		Stage stage = new Stage();
		stage.setTitle("Customer Orders");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		Group group = new Group();
		Scene scene = new Scene( group, 500, 500, Color.WHITE );
		    
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setStyle("-fx-background-color: #8e8e8e;");
		grid.prefHeightProperty().bind(scene.heightProperty());
		grid.prefWidthProperty().bind(scene.widthProperty());
		grid.setAlignment(Pos.TOP_LEFT);
		
		// gets the orders of the selected customer
		Order order = connect.getCustomerOrders(cus);
	
		// get the hashmap.
		Map<Integer, Product> orders = order.getOrders();
	
		// if orders.size is 0 will just display no orders
		if(orders.size() != 0) {
			
			Label customer = new Label("Orders for: " + cus.getFName() +  " " + cus.getLName());
			customer.setFont(Font.font("Helvetica", 18));
			customer.setTextFill(Color.BLACK);
			GridPane.setConstraints(customer, 1 , 0);
			
			Label customerValue = new Label(cus.getFName() + " " + cus.getLName());
			customerValue.setFont(Font.font("Helvetica", 18));
			customerValue.setTextFill(Color.BLACK);
			GridPane.setConstraints(customerValue, 2 , 0);
			
			grid.getChildren().add(customer);
			
			// go through hasmap and print out customers orders
			for (Map.Entry<Integer, Product> entry : orders.entrySet()) {
				  
				  Integer amount = entry.getKey();
				
				  Product value = (Product)entry.getValue();
				  
				  int maxIndex = grid.getChildren().stream().mapToInt(n -> {
						
						Integer row = GridPane.getRowIndex(n);
						Integer rowSpan = GridPane.getRowSpan(n);

					    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
					    
					}).max().orElse(-1);
				  
				  // checks to if product is of tv or phone to display all correct information
				  if(value instanceof TV) {
					  
					  TV tv = (TV)value;
					  
					  Label tvLabel = new Label("TV:");
					  tvLabel.setFont(Font.font("Helvetica", 16));
					  tvLabel.setTextFill(Color.BLACK);
					  GridPane.setConstraints( tvLabel, 0, maxIndex + 1);
					  
					  Label orderAmount = new Label(amount + " Ordered");
					  orderAmount.setFont(Font.font("Helvetica", 14));
					  orderAmount.setTextFill(Color.BLACK);
					  GridPane.setConstraints( orderAmount, 1, maxIndex + 1);
						
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
					  
					  Label cost  = new Label("Cost €:");
					  cost.setFont(Font.font("Helvetica", 14));
					  cost.setTextFill(Color.BLACK);
					  GridPane.setConstraints( cost, 1, maxIndex + 8 );
					  
					  double totalCost = totalCost(amount, value.getPrice());
					  
					  Label costValue  = new Label(Double.toString(totalCost));
					  costValue.setFont(Font.font("Helvetica", 14));
					  costValue.setTextFill(Color.BLACK);
					  GridPane.setConstraints( costValue, 2, maxIndex + 8 );
					  
					  Label space = new Label("");
					  GridPane.setConstraints( space, 0, maxIndex + 9 );
						
					  grid.getChildren().addAll( tvLabel, make, makeValue, model, modelValue, price, priceValue, screenSize, screenSizeValue, type, typeValue, threeD, threeDValue, cost, costValue, orderAmount, space); 
					  
				  }
				  else {
					  
					  Phone phone = (Phone)value;
					  
					  Label phoneLabel = new Label("Phone:");
					  phoneLabel.setFont(Font.font("Helvetica", 16));
					  phoneLabel.setTextFill(Color.BLACK);
					  GridPane.setConstraints( phoneLabel, 0, maxIndex + 1);
					  
					  Label orderAmount = new Label(amount + " Ordered");
					  orderAmount.setFont(Font.font("Helvetica", 14));
					  orderAmount.setTextFill(Color.BLACK);
					  GridPane.setConstraints( orderAmount, 1, maxIndex + 1);
						
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
					  
					  Label cost  = new Label("Cost €:");
					  cost.setFont(Font.font("Helvetica", 14));
					  cost.setTextFill(Color.BLACK);
					  GridPane.setConstraints( cost, 1, maxIndex + 6 );
					  
					  double totalCost = totalCost(amount, value.getPrice());
					  
					  Label costValue  = new Label(Double.toString(totalCost));
					  costValue.setFont(Font.font("Helvetica", 14));
					  costValue.setTextFill(Color.BLACK);
					  GridPane.setConstraints( costValue, 2, maxIndex + 6 );
					  
					  Label space = new Label("");
					  GridPane.setConstraints( space, 0, maxIndex + 7 );
					  
					  grid.getChildren().addAll( phoneLabel, make, makeValue, model, modelValue, price, priceValue, storage, storageValue, cost, costValue, orderAmount, space);	    
					  
				}
			}
			
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
			
			AlertBox.display("No Orders!", "No Orders for selected customer");
			
		}
	
	}

	private static double totalCost(int amount, double price) {
		
		return amount * price;
		
	}
	
}
