package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import base.common.ui.TitleBaseActivity;

public class LoginActivity extends TitleBaseActivity {

	private Button loginBtn;
	private EditText emailEt;
	private EditText passWordEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		initView();

	}

	private void initView() {
		initTitle();
		emailEt = (EditText) findViewById(R.id.user_email_et);
		passWordEt = (EditText) findViewById(R.id.user_psw_et);
		loginBtn = (Button) findViewById(R.id.login_btn);
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.login_text));
		getRightTitleBtn().setText(
				getResources().getString(R.string.register_btn_text));
		getRightTitleBtn().setTextColor(R.drawable.user_btn_color);
		setRightTitleBtn(R.drawable.ic_update_user_info);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
