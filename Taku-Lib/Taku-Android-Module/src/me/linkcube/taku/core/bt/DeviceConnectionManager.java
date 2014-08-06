package me.linkcube.taku.core.bt;

import java.util.Timer;
import java.util.TimerTask;

import me.linkcube.taku.TakuApplication;
import me.linkcube.taku.common.utils.Timber;
import android.bluetooth.BluetoothDevice;

public class DeviceConnectionManager {

	private Timer timer;

	private boolean mIsConnected = false;

	private static DeviceConnectionManager instance;

	public CheckConnectionCallback callback;

	private BluetoothDevice device;

	public boolean isSexPositionMode;

	public static DeviceConnectionManager getInstance() {
		if (instance == null) {
			synchronized (DeviceConnectionManager.class) {
				if (instance == null) {
					instance = new DeviceConnectionManager();
				}
			}
		}

		return instance;
	}

	private DeviceConnectionManager() {

	}

	public void startCheckConnetionTask() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				if (!isSexPositionMode) {
					if (mIsConnected != TakuApplication.toyService.checkData()) {
						mIsConnected = TakuApplication.toyService.checkData();
						Timber.d("mIsConnected:" + mIsConnected);
						if (mIsConnected) {
							callback.stable();
						} else {
							stopTimerTask();
						}
					}
				}

			}
		}, 3000, 3000);
	}

	public void cancelCheckConnectionTask() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void stopTimerTask() {
		callback.interrupted();
		cancelCheckConnectionTask();
	}

	public void setmIsConnected(boolean mIsConnected, BluetoothDevice device) {
		this.mIsConnected = mIsConnected;
		this.device = device;
	}

	public boolean isConnected() {
		return mIsConnected;
	}

	public boolean isSexPositionMode() {
		return isSexPositionMode;
	}

	public void setSexPositionMode(boolean isSexPositionMode) {
		this.isSexPositionMode = isSexPositionMode;
	}

	public BluetoothDevice getDeviceConnected() {
		return device;
	}

	public void setCheckConnectionCallBack(CheckConnectionCallback callback) {
		this.callback = callback;
	}

	public interface CheckConnectionCallback {

		/**
		 * 连接状态稳定
		 */
		void stable();

		/**
		 * 主动断开连接
		 */
		void disconnect();

		/**
		 * 被动断开连接
		 */
		void interrupted();
	}

}
