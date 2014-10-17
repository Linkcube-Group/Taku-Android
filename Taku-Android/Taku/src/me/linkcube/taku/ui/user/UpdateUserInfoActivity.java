package me.linkcube.taku.ui.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.AppConst.ParamKey;
import me.linkcube.taku.R;
import me.linkcube.taku.core.entity.UserAvatarEntity;
import me.linkcube.taku.core.entity.UserInfoEntity;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.CircleImageView;
import me.linkcube.taku.view.MenuItem;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.orm.SugarRecord;

import custom.android.util.AlertUtils;
import custom.android.util.PreferenceUtils;

public class UpdateUserInfoActivity extends BaseTitleActivity implements
		OnClickListener {

	private int CHANGE_AVATAR_GALLERY = 1020;
	private int CHANGE_AVATAR_CAMERA = 1021;
	private int CHANGE_NICKNAME = 1022;
	private int CHANGE_HEIGHT = 1023;
	private int CHANGE_WEIGHT = 1024;
	private CircleImageView userAvatarIv;
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
		userAvatarIv = (CircleImageView) findViewById(R.id.userAvatarIv);
		nicknameItem = (MenuItem) findViewById(R.id.nicknameItem);
		nicknameItem.setOnClickListener(this);
		genderItem = (MenuItem) findViewById(R.id.genderItem);
		genderItem.setOnClickListener(this);
		ageItem = (MenuItem) findViewById(R.id.ageItem);
		ageItem.setOnClickListener(this);
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
		getRightTitleBtn().setVisibility(View.INVISIBLE);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 上传头像
				showProgressDialog(getString(R.string.is_updating_user_info));
				if (userAvatar != null) {
					uploadAvatar();
				}
				if (isEditUserInfo()) {
					params.put(ParamKey.NICKNAME, nicknameItem.getTip());
					params.put(ParamKey.GENDER, genderItem.getTip());
					params.put(ParamKey.AGE, ageItem.getTip());
					params.put(ParamKey.WEIGHT, weightItem.getTip());
					params.put(ParamKey.HEIGHT, heightItem.getTip());
					UserRequest.editUserInfo(params,
							new HttpResponseListener() {

								@Override
								public void responseSuccess() {
									AlertUtils.showToast(
											UpdateUserInfoActivity.this,
											"更新用户信息成功！");
									SugarRecord.deleteAll(UserInfoEntity.class,
											"username=?",
											new String[] { PreferenceUtils
													.getString(KEY.USER_NAME,
															"") });
									SugarRecord.deleteAll(
											UserAvatarEntity.class,
											"username=?",
											new String[] { PreferenceUtils
													.getString(KEY.USER_NAME,
															"") });
									UserRequest.getUserInfo(new HttpResponseListener() {
										
										@Override
										public void responseSuccess() {
											hiddenProgressDialog();
											finish();
										}
										
										@Override
										public void responseFailed(int flag) {
											
										}
									});
								}

								@Override
								public void responseFailed(int flag) {
									switch (flag) {
									case ErrorFlag.INIT_USER_INFO_ERROR:
										AlertUtils.showToast(
												UpdateUserInfoActivity.this,
												"更新用户信息失败！");
										break;
									case ErrorFlag.NETWORK_ERROR:
										AlertUtils.showToast(
												UpdateUserInfoActivity.this,
												"网络错误，请检查！");
										break;
									default:
										break;
									}
								}
							});
				}
			}
		});
	}

	private void uploadAvatar() {
		try {
			params.put(ParamKey.AVATAR, userAvatar, "image/jpeg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		UserRequest.changeAvatar(UpdateUserInfoActivity.this, params,
				new HttpResponseListener() {

					@Override
					public void responseSuccess() {
						AlertUtils.showToast(UpdateUserInfoActivity.this,
								"上传头像成功！");
						if (!isEditUserInfo()) {
							SugarRecord.deleteAll(UserInfoEntity.class,
									"username=?",
									new String[] { PreferenceUtils.getString(
											KEY.USER_NAME, "") });
							SugarRecord.deleteAll(UserAvatarEntity.class,
									"username=?",
									new String[] { PreferenceUtils.getString(
											KEY.USER_NAME, "") });
							UserRequest.getUserInfo(new HttpResponseListener() {
								
								@Override
								public void responseSuccess() {
									hiddenProgressDialog();
									finish();
								}
								
								@Override
								public void responseFailed(int flag) {
									
								}
							});
						}
					}

					@Override
					public void responseFailed(int flag) {

					}
				});
	}

	private boolean isEditUserInfo() {
		UserInfoEntity userInfoEntity = UserManager.getInstance().getUserInfo();
		if (nicknameItem.getTip().equals(userInfoEntity.getNickname())
				&& genderItem.getTip().equals(userInfoEntity.getGender())
				&& ageItem.getTip().equals(userInfoEntity.getAge())
				&& heightItem.getTip().equals(userInfoEntity.getHeight())
				&& weightItem.getTip().equals(userInfoEntity.getWeight())) {
			return false;
		} else {
			return true;
		}

	}

	private void initData() {
		isMale = new String[] {
				getResources().getString(R.string.user_gender_female_text),
				getResources().getString(R.string.user_gender_male_text) };
		params = new RequestParams();
		UserInfoEntity userInfoEntity = UserManager.getInstance().getUserInfo();
		if (userInfoEntity != null) {
			userAvatarIv.setImageBitmap(BitmapUtils.convertToBitmap(UserManager
					.getInstance().getUserAvatarUrl()));
			nicknameItem.setTip(userInfoEntity.getNickname());
			genderItem.setTip(userInfoEntity.getGender());
			ageItem.setTip(userInfoEntity.getAge());
			heightItem.setTip(userInfoEntity.getHeight());
			weightItem.setTip(userInfoEntity.getWeight());
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.userAvatarLayout:
			doPickPhotoAction();
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
									if (!genderItem.getTip().equals(
											UserManager.getInstance()
													.getUserInfo().getGender())) {
										getRightTitleBtn().setVisibility(
												View.VISIBLE);
									}
									genderItem.setTip(isMale[which]);
									dialog.dismiss();
								}

							}).show();
			break;
		case R.id.ageItem:

			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year,
						int month, int dayOfMonth) {
					// Calendar月份是从0开始,所以month要加1
					ageItem.setTip(UserManager.getUserAge(year + "-"
							+ (month + 1) + "-" + dayOfMonth));
					getRightTitleBtn().setVisibility(View.VISIBLE);
				}
			};
			Calendar calendar = Calendar.getInstance();
			Dialog dialog = new DatePickerDialog(this, dateListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();

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
			heightIntent.putExtra("information", heightItem.getTip());
			heightIntent.putExtra("requestCode", CHANGE_HEIGHT);
			startActivityForResult(heightIntent, CHANGE_HEIGHT);
			break;
		case R.id.weightItem:
			Intent weightIntent = new Intent(UpdateUserInfoActivity.this,
					EditUserInfoActivity.class);
			weightIntent.putExtra("information", weightItem.getTip());
			weightIntent.putExtra("requestCode", CHANGE_WEIGHT);
			startActivityForResult(weightIntent, CHANGE_WEIGHT);
			break;
		default:
			break;
		}
	}

	private void doPickPhotoAction() {
		final Context dialogContext = new ContextThemeWrapper(this,
				android.R.style.Theme_Light);
		String cancel = "返回";
		String[] choices;
		choices = new String[2];
		choices[0] = getString(R.string.take_photo); // 拍照
		choices[1] = getString(R.string.pick_photo); // 从相册中选择
		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
				android.R.layout.simple_list_item_1, choices);
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				dialogContext);
		builder.setTitle(R.string.attachToContact);
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0: {
							String status = Environment
									.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
								doTakePhoto();// 用户点击了从照相机获取
							} else {
								Toast.makeText(UpdateUserInfoActivity.this,
										"没有SD卡", Toast.LENGTH_LONG).show();
							}
							break;
						}
						case 1:
							doPickPhotoFromGallery();// 从相册中去获取
							break;
						}
					}
				});
		builder.setNegativeButton(cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	private Uri photoUri;

	private void doTakePhoto() {
		try {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			String filename = System.currentTimeMillis() + "";
			ContentValues values = new ContentValues();
			values.put(Media.TITLE, filename);
			photoUri = getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(intent, CHANGE_AVATAR_CAMERA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.photoPickerNotFoundText,
					Toast.LENGTH_LONG).show();
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void doPickPhotoFromGallery() {
		Intent intent = new Intent();
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		// 根据版本号不同使用不同的Action
		if (Build.VERSION.SDK_INT < 19) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
		} else {
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}
		startActivityForResult(intent, CHANGE_AVATAR_GALLERY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CHANGE_AVATAR_GALLERY) {
			if (data != null) {
				Uri uri = data.getData();
				setAvatar(uri);
				getRightTitleBtn().setVisibility(View.VISIBLE);
			}
		} else if (requestCode == CHANGE_AVATAR_CAMERA) {
			Log.d("UpdateUserInfoActivity", "CHANGE_AVATAR_CAMERA:"
					+ CHANGE_AVATAR_CAMERA);
			if (data == null) {
				if (photoUri != null) {
					Uri uri = photoUri;
					setAvatar(uri);
					getRightTitleBtn().setVisibility(View.VISIBLE);
				}
			}
		}
		if (data != null) {
			if (requestCode == CHANGE_NICKNAME) {
				String nickName = data.getStringExtra("returnUserInfo");
				if (!nickName.equals(UserManager.getInstance().getUserInfo()
						.getNickname())) {
					getRightTitleBtn().setVisibility(View.VISIBLE);
				}
				nicknameItem.setTip(nickName);
			} else if (requestCode == CHANGE_HEIGHT) {
				String height = data.getStringExtra("returnUserInfo");
				if (!height.equals(UserManager.getInstance().getUserInfo()
						.getHeight())) {
					getRightTitleBtn().setVisibility(View.VISIBLE);
				}
				heightItem.setTip(height);
			} else if (requestCode == CHANGE_WEIGHT) {
				String weight = data.getStringExtra("returnUserInfo");
				if (!weight.equals(UserManager.getInstance().getUserInfo()
						.getWeight())) {
					getRightTitleBtn().setVisibility(View.VISIBLE);
				}
				weightItem.setTip(weight);
			}
		}
	}

	private void setAvatar(Uri uri) {
		if (null != uri) {
			ContentResolver cr = this.getContentResolver();
			String imagePath = PhotoFileUtils.getPath(this, uri);
			try {
				userAvatar = new File(imagePath);// BitmapUtils.getThumbUploadPath(imagePath));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Log.d("UpdateUserInfoActivity", "imagePath:" + imagePath);
			try {
				Bitmap photo = MediaStore.Images.Media.getBitmap(cr, uri);
				if (photo != null) {
					Bitmap smallBitmap = BitmapUtils.zoomBitmap(photo,
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
