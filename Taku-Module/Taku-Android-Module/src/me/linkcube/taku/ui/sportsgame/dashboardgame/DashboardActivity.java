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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
	// 设定运行目标
	private Button setTaget_btn;

	public DashboardActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_activity);
		init();
	}

	private void init() {
		// 得到控件对象
		close_imgBtn = (ImageButton) findViewById(R.id.close_imgBtn);
		connDevice_imgBtn = (ImageButton) findViewById(R.id.connDevice_imgBtn);
		share_imgBtn = (ImageButton) findViewById(R.id.share_imgBtn);
		heartRate = (Dashboard) findViewById(R.id.heartRate);
		speedRate = (Dashboard) findViewById(R.id.speedRate);
		cal = (Calorie) findViewById(R.id.cal);
		distance_tv = (TextView) findViewById(R.id.distance_tv);
		time_tv = (TextView) findViewById(R.id.time_tv);
		setTaget_btn = (Button) findViewById(R.id.setTaget_btn);

		// 给相应控件注册事件
		close_imgBtn.setOnClickListener(dashboardClickListener);
		connDevice_imgBtn.setOnClickListener(dashboardClickListener);
		share_imgBtn.setOnClickListener(dashboardClickListener);
		setTaget_btn.setOnClickListener(dashboardClickListener);

		// 设置属性
		speedRate.setScaleImageViewRes(R.drawable.dashboard_scale);
		speedRate.setPointerImageViewRes(R.drawable.dashboard_pointer);
		heartRate.setScaleImageViewRes(R.drawable.dashboard_scale);
		heartRate.setPointerImageViewRes(R.drawable.dashboard_pointer);
		cal.setCalImageViewRes(R.drawable.dashboard_scale);
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
			case R.id.share_imgBtn:// 分享
				showInfo("点击－－分享");
				// TODO
				break;
			case R.id.setTaget_btn:// 设定运动目标
				// TODO
				showInfo("点击－－设定运动目标");
				startActivity(new Intent(getApplicationContext(), TagetSettingActiviey.class));
				break;

			default:
				break;
			}

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
		time_tv.setText(timeString);

	}

}
