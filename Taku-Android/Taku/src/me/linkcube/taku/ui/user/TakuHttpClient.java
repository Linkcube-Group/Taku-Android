package me.linkcube.taku.ui.user;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class TakuHttpClient {

	// private static AsyncHttpClient client = null;
	//
	// private TakuHttpClient() {
	// }
	//
	// public static AsyncHttpClient getInstance() {
	// synchronized (TakuHttpClient.class) {
	// if (client == null) {
	// client = new AsyncHttpClient();
	// }
	// return client;
	// }
	// }

	private static final String BASE_URL = "http://112.124.22.252:8000/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void initCookie(Context context) {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		client.setCookieStore(myCookieStore);
	}

	public static void getCookie() {
		HttpContext httpContext = client.getHttpContext();
		CookieStore cookies = (CookieStore) httpContext
				.getAttribute(ClientContext.COOKIE_STORE);
		if (cookies != null) {
			for (Cookie c : cookies.getCookies()) {
				Log.d("main after ~~" + c.getName(), c.getValue());
			}
		} else {
			Log.d("main  after~~", "cookies is null");
		}
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		TakuHttpClient.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
		
	}

	public static void post(String url, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), responseHandler);
	}
	
	public static void post(Context context, String url, Header[] headers, RequestParams params, String contentType,
            ResponseHandlerInterface responseHandler) {
		client.post(context, url, headers, params, contentType, responseHandler);
	}
	
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

}
