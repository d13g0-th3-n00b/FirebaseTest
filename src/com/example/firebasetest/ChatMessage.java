package com.example.firebasetest;

public class ChatMessage {
	User user;
	String message;
	public ChatMessage(User user, String message) {
		super();
		this.user = user;
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
