package me.linkcube.taku.ui.main;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.view.ResideMenu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class MainActivity extends ActionBarActivity implements
		ResideMenu.OnMenuListener {

	private ResideMenu resideMenu;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_bg);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(this);
		resideMenu.setShadowVisible(false);
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

	// @Override
	// public void onNavigationDrawerItemSelected(int position) {
	// FragmentManager fragmentManager = getSupportFragmentManager();
	// switch (position) {
	// case 0:
	//
	// fragmentManager
	// .beginTransaction()
	// .replace(R.id.container,
	// SportsGamesFragment.newInstance(position)).commit();
	// break;
	// case 1:
	// fragmentManager = getSupportFragmentManager();
	// fragmentManager
	// .beginTransaction()
	// .replace(R.id.container,
	// SportsGamesFragment.newInstance(position)).commit();
	// break;
	// case 2:
	// fragmentManager = getSupportFragmentManager();
	// fragmentManager
	// .beginTransaction()
	// .replace(R.id.container,
	// SportsGamesFragment.newInstance(position)).commit();
	// break;
	//
	// case 3:
	// fragmentManager = getSupportFragmentManager();
	// fragmentManager
	// .beginTransaction()
	// .replace(R.id.container,
	// SportsGamesFragment.newInstance(position)).commit();
	// break;
	// default:
	// break;
	// }
	//
	// }

	public void onSectionAttached(int number) {
		switch (number) {
		case 0:
			mTitle = getString(R.string.sports_games);
			break;
		case 1:
			mTitle = getString(R.string.history);
			break;
		case 2:
			mTitle = getString(R.string.network_games);
			break;
		case 3:
			mTitle = getString(R.string.settings);
			break;
		default:
			break;
		}
		restoreActionBar();
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(mTitle);
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
