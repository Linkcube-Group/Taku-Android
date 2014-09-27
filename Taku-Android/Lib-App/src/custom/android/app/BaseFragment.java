package custom.android.app;

import java.io.Serializable;

import custom.android.util.Timber;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类,提供了便捷的Activity向Fragment传递的Serializable和简单类型数据的方法，
 * 并在重要的生命周期中打印Log，方便调试
 * 
 * @author Ervin
 */
public class BaseFragment extends Fragment {

	protected Activity mActivity;

	@SuppressWarnings("unchecked")
	protected <V extends Serializable> V getSerializable(final String name) {
		return (V) getArguments().getSerializable(name);
	}

	protected int getIntExtra(final String name) {
		return getArguments().getInt(name, -1);
	}

	protected boolean getBooleanExtra(final String name) {
		return getArguments().getBoolean(name, false);
	}

	protected String getStringExtra(final String name) {
		return getArguments().getString(name);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
		Timber.d(this.getClass().getName() + " onAttach");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Timber.d(this.getClass().getName() + " onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Timber.d(this.getClass().getName() + " onViewCreated");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Timber.d(this.getClass().getName() + " onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		Timber.d(this.getClass().getName() + " onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Timber.d(this.getClass().getName() + " onStop");
	}

	@Override
	public void onResume() {
		super.onResume();
		Timber.d(this.getClass().getName() + " onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Timber.d(this.getClass().getName() + " onPause");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Timber.d(this.getClass().getName() + " onDestroyView");
	}

}