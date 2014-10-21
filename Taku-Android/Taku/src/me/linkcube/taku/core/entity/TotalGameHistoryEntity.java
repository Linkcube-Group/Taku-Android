package me.linkcube.taku.core.entity;

public class TotalGameHistoryEntity {
	
	private String username;
	
	private String TotalDistance;
	
	private String TotalCalorie;
	
	private String TotalDuration;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTotalDistance() {
		return TotalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		TotalDistance = totalDistance;
	}

	public String getTotalCalorie() {
		return TotalCalorie;
	}

	public void setTotalCalorie(String totalCalorie) {
		TotalCalorie = totalCalorie;
	}

	public String getTotalDuration() {
		return TotalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		TotalDuration = totalDuration;
	}

}
