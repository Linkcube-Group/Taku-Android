package me.linkcube.taku.view;

import me.linkcube.taku.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuItem extends LinearLayout {

	/** menu item icon */
	private ImageView imageView;
	/** menu item title */
	private TextView textView;

	public MenuItem(Context context) {
		this(context, null);
	}

	public MenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
		final Resources res = getResources();
		final int defaultTextColor = res
				.getColor(R.color.default_menu_item_textColor);
		final float defaultTextSize = res
				.getDimension(R.dimen.default_menu_item_textSize);
		final int defaultTextBackground = res
				.getColor(R.color.default_menu_item_background);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MenuItem);

		textView.setText(a.getString(R.styleable.MenuItem_MenuItemText));
		textView.setTextColor(a.getColor(R.attr.MenuItemTextColor,
				defaultTextColor));
		textView.setTextSize(a.getDimension(R.attr.MenuItemTextSize,
				defaultTextSize));
		setBackgroundColor(a.getColor(R.attr.MenuItemBackground,
				defaultTextBackground));
		a.recycle();

	}

	private void initViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.abc_meun_item, this);
		textView = (TextView) findViewById(R.id.textView);
		imageView = (ImageView) findViewById(R.id.imageView);
	}

	/**
	 * set the icon color;
	 * 
	 * @param icon
	 */
	public void setIcon(int icon) {
		imageView.setImageResource(icon);
	}

	/**
	 * set the title with resource ;
	 * 
	 * @param title
	 */
	public void setTitle(int title) {
		textView.setText(title);
	}

	/**
	 * set the title with string;
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		textView.setText(title);
	}

}
