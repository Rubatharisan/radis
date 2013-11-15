package logic;

import java.util.ArrayList;
import java.util.Date;

public class LOrder {
	String orderID, orderStatus;
	String orderDate;
	int orderQuantity;
	LProduct product;

	public LOrder(String orderID,int orderQuantity, String orderStatus, String orderDate, LProduct product){
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
	public LProduct getProduct(){
		return product;
		
	}
	public String getProductName(){
		return product.getName();
	}
	public int getHours(){
		int hours = ((product.getETA() * orderQuantity / 6)/24);
		return hours;
	}
	public void submitOrder(ArrayList<LOrder> order){
		
	}
}
