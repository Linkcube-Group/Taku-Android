package me.linkcube.taku.ui.sportsgame;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.orm.SugarRecord;
import com.orm.query.Select;

import custom.android.util.PreferenceUtils;

import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.core.entity.SingleDayGameHistoryEntity;
import me.linkcube.taku.ui.user.UserManager;

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
		SportsGameManager.targetDistance = targetDistance;
	}

	public static SingleDayGameHistoryEntity getSingleDayGameHistoryEntity() {
		List<SingleDayGameHistoryEntity> singleDayGameHistoryEntities = Select
				.from(SingleDayGameHistoryEntity.class)
				.where("username=? and todaydate=?",
						new String[] {
								PreferenceUtils.getString(KEY.USER_NAME, ""),
								getTodayDate() }).list();
		if (singleDayGameHistoryEntities == null
				|| singleDayGameHistoryEntities.isEmpty()) {
			return null;
		} else if (!singleDayGameHistoryEntities.get(0).getTodaydate().equals(getTodayDate())) {
			return null;
		}else{
			return singleDayGameHistoryEntities.get(0);
		}
		
	}

	public static void setSingleDayGameHistoryEntity(String singleDayDistance,
			String singleDayDuration, String singleDayCalorie) {
		singleDayGameHistoryEntity = new SingleDayGameHistoryEntity();
		singleDayGameHistoryEntity.setSingleDayDistance(singleDayDistance);
		singleDayGameHistoryEntity.setSingleDayDuration(singleDayDuration);
		singleDayGameHistoryEntity.setSingleDayCalorie(singleDayCalorie);
		singleDayGameHistoryEntity.setTodaydate(getTodayDate());
		singleDayGameHistoryEntity.setUsername(PreferenceUtils.getString(
				KEY.USER_NAME, null));
	}

	public static void saveSingleDayGameRecord() {
		SugarRecord.deleteAll(SingleDayGameHistoryEntity.class,
				"username=? and todaydate=?",
				new String[] { PreferenceUtils.getString(KEY.USER_NAME, ""),
						singleDayGameHistoryEntity.getTodaydate() });
		SugarRecord.save(singleDayGameHistoryEntity);
	}

	public static String getTodayDate() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String todayDate = bartDateFormat.format(date);
		return todayDate;
	}

}
