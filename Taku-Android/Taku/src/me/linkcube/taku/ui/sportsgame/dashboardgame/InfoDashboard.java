package me.linkcube.taku.ui.sportsgame.dashboardgame;

import me.linkcube.taku.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 卡路里和心率显示组合控件
 * */
public class InfoDashboard extends RelativeLayout {
	private ImageView infoImageView;
	private TextView infoTextView;

	public InfoDashboard(Context context) {
		this(context, null);
	}

	public InfoDashboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.dashboard_info_layout,
				this, true);
		infoImageView = (ImageView) findViewById(R.id.infoImageView);
		infoTextView = (TextView) findViewById(R.id.infoTextView);
	}

	// 设置属性方法
	public void setInfoImageViewRes(int resId) {
		this.infoImageView.setImageResource(resId);
	}

	public void setInfoTextView(CharSequence text) {
		this.infoTextView.setText(text);
	}
	
	public String getInfoTextView(){
		return this.infoTextView.getText().toString();
	}

}
