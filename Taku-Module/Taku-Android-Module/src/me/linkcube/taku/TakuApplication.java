package me.linkcube.taku;

import base.common.util.PreferenceUtils;
import base.common.util.Timber;
import me.linkcube.taku.BuildConfig;
import android.app.Application;

public class TakuApplication extends Application {

	public TakuApplication() {
		super();
	}

	@Override
	public void onCreate() {
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		} else {
			Timber.plant(new Timber.HollowTree());
		}
		PreferenceUtils.initDataShare(getApplicationContext());
	}

}
