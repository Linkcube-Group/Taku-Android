package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SportsGamesFragment extends Fragment implements OnClickListener {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private Button dashboardBtn;

	private Button takuBtn;

	public static SportsGamesFragment newInstance(int sectionNumber) {
		SportsGamesFragment fragment = new SportsGamesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public SportsGamesFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sports_game_fragment,
				container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		dashboardBtn = (Button) view.findViewById(R.id.dashboardBtn);
		takuBtn = (Button) view.findViewById(R.id.takuBtn);
		dashboardBtn.setOnClickListener(this);
		takuBtn.setOnClickListener(this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// ((MainActivity) activity).onSectionAttached(getArguments().getInt(
		// ARG_SECTION_NUMBER));

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.dashboardBtn:
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