package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import base.common.ui.TitleBaseActivity;
import base.common.util.AlertUtils;
import base.common.util.StringUtils;

public class RegisterActivity extends TitleBaseActivity implements
		OnClickListener {
	private Button registerBtn;
	private EditText emailEt;
	private EditText passWordEt;
	private EditText confirmPswEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);

		initView();

	}

	private void initView() {
		initTitle();
		emailEt = (EditText) findViewById(R.id.user_email_et);
		passWordEt = (EditText) findViewById(R.id.user_psw_et);
		confirmPswEt = (EditText) findViewById(R.id.user_confirm_psw_et);
		registerBtn = (Button) findViewById(R.id.register_btn);
		registerBtn.setOnClickListener(this);
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.register_text));
		getRightTitleBtn().setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.register_btn:
			if (!StringUtils.isEmailAddress(emailEt.getText().toString())) {
				AlertUtils.showToast(
						this,
						getResources().getString(
								R.string.toast_username_hasbeen_email));
				emailEt.setText("");
			}
			if (!passWordEt.getText().toString()
					.equals(confirmPswEt.getText().toString())) {
				AlertUtils.showToast(
						this,
						getResources().getString(
								R.string.toast_username_hasbeen_email));
				passWordEt.setText("");
				confirmPswEt.setText("");
			}
			// TODO 注册相关事情
			startActivity(new Intent(RegisterActivity.this,InitUserInfoActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
