package custom.android.app;

import java.io.Serializable;

import custom.android.util.Timber;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {

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
