package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.bt.BTSettingActivity;
import me.linkcube.taku.ui.share.ShareActivity;
import me.linkcube.taku.ui.sportsgame.dashboardgame.InfoDashboard;
import me.linkcube.taku.ui.sportsgame.dashboardgame.SpeedDashboard;
import me.linkcube.taku.ui.sportsgame.dashboardgame.TargetCompletedView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
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

import com.ervinwang.bthelper.BTHelper;
import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;
import com.ervinwang.bthelper.utils.FormatUtils;
import com.unity3d.player.l;

/**
 * 仪表盘
 * */
public class DashboardActivity extends Activity {
	// 关闭
	private ImageButton close_imgBtn;
	// 连接设备
	private ImageButton connDevice_imgBtn;
	// 分享
	private ImageButton share_imgBtn;
	// 心率
	private InfoDashboard heartRate;
	// 速度
	private SpeedDashboard speedRate;
	// 卡路里
	private InfoDashboard cal;

	// 目标完成
	private TargetCompletedView mTasksView;
	// 记录用户设定的运动距离
	private double mtargetDistance;
	private int mTotalProgress;
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

	// Just for Test
	private int precent = 0;

	// 解析蓝牙发送过来的数据
	String blueToothString = "";

	public DashboardActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_activity);
		init();

		// new Thread(new ProgressRunable()).start();
		// speedRate.showRotateAnimation(0, 180);
		// speedRate.showRotateAnimation(90, 180);

		HandlerThread handlerThread = new HandlerThread("Dashboard_Handler");
		handlerThread.start();
		final DashboardHander dashboardHander = new DashboardHander(
				handlerThread.getLooper());

		// 接收玩具发送过来的数据
		BTManager.getInstance().startReceiveData(new IReceiveData() {

			@Override
			public void receiveData(int bytes, byte[] buffer) {
				byte[] buf_data = new byte[bytes];
				for (int i = 0; i < bytes; i++) {
					buf_data[i] = buffer[i];
				}
				blueToothString = FormatUtils.bytesToHexString(buf_data);
				Log.i("CXC", "----" + blueToothString);
				Log.d("Receive Data Hex 1 = ", blueToothString);
				Bundle data = new Bundle();
				data.putInt("progress", precent++);
				Message msg = dashboardHander.obtainMessage();
				msg.setData(data);
				msg.sendToTarget();

			}
		});

	}

	private int computeData() {
		return 88;
	}

	private void init() {
		// 得到控件对象
		close_imgBtn = (ImageButton) findViewById(R.id.close_imgBtn);
		connDevice_imgBtn = (ImageButton) findViewById(R.id.connDevice_imgBtn);
		share_imgBtn = (ImageButton) findViewById(R.id.share_imgBtn);

		heartRate = (InfoDashboard) findViewById(R.id.heartRate);
		speedRate = (SpeedDashboard) findViewById(R.id.speedRate);
		cal = (InfoDashboard) findViewById(R.id.cal);
		mTasksView = (TargetCompletedView) findViewById(R.id.tasks_view);

		distance_tv = (TextView) findViewById(R.id.distance_tv);
		time_tv = (TextView) findViewById(R.id.time_tv);
		setTaget_imgBtn = (ImageButton) findViewById(R.id.setTaget_imgBtn);

		// 给相应控件注册事件
		close_imgBtn.setOnClickListener(dashboardClickListener);
		connDevice_imgBtn.setOnClickListener(dashboardClickListener);
		share_imgBtn.setOnClickListener(dashboardClickListener);
		setTaget_imgBtn.setOnClickListener(dashboardClickListener);

		// 设置属性
		speedRate.setScaleImageViewRes(R.drawable.dashboard_speed_bg);
		speedRate.setPointerImageViewRes(R.drawable.dashboard_pointer);
		heartRate.setInfoImageViewRes(R.drawable.dashboard_heartrate_bg);
		heartRate.setInfoTextView("88");
		cal.setInfoImageViewRes(R.drawable.dashboard_cal_bg);
		cal.setInfoTextView("888");

		mTotalProgress = 100;
		mCurrentProgress = 0;
		mTasksView.setProgress(mCurrentProgress);
		setDistanceText("00");
		setTimeText("00:00");

	}

	OnClickListener dashboardClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.close_imgBtn:// 关闭
				exitDashbord();
				break;
			case R.id.connDevice_imgBtn:// 连接设备
				// TODO
				startActivity(new Intent(getApplicationContext(),
						BTSettingActivity.class));
				break;
			case R.id.share_imgBtn:// 分享
				startActivity(new Intent(getApplicationContext(),
						ShareActivity.class));
				// TODO
				break;
			case R.id.setTaget_imgBtn:// 设定运动目标
				// TODO
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
		// 创建一个 SpannableString对象
		distanceString = new SpannableString(distanceStr);
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		distanceString.setSpan(new RelativeSizeSpan(3.0f), 0, 2,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		distanceString.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色
		distance_tv.setText(distanceString);

	}

	private void setTimeText(String timeStr) {
		// 创建一个 SpannableString对象
		timeString = new SpannableString(timeStr);
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		timeString.setSpan(new RelativeSizeSpan(3.0f), 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		timeString.setSpan(new ForegroundColorSpan(Color.RED), 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		time_tv.setText(timeString);

	}

	Runnable dashboardRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 更新“目标完成”
			while (mCurrentProgress < mTotalProgress) {
				mCurrentProgress += 1;
				// mTasksView.setProgress(mCurrentProgress);

				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SETTING_TARGET_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			mtargetDistance = data.getDoubleExtra(
					TargetSettingActivity.TARGET_DISTANCE, 0.0);
			// 更新“目标完成”仪表盘中的目标运动距离---只有重绘的时候，显示才能改变
			mTasksView.setTargetDistance(mtargetDistance);
			mTasksView.setProgress(0);
		}
	}

	/**
	 * 用于更新UI控件
	 * */
	class DashboardHander extends Handler {
		// 构造函数
		public DashboardHander() {

		}

		public DashboardHander(Looper looper) {
			super(looper);

		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			int progress = (int) bundle.getInt("progress");
			mTasksView.setProgress(progress);
			Log.i("CXC", "---progress:" + progress);

		}
	}
}
