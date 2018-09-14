package project;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	
	private static ArrayList<Customer> customers;
	private static ProductDB database;
	private static Scanner kb;

	public static void main(String[] args) {
		
	/*	
	 *  Test code from project file
	 *  
	 * Phone p = new Phone("Apple", "iPhone 6", "64", 800);
		Phone p1 = new Phone("Samsung", "Galaxy s6", "64", 700);
		Phone p2 = new Phone("Apple", "iPhone 5", "64", 600);
		Phone p3 = new Phone("Samsung", "Galaxy s5", "32", 400);
		
		ProductDB database = new ProductDB();
		database.add(p1);
		database.add(p);
		database.add(p2);
		database.add(p3);
		
		Customer Mary = new Customer("Mary", "14 the pary, midleton, Co. Cork");
		
		Order o = new Order();
		o.add(p,12); // ordered 12 iphone 6 products
		o.add(p1,1); //ordered 1 Galaxy s6
		
		Mary.addOrder(o);
		
		Order o1 = new Order();
		o1.add(p2,1); // ordered 1 iphone 5 products
		o1.add(p3,5); //ordered 5 Galaxy s5 products
		
		Mary.addOrder(o1);
		
		Mary.printOrders(); */
		
		kb = new Scanner(System.in);
		
		customers = new ArrayList<Customer>();
		Customer mary = new Customer("Mary", "od");
		customers.add(mary);
		database = new ProductDB();
		
		/*
		 * Display menu and refer back until quit(6) is selected
		 */
		while(true) {
	
			System.out.println("1: Create a new Phone" + System.lineSeparator()
							+ "2: Create a new TV" + System.lineSeparator()
							+ "3: Add Customer" + System.lineSeparator()
							+ "4: Search for a product by supplying the product ID" + System.lineSeparator()
							+ "5: Display all products in the database" + System.lineSeparator()
							+ "6: Allow a customer to order some products by supplying the product ID and quantity for each product" + System.lineSeparator()
							+ "7: Display all orders that a customer has made and all the products that are in a given order" + System.lineSeparator()
							+ "8: Quit" + System.lineSeparator());
			
			try {
				
				int choice = Integer.parseInt(kb.nextLine());
				
				switch(choice) {
		
					case 1: createNewPhone();
					break;
					
					case 2: createNewTV();
					break;
					
					case 3: addCustomer();
					break;
					
					case 4: searchProduct();
					break;
						
					case 5: database.print(); System.out.println();
					break;	
					
					case 6: orderProduct();
					break;
					
					case 7: displayAllOrders();
					break;
						
					case 8: System.out.println("Goodbye"); kb.close(); System.exit(0);
					break;
					
					default: System.out.println("Please enter a number between 1 - 8");
					break;
				
				}
				
			}catch(Exception e) {
				
				System.out.println("Please enter in figure format. E.g: 1" + System.lineSeparator());
				
			}
			
		}
		
	}// end of main
		
	/*
	 * Creates phone with the information entered by the user. Checks user input
	 */
	public static void createNewPhone() {
		
		boolean valid = true;
		
		while(valid) {
		
			System.out.println("Please enter make");
			String make = kb.nextLine();
			
			if(make.length() == 0) { // checks to see if user input anything
				
				System.out.println("Enter a make" + System.lineSeparator());
				
				continue;
				
			}
			
			System.out.println("Please enter model");
			String model = kb.nextLine();
			
			if(model.length() == 0) {
				
				System.out.println("Enter a model" + System.lineSeparator());
				
				continue;
				
			}
			
			System.out.println("Please enter storgae size(in figure format) in GB");
			String storage = kb.nextLine();
			
			try {
				
				int test = Integer.parseInt(storage);// checks user input is a whole number if not will fail and inform user to input whole number
				
			}
			catch(Exception e) {
				
				System.out.println("Please enter storage in figure format. E.g 64" + System.lineSeparator());
				
				continue;
				
			}
			
			System.out.println("Please enter price");
			double price;
			
			try {
				
				price = kb.nextDouble();
				
			}
			catch(Exception e) {
				
				System.out.println("Please enter in figure format. E.g: 1.23" + System.lineSeparator());
				
				continue;
				
			}
			
			Phone p = new Phone(make, model, storage, price);
			database.add(p); // new Phone created and added to database
			
			System.out.println("Phone created." + System.lineSeparator());
			
			kb.nextLine();
			
			valid = false;
			
		}
		
	}// end of createPhone

	/*
	 * Creates a tv object with information provided by user
	 */
	public static void createNewTV() {
		
		boolean valid = true;
		
		while(valid) {
		
			System.out.println("Please enter make");
			String make = kb.nextLine();
			
			if(make.length() == 0) { // checks user input is not nothing
				
				System.out.println("Enter a make" + System.lineSeparator());
				
				continue;
				
			}
			
			System.out.println("Please enter type");
			String type = kb.nextLine();
			
			if(type.length() == 0) {
				
				System.out.println("Enter a type" + System.lineSeparator());
				
				continue;
				
			}
			
			System.out.println("Please enter screen size");
			double screenSize;
			
			try {
				
				screenSize = kb.nextDouble();
				
			}
			catch(Exception e) {
				
				System.out.println("Please enter screen size in figure format. E.g 15.4" + System.lineSeparator());
				
				continue;
				
			}
			
			kb.nextLine();
			
			System.out.println("Please enter y if tv is 3d capable or n if not");
			String cap3d = kb.nextLine();
			
			boolean capable3D = false;
			
			if(cap3d.toLowerCase().equals("y") || cap3d.toLowerCase().equals("n")) { // checks to see if user input is y or n. If not informs user
				
				if(cap3d.toLowerCase().equals("y")){
					
					capable3D = true;
					
				}
				
			}
			else {
				
				System.out.println("Please enter y or n");
				continue;
				
			}
			
			System.out.println("Please enter price");
			double price;
			
			try {
				
				price = kb.nextDouble();
				
			}
			catch(Exception e) {
				
				System.out.println("Please enter in figure format. E.g: 1.23" + System.lineSeparator());
				
				continue;
				
			}
			
			kb.hasNextLine();
			
			TV tv = new TV(make, screenSize, type, capable3D, price);
			database.add(tv);// Tv object created and added to database
			
			System.out.println("TV created." + System.lineSeparator());
			
			kb.nextLine();
			
			valid = false;
			
		}
		
	}// end of createNewTV
	
	/*
	 * Checks to see if database contains products and if so searches database by user input and displays product information if found
	 */
	public static void searchProduct() {
		
		boolean valid = true;
		
		if(database.size() == 0) {
			
			System.out.println("No products in DB" + System.lineSeparator());
			valid = false; 
			
		}
		
		while(valid) {
			
			System.out.println("Please enter product ID");
			
			//Read in Id from user and if in database prints out info
			try {
				
				int pID = kb.nextInt();
				
				Product p = (Product)database.search(pID);
				
				if(p == null) {
					
					System.out.println("No product ID in database" + System.lineSeparator());
					valid = false;
					kb.nextLine();
					
				}
				
				p.print();
				System.out.println();
				
				kb.nextLine();	
			
				valid = false;
				
			}
			catch(Exception e) {
				
				System.out.println("Please enter in figure format. E.g 22");
				
			}
			
		}
		
	}// end of searchProduct
	
	/*
	 * Checks to see if database contains any products if do will prompt for customer name and will get customer if exist
	 */
	public static void orderProduct() {
		
		boolean valid = true;
		
		if(database.size() == 0) {
			
			System.out.println("No products in DB" +System.lineSeparator());
			return;
			
		}
		
		System.out.println("Please enter customers name. Enter -1 to finish");
		String cusInputName = kb.nextLine();// read in customer name
		
		System.out.println("Please enter customers Address. Enter -1 to finish");
		String cusInputAdd = kb.nextLine();// read in customer address
		
		if(cusInputName.equals("-1") || cusInputAdd.equals("-1")){ // if -1 is entered by user cancels out of order product
			
			return;
			
		}
			
		Customer cus = getCustomer(cusInputName, cusInputAdd);
		
		if(cus == null) {
				
			System.out.println("Customer not on file");
			valid = false;
			
		}
		
		if(valid == true) {
			
			productAndAmount(cus);
			
			kb.nextLine();
			
		}
			
	}// end of OrderProduct
	
	/*
	 * gets customer based on name and address. Returns null if customer not found
	 * Used name and address for matching customers as thise are the variables given in the spec. ideally you would use DOB or an ID no.
	 */
	public static Customer getCustomer(String name, String address) {
		
		for(int i = 0; i < customers.size(); i++) {
			
			String testName = customers.get(i).getName().toLowerCase();
			String testAdd = customers.get(i).getAddress().toLowerCase();
			
			if(testName.equals(name.toLowerCase()) && testAdd.equals(address.toLowerCase())) {
				
				return customers.get(i);
				
			}
			
		}
		
		return null;
		
	}// end of getCustomer
	
	/*
	 * Prompts user for product and how many customer wants.
	 */
	public static void productAndAmount(Customer cus) {
		
		boolean valid = true;
		
		Order o = new Order();;
		
		while(valid) {
	
			try {
				
				System.out.println("Enter product ID. Enter -1 to finish");
				
				int pID = kb.nextInt();
				
				if(pID == -1) {
					
					valid = false;
					continue;
					
				}
				
				Product p = (Product)database.search(pID);
				
				if(p == null) {
					
					System.out.println("No Product ID in the DB");
					continue;
					
				}
				
				System.out.println("Please enter amount");
				
				int amount = kb.nextInt();
				
				if(amount == -1) {
					
					valid = false;
					
				}
				
				o.add(p, amount);
				
				System.out.println("You have ordered " + amount + " " + p.getName() + System.lineSeparator());
				
			}
			catch(Exception e) {
					
				System.out.println("Please enter in figure format. E.g 22");
				kb.nextLine();
				continue;
			}
			

		}
		
		if(o != null) {
			
			cus.addOrder(o);
			
		}
		
	}// end of Poduct and amount
	
	/*
	 * Displays all orders for a customer that was matched based off of name and address
	 */
	public static void displayAllOrders() {
		
		if(database.size() == 0 || customers.size() == 0) {
			
			System.out.println("No products in DB and/or Customers");
	
			
		}
		else {
		
			System.out.println("Please enter customers name.");
			String cusInputName = kb.nextLine();
			
			System.out.println("Please enter customers address.");
			String cusInputAdd = kb.nextLine();
			
			Customer cus = getCustomer(cusInputName, cusInputAdd);

			if(cus == null) {
				
				System.out.println("Customer not on file");
				
			}
			else {
			
				cus.printOrders();

			}
			
		}
		
	}// end of DisplayALLOrders
	
	/*
	 * adds a customer to the ArrayList if name and address are not matched to an existing customer in the list
	 */
	public static void addCustomer() {
		
		boolean valid = true;
		
		while(valid) {
			
			System.out.println("Please enter name");
			String name = kb.nextLine();
			
			if(name.length() == 0 || name.matches("[a-zA-Z]+") == false) {
				
				System.out.println("Please enter a name" + System.lineSeparator());
				continue;
				
			}
			
			System.out.println("Please enter address");	
			String address = kb.nextLine();
			
			if(address.length() == 0) {
				
				System.out.println("Please enter a address" + System.lineSeparator());
				continue;
				
			}
			
			boolean addCus = true;
			
			for(int i = 0; i < customers.size(); i++) {
				
				String testName = customers.get(i).getName().trim();
				String testAdd = customers.get(i).getAddress().trim();
				
				if(testName.equals(name.trim()) && testAdd.equals(address.trim())) {
					
					System.out.println("Customer already in database" + System.lineSeparator());
					addCus = false;
					break;
					
				}

			}
			
			if(addCus) {
				
				Customer cus = new Customer(name.trim(), address.trim());
				customers.add(cus);
				System.out.println("Customer added" + System.lineSeparator());
				
			}

			valid = false;

		}
		
	}// end of addCustomer

}
