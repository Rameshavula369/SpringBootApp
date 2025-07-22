package com.effort.remote;



import org.springframework.security.core.GrantedAuthority;

public class RemoteUserAuthority implements GrantedAuthority{
	private static final long serialVersionUID = -2054535755666372570L;
	private String authority;
	
	public RemoteUserAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
