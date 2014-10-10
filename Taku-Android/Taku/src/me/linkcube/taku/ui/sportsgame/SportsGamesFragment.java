package me.linkcube.taku.ui.sportsgame;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.bt.BTSettingActivity;
import me.linkcube.taku.ui.main.BaseSlidingFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.ervinwang.bthelper.BTManager;

public class SportsGamesFragment extends BaseSlidingFragment implements
		OnClickListener {

	private static final int BT_SETTING_REQUEST_CODE = 2;

	private static final String ARG_DRAWER_POSITION = "drawer_position";
	// 连接蓝牙设备按钮
	private ImageButton connectBtn = null;

	// 仪表盘
	private Button dashboardBtn;

	// 踏酷
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

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// 得到控件引用
		dashboardBtn = (Button) view.findViewById(R.id.panelBtn);
		takuBtn = (Button) view.findViewById(R.id.takuBtn);
		connectBtn = (ImageButton) view.findViewById(R.id.connectBtn);
		// 注册事件
		dashboardBtn.setOnClickListener(this);
		takuBtn.setOnClickListener(this);
		connectBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.panelBtn:
			// TODO
			startActivity(new Intent(getActivity(), DashboardActivity.class));
			break;
		case R.id.takuBtn:
			// if (!isToyConnected()) {
			// getActivity().startActivityForResult(
			// new Intent(getActivity(), BTSettingActivity.class),
			// BT_SETTING_REQUEST_CODE);
			//
			// } else {
			// // TODO
			// }
			break;
		case R.id.connectBtn:// 连接蓝牙设备
			// TODO
			startActivity(new Intent(getActivity(), BTSettingActivity.class));
			break;
		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BT_SETTING_REQUEST_CODE) {

		}
	}

	private boolean isToyConnected() {
		return BTManager.getInstance().getDeviceService().checkConnection();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.sports_games_fragment;
	}
}