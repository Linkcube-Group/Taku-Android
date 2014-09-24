package me.linkcube.taku.ui.setting;

import base.common.ui.DialogFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.linkcube.taku.R;
import me.linkcube.taku.view.TitleView;

public class SettingFragment extends DialogFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";
	
	private View layoutView;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layoutView=inflater.inflate(R.layout.setting_fragment, container, false);
		TitleView titleview=(TitleView)layoutView.findViewById(R.id.title_layout);
		initTitle(titleview);
		return  layoutView;
	}
	private void initTitle(TitleView titleview){
		titleview.setTitleText("设置");
		titleview.getRightTitleBtn().setVisibility(View.GONE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
	
}
