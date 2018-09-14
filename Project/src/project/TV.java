package project;

public class TV extends Product{
	
	String make;
	double screenSize;
	String type;
	boolean capable3D;
	
	public TV(String make, double screenSize, String type, boolean capable3D, double price) {
		
		super(make + " " + type, make + " " + type + System.lineSeparator() + "3D capable: " + capable3D, price);
		
		this.make = make;
		this.screenSize = screenSize;
		this.type = type;
		this.capable3D = capable3D;
		
	}
	
	public double getPrice() {
		
		return super.getPrice();
		
	}
	
	public void setPrice(double price) {
		
		super.setPrice(price);
		
	}

	public String getMake() {
		
		return make;

	}

	public void setMake(String make) {
		
		this.make = make;
		setProductInfo();
		
	}

	public double getScreenSize() {
		
		return screenSize;
		
	}

	public void setScreenSize(double screenSize) {
		
		this.screenSize = screenSize;
		setProductInfo();
		
	}

	public String getType() {
		
		return type;
		
	}

	public void setType(String type) {
		
		this.type = type;
		setProductInfo();
		
	}

	public boolean getCapable3D() {
		
		return capable3D;
		
	}

	public void setCapable3D(boolean capable3d) {
		
		capable3D = capable3d;
		setProductInfo();
	}
	
	/*
	 * Sets the new product name and Description when set methods are used
	 *
	 */
	private void setProductInfo() {
		
		super.setName(this.make + " " + this.type);
		super.setDescription(this.make + " " + this.type + System.lineSeparator() + "3D capable: " + this.capable3D);
		
	}
	
	public void print() {
		
		super.print();
		
		String print = "Make: " + this.make + System.lineSeparator()
					+ "Screen Soze: " + this.screenSize + System.lineSeparator()
					+ "Type: " + this.type + System.lineSeparator()
					+ "3D capable: " + this.capable3D;
		
		System.out.println(print);
		
	}
	
}
