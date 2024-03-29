package me.linkcube.taku.ui.networkgames;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.BaseSlidingFragment;
import android.app.Activity;
import android.os.Bundle;

public class NetworkGamesFragment extends BaseSlidingFragment {

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

	@Override
	protected int getLayoutId() {
		return R.layout.sports_games_fragment;
	}
}
