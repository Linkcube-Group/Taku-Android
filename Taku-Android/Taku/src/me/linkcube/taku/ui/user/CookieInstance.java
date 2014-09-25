package me.linkcube.taku.ui.user;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.PersistentCookieStore;

public class CookieInstance {
	
	private static CookieInstance cookieInstance = null;

	private CookieInstance() {
	}

	public static CookieInstance getInstance() {
		synchronized (CookieInstance.class) {
			if (cookieInstance == null) {
				cookieInstance = new CookieInstance();
			}
			return cookieInstance;
		}
	}
	
	public void initCookie(Context context){
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		ClientInstance.getInstance().setCookieStore(myCookieStore);
	}
	
	public void getCookie(){
		HttpContext httpContext = ClientInstance.getInstance().getHttpContext();
		CookieStore cookies = (CookieStore) httpContext
				.getAttribute(ClientContext.COOKIE_STORE);
		if (cookies != null) {
			for (Cookie c : cookies.getCookies()) {
				Log.d("main after ~~" + c.getName(),
						c.getValue());
			}
		} else {
			Log.d("main  after~~", "cookies is null");
		}
	}
	
}
