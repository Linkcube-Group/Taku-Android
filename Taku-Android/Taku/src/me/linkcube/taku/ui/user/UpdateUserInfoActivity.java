package me.linkcube.taku.ui.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.HttpUrl;
import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.AppConst.ParamKey;
import me.linkcube.taku.R;
import me.linkcube.taku.core.entity.UserInfoEntity;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.CircularImage;
import me.linkcube.taku.view.MenuItem;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orm.SugarRecord;

import custom.android.util.AlertUtils;
import custom.android.util.PreferenceUtils;

public class UpdateUserInfoActivity extends BaseTitleActivity implements
		OnClickListener {

	private int CHANGE_AVATAR = 1;
	private int CHANGE_NICKNAME = 2;
	private int CHANGE_HEIGHT = 3;
	private int CHANGE_WEIGHT = 4;
	private CircularImage userAvatarIv;
	private RequestParams params;
	private File userAvatar;
	private MenuItem nicknameItem, genderItem, ageItem, heightItem, weightItem;
	private String[] isMale;

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
		nicknameItem.setOnClickListener(this);
		genderItem = (MenuItem) findViewById(R.id.genderItem);
		genderItem.setOnClickListener(this);
		ageItem = (MenuItem) findViewById(R.id.ageItem);
		heightItem = (MenuItem) findViewById(R.id.heightItem);
		heightItem.setOnClickListener(this);
		weightItem = (MenuItem) findViewById(R.id.weightItem);
		weightItem.setOnClickListener(this);
	}

	private void initTitle() {
		setTitleText(getResources().getString(R.string.update_user_info_text));
		getRightTitleBtn().setText(
				getResources().getString(R.string.save_btn_text));
		setRightTitleBtn(R.drawable.user_btn_bg);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 上传头像
				try {
					params.put(ParamKey.AVATAR, userAvatar, "image/jpeg");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				UserRequest.changeAvatar(UpdateUserInfoActivity.this, params,
						new HttpResponseListener() {

							@Override
							public void responseSuccess() {
								params.put(ParamKey.NICKNAME, "踩踩踩踩");// nicknameItem.getTip()
								params.put(ParamKey.GENDER, genderItem.getTip());
								params.put(ParamKey.AGE, ageItem.getTip());
								params.put(ParamKey.WEIGHT, weightItem.getTip());
								params.put(ParamKey.HEIGHT, heightItem.getTip());
								UserRequest.editUserInfo(params,
										new HttpResponseListener() {

											@Override
											public void responseSuccess() {
												AlertUtils
														.showToast(
																UpdateUserInfoActivity.this,
																"更新用户信息成功！");
												SugarRecord
														.deleteAll(
																UserInfoEntity.class,
																"username=?",
																new String[] { PreferenceUtils
																		.getString(
																				KEY.USER_NAME,
																				"") });
												SugarRecord
														.deleteAll(UserInfoEntity.class);
												UserRequest.getUserInfo();
												finish();
											}

											@Override
											public void responseFailed(int flag) {
												switch (flag) {
												case ErrorFlag.INIT_USER_INFO_ERROR:
													AlertUtils
															.showToast(
																	UpdateUserInfoActivity.this,
																	"更新用户信息失败！");
													break;
												case ErrorFlag.NETWORK_ERROR:
													AlertUtils
															.showToast(
																	UpdateUserInfoActivity.this,
																	"网络错误，请检查！");
													break;
												default:
													break;
												}
											}
										});
							}

							@Override
							public void responseFailed(int flag) {

							}
						});
			}
		});
	}

	private void initData() {
		isMale = new String[] {
				getResources().getString(R.string.user_gender_female_text),
				getResources().getString(R.string.user_gender_male_text) };
		params = new RequestParams();
		UserInfoEntity userInfoEntity = UserManager.getInstance().getUserInfo();
		if (userInfoEntity != null) {
			ImageLoader.getInstance()
					.displayImage(
							HttpUrl.BASE_URL + userInfoEntity.getAvatar(),
							userAvatarIv);
			nicknameItem.setTip(userInfoEntity.getNickname());
			genderItem.setTip(userInfoEntity.getGender());
			ageItem.setTip(userInfoEntity.getAge());
			heightItem.setTip(userInfoEntity.getHeight() + "cm");
			weightItem.setTip(userInfoEntity.getWeight() + "kg");
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
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
			startActivityForResult(intent, CHANGE_AVATAR);
			break;
		case R.id.genderItem:
			Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(
					getResources().getString(R.string.user_gender_text))
					.setSingleChoiceItems(isMale, 0,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									genderItem.setTip(isMale[which]);
									dialog.dismiss();
								}

							}).show();
			break;
		case R.id.nicknameItem:
			Intent nickNameIntent = new Intent(UpdateUserInfoActivity.this,
					EditUserInfoActivity.class);
			nickNameIntent.putExtra("information", nicknameItem.getTip());
			nickNameIntent.putExtra("requestCode", CHANGE_NICKNAME);
			startActivityForResult(nickNameIntent, CHANGE_NICKNAME);
			break;
		case R.id.heightItem:
			Intent heightIntent = new Intent(UpdateUserInfoActivity.this,
					EditUserInfoActivity.class);
			heightIntent.putExtra("information", nicknameItem.getTip());
			heightIntent.putExtra("requestCode", CHANGE_HEIGHT);
			startActivityForResult(heightIntent, CHANGE_HEIGHT);
			break;
		case R.id.weightItem:
			Intent weightIntent = new Intent(UpdateUserInfoActivity.this,
					EditUserInfoActivity.class);
			weightIntent.putExtra("information", nicknameItem.getTip());
			weightIntent.putExtra("requestCode", CHANGE_WEIGHT);
			startActivityForResult(weightIntent, CHANGE_WEIGHT);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CHANGE_AVATAR) {
			if (null != data) {
				Uri uri = data.getData();
				ContentResolver cr = this.getContentResolver();
				String imagePath = PhotoFileUtils.getPath(this, uri);
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
		} else if (requestCode == CHANGE_NICKNAME) {
			String nickName = data.getStringExtra("returnUserInfo");
			nicknameItem.setTip(nickName);
		} else if (requestCode == CHANGE_HEIGHT) {
			String height = data.getStringExtra("returnUserInfo");
			nicknameItem.setTip(height + "cm");
		} else if (requestCode == CHANGE_WEIGHT) {
			String weight = data.getStringExtra("returnUserInfo");
			nicknameItem.setTip(weight + "kg");
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
