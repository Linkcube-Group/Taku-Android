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

import com.loopj.android.http.RequestParams;

import custom.android.util.AlertUtils;
import custom.android.util.PreferenceUtils;

public class LoginActivity extends BaseTitleActivity implements OnClickListener {

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
		loginBtn.setOnClickListener(this);
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.login_text));
		getRightTitleBtn().setText(
				getResources().getString(R.string.register_btn_text));
		setRightTitleBtn(R.drawable.user_btn_bg);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
				finish();
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login_btn:
			TakuHttpClient.initCookie(this);
			RequestParams params = new RequestParams();
//			params.put(ParamKey.EMAIL, "yxtest1@qq.com");// yangxintest@qq.com/313832830@qq.com
//			params.put(ParamKey.PWD, "1234567");// shisong/1234567
			params.put(ParamKey.EMAIL, emailEt.getText().toString());
			params.put(ParamKey.PWD, passWordEt.getText().toString());
			showProgressDialog(getString(R.string.is_logining));
			PreferenceUtils.setString(KEY.USER_NAME, emailEt.getText().toString());
			PreferenceUtils.setString(KEY.USER_PWD, passWordEt.getText().toString());
			UserRequest.userLogin(params,
					new HttpResponseListener() {

						@Override
						public void responseSuccess() {
							//保存当前用户
							hiddenProgressDialog();
							finish();
						}

						@Override
						public void responseFailed(int flag) {
							switch (flag) {
							case ErrorFlag.NETWORK_ERROR:
								AlertUtils.showToast(LoginActivity.this,
										"网络错误，请检查！");
								hiddenProgressDialog();
								break;
							case ErrorFlag.EMAIL_PSW_WRONG:
								AlertUtils.showToast(LoginActivity.this,
										"账号或密码错误！");
								hiddenProgressDialog();
								break;

							default:
								break;
							}
						}
					});
			break;

		default:
			break;
		}
	}

}
