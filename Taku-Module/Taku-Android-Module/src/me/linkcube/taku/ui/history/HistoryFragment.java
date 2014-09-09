package me.linkcube.taku.ui.history;

import android.app.Activity;
import android.os.Bundle;
import me.linkcube.taku.common.ui.DialogFragment;
import me.linkcube.taku.ui.main.MainActivity;

public class HistoryFragment extends DialogFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	public static HistoryFragment newInstance(int position) {
		HistoryFragment fragment = new HistoryFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_DRAWER_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	public HistoryFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onDrawerItemSelected(getArguments().getInt(
				ARG_DRAWER_POSITION));

	}
}
