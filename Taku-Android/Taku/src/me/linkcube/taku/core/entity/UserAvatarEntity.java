package me.linkcube.taku.core.entity;

import android.graphics.Bitmap;

import com.orm.dsl.Table;

@Table(name = "UserAvatarEntity")
public class UserAvatarEntity {

	private String username;
	private Bitmap avatarBitmap;
	
	public UserAvatarEntity(){
		
	}
	
	public UserAvatarEntity(String username, Bitmap avatar) {
		this.username = username;
		this.avatarBitmap = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Bitmap getAvatarBitmap() {
		return avatarBitmap;
	}

	public void setAvatarBitmap(Bitmap avatarBitmap) {
		this.avatarBitmap = avatarBitmap;
	}

}
