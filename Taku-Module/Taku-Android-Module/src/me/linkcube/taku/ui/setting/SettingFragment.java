package me.linkcube.taku.ui.setting;

import base.common.ui.DialogFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.linkcube.taku.R;

public class SettingFragment extends DialogFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

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
		return inflater.inflate(R.layout.setting_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
