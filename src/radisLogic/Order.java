package radisLogic;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	String orderID, orderStatus;
	String orderDate;
	int orderQuantity;
	Product product;

	public Order(String orderID,int orderQuantity, String orderStatus, String orderDate, Product product){
		this.orderID = orderID;
		this.orderQuantity = orderQuantity;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.product = product;
		
	}

	public String getOrderID() {
		return orderID;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getOrderDate() {
		return orderDate;
	}
	public Product getProduct(){
		return product;
		
	}
	public String getProductName(){
		return product.getName();
	}
	public int getHours(){
		int hours = ((product.getETA() * orderQuantity / 6)/24);
		return hours;
	}
	public void submitOrder(ArrayList<Order> order){
		
	}
}
