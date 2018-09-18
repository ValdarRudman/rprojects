package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PickBox {
	
	private static String value;
	
	/*
	 * Displays a window that allows the user to pick between Phone or tv. Used in conjuction with add product
	 */
	public static String display() {
    	
		value = null;
		
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Pick Device");
        window.setMinWidth(250);
        window.setResizable(true);
        
        Button phone = new Button("Phone");
        phone.setOnAction(e -> {
        	
        	value = "Phone";
        	
        	window.close();
        
        });

        Button tv = new Button("TV");
        tv.setOnAction(e -> {
        	
        	value = "TV";
        	
        	window.close();
        			
        });
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(phone, tv);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #8e8e8e;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        
      //Display window and wait for it to be closed before returning
        window.showAndWait();
        
        return value;
        
	}
}
