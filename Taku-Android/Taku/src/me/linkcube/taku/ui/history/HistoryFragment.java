package me.linkcube.taku.ui.history;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.BaseSlidingFragment;
import me.linkcube.taku.view.HistoryCircleChartView;
import me.linkcube.taku.view.TitleView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class HistoryFragment extends BaseSlidingFragment {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

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

	private void initTitle(TitleView titleview) {
		titleview.setTitleText("运动历史记录");
		titleview.getRightTitleBtn().setVisibility(View.GONE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		TitleView titleview = (TitleView) view.findViewById(R.id.title_layout);
		initTitle(titleview);
		historyCircleChartView = (HistoryCircleChartView) view
				.findViewById(R.id.historyCircleChartView);
		historyCircleChartView.onDrawHistory(70);
		historyCircleChartView.invalidate();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.history_fragment;
	}
}
