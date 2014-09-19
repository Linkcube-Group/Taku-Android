package me.linkcube.taku.ui.sportsgame.view;

import me.linkcube.taku.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 仪表盘－－心率和速度显示组合控件
 * */
public class Dashboard extends RelativeLayout {

	private ImageView scaleImageView;
	private ImageView pointerImageView;
	private DashboardRotateAnimation rotateAnimation;

	public Dashboard(Context context) {
		this(context, null);
	}

	public Dashboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.dashboard_layout, this,
				true);
		scaleImageView = (ImageView) findViewById(R.id.scaleImageView);
		pointerImageView = (ImageView) findViewById(R.id.pointerImageView);
		// 初始化旋转类
		rotateAnimation = new DashboardRotateAnimation();
		rotateAnimation.setRotateImageView(pointerImageView);

	}

	// 设置属性值
	public void setScaleImageViewRes(int resId) {
		this.scaleImageView.setImageResource(resId);
	}

	public void setPointerImageViewRes(int resId) {
		this.pointerImageView.setImageResource(resId);
	}

	public void showRotateAnimation(float fromDegrees, float toDegrees) {
		rotateAnimation.showAnimation(fromDegrees, toDegrees);

	}

}
