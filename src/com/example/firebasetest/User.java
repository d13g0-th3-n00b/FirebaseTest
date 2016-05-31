package com.example.firebasetest;

public class User {
	String uid;
	String email;
	String screenName;
	double totalMoney;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String email, String screenName, double totalMoney) {
		super();
		this.email = email;
		this.screenName = screenName;
		this.totalMoney = totalMoney;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
