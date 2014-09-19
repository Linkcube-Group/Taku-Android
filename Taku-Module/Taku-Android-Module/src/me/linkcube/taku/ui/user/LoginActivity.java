package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import base.common.ui.BaseActivity;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private ImageButton backBtn;
	private TextView titleTv;
	private Button loginBtn;
	private Button registerBtn;
	private EditText emailEt;
	private EditText passWordEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		initView();

	}

	private void initView() {
		backBtn = (ImageButton) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(this);
		titleTv = (TextView) findViewById(R.id.title_tv);
		titleTv.setText(getResources().getString(R.string.login_text));
		emailEt = (EditText) findViewById(R.id.user_email_et);
		passWordEt = (EditText) findViewById(R.id.user_psw_et);
		registerBtn = (Button) findViewById(R.id.title_right_btn);
		registerBtn.setOnClickListener(this);
		loginBtn = (Button) findViewById(R.id.login_btn);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.title_right_btn:
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;
		case R.id.back_btn:
			finish();
			break;
		default:
			break;
		}
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
