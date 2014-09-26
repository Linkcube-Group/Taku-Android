package me.linkcube.taku.ui.user;

import java.util.Calendar;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.ResponseKey;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserManager {
	
	private static UserManager userManager = null;
	
	private boolean isLogin=false;
	
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

	public void userLogin(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		ClientInstance.getInstance().post("http://115.29.175.17:8000/login",
				params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("userLogin", "statusCode:" + statusCode
								+ "---response:" + response.toString());
						try {
							if (statusCode == 200) {
								if (response.getBoolean(ResponseKey.STATUS)) {
									CookieInstance.getInstance().getCookie();
									getUserInfo();
									httpResponseListener.responseSuccess();
									setLogin(true);
								} else {
									httpResponseListener
											.responseFailed(ErrorFlag.EMAIL_PSW_WRONG);
								}
							} else {
								httpResponseListener
										.responseFailed(ErrorFlag.NETWORK_ERROR);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	public void userLogout(final HttpResponseListener httpResponseListener) {
		ClientInstance.getInstance().post("http://115.29.175.17:8000/logout",
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("userLogout", "statusCode:" + statusCode
								+ "---response:" + response.toString());
						try {
							if (statusCode == 200) {
								if (response.getBoolean(ResponseKey.STATUS)) {
									httpResponseListener.responseSuccess();
									setLogin(false);
								} else {
									httpResponseListener
											.responseFailed(ErrorFlag.LOGOUT_ERROR);
								}
							} else {
								httpResponseListener
										.responseFailed(ErrorFlag.NETWORK_ERROR);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void getUserInfo() {
		ClientInstance.getInstance().post("http://115.29.175.17:8000/getinfo",
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("getUserInfo", "statusCode:" + statusCode
								+ "---response:" + response);
						if (statusCode == 200) {

						} else {

						}
					}
				});
	}

	public void userRegister(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		ClientInstance.getInstance().post("http://115.29.175.17:8000/register",
				params, new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						Log.d("userRegister", "statusCode:" + statusCode
								+ "---response:" + response.toString());
						try {
							if (statusCode == 200) {
								if (response.getBoolean(ResponseKey.STATUS)) {
									httpResponseListener.responseSuccess();
								} else {
									httpResponseListener
											.responseFailed(ErrorFlag.EMAIL_REGISTER);
								}
							} else {
								httpResponseListener
										.responseFailed(ErrorFlag.NETWORK_ERROR);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	public void initUserInfo(RequestParams params,
			final HttpResponseListener httpResponseListener){
		ClientInstance.getInstance().post("http://115.29.175.17:8000/editinfo",
				params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("initUserInfo", "statusCode:" + statusCode
								+ "---response:" + response.toString());
						try {
							if (statusCode == 200) {
								if (response.getBoolean(ResponseKey.STATUS)) {
									httpResponseListener.responseSuccess();
								} else {
									httpResponseListener
											.responseFailed(ErrorFlag.INIT_USER_INFO_ERROR);
								}
							} else {
								httpResponseListener
										.responseFailed(ErrorFlag.NETWORK_ERROR);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	/**
	 * 根据生日获取年龄
	 * @param birthday
	 * @return
	 */
	public static String getUserAge(String birthday) {
		if (birthday != null&&!birthday.equals("")) {
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
