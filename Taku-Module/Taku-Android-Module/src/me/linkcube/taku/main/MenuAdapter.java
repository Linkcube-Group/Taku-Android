package me.linkcube.taku.main;

import me.linkcube.taku.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private Context mContext;

	private int[] drawableIds = new int[] { R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher };

	private int[] menuTitles = new int[] { R.string.sports_games,
			R.string.history, R.string.network_games, R.string.settings };

	public MenuAdapter(Context context) {
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
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.menu_drawer_item, parent, false);
		}
		TextView titleTv = (TextView) convertView
				.findViewById(R.id.menu_title_tv);
		titleTv.setText(mContext.getResources().getString(menuTitles[position]));
		Drawable drawable = mContext.getResources().getDrawable(
				drawableIds[position]);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());// ��������ͼƬ��С��������ʾ
		titleTv.setCompoundDrawables(drawable, null, null, null);

		return convertView;
	}
}
