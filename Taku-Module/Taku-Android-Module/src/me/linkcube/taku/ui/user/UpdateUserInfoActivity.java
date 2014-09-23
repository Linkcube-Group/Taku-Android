package me.linkcube.taku.ui.user;

import me.linkcube.taku.R;
import android.os.Bundle;
import android.view.View;
import base.common.ui.TitleBaseActivity;

public class UpdateUserInfoActivity extends TitleBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_user_info_activity);
		initView();
	}

	private void initView() {
		setTitleText(getResources().getString(R.string.update_user_info_text));
		getRightTitleBtn().setVisibility(View.GONE);
	}

	
}
