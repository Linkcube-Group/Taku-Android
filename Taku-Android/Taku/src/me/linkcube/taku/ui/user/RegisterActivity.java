package me.linkcube.taku.ui.user;

import java.io.UnsupportedEncodingException;

import me.linkcube.taku.R;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import base.common.ui.TitleBaseActivity;
import base.common.util.AlertUtils;
import base.common.util.StringUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

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
			// if (!StringUtils.isEmailAddress(emailEt.getText().toString())) {
			// AlertUtils.showToast(
			// this,
			// getResources().getString(
			// R.string.toast_username_hasbeen_email));
			// emailEt.setText("");
			// }
			// if (!passWordEt.getText().toString()
			// .equals(confirmPswEt.getText().toString())) {
			// AlertUtils.showToast(
			// this,
			// getResources().getString(
			// R.string.toast_psw_not_match));
			// passWordEt.setText("");
			// confirmPswEt.setText("");
			// }
			// if (passWordEt.getText().toString().length() < 6) {
			// Toast.makeText(RegisterActivity.this,
			// R.string.toast_psw_too_short, Toast.LENGTH_SHORT)
			// .show();
			// }
			// if
			// (StringUtils.containWhiteSpace(passWordEt.getText().toString()))
			// {
			// Toast.makeText(RegisterActivity.this,
			// R.string.toast_psw_couldnot_contain_space,
			// Toast.LENGTH_SHORT).show();
			// }
			// TODO 注册相关事情
			AsyncHttpClient client = new AsyncHttpClient();
//			RequestParams params = new RequestParams();
//			params.put("email", "yangxintest@163.com");
//			params.put("pwd", "1234567");
//			client.post("http://115.29.175.17/register", params,
//					new JsonHttpResponseHandler() {
//
//						@Override
//						public void onSuccess(int statusCode, Header[] headers,
//								JSONObject response) {
//							super.onSuccess(statusCode, headers, response);
//							Log.d("RegisterActivity", "statusCode:"+statusCode);
//						}
//				
//					});
//			client.post("http://115.29.175.17:8000/getinfo", new JsonHttpResponseHandler() {
//				@Override
//				public void onSuccess(int statusCode, Header[] headers,
//						JSONObject response) {
//					super.onSuccess(statusCode, headers, response);
//					Log.d("RegisterActivity", "statusCode:"+statusCode+"--response:"+response.toString());
//				}
//			});
			
			HttpContext httpContext = client.getHttpContext();
	        CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);//获取AsyncHttpClient中的CookieStore
	        if(cookies!=null){
	            for(Cookie c:cookies.getCookies()){
	            	Log.d("main before ~~"+c.getName(),c.getValue());
	            }
	        }else{
	        	Log.d("main  before~~","cookies is null");
	        }
	        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
	        client.setCookieStore(myCookieStore);
	        httpContext = client.getHttpContext();
	        cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
	        if(cookies!=null){
	            for(Cookie c:cookies.getCookies()){
	            	Log.d("main after ~~"+c.getName(),c.getValue());
	            }
	        }else{
	        	Log.d("main  after~~","cookies is null");
	        }
			
			// client.post(RegisterActivity.this,
			// "http://115.29.175.17/register", stringEntity,
			// "application/json", new JsonHttpResponseHandler(){
			//
			// @Override
			// public void onSuccess(int statusCode, Header[] headers,
			// JSONObject response) {
			// Log.d("RegisterActivity", "statusCode:"+statusCode);
			// }
			// });

			// startActivity(new
			// Intent(RegisterActivity.this,InitUserInfoActivity.class));
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
