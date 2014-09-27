package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.TitleBaseActivity;
import me.linkcube.taku.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class UserInfoActivity extends TitleBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_activity);
		initView();
	}

	private void initView() {
		initTitle();
		MenuItem movingTargetItem = (MenuItem) findViewById(R.id.movingTargetItem);
		movingTargetItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(UserInfoActivity.this,
						UpdateUserInfoActivity.class));
			}
		});
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
