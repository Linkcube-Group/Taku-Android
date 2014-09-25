package me.linkcube.taku.ui.user;


import com.loopj.android.http.AsyncHttpClient;

public class ClientInstance {

	private static AsyncHttpClient client = null;

	private ClientInstance() {
	}

	public static AsyncHttpClient getInstance() {
		synchronized (ClientInstance.class) {
			if (client == null) {
				client = new AsyncHttpClient();
			}
			return client;
		}
	}

}
