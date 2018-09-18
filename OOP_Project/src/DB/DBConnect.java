package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import objects.Customer;
import objects.Order;
import objects.Phone;
import objects.Product;
import objects.TV;

public class DBConnect {
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	/*
	 * Constuctor for Starting connection to database
	 */
	public DBConnect(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/oop_project", "rootnew", "rootjava");
			
			st = con.createStatement();
			
			
		}
		catch( Exception e ) {
			
			System.out.println("Error: " + e);
			
		}
		
	}
	
	/*
	 * Test method to see if Connected to Database
	public void getData() {
		
		try {
			
			String query = "select * from customer";
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				String name = rs.getString("first_name") + " " + rs.getString("last_name");
				String address = rs.getString("street") + "\n" + rs.getString("city") + "\n" + rs.getString("county");
			
			}
			
		}
		catch( Exception e) {
			
			System.out.println("Error: " + e);
			
		}
		
	}*/
	
	/*
	 * Get an ArrayList of customers stored in the customer table
	 */
	public ArrayList<Customer> getCustomers() {
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		try {
			
			String query = "select * from customer";
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String fName = rs.getString("first_name");
				String lName = rs.getString("last_name");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String county = rs.getString("county");
				
				Customer cus = new Customer(fName, lName, street, city, county);
				cus.setId(id);
				customers.add(cus);
				
			}
			
		}
		catch( Exception e) {
			
			System.out.println("Error: " + e);
			System.exit(0);
			
		}
		
		return customers;
		
	}
	
	/*
	 * Add a customer to the customer table. Values are from and ArrayList. 1st value is for the 1st column and so on
	 */
	public void addCustomer(Customer cus) {
		
		try{
				
			Statement state = con.createStatement();
				
			String insert = "INSERT INTO customer VALUES (default , '" + cus.getFName() + "', '" + cus.getLName() + "', '" + cus.getStreet() + "', '" + cus.getCity() + "' , '" + cus.getCounty() + "')";
	
			state.executeUpdate(insert);
				
		}
		catch( Exception e ){
				
			System.out.println( "Error: " + e );
			System.exit(0);
				
		}
		
	}
	
	/*
	 *  Add a product to the database by passing a Product
	 */
	public void addProduct(Product p) {
		
		try{
			
			Statement state = con.createStatement();
		
			//Check what Product is to call correct query
			if(p instanceof TV) {
				
				TV tv = (TV)p;
				
				// Insert into product table
				String insert = "INSERT INTO product (id, make, model, price, pType) VALUES(null, '" + tv.getMake() + "', '" + tv.getModel() + "', " + tv.getPrice() + ", '" + "TV');";
								
				state.executeUpdate(insert);
				
				// insert into tv product table
				insert = "INSERT INTO tv_product (id, screen_size, type, 3D_capable) VALUES(LAST_INSERT_ID(), '" +  tv.getScreenSize() + "', '" + tv.getType() + "', " + tv.getCapable3D() + ");";
				
				state.executeUpdate(insert);
				
			}
			else if(p instanceof Phone) {
				
				Phone phone = (Phone)p;
				
				String insert = "INSERT INTO product (id, make, model, price, pType) VALUES(null, '" + phone.getMake() + "', '" + phone.getModel() + "', " + phone.getPrice() + ", '" + "Phone');";
		
				state.executeUpdate(insert);
				
				insert = "INSERT INTO phone_product (id, storage) VALUES(LAST_INSERT_ID(), '" + phone.getStorage()  + "');";
				
				state.executeUpdate(insert);
			}
				
		}
		catch( Exception e ){
				
			System.out.println( "Error: " + e );
			System.exit(0);
				
		}
			
	}
		
	/*
	 * Get the product ids from product table. Returns an arraylist with all the current ids in the table
	 */
	public ArrayList<Integer> getProductCount() {
		
		ArrayList<Integer> values = new ArrayList<Integer>(); 
		
		try {
			
			String query = "select id from product";
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				
				values.add(id);
				
			}
			
		}
		catch( Exception e) {
			
			System.out.println("Error: " + e);
			System.exit(0);
			
		}
		
		return values;
		
	}
	
	/*
	 * Get product information using the product ID
	 */
	public Product getProductInfo(int id) {

		Product p = new Product();;
		ArrayList<String> values = new ArrayList<String>();

		try {
			
			// Get the pType for based on id. pType e.g Phone, TV
			String query = "SELECT product.pType FROM product WHERE product.id = " + id;
	
			rs = st.executeQuery(query);
			
			String pType = "";
			
			while(rs.next()) {
				
				pType = rs.getString("pType");
			
			}
			
			// Check if pType is TV or Phone. Calls a query to pull the info of the product id
			if(pType.equals("TV")) {

				query = "SELECT product.id, product.model, product.make, product.price, tv_product.screen_size, tv_product.type, tv_product.3D_capable FROM product " + 
						"LEFT JOIN tv_product ON product.id = tv_product.id " +
						"WHERE product.id = " + id;
				
			}
			else if(pType.equals("Phone")) {
				
				query = "SELECT product.id, product.model, product.make, product.price, phone_product.storage FROM product " +
						"LEFT JOIN phone_product ON product.id = phone_product.id " + 
						"WHERE product.id = " + id;
				
			}
			
			
			rs = st.executeQuery(query);
			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount(); // gets column cout from results
		
			while(rs.next()) {
				
				for(int i = 1; i < columnCount + 1; i++) {
					
					values.add(rs.getString(i)); // add column values to an arraylist
	
				}

			}
		
			// Based on the pType Will creat a Product that will be returned
			if(pType.equals("TV")) {
				
				p = null;
				
				boolean bool = false;
				
				if(Integer.parseInt(values.get(6)) == 1) {
					
					bool = true;
					
				}
				
				p = new TV(Integer.parseInt(values.get(0)), values.get(1), values.get(2), Double.parseDouble(values.get(3)), Double.parseDouble(values.get(4)), values.get(5), bool);
			
			}
			else if(pType.equals("Phone")) {
				
				p = null;
				
				p = new Phone(Integer.parseInt(values.get(0)), values.get(1), values.get(2), Double.parseDouble(values.get(3)), values.get(4));
				
			}
			
		}
		catch( Exception e) {
			
			System.out.println("Error: " + e);
			System.exit(0);
			
		}
	
		return p;
		
	}
	
	/*
	 * Gets all the products from the database and returns it in an arraylist of products
	 */
	public ArrayList<Product> getAllProducts(){
		
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>(); // out arraylist is number of rows inner arraylist stores the column values for a row
		ArrayList<Product> products = new ArrayList<Product>(); 
		
		try {
			
			// Gets TV products first
			String query = "SELECT product.id, product.model, product.make, product.price, tv_product.screen_size, tv_product.type, tv_product.3D_capable " + 
							"FROM product " + 
							"left JOIN tv_product ON product.id = tv_product.id " + 
							"where product.pType = 'TV'";
			
			rs = st.executeQuery(query);
		
			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();   // get column count from the results
			
			int i = 0;
			
			// goes through the results
			while(rs.next()) {
				
				values.add(new ArrayList<String>()); // add a new inner arraylist for each row
				
				for(int j = 0; j < columnCount; j++) {
					// goes through the results
					
					values.get(i).add(rs.getString(j + 1)); // store each column values in inner arraylist
					
				}
				
				i++;
			}		
			
			// create and store product
			for(int j = 0; j < values.size(); j++) {
				
				boolean bool = false;
				
				if(Integer.parseInt(values.get(j).get(6)) == 1) {
				
					bool = true;
						
				}
	
				TV tv = new TV(Integer.parseInt(values.get(j).get(0)), values.get(j).get(1), values.get(j).get(2), Double.parseDouble(values.get(j).get(3)), Double.parseDouble(values.get(j).get(4)), values.get(j).get(5), bool);
			
				products.add(tv);

			}
			
			values.clear();
		
			// Gets phone products
			query = "SELECT product.id, product.model, product.make, product.price, phone_product.storage FROM product LEFT JOIN phone_product ON product.id = phone_product.id WHERE product.pType = 'Phone'";
			
			rs = st.executeQuery(query);
			
			metadata = rs.getMetaData();
			columnCount = metadata.getColumnCount();  
			
			i = 0;
			
			// goes through the results
			while(rs.next()) {
				
				values.add(new ArrayList<String>());
				
				for(int j = 0; j < columnCount; j++) {
				
					values.get(i).add(rs.getString(j + 1)); // store each column values in inner arraylist
				}
				
				i++;
			}
			
			// add product to products arraylist
			for(int j = 0; j < values.size(); j++) {
				
					
				Phone phone = new Phone(Integer.parseInt(values.get(j).get(0)), values.get(j).get(1), values.get(j).get(2), Double.parseDouble(values.get(j).get(3)), values.get(j).get(4));
				products.add(phone);

			}
			
		
		}
		catch( Exception e) {
			
			System.out.println("Error: " + e);
			System.exit(0);
			
		}

		return products;
		
	}
	
	/*
	 * Adds a customers order to ther customer_order table and order_item table
	 */
	public void addOrder(Customer cus, Map<Integer, Product> orders) {
		
		try {
			
			Statement state = con.createStatement();
			
			String insert = null;
			
			// Insert into customer order table first
	    	insert = "INSERT INTO customer_order (id, customer_id) VALUES(null, " + cus.getId() + ")";
	    	
	    	state.executeUpdate(insert);
	    	
	    	//Get latest order id
	    	String query = "Select customer_order.id FROM customer_order WHERE customer_order.customer_id = " + cus.getId();
	    	
	    	rs = st.executeQuery(query);
	    	
	    	int id = 0;
	
	    	while(rs.next()) {
	    		
	    		id = rs.getInt("id");
	    		
	    	}

	    	// We go through the map and add the order to the order item table under customer oder
			for (Map.Entry<Integer, Product> entry : orders.entrySet()) {
				
			    Integer key = entry.getKey();
			    Product value = (Product)entry.getValue();	
			    
			    insert = "INSERT INTO order_item (id, order_id, product_id, unit_price, qauntity) VALUES(null, " + id + ", " + value.getId() + ", " + value.getPrice() + ", " + key + ")";
			    	
			    state.executeUpdate(insert);
			    
			}
			
		}
		catch( Exception e) {
			
			System.out.println("Error: " + e);
			System.exit(0);
			
		}
		
	}
	
	/*
	 * Gets a selected customers orders. Returns order object
	 */
	public Order getCustomerOrders(Customer cus) {
		
		Order order = new Order();

		try {
				
			// Select all infomation needed from product table tv product table, phone product table, customer order table and order item table
			String query = "SELECT * " +
							"FROM customer_order " +
							"left join order_item on order_item.order_id = customer_order.id " +
							"left join product on product.id = order_item.product_id " +
							"left join tv_product on product.id = tv_product.id " +
							"left join phone_product on phone_product.id = product.id " +
							"WHERE customer_order.customer_id = " + cus.getId();
				
				
			rs = st.executeQuery(query);
				
			//Add the oder to an map of Order
			while(rs.next()) {
					
				if(rs.getString("pType").equals("TV")) {
						
					TV tv = new TV(rs.getInt("id"), rs.getString("model"), rs.getString("make"), rs.getDouble("price"), rs.getDouble("screen_size"), rs.getString("type"), rs.getBoolean("3D_capable"));
						
					order.add(tv, rs.getInt("qauntity"));
						
				}
				else if(rs.getString("pType").equals("Phone")) {
					
					Phone phone= new Phone(rs.getInt("id"), rs.getString("model"), rs.getString("make"), rs.getDouble("price"), rs.getString("storage"));
					
					order.add(phone, rs.getInt("qauntity"));
				}
					
			}
				
		}
		catch( Exception e ) {
				
			System.out.println("Error: " + e);
			System.exit(0);
				
		}
		
		return order;
		
	}
	
}
