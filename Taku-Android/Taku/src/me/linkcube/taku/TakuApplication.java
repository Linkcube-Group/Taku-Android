package me.linkcube.taku;


import com.orm.SugarContext;

import custom.android.util.PreferenceUtils;
import custom.android.util.Timber;
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
		SugarContext.init(this);
	}
	
	@Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
