package me.linkcube.taku;

import me.linkcube.taku.BuildConfig;
import me.linkcube.taku.common.utils.Timber;
import me.linkcube.taku.core.service.ToyService;
import android.app.Application;

public class TakuApplication extends Application {

	public static ToyService toyService = new ToyService();

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
	}

}
