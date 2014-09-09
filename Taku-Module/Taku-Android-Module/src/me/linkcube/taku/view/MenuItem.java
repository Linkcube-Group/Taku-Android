package me.linkcube.taku.view;

import me.linkcube.taku.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
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
		final int defaultBackground = res
				.getColor(R.color.default_menu_item_background);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MenuItem);

		textView.setText(a.getString(R.styleable.MenuItem_Text));
		textView.setTextColor(a.getColor(R.styleable.MenuItem_TextColor,
				defaultTextColor));
		textView.setTextSize(a.getDimension(R.styleable.MenuItem_TextSize,
				defaultTextSize));

		Drawable background = a.getDrawable(R.styleable.MenuItem_Src);
		if (background != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				setBackground(background);
			} else {
				setBackgroundDrawable(background);
			}
		} else {
			int bgColor = a.getColor(R.styleable.MenuItem_Background,
					defaultBackground);
			setBackgroundColor(bgColor);
		}

		Drawable d = a.getDrawable(R.styleable.MenuItem_Src);
		if (d != null) {
			imageView.setImageDrawable(d);
		} else {
			d = res.getDrawable(R.drawable.ic_search);
			imageView.setImageDrawable(d);
		}

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
