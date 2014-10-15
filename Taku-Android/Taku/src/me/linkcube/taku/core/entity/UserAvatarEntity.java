package me.linkcube.taku.core.entity;

import com.orm.dsl.Table;

@Table(name = "UserAvatarEntity")
public class UserAvatarEntity {

	private String username;
	private String avatarSdUrl;
	
	public UserAvatarEntity(){
		
	}
	
	public UserAvatarEntity(String username, String avatarSdUrl) {
		this.username = username;
		this.avatarSdUrl = avatarSdUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatarSdUrl() {
		return avatarSdUrl;
	}

	public void setAvatarSdUrl(String avatarSdUrl) {
		this.avatarSdUrl = avatarSdUrl;
	}


}
