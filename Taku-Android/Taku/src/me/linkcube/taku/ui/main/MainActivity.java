package me.linkcube.taku.ui.main;

import custom.android.app.CustomFragmentActivity;
import me.linkcube.taku.ui.history.HistoryFragment;
import me.linkcube.taku.ui.main.MenuFragment.OnMenuItemClickListener;
import me.linkcube.taku.ui.main.view.SlidingMenu;
import me.linkcube.taku.ui.main.view.SlidingMenu.ISlidingMenu;
import me.linkcube.taku.ui.networkgames.NetworkGamesFragment;
import me.linkcube.taku.ui.setting.SettingFragment;
import me.linkcube.taku.ui.sportsgame.SportsGamesFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends CustomFragmentActivity implements
		ISlidingMenu, OnMenuItemClickListener {
	
	private String tag="MainActivity";

	private static final int SPORTS_GAMES = 0;

	private static final int HISTORY = 1;

	private static final int NETWORK_GAMES = 2;

	private static final int SETTINGS = 3;

	private int mPosition = SPORTS_GAMES;

	private SlidingMenu menu;

	private BaseSlidingFragment currentContentFragment;

	private BaseSlidingFragment menuFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menu = new SlidingMenu();
		menuFragment = new MenuFragment();
		currentContentFragment = SportsGamesFragment.newInstance(SPORTS_GAMES);
		menu.attachToActivity(this);
	}

	@Override
	public void onMenuOpened() {
		menu.openMenu();
	}

	@Override
	public void onMenuClosed() {
		menu.closeMenu();
	}

	@Override
	public BaseSlidingFragment getMenuFragment() {
		return menuFragment;
	}

	@Override
	public BaseSlidingFragment getContentFragment() {
		return currentContentFragment;
	}

	@Override
	public void onMenuItemClick(View view, int position) {
		Log.i("CXC", "----position:" + position);
		Log.i("CXC", "++++mPosition:" + mPosition);
		// if (mPosition == position) {
		// resideMenu.closeMenu();
		// return;
		// }
		switch (position) {
		case SPORTS_GAMES:// 运动游戏
			currentContentFragment = SportsGamesFragment.newInstance(position);
			break;
		case HISTORY:// 历史记录
			currentContentFragment = HistoryFragment.newInstance(position);
			break;
		case NETWORK_GAMES:// 联网游戏
			currentContentFragment = NetworkGamesFragment.newInstance(position);
			break;
		case SETTINGS:// 设置
			currentContentFragment = SettingFragment.newInstance(position);
			break;
		default:
			break;
		}
		menu.replaceContentFragment(currentContentFragment);
		menu.closeMenu();
	}
}
