package me.linkcube.taku.ui.networkgames;

import custom.android.app.DialogFragment;
import android.app.Activity;
import android.os.Bundle;

public class NetworkGamesFragment extends DialogFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	public static NetworkGamesFragment newInstance(int position) {
		NetworkGamesFragment fragment = new NetworkGamesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_DRAWER_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	public NetworkGamesFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}
}
