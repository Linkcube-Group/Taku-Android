package me.linkcube.taku.ui.setting;

import custom.android.app.DialogFragment;
import custom.android.util.AlertUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import me.linkcube.taku.R;
import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.ui.user.HttpResponseListener;
import me.linkcube.taku.ui.user.LoginActivity;
import me.linkcube.taku.ui.user.RegisterActivity;
import me.linkcube.taku.ui.user.UserManager;
import me.linkcube.taku.view.TitleView;

public class SettingFragment extends DialogFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	private View layoutView;

	private Button settingLoginBtn;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layoutView = inflater.inflate(R.layout.setting_fragment, container,
				false);
		TitleView titleview = (TitleView) layoutView
				.findViewById(R.id.title_layout);
		initTitle(titleview);
		settingLoginBtn = (Button) layoutView
				.findViewById(R.id.setting_login_btn);
		settingLoginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (UserManager.getInstance().isLogin()) {
					UserManager.getInstance().userLogout(
							new HttpResponseListener() {

								@Override
								public void responseSuccess() {
									settingLoginBtn
											.setText(getResources().getString(
													R.string.login_btn_text));
									AlertUtils.showToast(getActivity(),
											"当前用户已退出！");
								}

								@Override
								public void responseFailed(int flag) {
									switch (flag) {
									case ErrorFlag.LOGOUT_ERROR:
										AlertUtils.showToast(getActivity(),
												"退出登录失败！");
										break;
									case ErrorFlag.NETWORK_ERROR:
										AlertUtils.showToast(getActivity(),
												"网络错误，请检查！");
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
		return layoutView;
	}

	private void initTitle(TitleView titleview) {
		titleview.setTitleText(getResources().getString(
				R.string.setting_title_text));
		titleview.getRightTitleBtn().setVisibility(View.GONE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

}
