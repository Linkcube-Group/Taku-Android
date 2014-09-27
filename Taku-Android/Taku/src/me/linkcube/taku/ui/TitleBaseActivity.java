package me.linkcube.taku.ui;

import base.common.ui.BaseActivity;
import me.linkcube.taku.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 用于显示title的activity基类
 * @author xinyang
 *
 */
public class TitleBaseActivity extends BaseActivity {

	public FrameLayout ly_content;
	private View contentView;
	public View titleLayout;
	private RelativeLayout titleRelativeLayout;
	protected Button leftTitleBtn;
	private TextView titleText;
	protected Button rightTitleBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		titleLayout = LayoutInflater.from(this).inflate(
				R.layout.user_action_bar, null);
		titleRelativeLayout = (RelativeLayout) titleLayout
				.findViewById(R.id.title_layout);
		leftTitleBtn = (Button) titleLayout.findViewById(R.id.title_left_btn);
		titleText = (TextView) titleLayout.findViewById(R.id.title_text);
		rightTitleBtn = (Button) titleLayout.findViewById(R.id.title_right_btn);

		leftTitleBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/***
	 * 设置内容区域
	 * 
	 * @param resId
	 *            资源文件ID
	 */
	public void setContentView(int resId) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(resId, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(layoutParams);
		ly_content = new FrameLayout(this);
		ly_content.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		ly_content.addView(contentView);
		ly_content.addView(titleLayout);
		setContentView(ly_content);
	}

	/**
	 * 标题
	 * 
	 * @param title
	 */
	protected void setTitleText(String title) {
		this.titleText.setText(title);
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param resId
	 */
	protected void setRightTitleBtn(int resId) {
		if (null != rightTitleBtn) {
			rightTitleBtn.setBackgroundResource(resId);
		}
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param drawable
	 */
	protected void setRightTitleIv(Drawable drawable) {
		if (null != rightTitleBtn) {
			rightTitleBtn.setBackgroundDrawable(drawable);
		}
	}
	
	public Button getLeftTitleBtn(){
		return leftTitleBtn;
	}

	/**
	 * 得到右边的按钮
	 * 
	 * @return
	 */
	public Button getRightTitleBtn() {
		return rightTitleBtn;
	}

	public RelativeLayout getTitleViewRl() {
		return titleRelativeLayout;
	}
}
