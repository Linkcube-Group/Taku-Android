package me.linkcube.taku.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import me.linkcube.taku.R;
import me.linkcube.taku.ui.main.view.SlidingMenuItem;
import me.linkcube.taku.ui.user.LoginActivity;
import me.linkcube.taku.ui.user.UserInfoActivity;
import me.linkcube.taku.ui.user.UserManager;

public class MenuFragment extends BaseSlidingFragment implements
		OnClickListener {

	private SlidingMenuItem sportsGamesMenuItem, historyMenuItem,
			networkGamesMenuItem, settingsMenuItem;

	private OnMenuItemClickListener listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnMenuItemClickListener) {
			listener = (OnMenuItemClickListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement OnMenuItemClickListener");
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ImageView avatar_iv = (ImageView) view.findViewById(R.id.avatar_iv);
		TextView user_name_tv = (TextView) view.findViewById(R.id.user_name_tv);
		avatar_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (UserManager.getInstance().isLogin()) {
					getActivity().startActivity(
							new Intent(getActivity(), UserInfoActivity.class));
				} else {
					getActivity().startActivity(
							new Intent(getActivity(), LoginActivity.class));
				}
			}
		});
		sportsGamesMenuItem = (SlidingMenuItem) view
				.findViewById(R.id.sportsGamesBtn);
		historyMenuItem = (SlidingMenuItem) view.findViewById(R.id.historyBtn);
		networkGamesMenuItem = (SlidingMenuItem) view
				.findViewById(R.id.networkGamesBtn);
		settingsMenuItem = (SlidingMenuItem) view.findViewById(R.id.settingsBtn);
		sportsGamesMenuItem.setTitle(R.string.sports_games)
				.setIcon(R.drawable.ic_menu_sports_games)
				.setOnClickListener(this);
		historyMenuItem.setTitle(R.string.history)
				.setIcon(R.drawable.ic_menu_history).setOnClickListener(this);
		networkGamesMenuItem.setTitle(R.string.network_games)
				.setIcon(R.drawable.ic_menu_network_games)
				.setOnClickListener(this);
		settingsMenuItem.setTitle(R.string.settings)
				.setIcon(R.drawable.ic_menu_settings).setOnClickListener(this);
	}

	public interface OnMenuItemClickListener {
		public void onMenuItemClick(View view, int position);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.menu_fragment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sportsGamesBtn:
			listener.onMenuItemClick(sportsGamesMenuItem, 0);
			break;
		case R.id.historyBtn:
			listener.onMenuItemClick(historyMenuItem, 1);
			break;
		case R.id.networkGamesBtn:
			listener.onMenuItemClick(networkGamesMenuItem, 2);
			break;
		case R.id.settingsBtn:
			listener.onMenuItemClick(settingsMenuItem, 3);
			break;
		default:
			break;
		}
	}

}
