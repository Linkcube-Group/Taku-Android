package me.linkcube.taku.core.entity;

import com.orm.dsl.Table;

@Table(name = "SingleDayGameHistoryEntity")
public class SingleDayGameHistoryEntity {

	private String username;

	private String todaydate;
	
	private String target;

	private String distance;

	private String calorie;

	private String duration;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTodaydate() {
		return todaydate;
	}

	public void setTodaydate(String todaydate) {
		this.todaydate = todaydate;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCalorie() {
		return calorie;
	}

	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
