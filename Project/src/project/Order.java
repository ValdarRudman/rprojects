package project;

import java.util.ArrayList;

public class Order {

	private ArrayList<OrderDetails> orderDetails;
	
	public Order() {
		
		orderDetails = new ArrayList<OrderDetails>();
		
	}
	
	public void add(Product product, int quantity) {
		
		OrderDetails od = new OrderDetails(product, quantity);
		
		this.orderDetails.add(od);
		
	}
	
	public void print() {
		
		for(int i = 0; i < this.orderDetails.size(); i++) {
			
			System.out.println("You ordered " + this.orderDetails.get(i).getQuantity() + " " + this.orderDetails.get(i).getName());
			
		}
		
	}
	
}
