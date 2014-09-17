package me.linkcube.taku.ui.welcome;

import static me.linkcube.taku.AppConst.KEY.SHOW_GUIDE;
import me.linkcube.taku.AppConfig;
import me.linkcube.taku.R;
import me.linkcube.taku.common.ui.DialogActivity;
import me.linkcube.taku.common.util.AlertUtils;
import me.linkcube.taku.common.util.NetworkUtils;
import me.linkcube.taku.common.util.PreferenceUtils;
import me.linkcube.taku.common.util.Timber;
import me.linkcube.taku.ui.main.MainActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


/**
 * 欢迎页面
 * 
 * @author Orange
 * 
 */
public class SplashActivity extends DialogActivity {

	private boolean isShowGuide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);
		Timber.d("数据库初始化");
		// TODO 数据库初始化
		// TODO 键值初始化

	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				init();
			}
		}, 0);
	}

	private void init() {
		boolean available = NetworkUtils.isNetworkAvailable(this);
		isShowGuide = isShowGuide();
		if (isShowGuide) {
			startActivity(new Intent(mActivity, GuideActivity.class));
			finish();
		} else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
		}
		// Timber.d("检查本地网络");
		// if (!available) {
		// showNetworkDialog();
		// return;
		// } else {
		// startActivity(new Intent(this, GuideActivity.class));
		// }

	}

	private void showNetworkDialog() {
		AlertUtils.showAlert(this, "请确认您可以访问互联网，若有疑问请与客服人员联系。", "提示", "设置",
				"取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 打开wifi设置页面
						if (android.os.Build.VERSION.SDK_INT > 10) {
							// 3.0以上打开设置界面
							startActivity(new Intent(
									android.provider.Settings.ACTION_SETTINGS));
						} else {
							startActivity(new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
						finish();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						startActivity(new Intent(mActivity, GuideActivity.class));
						finish();
					}

				});
	}

	/**
	 * 判断用户是否需要展示引导页
	 */
	public boolean isShowGuide() {
		String guideTime = PreferenceUtils.getString(SHOW_GUIDE, null);
		if (guideTime == null) {
			PreferenceUtils.setString(SHOW_GUIDE, AppConfig.GUIDE_TIME);
			return true;
		} else {
			if (guideTime.equals(AppConfig.GUIDE_TIME)) {
				return false;
			} else {
				return true;
			}
		}
	}

}
