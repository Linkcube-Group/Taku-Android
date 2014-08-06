package me.linkcube.taku;

import me.linkcube.taku.bt.BTSettingActivity;
import me.linkcube.taku.core.bt.BTUtils;

import com.unity3d.player.UnityPlayerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TakuActivity extends UnityPlayerActivity implements ITakuActivity {

	Context mContext = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
	}

	public void startBTSettingActivity(String name) {
		Intent intent = new Intent(mContext, BTSettingActivity.class);
		this.startActivity(intent);
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeviceNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHexData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBluetoothEnabled() {
		return BTUtils.isBluetoothEnabled();
	}

	@Override
	public boolean isToyConnected() {
		return false;
	}

}
