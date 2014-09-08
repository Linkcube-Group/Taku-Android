package me.linkcube.taku.view.menu;

import me.linkcube.taku.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ResideMenuDrawer extends LinearLayout {

	private ListView listView;

	private MenuDrawerAdapter adapter;

	public ResideMenuDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ResideMenuDrawer(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		inflate(context, R.layout.residemenu_drawer, this);
		listView = (ListView) findViewById(R.id.list);
		adapter = new MenuDrawerAdapter(context);
		listView.setAdapter(adapter);
	}

}
