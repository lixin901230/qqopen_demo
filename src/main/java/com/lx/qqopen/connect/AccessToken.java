package com.lx.qqopen.connect;

/**
 * QQ开放平台接口调用认证凭据
 * @author lx
 */
public class AccessToken {

	private String token;
	private int expiresIn;
	
	public AccessToken() {
		
	}
	
	public AccessToken(String token, int expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
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
	
}
