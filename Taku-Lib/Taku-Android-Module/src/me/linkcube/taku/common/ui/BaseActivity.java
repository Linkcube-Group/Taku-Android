package me.linkcube.taku.common.ui;

import java.io.Serializable;

import me.linkcube.taku.common.utils.Timber;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Activity基类，提供了生命周期的日志输出，定义了一些变量
 * 
 * @author Orange
 * 
 */
public abstract class BaseActivity extends Activity {

	protected Activity mActivity = this;
	protected View actionbarView = null;

	@SuppressWarnings("unchecked")
	protected <V extends Serializable> V getSerializable(final String name) {
		return (V) getIntent().getSerializableExtra(name);
	}

	protected int getIntExtra(final String name) {
		return getIntent().getIntExtra(name, -1);
	}

	protected boolean getBooleanExtra(final String name) {
		return getIntent().getBooleanExtra(name, false);
	}

	protected String getStringExtra(final String name) {
		return getIntent().getStringExtra(name);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Timber.d("onCreate");

	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Timber.d("onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Timber.d("onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Timber.d("onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Timber.d("onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Timber.d("onStop");
	}

}
