package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.sportsgame.dashboardgame.wheel.StrericWheelAdapter;
import me.linkcube.taku.ui.sportsgame.dashboardgame.wheel.WheelView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 设定运动目标
 * */
public class TargetSettingActivity extends Activity {
	// 返回时传递参数使用
	public static final String TARGET_DISTANCE = "me.linkcube.taku.ui.sportsgame.TargetSettingActivity.distance";
	public static final String TARGET_TIME = "me.linkcube.taku.ui.sportsgame.TargetSettingActivity.time";
	public static final String TARGET_CAL = "me.linkcube.taku.ui.sportsgame.TargetSettingActivity.cal";
	// 返回
	private ImageButton back_imgBtn;
	// 确定
	private Button submit_btn;

	// 运动距离选择器
	private WheelView distanceWheelView;
	// 运动距离选择器内容
	private String[] distanceArray = null;
	private static final int DISTANCE_ARRAY_COUNT = 20;
	private static final double DISTANCE_ARRAY_OFFSET = 0.5;

	// 运动时间选择器
	private WheelView timeWheelView;
	// 运动时间选择器内容
	private String[] timeArray = null;
	private static final int TIME_ARRAY_COUNT = 36;
	private static final int TIME_ARRAY_OFFSET = 5;

	// 消耗卡路里选择器
	private WheelView calWheelView;
	// 消耗卡路里选择器内容
	private String[] calArray = null;
	private static final int CAL_ARRAY_COUNT = 30;
	private static final int CAL_ARRAY_OFFSET = 100;

	public TargetSettingActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taget_setting_layout);
		init();
	}

	private void init() {
		// 得到控件对象
		back_imgBtn = (ImageButton) findViewById(R.id.back_imgBtn);
		submit_btn = (Button) findViewById(R.id.submit_btn);
		distanceWheelView = (WheelView) findViewById(R.id.setDistance_wheel);
		timeWheelView = (WheelView) findViewById(R.id.setTime_wheel);
		calWheelView = (WheelView) findViewById(R.id.setCal_wheel);

		// 初始化距离选择内容
		distanceArray = new String[DISTANCE_ARRAY_COUNT];
		for (int i = 0; i < distanceArray.length; i++) {
			distanceArray[i] = String.valueOf((i + 1) * DISTANCE_ARRAY_OFFSET);
		}
		// 初始化时间选择内容
		timeArray = new String[TIME_ARRAY_COUNT];
		for (int i = 0; i < timeArray.length; i++) {
			timeArray[i] = String.valueOf((i + 1) * TIME_ARRAY_OFFSET);
		}
		// 初始化cal选择内容
		calArray = new String[CAL_ARRAY_COUNT];
		for (int i = 0; i < calArray.length; i++) {
			calArray[i] = String.valueOf((i + 1) * CAL_ARRAY_OFFSET);
		}

		// 添加数据－－距离
		distanceWheelView.setAdapter(new StrericWheelAdapter(distanceArray));
		distanceWheelView.setCurrentItem(5);
		distanceWheelView.setCyclic(true);
		distanceWheelView
				.setInterpolator(new AnticipateOvershootInterpolator());
		// 添加数据－－时间
		timeWheelView.setAdapter(new StrericWheelAdapter(timeArray));
		timeWheelView.setCurrentItem(5);
		timeWheelView.setCyclic(true);
		timeWheelView.setInterpolator(new AnticipateOvershootInterpolator());
		// 添加数据－－cal
		calWheelView.setAdapter(new StrericWheelAdapter(calArray));
		calWheelView.setCurrentItem(5);
		calWheelView.setCyclic(true);
		calWheelView.setInterpolator(new AnticipateOvershootInterpolator());

		// 注册事件
		back_imgBtn.setOnClickListener(tagetSettingClickListener);
		submit_btn.setOnClickListener(tagetSettingClickListener);

	}

	OnClickListener tagetSettingClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_imgBtn:// 关闭
				exitTagetSetting();
				break;

			case R.id.submit_btn:// 确定
				// TODO
				submitTagetSetting();
				break;
			default:
				break;
			}

		}
	};

	/**
	 * 退出当前
	 * */
	private void exitTagetSetting() {
		this.finish();
	}

	/**
	 * 提交设定
	 * */
	private void submitTagetSetting() {
		Intent intent = new Intent();
		// 运动距离
		double targetDistance=(distanceWheelView.getCurrentItem() + 1)* DISTANCE_ARRAY_OFFSET;
		intent.putExtra(TARGET_DISTANCE,targetDistance);
		SportsGameManager.setTargetDistance(targetDistance);
		Log.d("submitTagetSetting", "targetDistance:"+targetDistance);
		// 运动时间

		// 消耗卡路里

		this.setResult(RESULT_OK, intent);
		this.finish();
	}

}
