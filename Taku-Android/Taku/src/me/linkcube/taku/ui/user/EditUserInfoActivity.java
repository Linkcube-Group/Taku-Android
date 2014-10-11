package me.linkcube.taku.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import me.linkcube.taku.R;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.ClearEditText;

public class EditUserInfoActivity extends BaseTitleActivity {

	private String information;
	private int requestCode;
	private static final int CHANGE_NICKNAME = 1022;
	private static final int CHANGE_HEIGHT = 1023;
	private static final int CHANGE_WEIGHT = 1024;
	
	private ClearEditText userInfoEt;
	private TextView userInfoDescriptionTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_user_info_activity);

		Intent intent = getIntent();
		information = intent.getStringExtra("information");
		requestCode = intent.getIntExtra("requestCode", 0);

		initView();
	}

	private void initView() {
		userInfoEt=(ClearEditText)findViewById(R.id.edit_user_info_et);
		userInfoDescriptionTv=(TextView)findViewById(R.id.user_info_description_tv);
		initTitle();
	}

	private void initTitle() {
		switch (requestCode) {
		case CHANGE_NICKNAME:
			setTitleText("编辑昵称");
			userInfoEt.setText(information);
			userInfoEt.setInputType(InputType.TYPE_CLASS_TEXT);
			userInfoDescriptionTv.setText("换个名字换个心情!");
			break;
		case CHANGE_HEIGHT:
			setTitleText("编辑身高");
			userInfoEt.setText(information);
			userInfoEt.setInputType(InputType.TYPE_CLASS_NUMBER);
			userInfoDescriptionTv.setText("长个子啦！");
			break;
		case CHANGE_WEIGHT:
			setTitleText("编辑体重");
			userInfoEt.setText(information);
			userInfoEt.setInputType(InputType.TYPE_CLASS_NUMBER);
			userInfoDescriptionTv.setText("不要在乎现在的体重，我们会帮您减下去！");
			break;
		default:
			break;
		}
		getRightTitleBtn().setText(
				getResources().getString(R.string.save_btn_text));
		setRightTitleBtn(R.drawable.user_btn_bg);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent data=new Intent();
				data.putExtra("returnUserInfo", userInfoEt.getText().toString());
				Log.d("EditUserInfoActivity",userInfoEt.getText().toString());
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
	}
}
