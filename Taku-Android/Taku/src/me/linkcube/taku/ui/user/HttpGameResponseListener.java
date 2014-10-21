package me.linkcube.taku.ui.user;

public interface HttpGameResponseListener {

	void responseSuccess(Object object);
	
	void responseFailed(int flag);
}
