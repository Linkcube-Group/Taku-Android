package me.linkcube.taku.ui.profile;

import custom.android.app.CustomDialogFragment;
import android.app.Activity;
import android.os.Bundle;

public class ProfileFragment extends CustomDialogFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	public static ProfileFragment newInstance(int position) {
		ProfileFragment fragment = new ProfileFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_DRAWER_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	public ProfileFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

}
