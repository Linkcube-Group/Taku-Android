package me.linkcube.taku.ui.main.view;

import custom.android.view.ViewHelper;
import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.BaseSlidingFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class SlidingMenu {

	private SlidingPaneLayout mSlidingPaneLayout;

	private FragmentActivity mActivity;

	private DisplayMetrics displayMetrics = new DisplayMetrics();

	private int maxMargin = 0;

	private ISlidingMenu menu;

	public SlidingMenu() {
	}

	public void attachToActivity(FragmentActivity activity) {
		if (activity != null) {
			if (activity instanceof ISlidingMenu) {
				menu = (ISlidingMenu) activity;
			} else {
				throw new ClassCastException(activity.toString()
						+ " must implement ISlidingMenu");
			}
		} else {
			throw new IllegalArgumentException();
		}
		mActivity = activity;
		mActivity.getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		mActivity.setContentView(R.layout.slidingmenu_main_layout);
		mSlidingPaneLayout = (SlidingPaneLayout) mActivity
				.findViewById(R.id.slidingpanellayout);
		FragmentTransaction transaction = mActivity.getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingpane_menu, menu.getMenuFragment());
		transaction
				.replace(R.id.slidingpane_content, menu.getContentFragment());
		transaction.commit();
		maxMargin = displayMetrics.heightPixels / 10;
		mSlidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				// TODO Auto-generated method stub
				int contentMargin = (int) (slideOffset * maxMargin);
				FrameLayout.LayoutParams contentParams = menu
						.getContentFragment().getCurrentViewParams();
				contentParams.setMargins(0, contentMargin, 0, contentMargin);
				menu.getContentFragment().setCurrentViewPararms(contentParams);

				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
						/ (float) displayMetrics.heightPixels;
				View view = menu.getMenuFragment().getCurrentView();
				ViewHelper.setScaleX(view, scale);// 设置缩放的基准点
				ViewHelper.setScaleY(view, scale);// 设置缩放的基准点
				ViewHelper.setPivotX(view, 0);// 设置缩放和选择的点
				ViewHelper.setPivotY(view, displayMetrics.heightPixels / 2);// 设置缩放和选择的点
				ViewHelper.setAlpha(view, slideOffset);
			}

			@Override
			public void onPanelOpened(View panel) {
				menu.onMenuOpened();

			}

			@Override
			public void onPanelClosed(View panel) {
				menu.onMenuClosed();

			}
		});
	}

	public void replaceContentFragment(Fragment fragment) {
		mActivity.getSupportFragmentManager().beginTransaction()
				.replace(R.id.slidingpane_content, fragment).commit();
	}

	public void openMenu() {
		mSlidingPaneLayout.openPane();
	}

	public void closeMenu() {
		mSlidingPaneLayout.closePane();
	}

	public interface ISlidingMenu {

		void onMenuOpened();

		void onMenuClosed();

		BaseSlidingFragment getMenuFragment();

		BaseSlidingFragment getContentFragment();
	}
}
