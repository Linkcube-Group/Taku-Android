package me.linkcube.taku.ui.user;

import me.linkcube.taku.AppConst.Gender;
import me.linkcube.taku.R;
import me.linkcube.taku.core.entity.UserInfoEntity;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.ui.bt.BTSettingActivity;
import me.linkcube.taku.ui.sportsgame.TargetSettingActivity;
import me.linkcube.taku.view.CircularImage;
import me.linkcube.taku.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends BaseTitleActivity implements OnClickListener{

	private CircularImage userAvatarIv;
	private TextView userNameTv;
	private TextView userAge;
	private ImageView userGenderIv;

	private MenuItem btSettingItem, movingTargetItem, userHistoryRecordItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_activity);

		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initView() {
		initTitle();
		userAvatarIv = (CircularImage) findViewById(R.id.userAvatarIv);
		userNameTv = (TextView) findViewById(R.id.userNameTv);
		userAge = (TextView) findViewById(R.id.userAge);
		userGenderIv = (ImageView) findViewById(R.id.userGenderIv);
		btSettingItem = (MenuItem) findViewById(R.id.btSettingItem);
		btSettingItem.setOnClickListener(this);
		movingTargetItem = (MenuItem) findViewById(R.id.movingTargetItem);
		movingTargetItem.setOnClickListener(this);
		userHistoryRecordItem = (MenuItem) findViewById(R.id.userHistoryRecordItem);
		userHistoryRecordItem.setOnClickListener(this);
	}

	private void initData() {

		UserInfoEntity userInfoEntity = UserManager.getInstance().getUserInfo();
		if (userInfoEntity != null) {
			userNameTv.setText(userInfoEntity.getNickname());
			userAge.setText(userInfoEntity.getAge());
			if (userInfoEntity.getGender().equals(Gender.FEMALE)) {
				userGenderIv
						.setBackgroundResource(R.drawable.user_gender_female);
			} else {
				userGenderIv.setBackgroundResource(R.drawable.user_gender_male);
			}
			// 显示头像
			// ImageLoader.getInstance().displayImage(HttpUrl.BASE_URL+userInfoEntity.getAvatar(),
			// userAvatarIv);
			userAvatarIv.setImageBitmap(BitmapUtils.convertToBitmap(UserManager
					.getInstance().getUserAvatarUrl()));
		}
	}

	private void initTitle() {
		setTitleText("");
		setRightTitleBtn(R.drawable.ic_update_user_info);
		getRightTitleBtn().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(UserInfoActivity.this,
						UpdateUserInfoActivity.class));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btSettingItem:
			startActivity(new Intent(UserInfoActivity.this,
					BTSettingActivity.class));
			break;
		case R.id.movingTargetItem:
			startActivity(new Intent(UserInfoActivity.this,
					TargetSettingActivity.class));
			break;
		case R.id.userHistoryRecordItem:
			startActivity(new Intent(UserInfoActivity.this,
					UserHistoryRecordActivity.class));
			break;
		default:
			break;
		}
	}

}
