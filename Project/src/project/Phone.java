package project;

public class Phone extends Product{
	
	private String make;
	private String model;
	private String storage;
	
	public Phone(String make, String model, String storage, double price) {
		
		super(make + " " + model, make + " " + model + System.lineSeparator() + storage, price);
		
		this.make = make;
		this.model = model;
		this.storage = storage;
		
	}
	
	public String getMake() {
		
		return make;
		
	}

	public void setMake(String make) {
		
		this.make = make;
		setProductInfo();
	}

	public String getModel() {
		
		return model;
		
	}

	public void setModel(String model) {
		
		this.model = model;
		setProductInfo();
		
	}

	public String getStorage() {
		
		return storage;
		
	}

	public void setStorage(String storage) {
		
		this.storage = storage;
		setProductInfo();
		
	}

	/**
	 * Sets the new product name and Description when set methods are used
	 */
	private void setProductInfo() {
		
		super.setName(this.make + " " + this.model);
		super.setDescription(this.make + " " + this.model + System.lineSeparator() + this.storage);
		
	}
	
	public void setPrice(double price) {
		
		super.setPrice(price);
		
	}
	
	public double getPrice() {
		
		return super.getPrice();
		
	}
	
	public void print() {
		
		super.print();
		
		String print = "Make: " + this.make + System.lineSeparator()
					+ "Model: " + this.model + System.lineSeparator()
					+ "Storage: " + this.storage + "GB" + System.lineSeparator();
		
		System.out.println(print);
		
	}

}
