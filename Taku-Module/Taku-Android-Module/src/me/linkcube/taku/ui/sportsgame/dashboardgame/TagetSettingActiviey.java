package me.linkcube.taku.ui.sportsgame.dashboardgame;

import me.linkcube.taku.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 设定运动目标
 * */
public class TagetSettingActiviey extends Activity {
	private ImageButton back_imgBtn;
	private Button submit_btn;

	// 距离选择器如何实现－－－待定
	// 可以参考“生日”选择器－－－待定

	public TagetSettingActiviey() {
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
				//TODO
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

}
