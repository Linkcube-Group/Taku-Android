package me.linkcube.taku.ui.share;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.animation.FloatEvaluator;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class DrawPicService extends Service {
	public static final String DRAWPICSERVICE_PARAMETERS = "me.linkcube.taku.ui.share.parameters";

	// 绘制参数
	private SharePicParameters spParameters;

	private Bitmap mBitmap;
	// 画布
	private Canvas mCanvas;

	// 画笔－－写字
	private Paint mTextPaint;
	// 画笔－－写运动数据
	private Paint mDataPaint;

	private String textString;
	private String dataString;
	private String scalString;

	private float textString_width;
	private float dataString_width;
	private float scalString_width;
	private float x;

	public DrawPicService() {
		// TODO Auto-generated constructor stub
		System.out.println("MyService.MyService()");

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 当第一次创建Service 时，调用此方法， 此后，如果Service没有停止的情况下，再次启动Service ， 都不会再调用此方法
	 * */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// 初始化画笔
		mTextPaint = new Paint();
		mDataPaint = new Paint();
		// 设置画笔
		mTextPaint.setColor(Color.WHITE);// 白色画笔
		mTextPaint.setTextSize(80.0f);// 设置字体大小

		// 初始化画笔
		mDataPaint = new Paint();
		// 绘制文字
		mDataPaint.setColor(Color.RED);// 红色画笔
		mDataPaint.setTextSize(120.0f);// 设置字体大小

		Thread backgroundThread = new Thread(doBackgroundThreadProcessing,
				"backgroundThread");
		backgroundThread.start();

	}

	private Runnable doBackgroundThreadProcessing = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			drawBitmap(spParameters.getBg_resId(),
					spParameters.getHead_resId(), spParameters.getDistance(),
					spParameters.getTimeString(), spParameters.getCal());
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		// 接收参数
		spParameters = (SharePicParameters) intent
				.getSerializableExtra(DRAWPICSERVICE_PARAMETERS);

		return super.onStartCommand(intent, flags, startId);

	}

	/**
	 * 停止Service 时，调用此方法
	 * */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("MyService.onDestroy()");
		super.onDestroy();
	}

	/**
	 * @param bg_resId
	 *            : 背景图片的资源 ID,如，R.drawable.
	 * 
	 * @param head_resId
	 *            : 头部图片的资源ID
	 * @param distance
	 *            : 距离，如123.0f
	 * @param timeString
	 *            :时间字符串，如：23:12
	 * @param cal
	 *            :消耗的卡路里，如：456
	 * */
	public void drawBitmap(int bg_resId, int head_resId, float distance,
			String timeString, int cal) {
		Log.i("CXC", "----drawBitmap");

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bg_resId);
		// 得到图片的宽、高
		int bg_width = bitmap.getWidth();
		int height_bg = bitmap.getHeight();

		// 创建一个你需要尺寸的Bitmap
		mBitmap = Bitmap.createBitmap(bg_width, height_bg, Config.ARGB_8888);
		// 用这个Bitmap生成一个Canvas,然后canvas就会把内容绘制到上面这个bitmap中
		mCanvas = new Canvas(mBitmap);

		// 绘制背景图片
		mCanvas.drawBitmap(bitmap, 0.0f, 0.0f, mTextPaint);

		// 绘制图片
		bitmap = BitmapFactory.decodeResource(getResources(), head_resId);

		// 得到图片的宽、高
		int head_width = bitmap.getWidth();
		int head_height = bitmap.getHeight();
		// 绘制图片－－保证其在水平方向居中
		mCanvas.drawBitmap(bitmap, (bg_width - head_width) / 2, 0.0f,
				mTextPaint);

		// 绘制运动距离文字
		textString = "运动距离：";
		dataString = String.valueOf(distance);
		scalString = "米";

		textString_width = mTextPaint.measureText(textString, 0,
				textString.length());

		dataString_width = mDataPaint.measureText(dataString, 0,
				dataString.length());
		scalString_width = mTextPaint.measureText(scalString, 0,
				scalString.length());
		x = (bg_width - textString_width - dataString_width - scalString_width) / 2;

		mCanvas.drawText(textString, x, head_height + 30, mTextPaint);// 绘制文字
		mCanvas.drawText(dataString, x + textString_width, head_height + 30,
				mDataPaint);// 绘制文字

		mCanvas.drawText(scalString, x + textString_width + dataString_width,
				head_height + 30, mTextPaint);// 绘制文字

		// 绘制运动时间文字
		textString = "运动时间：";
		dataString = String.valueOf(timeString);
		scalString = "分/秒";

		textString_width = mTextPaint.measureText(textString, 0,
				textString.length());

		dataString_width = mDataPaint.measureText(dataString, 0,
				dataString.length());
		scalString_width = mTextPaint.measureText(scalString, 0,
				scalString.length());
		x = (bg_width - textString_width - dataString_width - scalString_width) / 2;

		mCanvas.drawText(textString, x, head_height + 180, mTextPaint);// 绘制文字
		mCanvas.drawText(dataString, x + textString_width, head_height + 180,
				mDataPaint);// 绘制文字

		mCanvas.drawText(scalString, x + textString_width + dataString_width,
				head_height + 180, mTextPaint);// 绘制文字

		// 绘制消耗卡路里文字
		textString = "消耗：";
		dataString = String.valueOf(cal);
		scalString = "大卡";

		textString_width = mTextPaint.measureText(textString, 0,
				textString.length());

		dataString_width = mDataPaint.measureText(dataString, 0,
				dataString.length());
		scalString_width = mTextPaint.measureText(scalString, 0,
				scalString.length());
		x = (bg_width - textString_width - dataString_width - scalString_width) / 2;

		mCanvas.drawText(textString, x, head_height + 330, mTextPaint);// 绘制文字
		mCanvas.drawText(dataString, x + textString_width, head_height + 330,
				mDataPaint);// 绘制文字

		mCanvas.drawText(scalString, x + textString_width + dataString_width,
				head_height + 330, mTextPaint);// 绘制文字

		// 保存绘图
		mCanvas.save(Canvas.ALL_SAVE_FLAG);
		mCanvas.restore();

		savePicPNG();
	}

	/**
	 * 保存为本地PNG图片
	 * */
	private void savePicPNG() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/share_pic.png");// 保存到sdcard根目录下，文件名为share_pic.png
		Log.i("CXC", Environment.getExternalStorageDirectory().getPath());
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
