package me.linkcube.taku.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import me.linkcube.taku.R;
import me.linkcube.taku.common.ui.BaseActivity;
import me.linkcube.taku.common.util.AlertUtils;
import me.linkcube.taku.common.util.StringUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private Button titleRightBtn;
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
		emailEt = (EditText) findViewById(R.id.user_email_et);
		passWordEt = (EditText) findViewById(R.id.user_psw_et);
		confirmPswEt = (EditText) findViewById(R.id.user_confirm_psw_et);
		titleRightBtn = (Button) findViewById(R.id.title_right_btn);
		titleRightBtn.setVisibility(View.INVISIBLE);
		registerBtn = (Button) findViewById(R.id.register_btn);
		registerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.register_btn:
			Log.d("onClick", R.id.register_btn+"");
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
			finish();
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
