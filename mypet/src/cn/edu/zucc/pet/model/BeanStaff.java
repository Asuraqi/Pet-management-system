package cn.edu.zucc.pet.model;

import java.util.Date;

public class BeanStaff {
	private int      staff_id;
	private String   staff_name;
	private String   staff_rank;
	public int getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getStaff_rank() {
		return staff_rank;
	}
	public void setStaff_rank(String staff_rank) {
		this.staff_rank = staff_rank;
	}
	public String getStaff_pwd() {
		return staff_pwd;
	}
	public void setStaff_pwd(String staff_pwd) {
		this.staff_pwd = staff_pwd;
	}
	private String   staff_pwd;
}
