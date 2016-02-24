package com.lx.qqopen.connect;

/**
 * QQ开放平台接口调用认证凭据
 * @author lx
 */
public class AccessToken {

	private String token;
	private int expiresIn;
	private String refreshToken;
	
	public AccessToken() {
		
	}
	
	public AccessToken(String token, int expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}
	
	public AccessToken(String token, int expiresIn, String refreshToken) {
		this.token = token;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
}
