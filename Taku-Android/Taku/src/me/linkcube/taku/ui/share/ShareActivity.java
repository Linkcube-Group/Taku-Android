package me.linkcube.taku.ui.share;

import me.linkcube.taku.R;
import android.app.Activity;
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

/**
 * 分享
 * */
public class ShareActivity extends Activity {
	// 返回
	private ImageButton back_imgBtn;
	// 运动距离
	private TextView distance_tv;
	private SpannableString distanceString;
	// 运行时间
	private TextView time_tv;
	private SpannableString timeString;
	// 卡路里
	private TextView cal_tv;
	private SpannableString calString;
	// 分享
	private Button share_btn;

	public ShareActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_activity);
		init();
	}

	private void init() {
		// 得到控件
		back_imgBtn = (ImageButton) findViewById(R.id.back_imgBtn);
		distance_tv = (TextView) findViewById(R.id.distance_tv);
		time_tv = (TextView) findViewById(R.id.time_tv);
		cal_tv = (TextView) findViewById(R.id.cal_tv);
		share_btn = (Button) findViewById(R.id.share_btn);
		//设置效果
		setDistanceText("3000");
		setTimeText("30:12");
		setCalText("475");

		// 注册事件
		back_imgBtn.setOnClickListener(onShareActivityButtonClickListener);
		share_btn.setOnClickListener(onShareActivityButtonClickListener);

	}

	OnClickListener onShareActivityButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_imgBtn:
				exitShareActivity();
				break;
			case R.id.share_btn:
				// TODO
				break;

			default:
				break;
			}

		}
	};

	private void exitShareActivity() {
		this.finish();
	}
	
	/**
	 * 设置TextView 文字效果
	 * */
	private void setDistanceText(String distanceStr) {
		// 创建一个 SpannableString对象
		distanceString = new SpannableString("运动距离：" + distanceStr + " 米");
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		distanceString.setSpan(new RelativeSizeSpan(2.0f), 5, 9,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		distanceString.setSpan(new ForegroundColorSpan(Color.RED), 5, 9,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色
		
//		distanceString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 5,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
//		distanceString.setSpan(new ForegroundColorSpan(Color.BLACK), 9, 10,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		distance_tv.setText(distanceString);

	}

	private void setTimeText(String timeStr) {
		// 创建一个 SpannableString对象
		timeString = new SpannableString("运动时间：" + timeStr + " 分/秒");
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		timeString.setSpan(new RelativeSizeSpan(2.0f), 5, 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		timeString.setSpan(new ForegroundColorSpan(Color.RED), 5, 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色
		
//		timeString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 5,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
//		timeString.setSpan(new ForegroundColorSpan(Color.BLACK), 10, 11,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		time_tv.setText(timeString);

	}
	private void setCalText(String calStr) {
		// 创建一个 SpannableString对象
		calString = new SpannableString("燃烧：" + calStr + " 大卡");
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		calString.setSpan(new RelativeSizeSpan(2.0f), 3, 6,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		calString.setSpan(new ForegroundColorSpan(Color.RED), 3, 6,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色
		
//		calString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 5,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
//		calString.setSpan(new ForegroundColorSpan(Color.BLACK), 10, 11,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为黑色
		cal_tv.setText(calString);

	}
}
