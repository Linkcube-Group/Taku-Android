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
 * 仪表盘－－卡路里显示组合控件
 * */
public class Calorie extends RelativeLayout {
	private ImageView calImageView;
	private TextView calTextView;

	public Calorie(Context context) {
		this(context, null);
	}

	public Calorie(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cal_layout, this, true);
		calImageView = (ImageView) findViewById(R.id.calImageView);
		calTextView = (TextView) findViewById(R.id.calTextView);
		Log.i("CXC", "XXXXXXXXXXXX------");
	}

	// 设置属性方法
	public void setCalImageViewRes(int resId) {
		this.calImageView.setImageResource(resId);
	}

	public void setCalTextView(CharSequence text) {
		this.calTextView.setText(text);
	}

}
