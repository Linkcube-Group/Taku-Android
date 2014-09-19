package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import base.common.ui.BaseActivity;
import base.common.util.AlertUtils;
import base.common.util.StringUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private ImageButton backBtn;
	private TextView titleTv;
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
		backBtn=(ImageButton)findViewById(R.id.back_btn);
		backBtn.setOnClickListener(this);
		titleTv=(TextView)findViewById(R.id.title_tv);
		titleTv.setText(getResources().getString(R.string.register_text));
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
			break;
		case R.id.back_btn:
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
