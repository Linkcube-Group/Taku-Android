package me.linkcube.taku.ui.user;

import java.util.Calendar;
import java.util.List;

import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.AppConst.PATH;
import me.linkcube.taku.R.string;
import me.linkcube.taku.core.entity.UserAvatarEntity;
import me.linkcube.taku.core.entity.UserInfoEntity;
import android.util.Log;

import com.orm.query.Select;

import custom.android.util.PreferenceUtils;

public class UserManager {

	private static UserManager userManager = null;

	private boolean isLogin = false;

	private UserManager() {
	}

	public static UserManager getInstance() {
		synchronized (UserManager.class) {
			if (userManager == null) {
				userManager = new UserManager();
			}
			return userManager;
		}
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	private UserInfoEntity userInfoEntity = null;

	public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
	}

	public UserInfoEntity getUserInfo() {
		if (!isLogin)
			return null;
		List<UserInfoEntity> userInfoEntities = Select
				.from(UserInfoEntity.class)
				.where("username=?",
						new String[] { PreferenceUtils.getString(KEY.USER_NAME,
								"") }).list();
		if (userInfoEntities == null || userInfoEntities.isEmpty())
			return null;
		Log.d("getUserInfo", "userInfoEntity:"
				+ userInfoEntities.get(0).getNickname());
		userInfoEntity=userInfoEntities.get(0);
		return userInfoEntity;
	}
	
	public String getUserHeight(){
		return userInfoEntity.getHeight();
	}
	
	public String getUserWeight(){
		return userInfoEntity.getWeight();
	}

	public String getUserAvatarUrl() {
		if (!isLogin)
			return null;
		List<UserAvatarEntity> userAvatarEntities = Select
				.from(UserAvatarEntity.class)
				.where("username=?",
						new String[] { PreferenceUtils.getString(KEY.USER_NAME,
								"") }).list();
		if (userAvatarEntities == null || userAvatarEntities.isEmpty())
			return null;
		Log.d("getUserAvatarUrl", "userInfoEntity:"
				+ userAvatarEntities.get(0).getAvatarSdUrl());
		return PATH.SDCARD_PATH + userAvatarEntities.get(0).getAvatarSdUrl(); // BitmapUtils.convertToBitmap();
	}

	/**
	 * 根据生日获取年龄
	 * 
	 * @param birthday
	 * @return
	 */
	public static String getUserAge(String birthday) {
		if (birthday != null && !birthday.equals("")) {
			String[] birthData = birthday.split("-");
			int birthYear = Integer.parseInt(birthData[0]);
			Calendar cal = Calendar.getInstance();
			int curYear = cal.get(Calendar.YEAR);
			int age = curYear - birthYear;
			String ageString = age + "";
			return ageString;
		} else {
			return "23";
		}
	}
}
