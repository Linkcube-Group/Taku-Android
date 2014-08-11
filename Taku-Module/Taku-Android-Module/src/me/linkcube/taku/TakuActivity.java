package me.linkcube.taku;

import me.linkcube.taku.bt.BTSettingActivity;
import me.linkcube.taku.common.utils.FormatUtils;
import me.linkcube.taku.common.utils.Timber;

import com.ervinwang.bthelper.BTHelper;
import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;
import com.unity3d.player.UnityPlayerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TakuActivity extends UnityPlayerActivity implements ITakuActivity {

	Context mContext = null;

	private String data = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
	}

	public void startBTSettingActivity() {
		Intent intent = new Intent(mContext, BTSettingActivity.class);
		this.startActivity(intent);
	}

	@Override
	public String getData() {
		Timber.d("getData data= " + data);
		String temp = data;
		Timber.d("getData temp= " + temp);
		return temp;
	}

	@Override
	public boolean isBluetoothEnabled() {
		return BTHelper.isBluetoothEnabled();
	}

	@Override
	public boolean isToyConnected() {
		return BTManager.getInstance().getDeviceService().checkConnection();
	}

	@Override
	public void clearBufferData() {
		Timber.d("clearBufferData = " + data);
		data = "";
	}

	@Override
	public void startReceiveData() {
		BTManager.getInstance().startReceiveData(new IReceiveData() {

			@Override
			public void receiveData(int bytes, byte[] buffer) {
				byte[] buf_data = new byte[bytes];
				for (int i = 0; i < bytes; i++) {
					buf_data[i] = buffer[i];
				}
				data = FormatUtils.bytesToHexString(buf_data);
				Log.d("Receive Data Hex 1 = ", data);
				Log.d("Receive Data Hex 2 = ", getData());
			}
		});
	}

	@Override
	public void stopReceiveData() {
		BTManager.getInstance().stopReceiveData();
	}

	public void testGetDataFromUnity(String data) {
		Timber.d("testGetDataFromUnity=" + data);
	}

}
