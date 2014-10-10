package me.linkcube.taku.ui.share;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import me.linkcube.taku.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 分享
 * */
public class ShareActivity extends Activity {
	// 返回
	private ImageButton back_imgBtn;
	// 运动距离
	private TextView distance_tv;
	private SpannableString distanceString;
	// 运行时间
	private TextView time_tv;
	private SpannableString timeString;
	// 卡路里
	private TextView cal_tv;
	private SpannableString calString;
	// 分享
	private Button share_btn;
	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	public ShareActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_activity);
		init();
	}

	private void init() {
		// 得到控件
		back_imgBtn = (ImageButton) findViewById(R.id.back_imgBtn);
		distance_tv = (TextView) findViewById(R.id.distance_tv);
		time_tv = (TextView) findViewById(R.id.time_tv);
		cal_tv = (TextView) findViewById(R.id.cal_tv);
		share_btn = (Button) findViewById(R.id.share_btn);
		// 设置效果
		setDistanceText("3000");
		setTimeText("30:12");
		setCalText("475");

		// 注册事件
		back_imgBtn.setOnClickListener(onShareActivityButtonClickListener);
		share_btn.setOnClickListener(onShareActivityButtonClickListener);

		/**
		 * ISSO（免登录）分享到QQ 添加ＱＱ平台到SDK --没有它的话，会出现错误:请添加QQ平台到SDK
		 * */
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
				"c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.addToSocialSDK();

		/**
		 * ISSO（免登录）分享到QQ空间 添加ＱＱ空间平台到SDK --没有它的话，会出现错误:请添加Qzone平台到SDK
		 **/
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qZoneSsoHandler.addToSocialSDK();

		/**
		 * ISSO（免登录）分享到微信平台 添加微信平台到SDK --没有它的话，会出现错误:请添加XXXX平台到SDK
		 **/
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		// 下一步去申请AppID＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		String appID = "wx967daebe835fbeac";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID);
		wxHandler.addToSocialSDK();
		/**
		 * ISSO（免登录）分享到微信朋友圈 添加微信朋友圈到SDK --没有它的话，会出现错误:请添加XXXX平台到SDK
		 **/
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// 设置腾讯微博SSO handler
		/*
		 * 
		 * 1.手机中必须安装微博客户端V3.8.1及以上的版本才支持SSO功能.
		 * 2.腾讯微博的SSO没有回调。由于腾讯微博SSO没有提供回调，因此腾讯微博SSO不会在onActivityResult方法内被调用
		 * （腾讯微博授权流程不经过onActivityResult方法).
		 */
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

		// 添加人人网SSO授权功能
		// APPID:201874
		// API Key:28401c0964f04a72a14c812d6132fcef
		// Secret:3bf66e42db1e4fa9829b955cc300b737
		/*
		 * 1.手机中必须安装人人客户端V5.9.3及以上的版本才支持SSO功能。
		 * 2.由于人人SSO没有提供回调，因此人人SSO不会在onActivityResult方法内被调用
		 * （人人SSO授权流程不经过onActivityResult方法)。
		 */
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(this,
				"201874", "28401c0964f04a72a14c812d6132fcef",
				"3bf66e42db1e4fa9829b955cc300b737");
		mController.getConfig().setSsoHandler(renrenSsoHandler);

	}

	OnClickListener onShareActivityButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_imgBtn:
				exitShareActivity();
				break;
			case R.id.share_btn:
				// TODO
				// 弹出分享面板
				mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
						SHARE_MEDIA.DOUBAN);
				sharePicClick();
				break;

			default:
				break;
			}

		}
	};

	private void exitShareActivity() {
		this.finish();
	}

	private void sharePicClick() {
		Log.i("CXC", "---sharePicClick");

		// 设置分享内容
		mController
				.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能--cxc，http://www.umeng.com/social");
		// 设置分享图片，参数2为本地图片的资源引用
		mController.setShareMedia(new UMImage(this, R.drawable.ic_launcher));
		// 是否只有已登录用户才能打开分享选择页
		mController.openShare(this, false);

	}

	/**
	 * 设置TextView 文字效果
	 * */
	private void setDistanceText(String distanceStr) {
		// 创建一个 SpannableString对象
		distanceString = new SpannableString("运动距离：" + distanceStr + " 米");
		int length = distanceStr.length();
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		distanceString.setSpan(new RelativeSizeSpan(2.0f), 5, 5 + length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		distanceString.setSpan(new ForegroundColorSpan(Color.RED), 5,
				5 + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		distance_tv.setText(distanceString);

	}

	private void setTimeText(String timeStr) {
		// 创建一个 SpannableString对象
		timeString = new SpannableString("运动时间：" + timeStr + " 分/秒");
		int length = timeStr.length();
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		timeString.setSpan(new RelativeSizeSpan(2.0f), 5, 5 + length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		timeString.setSpan(new ForegroundColorSpan(Color.RED), 5, 5 + length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		time_tv.setText(timeString);

	}

	private void setCalText(String calStr) {
		// 创建一个 SpannableString对象
		calString = new SpannableString("燃烧：" + calStr + " 大卡");
		int length = calStr.length();
		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		calString.setSpan(new RelativeSizeSpan(2.0f), 3, 3 + length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		calString.setSpan(new ForegroundColorSpan(Color.RED), 3, 3 + length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为红色

		cal_tv.setText(calString);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
}
