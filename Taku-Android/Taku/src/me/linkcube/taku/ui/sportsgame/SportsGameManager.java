package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.ui.user.UserManager;

public class SportsGameManager {

	private static int userHeight = 175;

	private static int userWeight = 67;

	/**
	 * 计算步长
	 * 
	 * @return
	 */
	public static double getStepLength() {
		return (getUserHeight() - 155.911) / 26.2;
	}

	/**
	 * 计算速度
	 * 
	 * @param currentTime
	 * @param preTime
	 * @return
	 */
	public static double calculateSpeed(long preTime, long currentTime) {
		return 1000 * (getStepLength() * 3.6) / (currentTime - preTime);
	}

	/**
	 * 计算消耗热量
	 * 
	 * @return
	 */
	public static double calculateCalorie(double distance) {
		return distance * getUserWeight();
	}

	/**
	 * 计算距离
	 * 
	 * @param stepCount
	 * @return
	 */
	public static double calculateDistance(int stepCount) {
		return getStepLength() * stepCount / 1000.0;
	}

	private static int getUserHeight() {
		if (UserManager.getInstance().getUserInfo() == null) {
			return userHeight;
		} else {
			return Integer.parseInt(UserManager.getInstance().getUserInfo()
					.getHeight());
		}
	}

	private static double getUserWeight() {
		if (UserManager.getInstance().getUserInfo() == null) {
			return userWeight;
		} else {
			return Integer.parseInt(UserManager.getInstance().getUserInfo()
					.getWeight());
		}
	}

}
