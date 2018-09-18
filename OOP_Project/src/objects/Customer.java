package objects;

public class Customer {
	
	private int id;
	private String fName;
	private String lName;
	private String street;
	private String city;
	private String county;
	
	/*
	 * Default constructor that creates a customer object with no information
	 */
	public Customer() {}
	
	/*
	 * Create a customer with there information
	 */
	public Customer(String fName, String lName, String street, String city, String county) {
		
		this.fName = fName;
		this.lName = lName;
		this.street = street;
		this.city = city;
		this.county = county;
		
	}
	
	public String getFName() {
		
		return this.fName;
		
	}
	

	public void setFName(String name) {
		
		this.fName = name;
		
	}
	
	public String getLName() {
		
		return this.lName;
		
	}
	

	public void setLName(String name) {
		
		this.lName = name;
		
	}

	public String getStreet() {
		
		return this.street;
		
	}
	
	public void setStreet(String street) {
		
		this.street = street;
		
	}
	
	public String getCity() {
		
		return this.city;
		
	}
	
	public void setCity(String city) {
		
		this.city = city;
		
	}
	
	public String getCounty() {
		
		return this.county;
		
	}
	
	public void setCounty(String county) {
		
		this.county = county;
		
	}
	
	public int getId() {
		
		return this.id;
		
	}

	public void setId(int id) {
		
		this.id = id;
		
	}
	
	public String toString() {
		
		return this.getFName() + " " + this.getLName() + "\n" + this.getStreet() + "\n" + this.getCity() + "\n" + this.getCity();
		
	}
	
	public void print() {
		
		System.out.println(this.toString());
		
	}
	
}
