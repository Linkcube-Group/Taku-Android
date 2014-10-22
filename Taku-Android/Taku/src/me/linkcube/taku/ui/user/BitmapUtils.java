package me.linkcube.taku.ui.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
		Log.e("saveBitmap", "picName:" + picName.substring(6, picName.length()));
		File f = new File("/sdcard/taku/static", picName.substring(6,
				picName.length()));
		File temp = new File("/sdcard/taku");// 自已项目 文件夹
		if (!temp.exists()) {
			temp.mkdir();
		}
		File temp2 = new File("/sdcard/taku/static");// 自已项目 文件夹
		if (!temp2.exists()) {
			temp2.mkdir();
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
	 * 
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap convertToBitmap(String path) {
		return BitmapFactory.decodeFile(path);
	}
	
	public static boolean isFileExist(String path){
		File avatarFile = new File("/sdcard/taku/"+path);
		if (!avatarFile.exists()) 
			return false;
		return true;
	}

	public static String getThumbUploadPath(String oldPath) throws Exception {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(oldPath, options);
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		int reqHeight = 800;
		int reqWidth = 480;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		// 在内存中创建bitmap对象，这个对象按照缩放大小创建的
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		System.out.println("calculateInSampleSize(options, 480, 800);==="
				+ calculateInSampleSize(options, 480, 800));
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(oldPath, options);
		Bitmap bbb = compressImage(bitmap);
		String timeStamp = System.currentTimeMillis() + ".jpg";
		BitmapUtils.saveBitmap("static/" + timeStamp, bbb);
		return "/sdcard/taku/static/" + timeStamp;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			options -= 10;// 每次都减少10
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/** 缩放Bitmap图片 **/

	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}
}
