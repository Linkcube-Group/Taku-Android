package me.linkcube.taku.service;

import me.linkcube.taku.TakuApplication;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 玩具启动服务与activity通信
 * 
 * 
 */
public class ToyServiceConnection implements ServiceConnection {

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		TakuApplication.toyServiceCall = (IToyServiceCall) service;
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		TakuApplication.toyServiceCall = null;
	}
}
