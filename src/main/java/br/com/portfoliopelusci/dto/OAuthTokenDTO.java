package br.com.portfoliopelusci.dto;

import java.time.Instant;

public class OAuthTokenDTO {
	private String access_token;
    private String refresh_token;
    private Instant expires_at;
    
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public Instant getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(Instant expires_at) {
		this.expires_at = expires_at;
	}
    

}
