package project;

import java.util.ArrayList;

public class Customer {
	
	private String name;
	private String address;
	private ArrayList<Order> orders;
	
	public Customer(String name, String address) {
		
		this.name = name;
		this.address = address;
		this.orders = new ArrayList<Order>();
		
	}
	
	public String getName() {
		
		return name;
		
	}

	public void setName(String name) {
		
		this.name = name;
		
	}

	public String getAddress() {
		
		return address;
		
	}

	public void setAddress(String address) {
		
		this.address = address;
		
	}

	public ArrayList<Order> getOrders() {
		
		return orders;
		
	}

	public void addOrder(Order o) {
		
		this.orders.add(o);
		
	}
	
	public void remove(Order o) {
		
		if(this.orders.size() > 0) {
			
			this.orders.remove(o);
			
		}
		else {
			
			System.out.println("No orders");
			
		}
	}

	public void printOrders() {
		
		
		for(int i = 0; i < orders.size(); i++) {
			
			System.out.println("Order " + (i + 1));
			
			orders.get(i).print();
			
			System.out.println();
			
		}
		
	}

}
