package me.linkcube.taku.ui.user;

import java.util.List;

import me.linkcube.taku.AppConst.Gender;
import me.linkcube.taku.AppConst.KEY;
import me.linkcube.taku.R;
import me.linkcube.taku.core.entity.UserInfoEntity;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.query.Select;

import custom.android.util.PreferenceUtils;

public class UserInfoActivity extends BaseTitleActivity {

	private TextView userNameTv;
	private TextView userAge;
	private ImageView userGenderIv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_activity);
		initView();
		
		initData();
	}

	private void initView() {
		initTitle();
		userNameTv=(TextView)findViewById(R.id.userNameTv);
		userAge=(TextView)findViewById(R.id.userAge);
		userGenderIv=(ImageView)findViewById(R.id.userGenderIv);
		MenuItem movingTargetItem = (MenuItem) findViewById(R.id.movingTargetItem);
		movingTargetItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(UserInfoActivity.this,
						UpdateUserInfoActivity.class));
			}
		});
	}

	private void initData() {
		UserInfoEntity userInfoEntity=UserManager.getInstance().getUserInfo();
		if(userInfoEntity!=null){
			userNameTv.setText(userInfoEntity.getNickname());
			userAge.setText(userInfoEntity.getAge());
			if(userInfoEntity.getGender().equals(Gender.FEMALE)){
				userGenderIv.setBackgroundResource(R.drawable.user_gender_female);
			}else{
				userGenderIv.setBackgroundResource(R.drawable.user_gender_male);
			}
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

}
