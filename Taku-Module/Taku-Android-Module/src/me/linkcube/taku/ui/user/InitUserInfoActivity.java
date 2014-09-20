package me.linkcube.taku.ui.user;

import java.util.ArrayList;
import java.util.List;

import me.linkcube.taku.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import base.common.ui.TitleBaseActivity;
import base.common.util.Rotate3DUtils;

public class InitUserInfoActivity extends TitleBaseActivity implements
		OnTouchListener {

	//private List<ViewGroup> viewGroups = new ArrayList<ViewGroup>();
	private List<EditText> editTexts = new ArrayList<EditText>();
	private int[] viewGroupsRes = { R.id.user_nickname_fl,
			R.id.user_birthday_fl, R.id.user_weight_fl, R.id.user_height_fl };
	private int[] editTextsRes = { R.id.user_nickname_et,
			R.id.user_birthday_et, R.id.user_weight_et, R.id.user_height_et };
	private ImageView user_gender_female_iv;
	private ImageView user_gender_male_iv;
	private Button submitBtn;
	private int isFemale = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init_user_info_activity);
		initView();
	}

	private void initView() {
		initTitle();
		for (int i = 0; i < viewGroupsRes.length; i++) {
			final ViewGroup viewGroup = (ViewGroup) findViewById(viewGroupsRes[i]);
			final EditText editText = (EditText) findViewById(editTextsRes[i]);
			editTexts.add(editText);
			editText.setOnTouchListener(this);
			viewGroup.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					applyRotation(viewGroup, editText, 0, 0, -180);// 左旋90度
				}
			});
		}
		user_gender_female_iv = (ImageView) findViewById(R.id.user_gender_female_iv);
		user_gender_female_iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user_gender_female_iv.setSelected(true);
				user_gender_male_iv.setSelected(false);
				isFemale = 1;
			}
		});
		user_gender_male_iv = (ImageView) findViewById(R.id.user_gender_male_iv);
		user_gender_male_iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user_gender_female_iv.setSelected(false);
				user_gender_male_iv.setSelected(true);
				isFemale = 0;
			}
		});
		submitBtn = (Button) findViewById(R.id.submit_btn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				for (int i = 0; i < editTextsRes.length; i++) {
					Log.d("InitUserInfoActivity", editTexts.get(i).getText().toString());
				}
			}
		});
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.init_user_info_text));
		getRightTitleBtn().setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.user_birthday_et:
				DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int year,
							int month, int dayOfMonth) {
						// Calendar月份是从0开始,所以month要加1
						((EditText) v).setText(year + "-" + (month + 1) + "-"
								+ dayOfMonth);
					}
				};
				Dialog dialog = new DatePickerDialog(this, dateListener, 1988,
						0, 23);
				// calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)
				dialog.show();
				break;

			default:
				break;
			}
		}
		return false;
	}

	/*
	 * 应用变换的方法，里面将会使用之前写好的Rotate3d类
	 */
	private void applyRotation(ViewGroup mContainer, View showView,
			int position, float start, float end) {
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;
		final Rotate3DUtils rotation = new Rotate3DUtils(start, end, centerX,
				centerY, 0, true);
		rotation.setDuration(1000); // 可设置翻转的时间，以ms为单位
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(showView));
		mContainer.startAnimation(rotation); // 开始翻转前90度
	}

	/*
	 * 这个类用于监听前90度翻转完成
	 */
	private final class DisplayNextView implements Animation.AnimationListener {

		private View showView;
		private boolean which = false;

		private DisplayNextView(View showView) {
			this.showView = showView;
		}

		public void onAnimationStart(Animation animation) {

		}

		public void onAnimationEnd(Animation animation) {
			if (showView.getVisibility() == View.GONE) {
				showView.setVisibility(View.VISIBLE);
				showView.setFocusable(true);
			} else {
				showView.setVisibility(View.GONE);
			}
		}

		public void onAnimationRepeat(Animation animation) {

		}
	}

}
