package me.linkcube.taku.ui.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapUtils {
	/**
	 * 保存图片到sd卡
	 * 
	 * @param picName
	 * @param mBitmap
	 */
	public static void saveBitmap(String picName, Bitmap mBitmap) {
		Log.e("saveBitmap", "保存图片");
		File f = new File("/sdcard/taku/static", picName.substring(6, picName.length()));
		File temp = new File("/sdcard/taku/static");//自已项目 文件夹  
        if (!temp.exists()) {  
            temp.mkdir();  
        }
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			Log.i("saveBitmap", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从sd卡中获取图片
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap convertToBitmap(String path) {
		return BitmapFactory.decodeFile(path);
	}
}
