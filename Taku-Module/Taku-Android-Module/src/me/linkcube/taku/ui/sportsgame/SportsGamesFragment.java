package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.MainActivity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SportsGamesFragment extends Fragment implements OnClickListener {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	private Button dashboardBtn;

	private Button takuBtn;

	public static SportsGamesFragment newInstance(int position) {
		SportsGamesFragment fragment = new SportsGamesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_DRAWER_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	public SportsGamesFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onDrawerItemSelected(getArguments().getInt(
				ARG_DRAWER_POSITION));

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sports_games_fragment,
				container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		dashboardBtn = (Button) view.findViewById(R.id.panelBtn);
		takuBtn = (Button) view.findViewById(R.id.takuBtn);
		dashboardBtn.setOnClickListener(this);
		takuBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.panelBtn:
			// TODO
			break;
		case R.id.takuBtn:
			// TODO
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}