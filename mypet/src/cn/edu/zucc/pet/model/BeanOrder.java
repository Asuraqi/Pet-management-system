package cn.edu.zucc.pet.model;

public class BeanOrder {
	private int   order_id;
	private int   commodity_id;
	private int   user_id;
	private int   number;
	private float   sale;
	private String   deliver_condition;
	private int   staff_id;
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public float getSale() {
		return sale;
	}
	public void setSale(float sale) {
		this.sale = sale;
	}
	public String getDeliver_condition() {
		return deliver_condition;
	}
	public void setDeliver_condition(String deliver_condition) {
		this.deliver_condition = deliver_condition;
	}
	public int getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}  
	
}
