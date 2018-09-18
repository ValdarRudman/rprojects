package objects;

public class Product {

	private int id;
	private String make;
	private String model;
	private double price;
	
	/*
	 * Default constructor. Creates a product object with no information
	 */
	public Product() {}
	
	/*
	 * Creat a product object with the information needed. This is a parent class to tv and phone
	 */
	public Product(int id, String model, String make, double price) {
		
		this.id = id;
		this.make = make;
		this.model = model;
		this.price = price;
				
	}
	
	public int getId() {
		
		return this.id;
		
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String toString() {
		
		return this.getId() + " " + this.getMake() + " " + this.getModel() + " " + this.getPrice();
		
	}
	
	public void print() {
		
		System.out.println(this.toString());
		
	}
	
}