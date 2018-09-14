package project;

public class OrderDetails {

	private Product product;
	private int quantity;
	
	public OrderDetails(Product product, int quantity){
		
		this.product = product;
		this.quantity = quantity;
		
	}
	
	public Product getProduct() {
		
		return product;
		
	}
	
	public int getQuantity() {
		
		return quantity;
		
	}
	
	public String getName() {
		
		return this.product.getName();
		
	}
	
}
