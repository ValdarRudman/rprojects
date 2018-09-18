package gui;

import java.util.ArrayList;

import DB.DBConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Phone;
import objects.Product;
import objects.TV;

public class ReadWindow {
	
	private GridPane grid;

	private int yValue = 0;
	private int xValue = 0;
	private int MAX_Y_VALUE = 8;
	
	/*
	 * Display all products in the database
	 */
	public ReadWindow(DBConnect connect) {
		
		Stage stage = new Stage();
		stage.setTitle("Products");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		Group group = new Group();
		Scene scene = new Scene( group, 1200, 800, Color.WHITE );
		    
		grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setStyle("-fx-background-color: #8e8e8e;");
		grid.prefHeightProperty().bind(scene.heightProperty());
		grid.prefWidthProperty().bind(scene.widthProperty());
	
		// gets all products from the database
		ArrayList<Product> products = connect.getAllProducts();
	
		if(products.size() != 0) {

			
			// goes through list of products and adds them to the grid pane. 
			for( int i = 0; i < products.size(); i ++) {
				
				Product pro = products.get(i);
				
				if(pro instanceof TV) {
					
					addTVDetailsToGridPane(pro);

					
				}
				else {
					
					addPhoneDetailsToGridPane(pro);
					
				}
				
			}

			// Adds gridpane to a scroll pane
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
			
			AlertBox.display("No Products", "No products in database");
		}

	}
	
	/*
	 * Create a  window that will show a product from a database based on the product id
	 */
	public ReadWindow(DBConnect connect, int value) {
		
		Stage stage = new Stage();
		stage.setTitle("Product");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		Group group = new Group();
		Scene scene = new Scene( group, 400, 400, Color.WHITE );
		    
		grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(20);
		grid.setHgap(50);
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setStyle("-fx-background-color: #8e8e8e;");
		grid.prefHeightProperty().bind(scene.heightProperty());
		grid.prefWidthProperty().bind(scene.widthProperty());
		
		//get product wil the product id
		Product pro = connect.getProductInfo(value);
		
		//checks to see if product is an instance of tv or phone
		if(pro instanceof TV) {
			
			addTVDetailsToGridPane(pro);
			
		}
		else {
			
			addPhoneDetailsToGridPane(pro);
			
		}

		group.getChildren().add(grid);
		stage.setScene( scene );
		stage.showAndWait();
		
	}
	
	/*
	 * Adds Tv product details to gridpane
	 */
	private void addTVDetailsToGridPane(Product p) {
		
		if(yValue == 0) {
			
			this.xValue = getLastRow();
			
		}
		
		TV tv = (TV)p;
		
		Label tvLabel = new Label("TV:");
		tvLabel.setFont(Font.font("Helvetica", 18));
		tvLabel.setTextFill(Color.BLACK);
		GridPane.setConstraints( tvLabel, yValue, xValue + 1);
		
		Label make = new Label("Make:");
		make.setFont(Font.font("Helvetica", 14));
		make.setTextFill(Color.BLACK);
		GridPane.setConstraints( make, yValue + 1, xValue + 2 );
		
		Label makeValue = new Label(tv.getMake());
		makeValue.setFont(Font.font("Helvetica", 14));
		makeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( makeValue, yValue + 2, xValue + 2 );
		
		Label model = new Label("Model:");
		model.setFont(Font.font("Helvetica", 14));
		model.setTextFill(Color.BLACK);
		GridPane.setConstraints( model, yValue + 1, xValue + 3 );
		
		Label modelValue = new Label(tv.getModel());
		modelValue.setFont(Font.font("Helvetica", 14));
		modelValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( modelValue, yValue + 2, xValue + 3 );
		
		Label price = new Label("Price:");
		price.setFont(Font.font("Helvetica", 14));
		price.setTextFill(Color.BLACK);
		GridPane.setConstraints( price, yValue + 1, xValue + 4 );
		
		Label priceValue = new Label(Double.toString(tv.getPrice()));
		priceValue.setFont(Font.font("Helvetica", 14));
		priceValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( priceValue, yValue + 2, xValue + 4 );
		
		Label screenSize = new Label("SreenSize(IN):");
		screenSize.setFont(Font.font("Helvetica", 14));
		screenSize.setTextFill(Color.BLACK);
		GridPane.setConstraints( screenSize, yValue + 1, xValue + 5 );
		
		Label screenSizeValue = new Label(Double.toString(tv.getScreenSize()));
		screenSizeValue.setFont(Font.font("Helvetica", 14));
		screenSizeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( screenSizeValue, yValue + 2, xValue + 5 );
		
		Label type = new Label("Type:");
		type.setFont(Font.font("Helvetica", 14));
		type.setTextFill(Color.BLACK);
		GridPane.setConstraints( type, yValue + 1, xValue + 6 );
		
		Label typeValue = new Label(tv.getType());
		typeValue.setFont(Font.font("Helvetica", 14));
		typeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( typeValue, yValue + 2, xValue + 6 );
		
		Label threeD = new Label("3D:");
		threeD.setFont(Font.font("Helvetica", 14));
		threeD.setTextFill(Color.BLACK);
		GridPane.setConstraints( threeD, yValue + 1, xValue + 7 );
		
		String bool = "No";
		
		if(tv.getCapable3D()) {
			
			bool = "Yes";
			
		}
		
		Label threeDValue = new Label(bool);
		threeDValue.setFont(Font.font("Helvetica", 14));
		threeDValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( threeDValue, yValue + 2, xValue + 7 );
		
		Label space = new Label("");
		GridPane.setConstraints( space, yValue, xValue + 8 );
		
		checkYValue();
		
		grid.getChildren().addAll( tvLabel, make, makeValue, model, modelValue, price, priceValue, screenSize, screenSizeValue, type, typeValue, threeD, threeDValue,space);
		
	}
	
	/*
	 * Add phone details to gripane
	 */
	private void addPhoneDetailsToGridPane(Product p) {
		
		Phone phone = (Phone)p;
		
		if(yValue == 0) {
			
			this.xValue = getLastRow();
			
		}
		
		Label phoneLabel = new Label("Phone:");
		phoneLabel.setFont(Font.font("Helvetica", 18));
		phoneLabel.setTextFill(Color.BLACK);
		GridPane.setConstraints( phoneLabel, yValue + 0, xValue + 1);
		
		Label make = new Label("Make:");
		make.setFont(Font.font("Helvetica", 14));
		make.setTextFill(Color.BLACK);
		GridPane.setConstraints( make, yValue + 1, xValue + 2 );
		
		Label makeValue = new Label(phone.getMake());
		makeValue.setFont(Font.font("Helvetica", 14));
		makeValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( makeValue, yValue + 2, xValue + 2 );
		
		Label model = new Label("Model:");
		model.setFont(Font.font("Helvetica", 14));
		model.setTextFill(Color.BLACK);
		GridPane.setConstraints( model, yValue + 1, xValue + 3 );
		
		Label modelValue = new Label(phone.getModel());
		modelValue.setFont(Font.font("Helvetica", 14));
		modelValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( modelValue, yValue + 2, xValue + 3 );
		
		Label price = new Label("Price:");
		price.setFont(Font.font("Helvetica", 14));
		price.setTextFill(Color.BLACK);
		GridPane.setConstraints( price, yValue + 1, xValue + 4 );
		
		Label priceValue = new Label(Double.toString(phone.getPrice()));
		priceValue.setFont(Font.font("Helvetica", 14));
		priceValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( priceValue, yValue + 2, xValue + 4 );
		
		Label storage = new Label("Storage(GB):");
		storage.setFont(Font.font("Helvetica", 14));
		storage.setTextFill(Color.BLACK);
		GridPane.setConstraints( storage, yValue + 1, xValue + 5 );
		
		Label storageValue = new Label(phone.getStorage());
		storageValue.setFont(Font.font("Helvetica", 14));
		storageValue.setTextFill(Color.BLACK);
		GridPane.setConstraints( storageValue, yValue + 2, xValue + 5 );
		
		Label space = new Label("");
		GridPane.setConstraints( space, yValue, xValue + 6 );
		
		checkYValue();
		
		grid.getChildren().addAll( phoneLabel, make, makeValue, model, modelValue, price, priceValue, storage, storageValue, space);
		
	}
	
	/* 
	 * Checks yValue. Resets when equal to 12 else adds 4
	 */
	private void checkYValue() {
		
		if(this.yValue == this.MAX_Y_VALUE) {
		
			this.yValue = 0;
			
		}
		else {
			
			this.yValue += 4;
			
		}
		
	}
	
	private int getLastRow() {
		
		return grid.getChildren().stream().mapToInt(n -> {
			
			Integer row = GridPane.getRowIndex(n);
			Integer rowSpan = GridPane.getRowSpan(n);

		    return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
		    
		}).max().orElse(-1);
		
	}

}
