package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.AppConst.GameFrame;

import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;

public class HeartRateManager {

	private static long preSecond, nextSecond;

	private static boolean isStart = false;

	private static int heartRate;

	public static void receiveHeartRateData(
			final HeartRateListener heartRateListener) {
		// BTManager.getInstance().startReceiveData(new IReceiveData() {
		//
		// @Override
		// public void receiveData(int bytes, byte[] buffer) {
		// byte[] buf_data = new byte[bytes];
		// for (int i = 0; i < bytes; i++) {
		// buf_data[i] = buffer[i];
		// }
		// }
		// });

		// 传过来的是心跳了一次
		if (!isStart) {
			isStart = true;
			preSecond = System.currentTimeMillis();
		} else {
			nextSecond = System.currentTimeMillis();
			heartRate = (int) (60000 / (nextSecond - preSecond));
			heartRateListener.onHeartRateListener(heartRate);
			preSecond = nextSecond;
		}
	}

	public interface HeartRateListener {
		void onHeartRateListener(int heartRate);
	}
}
