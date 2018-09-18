package objects;

public class TV extends Product{

	double screenSize;
	String type;
	boolean capable3D;
	
	/*
	 * Default constructor that creates a TV object with no information
	 */
	public TV() {}
	
	/*
	 * Create a TV object with the information on the object. Extends product class
	 */
	public TV(int id, String model, String make, double price, double screenSize, String type, boolean capable3D) {
		
		super(id, model, make, price);
		this.screenSize = screenSize;
		this.type = type;
		this.capable3D = capable3D;
		
	}
	
	public int getId() {
		
		return super.getId();
		
	}
	
	public double getPrice() {
		
		return super.getPrice();
		
	}
	
	public void setPrice(double price) {
		
		super.setPrice(price);
		
	}

	public String getMake() {
		
		return super.getMake();

	}

	public void setMake(String make) {
		
		super.setMake(make);
		
	}

	public double getScreenSize() {
		
		return screenSize;
		
	}

	public void setScreenSize(double screenSize) {
		
		this.screenSize = screenSize;
		
	}

	public String getType() {
		
		return type;
		
	}

	public void setType(String type) {
		
		this.type = type;
		
	}

	public boolean isCapable3D() {
		
		return capable3D;
		
	}

	public void setCapable3D(boolean capable3d) {
		
		capable3D = capable3d;

	}
	
	public void setModel(String model) {
		
		super.setModel(model);
		
	}
	
	public String getModel() {
		
		return super.getModel();
		
	}
	
	public boolean getCapable3D() {
		
		return this.capable3D;
		
	}
	
	public String toString() {
		
		return super.toString() + " " + this.screenSize + " " + this.type + " " + this.capable3D;
		
	}
	
	public void print() {
		
		System.out.println(this.toString());
		
	}
	
}
