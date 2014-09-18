package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import me.linkcube.taku.common.ui.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener {

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
