package me.linkcube.taku;

import me.linkcube.taku.BuildConfig;
import me.linkcube.taku.common.util.PreferenceUtils;
import me.linkcube.taku.common.util.Timber;
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
