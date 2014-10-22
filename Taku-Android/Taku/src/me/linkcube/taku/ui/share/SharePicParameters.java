package me.linkcube.taku.ui.share;

import java.io.Serializable;

/**
 * 绘制分享图片时所用到的信息
 * */
public class SharePicParameters implements Serializable {
	// 背景图片ID
	private int bg_resId;
	// 顶部图片ID
	private int head_resId;
	// 运动距离
	private float distance;
	// 运动时间
	private String timeString;
	// 消耗卡路里
	private int cal;

	public SharePicParameters() {
		// TODO Auto-generated constructor stub
	}

	public SharePicParameters(int bg_resId, int head_resId, float distance,
			String timeString, int cal) {
		this.bg_resId = bg_resId;
		this.head_resId = head_resId;
		this.distance = distance;
		this.timeString = timeString;
		this.cal = cal;
	}

	public int getBg_resId() {
		return bg_resId;
	}

	public void setBg_resId(int bg_resId) {
		this.bg_resId = bg_resId;
	}

	public int getHead_resId() {
		return head_resId;
	}

	public void setHead_resId(int head_resId) {
		this.head_resId = head_resId;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public int getCal() {
		return cal;
	}

	public void setCal(int cal) {
		this.cal = cal;
	}

}
