package me.linkcube.taku.ui.user;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.CircularImage;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class UpdateUserInfoActivity extends BaseTitleActivity implements
		OnClickListener {

	private static final int REQUEST_CODE = 1;
	private CircularImage userAvatorIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_user_info_activity);
		initView();
	}

	private void initView() {
		initTitle();
		LinearLayout userAvatorLayout = (LinearLayout) findViewById(R.id.userAvatorLayout);
		userAvatorLayout.setOnClickListener(this);
		userAvatorIv = (CircularImage) findViewById(R.id.userAvatorIv);
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.update_user_info_text));
		getRightTitleBtn().setText(
				getResources().getString(R.string.save_btn_text));
		setRightTitleBtn(R.drawable.user_btn_bg);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.userAvatorLayout:
			Intent intent = new Intent();
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			// 根据版本号不同使用不同的Action
			if (Build.VERSION.SDK_INT < 19) {
				intent.setAction(Intent.ACTION_GET_CONTENT);
			} else {
				intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			}
			startActivityForResult(intent, REQUEST_CODE);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			if (null != data) {
				Uri uri = data.getData();
				InputStream inputStream;
				String[] proj = { MediaStore.Images.Media.DATA };
				ContentResolver cr = this.getContentResolver();
				Cursor cursor = cr.query(uri, proj, null, null, null);
				String imagePath = null;
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
						imagePath = cursor.getString(column_index);
					}
				}
				Log.d("UpdateUserInfoActivity", "uri:" + imagePath);
				try {
					inputStream = getContentResolver().openInputStream(uri);
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					userAvatorIv.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
