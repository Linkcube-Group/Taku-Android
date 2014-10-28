package me.linkcube.taku.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import custom.android.app.CustomFragment;

public abstract class BaseSlidingFragment extends CustomFragment {

	private View currentView;

	public View getCurrentView() {
		return currentView;
	}

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams(); 
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentView = inflater.inflate(getLayoutId(), container, false);
		return currentView;
	}

	protected abstract int getLayoutId();

}
