package me.linkcube.taku.ui.sportsgame;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import me.linkcube.taku.R;
import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.GameFrame;
import me.linkcube.taku.AppConst.ParamKey;
import me.linkcube.taku.core.entity.SingleDayGameHistoryEntity;
import me.linkcube.taku.ui.bt.BTSettingActivity;
import me.linkcube.taku.ui.request.GameRequest;
import me.linkcube.taku.ui.share.ShareActivity;
import me.linkcube.taku.ui.sportsgame.dashboardgame.InfoDashboard;
import me.linkcube.taku.ui.sportsgame.dashboardgame.SpeedDashboard;
import me.linkcube.taku.ui.sportsgame.dashboardgame.TargetCompletedView;
import me.linkcube.taku.ui.user.HttpResponseListener;
import me.linkcube.taku.ui.user.UpdateUserInfoActivity;
import me.linkcube.taku.ui.user.UserManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;
import com.loopj.android.http.RequestParams;

import custom.android.app.CustomFragmentActivity;
import custom.android.util.AlertUtils;

/**
 * 仪表盘界面
 * 
 * @author Administrator
 * 
 */
public class DashboardActivity extends CustomFragmentActivity {
	// 关闭
	private ImageButton close_imgBtn;
	// 分享
	private ImageButton share_imgBtn;
	// 心率
	private InfoDashboard heartRate;
	// 速度
	private SpeedDashboard speedRate;
	// 卡路里
	private InfoDashboard calorieView;

	// 目标完成
	private TargetCompletedView mTasksView;
	// 记录用户设定的运动距离
	private int mCurrentProgress;
	// 运动距离
	private TextView distance_tv;
	private SpannableString distanceString;
	// 运动时间
	private TextView time_tv;
	private SpannableString timeString;
	// 设定运行目标
	private ImageButton setTaget_imgBtn;

	private static final int SETTING_TARGET_REQUEST_CODE = 1;

	// 起始时间－－记录用户进入taku仪表盘的时间点
	private long startTime = System.currentTimeMillis();

	// 记录前一帧的时间点
	private long preTime = startTime;
	// 记录当前帧的时间点
	private long currentTime = startTime;

	// 步数，记录总步数
	private int stepCount = 0;

	private boolean isGameStart = false;

	private Timer gameDurationTimer;
	private Timer decayTimer;

	private String[] countTime = new String[2];
	private int countSecond = 0;
	double distanceRecord = 0;

	// 解析蓝牙发送过来的数据
	String blueToothString = "";
	DecimalFormat dFormat = new java.text.DecimalFormat("#.##");

	public DashboardActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_activity);
		initView();

		initData();

		HandlerThread handlerThread = new HandlerThread("Dashboard_Handler");
		handlerThread.start();
	}

	private void initView() {
		// 得到控件对象
		close_imgBtn = (ImageButton) findViewById(R.id.close_imgBtn);
		share_imgBtn = (ImageButton) findViewById(R.id.share_imgBtn);

		heartRate = (InfoDashboard) findViewById(R.id.heartRate);
		speedRate = (SpeedDashboard) findViewById(R.id.speedRate);
		calorieView = (InfoDashboard) findViewById(R.id.calorie_view);
		mTasksView = (TargetCompletedView) findViewById(R.id.tasks_view);

		distance_tv = (TextView) findViewById(R.id.distance_tv);
		time_tv = (TextView) findViewById(R.id.time_tv);
		setTaget_imgBtn = (ImageButton) findViewById(R.id.setTaget_imgBtn);

		// 给相应控件注册事件
		close_imgBtn.setOnClickListener(dashboardClickListener);
		share_imgBtn.setOnClickListener(dashboardClickListener);
		setTaget_imgBtn.setOnClickListener(dashboardClickListener);

		// 设置属性
		speedRate.setScaleImageViewRes(R.drawable.dashboard_speed_bg);
		speedRate.setPointerImageViewRes(R.drawable.dashboard_pointer);
		heartRate.setInfoImageViewRes(R.drawable.dashboard_heartrate_bg);
		heartRate.setInfoTextView("0");
		calorieView.setInfoImageViewRes(R.drawable.dashboard_cal_bg);

		speedRate.showRotateAnimation(-90, -90);

	}

	private void initData() {
		mTasksView.setTargetDistance(SportsGameManager.getTargetDistance());
		if (UserManager.getInstance().isLogin()) {
			SingleDayGameHistoryEntity singleDayGameHistoryEntity = SportsGameManager
					.getSingleDayGameHistoryEntity();
			if (singleDayGameHistoryEntity != null) {
				Log.d("getSingleDayGameHistoryEntity",
						"--singleDayGameHistoryEntity.getSingleDayDuration():"
								+ singleDayGameHistoryEntity
										.getSingleDayDuration());
				setDistanceText(singleDayGameHistoryEntity
						.getSingleDayDistance());
				setTimeText(SportsGameManager.durationToTime(Integer
						.parseInt(singleDayGameHistoryEntity
								.getSingleDayDuration())));
				calorieView.setInfoTextView(singleDayGameHistoryEntity
						.getSingleDayCalorie());
				double distance = SportsGameManager.fromStringToDouble(singleDayGameHistoryEntity
						.getSingleDayDistance());// 转换为double类型
				int progress = (int) (distance * 100 / SportsGameManager
						.getTargetDistance());
				mTasksView.setProgress(progress);
			} else {
				SportsGameManager.setSingleDayGameHistoryEntity("0", "0", "0");
				initGameData("00", "00:00", "0");
			}
		} else {
			initGameData("00", "00:00", "0");
		}
	}

	private void initGameData(String singleDayDistance,
			String singleDayDuration, String singleDayCalorie) {
		setDistanceText(singleDayDistance);
		setTimeText(singleDayDuration);
		calorieView.setInfoTextView(singleDayCalorie);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 接收玩具发送过来的数据
		BTManager.getInstance().startReceiveData(new IReceiveData() {

			@Override
			public void receiveData(int bytes, byte[] buffer) {

				byte[] buf_data = new byte[bytes];
				for (int i = 0; i < bytes; i++) {
					buf_data[i] = buffer[i];
				}
				// 传过来的是走了一步
				if (buf_data[2] == GameFrame.SPEED_FRAME[2]) {
					if (!isGameStart) {
						isGameStart = true;
						preTime = System.currentTimeMillis();
						gameDurationTimer = new Timer();
						decayTimer = new Timer();
						gameDurationTimer.schedule(new TimerTask() {
							@Override
							public void run() {
								countSecond = SportsGameManager
										.calculateDuration(time_tv.getText()
												.toString());
								countSecond++;
								Bundle timeData = new Bundle();
								timeData.putString("counttime",
										SportsGameManager
												.durationToTime(countSecond));
								Message timeMsg = new Message();
								timeMsg.setData(timeData);
								timeHandler.sendMessage(timeMsg);
							}
						}, 0, 1000);
						distanceRecord = SportsGameManager.fromStringToDouble(distance_tv
								.getText().toString());// 转换为double类型
						Log.d("DashboardActivity", "distanceRecord:---"
								+ distanceRecord);
					} else {
						// 计算速度
						currentTime = System.currentTimeMillis();
						double speed = SportsGameManager.calculateSpeed(
								preTime, currentTime);
						Log.d("DashboardActivity", "speed:" + speed);
						preTime = currentTime;
						// 计算距离
						stepCount++;
						Log.d("DashboardActivity", "distanceRecord:"
								+ distanceRecord);
						double distance = distanceRecord
								+ Double.parseDouble(dFormat
										.format(SportsGameManager
												.calculateDistance(stepCount)));
						Log.d("DashboardActivity", "distance:" + distance);
						// 计算热量
						double calorie = SportsGameManager
								.calculateCalorie(distance);
						Log.d("DashboardActivity", "calorie:" + calorie);

						Bundle data = new Bundle();
						data.putDouble("speed", speed);
						data.putDouble("distance", distance);
						data.putDouble("calorie", calorie);
						Message msg = new Message();
						msg.setData(data);
						dashboardHander.sendMessage(msg);

						// 速度的衰减
						decayTimer.schedule(new TimerTask() {
							@Override
							public void run() {
								decayHandler.sendEmptyMessage(0);
							}
						}, 0, 1000);
					}
				}

			}
		});
	}

	private Handler decayHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (fromDegrees <= -90) {

			} else {
				toDegrees = fromDegrees - 10;
				speedRate.showRotateAnimation(fromDegrees, toDegrees);
				fromDegrees = toDegrees;
			}
		}
	};

	private float fromDegrees = -90, toDegrees = -90;
	private Handler dashboardHander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			int progress = (int) (bundle.getDouble("distance") * 100 / SportsGameManager
					.getTargetDistance());
			// 更新距离控件
			mTasksView.setProgress(progress);
			Log.i("CXC", "---progress:" + progress);
			// 更新消耗热量控件
			calorieView.setInfoTextView((int) bundle.getDouble("calorie") + "");
			// 更新速度控件
			toDegrees = (float) ((bundle.getDouble("speed") - 15) * 6);
			Log.d("DashboardActivity", "---fromDegrees:" + fromDegrees
					+ ";---toDegrees:" + toDegrees);
			speedRate.showRotateAnimation(fromDegrees, toDegrees);
			fromDegrees = toDegrees;
			// 更新距离控件
			setDistanceText(dFormat.format(((bundle.getDouble("distance")))));
		}
	};

	private Handler timeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			setTimeText(bundle.getString("counttime"));
		}
	};

	OnClickListener dashboardClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_imgBtn:// 关闭
				exitDashbord();
				break;
			case R.id.share_imgBtn:// 分享
				Intent shareIntent = new Intent(getApplicationContext(),
						ShareActivity.class);
				Bundle shareBundle = new Bundle();
				shareBundle.putString("distance", distance_tv.getText()
						.toString());
				shareBundle.putString("duration", time_tv.getText().toString());
				shareBundle.putString("calorie", calorieView.getInfoTextView());
				shareIntent.putExtras(shareBundle);
				startActivity(shareIntent);
				break;
			case R.id.setTaget_imgBtn:// 设定运动目标
				startActivityForResult(new Intent(getApplicationContext(),
						TargetSettingActivity.class),
						SETTING_TARGET_REQUEST_CODE);
				break;
			default:
				break;
			}

		}
	};

	/**
	 * 退出
	 * */
	private void exitDashbord() {
		this.finish();
	}

	/**
	 * 设置TextView 文字效果
	 * */
	private void setDistanceText(String distanceStr) {
		int length = distanceStr.length();
		// 创建一个 SpannableString对象
		distanceString = new SpannableString(distanceStr);
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		distanceString.setSpan(new RelativeSizeSpan(3.0f), 0, length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		distanceString.setSpan(new ForegroundColorSpan(Color.RED), 0, length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色
		distance_tv.setText(distanceString);

	}

	private void setTimeText(String timeStr) {
		int length = timeStr.length();
		// 创建一个 SpannableString对象
		timeString = new SpannableString(timeStr);
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		timeString.setSpan(new RelativeSizeSpan(3.0f), 0, length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		timeString.setSpan(new ForegroundColorSpan(Color.RED), 0, length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		time_tv.setText(timeString);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case SETTING_TARGET_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				double mtargetDistance = data.getDoubleExtra(
						TargetSettingActivity.TARGET_DISTANCE, 0.0);
				// 更新“目标完成”仪表盘中的目标运动距离---只有重绘的时候，显示才能改变
				mTasksView.setTargetDistance(mtargetDistance);
				mTasksView.setProgress(0);
				// 每一次都要记得把之前的信息清零！！！，比如当前进度
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (UserManager.getInstance().isLogin()) {
			// 上传数据
			RequestParams params = new RequestParams();
			params.put(ParamKey.RECORD_DATE, SportsGameManager.getTodayDate());
			params.put(ParamKey.CALORIE, calorieView.getInfoTextView());
			params.put(ParamKey.DURATION, time_tv.getText().toString());
			params.put(ParamKey.DISTANCE, SportsGameManager.calculateDuration(distance_tv.getText().toString()));
			params.put(ParamKey.TARGET, SportsGameManager.getTargetDistance());
			
			Log.d("DashboardActivity", "params:"+params.toString());
			
			GameRequest.uploadGameRecord(params, new HttpResponseListener() {

				@Override
				public void responseSuccess() {

				}

				@Override
				public void responseFailed(int flag) {
					switch (flag) {
					case ErrorFlag.UPLOAD_GAME_RECORD_WRONG:
						AlertUtils.showToast(DashboardActivity.this, "上传数据失败！");
						break;
					case ErrorFlag.NETWORK_ERROR:
						AlertUtils.showToast(DashboardActivity.this,
								"网络错误，请检查！");
						break;
					default:
						break;
					}
				}
			});
			SportsGameManager.setSingleDayGameHistoryEntity(
					distance_tv.getText().toString(),
					SportsGameManager.calculateDuration(time_tv.getText()
							.toString()) + "", calorieView.getInfoTextView());
			SportsGameManager.saveSingleDayGameRecord();
		}
	}

}
