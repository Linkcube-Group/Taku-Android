package me.linkcube.taku.ui.main;

import base.common.ui.DialogFragmentActivity;
import me.linkcube.taku.R;
import me.linkcube.taku.ui.history.HistoryFragment;
import me.linkcube.taku.ui.main.view.Drawer.OnDrawerItemClickListener;
import me.linkcube.taku.ui.main.view.ResideMenu;
import me.linkcube.taku.ui.networkgames.NetworkGamesFragment;
import me.linkcube.taku.ui.setting.SettingFragment;
import me.linkcube.taku.ui.sportsgame.SportsGamesFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends DialogFragmentActivity implements
		ResideMenu.OnMenuListener, OnDrawerItemClickListener {

	private final int SPORTS_GAMES = 0;

	private final int HISTORY = 1;

	private final int NETWORK_GAMES = 2;

	private final int SETTINGS = 3;

	private ResideMenu resideMenu;

	private int mPosition = SPORTS_GAMES;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_bg);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(this);
		resideMenu.setShadowVisible(false);
		resideMenu.setOnDrawerItemClickListener(this);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, SportsGamesFragment.newInstance(0))
				.commit();

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void openMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerItemClick(View view, int position) {
		Log.i("CXC", "----position:"+position);
		Log.i("CXC","++++mPosition:"+mPosition);
		if (mPosition != position) {
			resideMenu.closeMenu();
			mPosition = position;
			return;
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case SPORTS_GAMES:// 运动游戏
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							SportsGamesFragment.newInstance(position)).commit();
			break;
		case HISTORY:// 历史记录
			fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							HistoryFragment.newInstance(position)).commit();
			break;
		case NETWORK_GAMES:// 联网游戏
			fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							NetworkGamesFragment.newInstance(position))
					.commit();
			break;

		case SETTINGS:// 设置
			fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							SettingFragment.newInstance(position)).commit();
			break;
		default:
			
			break;
		}

	}

}
