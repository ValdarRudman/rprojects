package objects;

import java.util.HashMap;
import java.util.Map;

public class Order {

	private Map<Integer, Product> orders = new HashMap<Integer, Product>();
	
	/*
	 * Create an order object. Uses Hashmap to store the product and amount ordered for that product
	 */
	public Order() {
		
		orders = new HashMap<Integer, Product>();
		
	}
	
	public void add(Product p, int amount) {
		
		orders.put(amount, p);
		
	}
	
	public Map<Integer, Product> getOrders(){
		
		return this.orders;
		
	}
	
}
