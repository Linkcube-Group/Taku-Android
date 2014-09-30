package me.linkcube.taku.ui.user;

import java.util.Calendar;
import java.util.List;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.AppConst.ResponseKey;
import me.linkcube.taku.core.entity.UserInfoEntity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orm.query.Select;

import custom.android.util.PreferenceUtils;

public class UserManager {

	private static UserManager userManager = null;

	private boolean isLogin = false;
	
	private static UserInfoEntity userInfoEntity=null;

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
		if(!isLogin){
			userInfoEntity=null;
		}
	}
	
	public UserInfoEntity getUserInfo(){
		if(userInfoEntity==null){
			List<UserInfoEntity> userInfoEntities = Select.from(UserInfoEntity.class).where("username=?", new String[]{PreferenceUtils.getString(KEY.USER_NAME, "")}).list();
			if(userInfoEntities==null||userInfoEntities.isEmpty())
				return null;
			userInfoEntity=userInfoEntities.get(0);
			return userInfoEntity;
		}else{
			Log.d("getUserInfo", "userInfoEntity:"+userInfoEntity.getNickname());
			return userInfoEntity;
		}
	}
	
	

	public static void setUserInfoEntity(UserInfoEntity userInfoEntity) {
		UserManager.userInfoEntity = userInfoEntity;
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
