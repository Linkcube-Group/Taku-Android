package me.linkcube.taku.ui.history;

import custom.android.app.BaseFragment;
import me.linkcube.taku.R;
import me.linkcube.taku.view.HistoryCircleChartView;
import me.linkcube.taku.view.TitleView;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryFragment extends BaseFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	private View layoutView;

	private HistoryCircleChartView historyCircleChartView;

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

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layoutView = inflater.inflate(R.layout.history_fragment, container,
				false);
		TitleView titleview = (TitleView) layoutView
				.findViewById(R.id.title_layout);
		initTitle(titleview);

		historyCircleChartView = (HistoryCircleChartView) layoutView
				.findViewById(R.id.historyCircleChartView);

		return layoutView;
	}

	private void initTitle(TitleView titleview) {
		titleview.setTitleText("运动历史记录");
		titleview.getRightTitleBtn().setVisibility(View.GONE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		historyCircleChartView.onDrawHistory(70);
		historyCircleChartView.invalidate();
	}
}
