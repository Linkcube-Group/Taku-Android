package me.linkcube.taku.ui.main;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.view.ResideMenu;
import me.linkcube.taku.ui.sportsgame.SportsGamesFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity implements
		ResideMenu.OnMenuListener, OnItemClickListener {

	private final int SPORTS_GAMES = 0;

	private final int HISTORY = 1;

	private final int NETWORK_GAMES = 2;

	private final int SETTINGS = 3;

	private ResideMenu resideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_bg);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(this);
		resideMenu.setShadowVisible(false);
		resideMenu.setOnItemClickListener(this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
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
							SportsGamesFragment.newInstance(position)).commit();
			break;
		case NETWORK_GAMES:// 联网游戏
			fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							SportsGamesFragment.newInstance(position)).commit();
			break;

		case SETTINGS:// 设置
			fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							SportsGamesFragment.newInstance(position)).commit();
			break;
		default:
			break;
		}
	}

	public void onDrawerItemSelected(int drawerPosition) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void openMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeMenu() {
		// TODO Auto-generated method stub

	}

}
