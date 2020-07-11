package cn.edu.zucc.pet.model;

import java.util.Date;

public class BeanCommodity {
	 private int commodity_id;        
	 private int  category_id;         
	 private String commodity_name;
	 private String  category_name;
	 private String  commodity_brand;
	 private float  commodity_price;
	 private int  commodity_barcode;
	public int getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCommodity_brand() {
		return commodity_brand;
	}
	public void setCommodity_brand(String commodity_brand) {
		this.commodity_brand = commodity_brand;
	}
	public float getCommodity_price() {
		return commodity_price;
	}
	public void setCommodity_price(float commodity_price) {
		this.commodity_price = commodity_price;
	}
	public int getCommodity_barcode() {
		return commodity_barcode;
	}
	public void setCommodity_barcode(int commodity_barcode) {
		this.commodity_barcode = commodity_barcode;
	}
}
