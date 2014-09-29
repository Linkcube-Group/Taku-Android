package me.linkcube.taku.ui.user;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.ResponseKey;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 用户相关的网络请求
 * 
 * @author xinyang
 * 
 */
public class UserRequest {

	public static void userLogin(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("login", params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.d("userLogin", "statusCode:" + statusCode + "---response:"
						+ response.toString());
				try {
					if (statusCode == 200) {
						if (response.getBoolean(ResponseKey.STATUS)) {
							TakuHttpClient.getCookie();
							getUserInfo();
							httpResponseListener.responseSuccess();
							UserManager.getInstance().setLogin(true);
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

	public static void userLogout(
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("logout", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.d("userLogout", "statusCode:" + statusCode + "---response:"
						+ response.toString());
				try {
					if (statusCode == 200) {
						if (response.getBoolean(ResponseKey.STATUS)) {
							httpResponseListener.responseSuccess();
							UserManager.getInstance().setLogin(false);
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

	private static void getUserInfo() {
		TakuHttpClient.post("getinfo", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.d("getUserInfo", "statusCode:" + statusCode
						+ "---response:" + response);
				try {
					if (statusCode == 200) {
						if (response.getBoolean(ResponseKey.STATUS)) {
							String userInfo = response.getString("info");
							// Gson gson = new Gson();
							// UserInfoEntity userInfoEntity = gson.fromJson(
							// userInfo, UserInfoEntity.class);
							// userInfoEntity.save();
							// TODO 获取到用户个人信息后的一些操作
							// Log.d("getUserInfo",
							// "Nickname:" + userInfoEntity.getNickname());
						} else {

						}
					} else {

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void userRegister(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("register", params, new JsonHttpResponseHandler() {

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

	public static void editUserInfo(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("editinfo", params, new JsonHttpResponseHandler() {
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

	public static void changeAvatar(Context context, RequestParams params,
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("changeavatar", params,new JsonHttpResponseHandler() {
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

}
