package me.linkcube.taku.view;

import me.linkcube.taku.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleView extends LinearLayout {

	protected Button leftTitleBtn;
	private TextView titleText;
	protected Button rightTitleBtn;

	public TitleView(Context context) {
		this(context, null);
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		View titleLayout = LayoutInflater.from(context).inflate(
				R.layout.user_action_bar, this);
		leftTitleBtn = (Button) titleLayout.findViewById(R.id.title_left_btn);
		titleText = (TextView) titleLayout.findViewById(R.id.title_text);
		rightTitleBtn = (Button) titleLayout.findViewById(R.id.title_right_btn);
	}

	/**
	 * 标题
	 * 
	 * @param title
	 */
	public void setTitleText(String title) {
		this.titleText.setText(title);
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setRightTitleIv(int resId) {
		if (null != rightTitleBtn) {
			rightTitleBtn.setBackgroundResource(resId);
		}
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param drawable
	 */
	public void setRightTitleIv(Drawable drawable) {
		if (null != rightTitleBtn) {
			rightTitleBtn.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 得到右边的按钮
	 * 
	 * @return
	 */
	public Button getRightTitleBtn() {
		return rightTitleBtn;
	}

}
