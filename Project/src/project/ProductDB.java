package project;

import java.util.ArrayList;

public class ProductDB {
	
	 private ArrayList<Product> products;
	
	public ProductDB() {
		
		products = new ArrayList<Product>();
		
	}
	
	public void add(Product p) {
		
		this.products.add(p);
		
	}
	
	public void remove(Product p) {
		
		if(this.products.size() > 0) {
			
			this.products.remove(p);
			
		}
		else {
			
			System.out.println("No products in DB");
			
		}
		
	}
	
	public Product search(int productID) {
		
		if(this.products.size() > 0 && productID < this.products.size()) {
			 
			for(int i = 0; i < this.products.size(); i++) {
				
				if(products.get(i).getProductID() == productID) {
					
					return this.products.get(i);
					
				}
				
			}
				
		}else {
			
			System.out.println("No products in DB");
			
		}
		
		return null;
		
	}
	
	public int size() {
		
		return this.products.size();
		
	}
	
	public void print() {
	
		for(int i =0; i < this.products.size(); i++) {
			
			this.products.get(i).print();
			
		}
		
	}
	
}
