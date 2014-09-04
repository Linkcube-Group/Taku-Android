package me.linkcube.taku.ui.main.view;

import me.linkcube.taku.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ResideMenuDrawer extends LinearLayout {

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
	}

}
