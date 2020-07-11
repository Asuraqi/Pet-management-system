package cn.edu.zucc.pet.model;

import java.sql.Timestamp;
import java.util.Date;

public class BeanServiceOrder {
	private int  pet_id;
	private int  commodity_id;
	private int   staff_id;
	private String   service_id;
	private Timestamp   service_time;
	private Timestamp   service_finish_time;
	public int getPet_id() {
		return pet_id;
	}
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	public int getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}
	public int getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public Timestamp getService_time() {
		return service_time;
	}
	public void setService_time(Timestamp service_time) {
		this.service_time = service_time;
	}
	public Timestamp getService_finish_time() {
		return service_finish_time;
	}
	public void setService_finish_time(Timestamp service_finish_time) {
		this.service_finish_time = service_finish_time;
	}
	
}
