package me.linkcube.taku;

public class AppConst {

	public static class KEY {

		/** 引导页展示标志位 */
		public static final String SHOW_GUIDE = "ShowGuide";

		/** 是否自动登录 */
		public static final String AUTO_LOGIN = "AutoLogin";

		/** 登录用户名 */
		public static final String USER_NAME = "UserName";

		/** 登录密码 */
		public static final String USER_PWD = "UserPwd";

		/** 原始密码 */
		public static final String OLD_USER_PWD = "OldUserPwd";
	}

	public static class Gender {

		public static final String MALE = "男";

		public static final String FEMALE = "女";

	}

	/**
	 * 软件更新
	 * 
	 */
	public static class AppUpdate {

		public static String CHECK_VERSION_URL = "http://115.29.175.17/version";

		public static String APK_UPDATE_FLAG = "apk_update_flag";// "0"表示不需要更新也不需要展示，“1”表示需要提示更新，“2”表示已经提示过，需要在setting页面展示

		public static String APK_NAME = "linkcube.apk";

		public static String APK_VERSION = "apk_version";

		public static String APK_SIZE = "apk_size";

		public static String APK_DESCRIPTION = "apk_description";

		public static String APK_DOWNLOAD_URL = "apk_download_url";

	}

	public static class Device {

		public static String DEVICE_NAME = "device_name";

		public static String DEVICE_ADDRESS = "device_address";
	}

	/**
	 * 有盟统计
	 * 
	 */
	public static class UmengEvent {

		public static String SHACK_MODE_EVENT = "shackmodeevent";

		public static String VOICE_MODE_EVENT = "voicemodeevent";

		public static String MIC_MODE_EVENT = "micmodeevent";

		public static String SEXPOSITION_MODE_EVENT = "sexpositionmodeevent";

		public static String IS_CONNECT_TOY = "isconnecttoy";

		public static String CONNECT_TOY_DURATION = "ConnectToyDuration";

	}

	public static class HttpUrl {
		public static final String BASE_URL = "http://112.124.22.252:8000/";
	}

	/**
	 * 网络请求错误时返回的标识
	 * 
	 * @author xinyang
	 * 
	 */
	public static class ErrorFlag {

		public static final int NETWORK_ERROR = -1;

		public static final int EMAIL_PSW_WRONG = -2;

		public static final int EMAIL_REGISTER = -3;

		public static final int INIT_USER_INFO_ERROR = -4;

		public static final int LOGOUT_ERROR = -5;

		public static final int GET_AVATAR_ERROR = -6;

		public static final int UPLOAD_GAME_RECORD_WRONG = -7;

		public static final int GET_SINGLEDAY_HISTORY_WRONG = -8;

		public static final int GET_TOTAL_HISTORY_WRONG = -9;
	}

	public static class ResponseKey {

		public static final String STATUS = "status";

		public static final String MSG = "msg";

	}

	public static class ParamKey {

		public static final String EMAIL = "email";

		public static final String PWD = "pwd";

		public static final String NICKNAME = "nickname";

		public static final String GENDER = "gender";

		public static final String AGE = "age";

		public static final String HEIGHT = "height";

		public static final String WEIGHT = "weight";

		public static final String AVATAR = "avatar";

		public static final String RECORD_DATE = "recordDate";

		public static final String CALORIE = "calorie";

		public static final String DURATION = "duration";

		public static final String DISTANCE = "distance";
		
		public static final String TARGET = "target";

		public static final String HISTORY_DATE = "historyDate";

	}

	public static class GameFrame {
		public static final byte[] SPEED_FRAME = { 0x25, 0x2, 0x2, 0x0, 0x0,
				0x0, 0x0, 0x29 };
		public static final byte[] HEART_RATE_FRAME = { 0x25, 0x3, 0x0, 0x0, 0x0,
			0x0, 0x0, 0x28 };
		public static final byte[] PRESS_FRAME = { 0x26, 0x1, 0x0, 0x0, 0x0,
			0x0, 0x0, 0x26 };
	}
	
	public static class PATH{
		public static final String SDCARD_PATH = "/sdcard/taku/";
	}

}
