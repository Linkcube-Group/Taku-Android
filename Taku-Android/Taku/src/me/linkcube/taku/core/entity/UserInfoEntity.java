package me.linkcube.taku.core.entity;

import com.orm.dsl.Table;

@Table(name = "UserInfoEntity")
public class UserInfoEntity {

	private String username;
	private String nickname;
	private String gender;
	private String height;
	private String weight;
	private String avatar;
	private int age;

	public UserInfoEntity() {

	}

	public UserInfoEntity(String username, String nickname, String gender,
			String height, String weight, String avatar, int age) {
		this.username = username;
		this.nickname = nickname;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.avatar = avatar;
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAge() {
		return age + "";
	}

	public void setAge(int age) {
		this.age = age;
	}

}