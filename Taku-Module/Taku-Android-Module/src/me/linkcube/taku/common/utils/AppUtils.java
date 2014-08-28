package me.linkcube.taku.common.utils;

import static me.linkcube.taku.AppConst.Preference.*;
import me.linkcube.taku.AppConfig;

/**
 * 
 * @author Orange
 * 
 */
public class AppUtils {
	/**
	 * 判断用户是否需要展示引导页
	 */
	public static boolean isShowGuide() {
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
