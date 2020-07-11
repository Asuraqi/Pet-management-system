package cn.edu.zucc.pet.model;

import java.sql.Blob;
import java.util.Date;

public class BeanPet {
	private int 	pet_id;
	private int     user_id;
	private String   pet_nickname;
	private String   pet_category;
	private Blob   pet_photo;
	public int getPet_id() {
		return pet_id;
	}
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getPet_nickname() {
		return pet_nickname;
	}
	public void setPet_nickname(String pet_nickname) {
		this.pet_nickname = pet_nickname;
	}
	public String getPet_category() {
		return pet_category;
	}
	public void setPet_category(String pet_category) {
		this.pet_category = pet_category;
	}
	public Blob getPet_photo() {
		return pet_photo;
	}
	public void setPet_photo(Blob pet_photo) {
		this.pet_photo = pet_photo;
	}
	
}
