package me.linkcube.taku.ui.request;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.ResponseKey;
import me.linkcube.taku.core.entity.SingleDayGameHistoryEntity;
import me.linkcube.taku.core.entity.TotalGameHistoryEntity;
import me.linkcube.taku.ui.user.HttpGameResponseListener;
import me.linkcube.taku.ui.user.HttpResponseListener;
import me.linkcube.taku.ui.user.TakuHttpClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 游戏相关的网络请求
 * @author xinyang
 *
 */
public class GameRequest {
	public static void uploadGameRecord(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("uploadGameRecord", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("uploadGameRecord", "statusCode:" + statusCode
								+ "---response:" + response.toString());
						try {
							if (statusCode == 200) {
								if (response.getBoolean(ResponseKey.STATUS)) {
									// TODO 上传数据成功
								} else {
									httpResponseListener
											.responseFailed(ErrorFlag.UPLOAD_GAME_RECORD_WRONG);
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

	public static void getSingleDayHistory(RequestParams params,
			final HttpGameResponseListener httpGameResponseListener) {
		TakuHttpClient.post("getSingleDayHistory", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.d("getSingleDayHistory", "statusCode:" + statusCode
								+ "---response:" + response.toString());
						try {
							if (statusCode == 200) {
								if (response.getBoolean(ResponseKey.STATUS)) {
									String userInfo = response.getString("info");
									Gson gson = new Gson();
									SingleDayGameHistoryEntity singleDayGameHistoryEntity = gson.fromJson(
											userInfo, SingleDayGameHistoryEntity.class);
									httpGameResponseListener.responseSuccess(singleDayGameHistoryEntity);
								} else {
									httpGameResponseListener
											.responseFailed(ErrorFlag.GET_SINGLEDAY_HISTORY_WRONG);
								}
							} else {
								httpGameResponseListener
										.responseFailed(ErrorFlag.NETWORK_ERROR);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public static void getTotalHistory(
			final HttpGameResponseListener httpGameResponseListener) {
		TakuHttpClient.post("getTotalHistory", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.d("getTotalHistory", "statusCode:" + statusCode
						+ "---response:" + response.toString());
				try {
					if (statusCode == 200) {
						if (response.getBoolean(ResponseKey.STATUS)) {
							String userInfo = response.getString("info");
							Gson gson = new Gson();
							TotalGameHistoryEntity totalGameHistoryEntity = gson.fromJson(
									userInfo, TotalGameHistoryEntity.class);
							httpGameResponseListener.responseSuccess(totalGameHistoryEntity);
						} else {
							httpGameResponseListener
									.responseFailed(ErrorFlag.GET_TOTAL_HISTORY_WRONG);
						}
					} else {
						httpGameResponseListener
								.responseFailed(ErrorFlag.NETWORK_ERROR);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
