package me.linkcube.taku.core.entity;

import com.orm.dsl.Table;

@Table(name = "SingleDayGameHistoryEntity")
public class SingleDayGameHistoryEntity {

	private String username;

	private String todaydate;
	
	private String singleDayTarget;

	private String singleDayDistance;

	private String singleDayCalorie;

	private String singleDayDuration;

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
	
	public String getSingleDayTarget() {
		return singleDayTarget;
	}

	public void setSingleDayTarget(String singleDayTarget) {
		this.singleDayTarget = singleDayTarget;
	}

	public String getSingleDayDistance() {
		return singleDayDistance;
	}

	public void setSingleDayDistance(String singleDayDistance) {
		this.singleDayDistance = singleDayDistance;
	}

	public String getSingleDayCalorie() {
		return singleDayCalorie;
	}

	public void setSingleDayCalorie(String singleDayCalorie) {
		this.singleDayCalorie = singleDayCalorie;
	}

	public String getSingleDayDuration() {
		return singleDayDuration;
	}

	public void setSingleDayDuration(String singleDayDuration) {
		this.singleDayDuration = singleDayDuration;
	}
}
