package objects;

public class Phone extends Product{
	
	private String storage;
	
	/*
	 * Default constructor that create a Phone object with no information
	 */
	public Phone() {}
	
	/*
	 * Create a phone object with the information on it. Extends product class
	 */
	public Phone(int id, String model, String make, double price, String storage) {
		
		super(id, model, make, price);
		
		this.storage = storage;
		
	}
	
	public int getId() {
		
		return super.getId();
		
	}
	
	public String getMake() {
		
		return super.getMake();
		
	}

	public void setMake(String make) {
		
		super.setMake(make);

	}

	public String getModel() {
		
		return super.getModel();
		
	}

	public void setModel(String model) {
		
		super.setModel(model);
		
	}

	public String getStorage() {
		
		return storage;
		
	}

	public void setStorage(String storage) {
		
		this.storage = storage;
		
	}
	
	public double getPrice() {
		
		return super.getPrice();
		
	}
	
	public void setPrice(double price) {
		
		super.setPrice(price);
		
	}
	
	public String toString() {
		
		return super.toString() + " " + this.getStorage();
		
	}

	public void print() {
		
		System.out.println(this.toString());
		
	}
	
}
