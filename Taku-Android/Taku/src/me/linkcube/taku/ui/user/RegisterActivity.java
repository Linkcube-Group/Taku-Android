package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.AppConst.ParamKey;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.ui.request.UserRequest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import custom.android.util.AlertUtils;
import custom.android.util.PreferenceUtils;
import custom.android.util.StringUtils;

public class RegisterActivity extends BaseTitleActivity implements
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
			} else if (!passWordEt.getText().toString()
					.equals(confirmPswEt.getText().toString())) {
				AlertUtils.showToast(this,
						getResources().getString(R.string.toast_psw_not_match));
				passWordEt.setText("");
				confirmPswEt.setText("");
			} else if (passWordEt.getText().toString().length() < 6) {
				Toast.makeText(RegisterActivity.this,
						R.string.toast_psw_too_short, Toast.LENGTH_SHORT)
						.show();
			} else if (StringUtils.containWhiteSpace(passWordEt.getText()
					.toString())) {
				Toast.makeText(RegisterActivity.this,
						R.string.toast_psw_couldnot_contain_space,
						Toast.LENGTH_SHORT).show();
			} else {
				final RequestParams params = new RequestParams();
				params.put(ParamKey.EMAIL, emailEt.getText().toString());
				params.put(ParamKey.PWD, passWordEt.getText().toString());
				PreferenceUtils.setString(KEY.USER_NAME, emailEt.getText().toString());
				PreferenceUtils.setString(KEY.USER_PWD, passWordEt.getText().toString());
				UserRequest.userRegister(params,
						new HttpResponseListener() {

							@Override
							public void responseSuccess() {
								// 注册成功之后直接登录
								UserRequest.userLogin(params,
										new HttpResponseListener() {

											@Override
											public void responseSuccess() {
												AlertUtils
												.showToast(
														RegisterActivity.this,
														"注册成功！");
												startActivity(new Intent(
														RegisterActivity.this,
														InitUserInfoActivity.class));
												finish();
											}

											@Override
											public void responseFailed(int flag) {
												switch (flag) {
												case ErrorFlag.NETWORK_ERROR:
													AlertUtils
															.showToast(
																	RegisterActivity.this,
																	"网络错误，请检查！");
													break;
												case ErrorFlag.EMAIL_PSW_WRONG:
													AlertUtils
															.showToast(
																	RegisterActivity.this,
																	"账号或密码错误！");
													break;

												default:
													break;
												}
											}
										});
							}

							@Override
							public void responseFailed(int flag) {
								switch (flag) {
								case ErrorFlag.EMAIL_REGISTER:
									AlertUtils.showToast(RegisterActivity.this,
											"此邮箱已经注册！");
									break;
								case ErrorFlag.NETWORK_ERROR:
									AlertUtils.showToast(RegisterActivity.this,
											"网络错误，请检查！");
									break;
								default:
									break;
								}
							}
						});
			}
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
