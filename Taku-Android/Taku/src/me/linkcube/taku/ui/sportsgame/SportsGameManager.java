package me.linkcube.taku.ui.sportsgame;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.orm.SugarRecord;

import custom.android.util.PreferenceUtils;

import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.core.entity.SingleDayGameHistoryEntity;
import me.linkcube.taku.core.entity.UserInfoEntity;
import me.linkcube.taku.ui.user.UserManager;
import android.util.Log;

public class SportsGameManager {

	private static int userHeight = 175;

	private static int userWeight = 67;

	private static double targetDistance = 3;

	private static SingleDayGameHistoryEntity singleDayGameHistoryEntity = null;

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
			return Integer.parseInt(UserManager.getInstance().getUserHeight());
		}
	}

	private static double getUserWeight() {
		if (UserManager.getInstance().getUserInfo() == null) {
			return userWeight;
		} else {
			return Integer.parseInt(UserManager.getInstance().getUserWeight());
		}
	}

	public static double getTargetDistance() {
		return targetDistance;
	}

	public static void setTargetDistance(double targetDistance) {
		Log.d("setTargetDistance", "--targetDistance:" + targetDistance);
		SportsGameManager.targetDistance = targetDistance;
	}

	public static SingleDayGameHistoryEntity getSingleDayGameHistoryEntity() {
		if (singleDayGameHistoryEntity == null) {
			Log.d("getSingleDayGameHistoryEntity", "--targetDistance:" + 1);
			return null;
		}
		if (!singleDayGameHistoryEntity.getTodayDate().equals(getTodayDate())) {
			Log.d("getSingleDayGameHistoryEntity", "--targetDistance:" + 2);
			return null;
		}
		Log.d("getSingleDayGameHistoryEntity", "--targetDistance:" + 3);
		return singleDayGameHistoryEntity;
	}

	public static void setSingleDayGameHistoryEntity(String singleDayDistance,
			String singleDayDuration, String singleDayCalorie) {
		singleDayGameHistoryEntity = new SingleDayGameHistoryEntity();
		singleDayGameHistoryEntity.setSingleDayDistance(singleDayDistance);
		singleDayGameHistoryEntity.setSingleDayDuration(singleDayDuration);
		singleDayGameHistoryEntity.setSingleDayCalorie(singleDayCalorie);
		singleDayGameHistoryEntity.setTodayDate(getTodayDate());
		singleDayGameHistoryEntity.setUsername(PreferenceUtils.getString(
				KEY.USER_NAME, null));
	}

	public static void saveSingleDayGameRecord() {
		SugarRecord.deleteAll(SingleDayGameHistoryEntity.class, "username=? and todayDate=?",
				new String[] { PreferenceUtils.getString(KEY.USER_NAME, ""),singleDayGameHistoryEntity.getTodayDate() });
		SugarRecord.save(singleDayGameHistoryEntity);
	}

	public static String getTodayDate() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		String todayDate = bartDateFormat.format(date);
		return todayDate;
	}

}
