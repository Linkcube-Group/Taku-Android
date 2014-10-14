package me.linkcube.taku.ui.user;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.HttpUrl;
import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.AppConst.ResponseKey;
import me.linkcube.taku.core.entity.UserAvatarEntity;
import me.linkcube.taku.core.entity.UserInfoEntity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.orm.SugarRecord;

import custom.android.util.PreferenceUtils;

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

	public static void getUserInfo() {
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
							Gson gson = new Gson();
							UserInfoEntity userInfoEntity = gson.fromJson(
									userInfo, UserInfoEntity.class);
							if (UserManager.getInstance().getUserInfo() == null) {
								SugarRecord.save(userInfoEntity);
							}
							if(UserManager.getInstance().getUserAvatarUrl()==null){
								
							}
							final String avatarUrl=UserManager.getInstance().getUserInfo().getAvatar();
							Log.d("getUserInfo", "avatarUrl:" + avatarUrl);
							if(avatarUrl!=null){
								ImageLoader.getInstance().loadImage(HttpUrl.BASE_URL+avatarUrl, new ImageLoadingListener() {
									
									@Override
									public void onLoadingStarted(String imageUri, View view) {
										
									}
									
									@Override
									public void onLoadingFailed(String imageUri, View view,
											FailReason failReason) {
										
									}
									
									@Override
									public void onLoadingComplete(String imageUri, View view, Bitmap userAvatar) {
										BitmapUtils.saveBitmap(avatarUrl, userAvatar);
										UserAvatarEntity userAvatarEntity=new UserAvatarEntity(PreferenceUtils.getString(KEY.USER_NAME,""),avatarUrl);
										SugarRecord.save(userAvatarEntity);
									}
									
									@Override
									public void onLoadingCancelled(String imageUri, View view) {
										
									}
								});
							}
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
		TakuHttpClient.post("changeavatar", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("changeAvatar", "statusCode:" + statusCode
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
