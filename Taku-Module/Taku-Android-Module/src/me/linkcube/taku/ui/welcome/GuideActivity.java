package me.linkcube.taku.ui.welcome;

import me.linkcube.taku.R;
import me.linkcube.taku.common.ui.DialogActivity;
import me.linkcube.taku.ui.main.MainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * 引导功能页
 * 
 * @author Orange
 * 
 */
public class GuideActivity extends DialogActivity implements
		GuidePagerAdapter.OnGuideCompleteListener {
	private ViewPager viewPager;

	private GuidePagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity);
		setUpViews();
		adapter.setOnCompleteListener(this);
		viewPager.setAdapter(adapter);
	}

	private void setUpViews() {
		viewPager = (ViewPager) findViewById(R.id.viewPagerGuide);
		adapter = new GuidePagerAdapter(this);

	}

	@Override
	public void onGuideViewClick(int position) {
		if (position == GuidePagerAdapter.GUIDE_VIEW_NUM - 1) {
			startActivity(new Intent(GuideActivity.this, MainActivity.class));
			finish();
		}
	}
}
