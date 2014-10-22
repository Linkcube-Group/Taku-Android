package me.linkcube.taku.ui.setting;

import custom.android.util.AlertUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import me.linkcube.taku.R;
import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.ui.main.BaseSlidingFragment;
import me.linkcube.taku.ui.request.UserRequest;
import me.linkcube.taku.ui.user.HttpResponseListener;
import me.linkcube.taku.ui.user.LoginActivity;
import me.linkcube.taku.ui.user.UserManager;
import me.linkcube.taku.view.MenuItem;
import me.linkcube.taku.view.TitleView;

public class SettingFragment extends BaseSlidingFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	private Button settingLoginBtn;

	private MenuItem feedbackItem;
	
	private UpdateUIListener updateUIListener;

	public static SettingFragment newInstance(int position) {
		SettingFragment fragment = new SettingFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_DRAWER_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	public SettingFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	private void initData() {
		if (UserManager.getInstance().isLogin()) {
			settingLoginBtn.setText(getResources().getString(
					R.string.logout_btn_text));
		} else {
			settingLoginBtn.setText(getResources().getString(
					R.string.login_btn_text));
		}
	}

	private void initTitle(TitleView titleview) {
		titleview.setTitleText(getResources().getString(
				R.string.setting_title_text));
		titleview.setLeftTitleIv(R.drawable.drawer_btn);
		titleview.getRightTitleBtn().setVisibility(View.GONE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		TitleView titleview = (TitleView) view.findViewById(R.id.title_layout);
		initTitle(titleview);
		feedbackItem = (MenuItem) view.findViewById(R.id.feedbackItem);
		feedbackItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), FeedbackActivity.class));
			}
		});
		settingLoginBtn = (Button) view.findViewById(R.id.setting_login_btn);
		settingLoginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (UserManager.getInstance().isLogin()) {
					UserRequest.userLogout(new HttpResponseListener() {

						@Override
						public void responseSuccess() {
							settingLoginBtn.setText(getResources().getString(
									R.string.login_btn_text));
							UserManager.getInstance().setLogin(false);
							UserManager.getInstance().setUserInfoEntity(null);
							updateUIListener.onLogoutUpdateUI();
							AlertUtils.showToast(getActivity(), "当前用户已退出！");
						}

						@Override
						public void responseFailed(int flag) {
							switch (flag) {
							case ErrorFlag.LOGOUT_ERROR:
								AlertUtils.showToast(getActivity(), "退出登录失败！");
								break;
							case ErrorFlag.NETWORK_ERROR:
								AlertUtils
										.showToast(getActivity(), "网络错误，请检查！");
								break;
							default:
								break;
							}
						}
					});
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			}
		});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.setting_fragment;
	}
	
	public void setUpdateUIListener(UpdateUIListener updateUIListener) {
		this.updateUIListener = updateUIListener;
	}

	public interface UpdateUIListener{
		public void onLogoutUpdateUI();
	}

}
