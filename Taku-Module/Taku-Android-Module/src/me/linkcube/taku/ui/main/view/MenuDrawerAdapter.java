package me.linkcube.taku.ui.main.view;

import me.linkcube.taku.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MenuDrawerAdapter extends BaseAdapter {

	private Context mContext;

	private int[] drawableIds = new int[] { R.drawable.ic_menu_sports_games,
			R.drawable.ic_menu_history, R.drawable.ic_menu_network_games,
			R.drawable.ic_menu_settings };

	private int[] menuTitles = new int[] { R.string.sports_games,
			R.string.history, R.string.network_games, R.string.settings };

	public MenuDrawerAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return menuTitles.length;
	}

	@Override
	public Object getItem(int position) {
		return menuTitles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ResideMenuItem item = new ResideMenuItem(mContext,
				drawableIds[position], menuTitles[position]);
		return item;
	}
}
