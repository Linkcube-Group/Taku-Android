package me.linkcube.taku.common.ui;

import java.io.Serializable;

import me.linkcube.taku.common.util.Timber;
import android.app.Activity;
import android.os.Bundle;

/**
 * Activity基类，提供了生命周期的日志输出，定义了一些变量
 * 
 * @author Orange
 * 
 */
public abstract class BaseActivity extends Activity {

	protected Activity mActivity = this;

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
		Timber.d(this.getClass().getName() + " onCreate");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Timber.d(this.getClass().getName() + " onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Timber.d(this.getClass().getName() + " onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Timber.d(this.getClass().getName() + " onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Timber.d(this.getClass().getName() + " onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Timber.d(this.getClass().getName() + " onStop");
	}

}
