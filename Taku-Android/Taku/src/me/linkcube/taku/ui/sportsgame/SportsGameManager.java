package me.linkcube.taku.ui.sportsgame;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	/**
	 * 计算时长
	 * 
	 * @param time
	 * @return
	 */
	public static int calculateDuration(String time) {
		String[] timeStrings = time.split(":");
		return Integer.valueOf(timeStrings[0]) * 60
				+ Integer.valueOf(timeStrings[1]);
	}

	public static String durationToTime(int duration) {
		String[] countTime = new String[2];
		countTime[0] = addZero(duration / 60);
		countTime[1] = addZero(duration % 60);
		return countTime[0] + ":" + countTime[1];
	}

	private static String addZero(int time) {
		if (0 <= time && time <= 9)
			return "0" + time;
		else {
			return time + "";
		}
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
		} else if (!singleDayGameHistoryEntities.get(0).getTodaydate()
				.equals(getTodayDate())) {
			return null;
		} else {
			return singleDayGameHistoryEntities.get(0);
		}

	}

	public static void setSingleDayGameHistoryEntity(String singleDayDistance,
			String singleDayDuration, String singleDayCalorie) {
		singleDayGameHistoryEntity = new SingleDayGameHistoryEntity();
		singleDayGameHistoryEntity.setDistance(singleDayDistance);
		singleDayGameHistoryEntity.setDuration(singleDayDuration);
		singleDayGameHistoryEntity.setCalorie(singleDayCalorie);
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

	private static SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getTodayDate() {
		
		Date date = new Date();
		String todayDate = bartDateFormat.format(date);
		return todayDate;
	}

	public static String getDate(String preDate,int flag) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(bartDateFormat.parse(preDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, flag);
		Date d2 = calendar.getTime();
		return bartDateFormat.format(d2);
	}
	static DecimalFormat dFormat = new java.text.DecimalFormat("#.##");
	
	public static double fromStringToDouble(String str){
		double num=Double.parseDouble(str);
		Double.parseDouble(dFormat.format(num));
		return num;
	}
	
	public static double saveTwoPointDouble(double num){
		return Double.parseDouble(dFormat.format(num));
	}
}
