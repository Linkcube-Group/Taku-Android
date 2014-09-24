package me.linkcube.taku.ui.user;

import java.util.List;

import me.linkcube.taku.R;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import base.common.ui.TitleBaseActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends TitleBaseActivity implements OnClickListener{

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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login_btn:
			final AsyncHttpClient client = new AsyncHttpClient();
			PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
	        client.setCookieStore(myCookieStore);
			RequestParams params = new RequestParams();
			params.put("email", "313832830@qq.com");
			params.put("pwd", "shisong");
			client.post("http://115.29.175.17:8000/login", params,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							super.onSuccess(statusCode, headers, response);
							Log.d("RegisterActivity", "statusCode:"+statusCode);
							HttpContext httpContext = client.getHttpContext();
							
					        CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
					        if(cookies!=null){
					            for(Cookie c:cookies.getCookies()){
					            	Log.d("main after ~~"+c.getName(),c.getValue());
					            }
					        }else{
					        	Log.d("main  after~~","cookies is null");
					        }
						}
					});
			
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
