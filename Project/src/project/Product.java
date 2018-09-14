package project;

public class Product {

	private String name;
	private String description;
	private double price;
	private int productID;
	private static int productIDCount;
	
	/**
	 * Product constructor where it will assign the Product ID off of productIDCount and then increments productIDCount
	 * 
	 * @param name
	 * @param description
	 * @param price
	 */
	public Product(String name, String description, double price) {
		
		this.name = name;
		this.description = description;
		this.price = price;
		
		this.productID = productIDCount;
		productIDCount++;
				
	}
	
	public String getName() {
		
		return name;
		
	}

	public void setName(String name) {
		
		this.name = name;
		
	}

	public String getDescription() {
		
		return description;
		
	}

	public void setDescription(String description) {
		
		this.description = description;
		
	}

	public double getPrice() {
		
		return price;
		
	}

	public void setPrice(double price) {
		
		this.price = price;
		
	}

	public int getProductID() {
		
		return productID;
		
	}

	public void print() {
		
		String print = "Name: " + this.name + System.lineSeparator()
					+ "Description: " + this.description + System.lineSeparator()
					+ "Price: " + this.price + System.lineSeparator()
					+ "Product ID: " + this.productID;
		
		System.out.println(print);
		
	}
	
}
