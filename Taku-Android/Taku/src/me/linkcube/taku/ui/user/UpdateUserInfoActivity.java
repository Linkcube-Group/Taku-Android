package me.linkcube.taku.ui.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.loopj.android.http.RequestParams;

import custom.android.util.AlertUtils;

import me.linkcube.taku.R;
import me.linkcube.taku.AppConst.ParamKey;
import me.linkcube.taku.core.entity.UserInfoEntity;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.CircularImage;
import me.linkcube.taku.view.MenuItem;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class UpdateUserInfoActivity extends BaseTitleActivity implements
		OnClickListener {

	private static final int UPDATE_AVATAR_REQUEST_CODE = 1;
	private CircularImage userAvatarIv;
	private RequestParams params;
	private File userAvatar;
	private MenuItem nicknameItem, genderItem, ageItem, heightItem, weightItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_user_info_activity);
		initView();

		initData();
	}

	private void initView() {
		initTitle();
		LinearLayout userAvatarLayout = (LinearLayout) findViewById(R.id.userAvatarLayout);
		userAvatarLayout.setOnClickListener(this);
		userAvatarIv = (CircularImage) findViewById(R.id.userAvatarIv);
		nicknameItem = (MenuItem) findViewById(R.id.nicknameItem);
		genderItem = (MenuItem) findViewById(R.id.genderItem);
		ageItem = (MenuItem) findViewById(R.id.ageItem);
		heightItem = (MenuItem) findViewById(R.id.heightItem);
		weightItem = (MenuItem) findViewById(R.id.weightItem);
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.update_user_info_text));
		getRightTitleBtn().setText(
				getResources().getString(R.string.save_btn_text));
		setRightTitleBtn(R.drawable.user_btn_bg);
		//params.set
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				try {
////					params.put(ParamKey.AVATAR, userAvatar,"multipart/form-data");
////					params.put(ParamKey.AVATAR, userAvatar, name, "multipart/form-data", true);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
				UserRequest.changeAvatar(UpdateUserInfoActivity.this, params,
						new HttpResponseListener() {

							@Override
							public void responseSuccess() {
								AlertUtils.showToast(
										UpdateUserInfoActivity.this, "修改头像完成！");
							}

							@Override
							public void responseFailed(int flag) {

							}
						});
				// params.put(ParamKey.NICKNAME, "人特人");
				// params.put(ParamKey.GENDER, "男");
				// params.put(ParamKey.AGE, "21");
				// params.put(ParamKey.WEIGHT, "21");
				// params.put(ParamKey.HEIGHT, "21");
				// UserRequest.editUserInfo(params, new HttpResponseListener() {
				//
				// @Override
				// public void responseSuccess() {
				// AlertUtils.showToast(UpdateUserInfoActivity.this,
				// "更新用户信息成功！");
				// }
				//
				// @Override
				// public void responseFailed(int flag) {
				// switch (flag) {
				// case ErrorFlag.INIT_USER_INFO_ERROR:
				// AlertUtils.showToast(UpdateUserInfoActivity.this,
				// "初始化用户信息失败！");
				// break;
				// case ErrorFlag.NETWORK_ERROR:
				// AlertUtils.showToast(UpdateUserInfoActivity.this,
				// "网络错误，请检查！");
				// break;
				// default:
				// break;
				// }
				// }
				// });
			}
		});
	}

	private void initData() {
		params = new RequestParams();
		UserInfoEntity userInfoEntity = UserManager.getInstance().getUserInfo();
		if (userInfoEntity != null) {
			nicknameItem.setTip(userInfoEntity.getNickname());
			genderItem.setTip(userInfoEntity.getGender());
			ageItem.setTip(userInfoEntity.getAge());
			heightItem.setTip(userInfoEntity.getHeight());
			weightItem.setTip(userInfoEntity.getWeight());
		}
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.userAvatarLayout:
			Intent intent = new Intent();
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			// 根据版本号不同使用不同的Action
			if (Build.VERSION.SDK_INT < 19) {
				intent.setAction(Intent.ACTION_GET_CONTENT);
			} else {
				intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			}
			startActivityForResult(intent, UPDATE_AVATAR_REQUEST_CODE);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == UPDATE_AVATAR_REQUEST_CODE) {
			if (null != data) {
				Uri uri = data.getData();
				ContentResolver cr = this.getContentResolver();
				String imagePath=PhotoFileUtils.getPath(this, uri);
				userAvatar = new File(imagePath);
				Log.d("UpdateUserInfoActivity", "imagePath:" + imagePath);
				try {
					Bitmap photo = MediaStore.Images.Media.getBitmap(cr, uri);
					if (photo != null) {
						Bitmap smallBitmap = zoomBitmap(photo,
								photo.getWidth() / 2, photo.getHeight() / 2);
						photo.recycle();
						userAvatarIv.setImageBitmap(smallBitmap);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** 缩放Bitmap图片 **/

	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {

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
