package me.dausech.accbackend.security;

import lombok.Data;

@Data
public class AccountCredentials {
	public AccountCredentials() {}
	
	public AccountCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	private String username;
	private String password;

}
