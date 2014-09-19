package me.linkcube.taku.ui.sportsgame.dashboardgame;

import me.linkcube.taku.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardRunActivity extends Activity {
	// 关闭
	private ImageButton close_imgBtn;
	// 连接设备－－》taku
	private ImageButton connDevice_imgBtn;
	// 停止
	private ImageButton stop_imgBtn;
	// 心率
	private Dashboard heartRate;
	// 速度
	private Dashboard speedRate;
	// 卡路里
	private Calorie cal;
	// 运动距离
	private TextView distance_tv;
	private SpannableString distanceString;
	// 运动时间
	private TextView time_tv;
	private SpannableString timeString;
	// 运动当前距离
	private SeekBar currentDistance_sb;

	public DashboardRunActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_run_layout);
		init();
	}

	private void init() {
		// 得到控件对象
		close_imgBtn = (ImageButton) findViewById(R.id.close_imgBtn);
		connDevice_imgBtn = (ImageButton) findViewById(R.id.connDevice_imgBtn);
		stop_imgBtn = (ImageButton) findViewById(R.id.stop_imgBtn);
		heartRate = (Dashboard) findViewById(R.id.heartRate);
		speedRate = (Dashboard) findViewById(R.id.speedRate);
		cal = (Calorie) findViewById(R.id.cal);
		distance_tv = (TextView) findViewById(R.id.distance_tv);
		time_tv = (TextView) findViewById(R.id.time_tv);
		currentDistance_sb = (SeekBar) findViewById(R.id.currentDistance_sb);

		// 给相应控件注册事件
		close_imgBtn.setOnClickListener(dashboardClickListener);
		connDevice_imgBtn.setOnClickListener(dashboardClickListener);
		stop_imgBtn.setOnClickListener(dashboardClickListener);
		stop_imgBtn.setOnClickListener(dashboardClickListener);
		currentDistance_sb
				.setOnSeekBarChangeListener(currentDistanceChangedListener);

		// 设置属性
		speedRate.setScaleImageViewRes(R.drawable.dashboard_speed_bg);
		speedRate.setPointerImageViewRes(R.drawable.dashboard_pointer);
		heartRate.setScaleImageViewRes(R.drawable.dashboard_heartrate_bg);
		heartRate.setPointerImageViewRes(R.drawable.dashboard_pointer);
		cal.setCalImageViewRes(R.drawable.dashboard_cal_bg);
		cal.setCalTextView("消耗:XXXX卡");

		setDistanceText("0000");
		setTimeText("00:00");

	}

	OnClickListener dashboardClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.close_imgBtn:// 关闭
				showInfo("点击－－关闭");
				exitDashbord();
				break;
			case R.id.connDevice_imgBtn:// 连接设备
				// TODO
				showInfo("点击－－连接设备");
				break;
			case R.id.stop_imgBtn:// 停止
				showInfo("点击－－分享");
				// TODO
				break;
			case R.id.setTaget_btn:// 设定运动目标
				// TODO
				showInfo("点击－－设定运动目标");
				startActivity(new Intent(getApplicationContext(),
						TagetSettingActiviey.class));
				break;

			default:
				break;
			}

		}
	};

	OnSeekBarChangeListener currentDistanceChangedListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			showInfo("当前运动距离：" + seekBar.getProgress() + "米");
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	private void showInfo(String infoString) {
		Toast.makeText(getApplicationContext(), infoString, Toast.LENGTH_LONG)
				.show();
	}

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
		distanceString = new SpannableString("运动距离：" + distanceStr + "米");
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		distanceString.setSpan(new RelativeSizeSpan(2.0f), 5, 9,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		distanceString.setSpan(new ForegroundColorSpan(Color.RED), 5, 9,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		distanceString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		distanceString.setSpan(new ForegroundColorSpan(Color.BLACK), 9, 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		distance_tv.setText(distanceString);

	}

	private void setTimeText(String timeStr) {
		// 创建一个 SpannableString对象
		timeString = new SpannableString("运动时间：" + timeStr + "分/秒");
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		timeString.setSpan(new RelativeSizeSpan(2.0f), 5, 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		timeString.setSpan(new ForegroundColorSpan(Color.RED), 5, 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		timeString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		timeString.setSpan(new ForegroundColorSpan(Color.BLACK), 10, 11,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		time_tv.setText(timeString);

	}
}
