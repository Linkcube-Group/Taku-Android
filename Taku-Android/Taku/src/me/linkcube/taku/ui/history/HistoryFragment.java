package me.linkcube.taku.ui.history;

import com.loopj.android.http.RequestParams;

import me.linkcube.taku.R;
import me.linkcube.taku.AppConst.ParamKey;
import me.linkcube.taku.core.entity.SingleDayGameHistoryEntity;
import me.linkcube.taku.core.entity.TotalGameHistoryEntity;
import me.linkcube.taku.ui.main.BaseSlidingFragment;
import me.linkcube.taku.ui.request.GameRequest;
import me.linkcube.taku.ui.sportsgame.SportsGameManager;
import me.linkcube.taku.ui.user.HttpGameResponseListener;
import me.linkcube.taku.view.HistoryCircleChartView;
import me.linkcube.taku.view.TitleView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryFragment extends BaseSlidingFragment implements
		OnClickListener {

	private static final String ARG_DRAWER_POSITION = "drawer_position";

	private HistoryCircleChartView historyCircleChartView;

	private ImageView preDateIv, nextDateIv;

	private TextView historyDateTv;

	private TextView totalDistanceTv, totalDurationTv, totalCalorieTv;

	private TextView singleDayDistanceTv, singleDayCalorieTv,
			singleDayDurationTv;
	
	private double percent=0;

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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initView(view);

		initData();
		
	}

	private void initView(View view) {
		TitleView titleview = (TitleView) view.findViewById(R.id.title_layout);
		initTitle(titleview);
		preDateIv = (ImageView) view.findViewById(R.id.preDateIv);
		preDateIv.setOnClickListener(this);
		nextDateIv = (ImageView) view.findViewById(R.id.nextDateIv);
		nextDateIv.setOnClickListener(this);
		historyDateTv = (TextView) view.findViewById(R.id.historyDateTv);
		historyCircleChartView = (HistoryCircleChartView) view
				.findViewById(R.id.historyCircleChartView);
		// 每日
		singleDayDistanceTv = (TextView) view
				.findViewById(R.id.singleDayDistanceTv);
		singleDayCalorieTv = (TextView) view
				.findViewById(R.id.singleDayCalorieTv);
		singleDayDurationTv = (TextView) view
				.findViewById(R.id.singleDayDurationTv);
		// 总计
		totalDistanceTv = (TextView) view.findViewById(R.id.totalDistanceTv);
		totalDurationTv = (TextView) view.findViewById(R.id.totalDurationTv);
		totalCalorieTv = (TextView) view.findViewById(R.id.totalCalorieTv);
	}
	
	private void initTitle(TitleView titleview) {
		titleview.setTitleText(getString(R.string.historical_records));
		titleview.setLeftTitleIv(R.drawable.drawer_btn);
		titleview.getRightTitleBtn().setVisibility(View.GONE);
	}

	private void initData() {
		historyDateTv.setText(SportsGameManager.getTodayDate());
		// 获取全部历史记录
		GameRequest.getTotalHistory(new HttpGameResponseListener() {

			@Override
			public void responseSuccess(Object object) {
				TotalGameHistoryEntity totalGameHistoryEntity = (TotalGameHistoryEntity) object;
				totalDistanceTv.setText(totalGameHistoryEntity
						.getTotalDistance() + "千米");
				totalDurationTv.setText(totalGameHistoryEntity
						.getTotalDuration() + "秒");
				totalCalorieTv.setText(totalGameHistoryEntity.getTotalCalorie()
						+ "卡");
			}

			@Override
			public void responseFailed(int flag) {

			}
		});
		
		getSingleDayHistoryRecord(SportsGameManager.getTodayDate());
		
	}

	@Override
	protected int getLayoutId() {
		return R.layout.history_fragment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preDateIv:
			String preDateStr = historyDateTv.getText().toString();
			getSingleDayHistoryRecord(SportsGameManager.getDate(preDateStr, -1));
			break;
		case R.id.nextDateIv:
			String nextDateStr = historyDateTv.getText().toString();
			getSingleDayHistoryRecord(SportsGameManager.getDate(nextDateStr, 1));
			break;
		default:
			break;
		}
	}

	private void getSingleDayHistoryRecord(String requestDate) {
		historyDateTv.setText(requestDate);
		RequestParams params = new RequestParams();
		params.put(ParamKey.HISTORY_DATE, requestDate);
		GameRequest.getSingleDayHistory(params, new HttpGameResponseListener() {

			@Override
			public void responseSuccess(Object object) {
				SingleDayGameHistoryEntity singleDayGameHistoryEntity = (SingleDayGameHistoryEntity) object;
				singleDayDistanceTv.setText(singleDayGameHistoryEntity.getSingleDayDistance()+ "千米");
				singleDayDurationTv.setText(singleDayGameHistoryEntity.getSingleDayDuration()+ "秒");
				singleDayCalorieTv.setText(singleDayGameHistoryEntity.getSingleDayCalorie()+ "卡");
				percent=SportsGameManager.fromStringToDouble(singleDayGameHistoryEntity.getSingleDayDistance())/SportsGameManager.getTargetDistance();
				historyCircleChartView.setPercent(SportsGameManager.saveTwoPointDouble(percent*100));
				historyCircleChartView.invalidate();
			}

			@Override
			public void responseFailed(int flag) {

			}
		});
	}
}
