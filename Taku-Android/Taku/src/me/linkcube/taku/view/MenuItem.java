package me.linkcube.taku.view;

import me.linkcube.taku.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuItem extends LinearLayout {

	/** menu item icon */
	private ImageView imageView;
	/** menu item title */
	private TextView titleView;
	/** menu item tip */
	private TextView tipView;

	public MenuItem(Context context) {
		this(context, null);
	}

	@SuppressLint("NewApi")
	public MenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
		final Resources res = getResources();
		final int defaultTitleTextColor = res
				.getColor(R.color.default_menu_item_titleTextColor);
		final float defaultTitleTextSize = res
				.getDimension(R.dimen.default_menu_item_titleTextSize);
		final int defaultTipTextColor = res
				.getColor(R.color.default_menu_item_tipTextColor);
		final float defaultTipTextSize = res
				.getDimension(R.dimen.default_menu_item_tipTextSize);

//		final int defaultTitleBackground = res
//				.getColor(R.color.default_menu_item_background);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MenuItem);

		titleView.setText(a.getString(R.styleable.MenuItem_TitleText));
		titleView.setTextColor(a.getColor(R.styleable.MenuItem_TitleTextColor,
				defaultTitleTextColor));
//		titleView.setTextSize(a.getDimension(
//				R.styleable.MenuItem_TitleTextSize, defaultTitleTextSize));
		tipView.setText(a.getString(R.styleable.MenuItem_TipText));
		tipView.setTextColor(a.getColor(R.styleable.MenuItem_TipTextColor,
				defaultTipTextColor));
//		tipView.setTextSize(a.getDimension(R.styleable.MenuItem_TipTextSize,
//				defaultTipTextSize));

//		Drawable background = a.getDrawable(R.styleable.MenuItem_Src);
//		if (background != null) {
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//				setBackground(background);
//			} else {
//				setBackgroundDrawable(background);
//			}
//		} else {
//			int bgColor = a.getColor(R.styleable.MenuItem_Background,
//					defaultTitleBackground);
//			setBackgroundColor(bgColor);
//		}

		Drawable d = a.getDrawable(R.styleable.MenuItem_Src);
		if (d != null) {
			imageView.setImageDrawable(d);
		} else {
			d = res.getDrawable(R.drawable.ic_right_arrow);
			imageView.setImageDrawable(d);
		}

		a.recycle();

	}

	private void initViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.abc_meun_item, this);
		titleView = (TextView) findViewById(R.id.textView);
		imageView = (ImageView) findViewById(R.id.imageView);
		tipView = (TextView) findViewById(R.id.tipTv);
	}

	public void setIcon(int icon) {
		imageView.setImageResource(icon);
	}

	public void setTitle(int title) {
		titleView.setText(title);
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void setTip(int tip) {
		tipView.setText(tip);
	}

	public void setTip(String tip) {
		tipView.setText(tip);
	}
	
	public String getTip(){
		return tipView.getText().toString();
	}

}
