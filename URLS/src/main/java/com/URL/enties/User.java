package com.URL.enties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User_Data")
public class User {
	
	
	 
	 public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "Id")
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	
	 private String userId;
	 @Column
	private String password;
	
	 @Column(name = "Email")
	private String email;
	 @Column(name = "user_name")
		private String username;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
