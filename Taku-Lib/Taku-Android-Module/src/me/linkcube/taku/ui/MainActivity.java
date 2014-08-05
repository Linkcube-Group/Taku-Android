package me.linkcube.taku.ui;

import me.linkcube.taku.ui.bt.BTSettingActivity;

import com.unity3d.player.UnityPlayerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends UnityPlayerActivity {

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

}
