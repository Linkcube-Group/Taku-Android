package me.linkcube.taku.ui.main.view;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.user.LoginActivity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Drawer extends LinearLayout implements View.OnClickListener {

	private DrawerItem[] items;

	private int[] titles = new int[] { R.string.sports_games, R.string.history,
			R.string.network_games, R.string.settings };

	private int[] icons = new int[] { R.drawable.ic_menu_sports_games,
			R.drawable.ic_menu_history, R.drawable.ic_menu_network_games,
			R.drawable.ic_menu_settings };

	private OnDrawerItemClickListener listener;

	public Drawer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Drawer(Context context) {
		super(context);
		init(context);
	}

	private void init(final Context context) {
		inflate(context, R.layout.residemenu_drawer, this);
		items = new DrawerItem[4];
		items[0] = (DrawerItem) findViewById(R.id.sportsGamesBtn);
		items[1] = (DrawerItem) findViewById(R.id.historyBtn);
		items[2] = (DrawerItem) findViewById(R.id.networkGamesBtn);
		items[3] = (DrawerItem) findViewById(R.id.settingsBtn);
		for (int i = 0; i < 4; i++) {
			items[i].setTitle(titles[i]).setIcon(icons[i])
					.setOnClickListener(this);
		}
		ImageView avatar_iv=(ImageView)findViewById(R.id.avatar_iv);
		avatar_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				context.startActivity(new Intent(getContext(),LoginActivity.class));
			}
		});
	}

	public void setOnDrawerItemClickListener(OnDrawerItemClickListener listener) {
		this.listener = listener;
	}

	public Drawer getDrawer() {
		return this;
	}

	public interface OnDrawerItemClickListener {
		public void onDrawerItemClick(View view, int position);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sportsGamesBtn:
			listener.onDrawerItemClick(items[0], 0);
			break;
		case R.id.historyBtn:
			listener.onDrawerItemClick(items[1], 1);
			break;
		case R.id.networkGamesBtn:
			listener.onDrawerItemClick(items[2], 2);
			break;
		case R.id.settingsBtn:
			listener.onDrawerItemClick(items[3], 3);
			break;
		default:
			break;
		}
	}
}
