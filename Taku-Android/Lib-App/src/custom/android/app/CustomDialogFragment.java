package custom.android.app;

import custom.android.util.AlertUtils;
import android.app.ProgressDialog;

public abstract class CustomDialogFragment extends BaseFragment {

	protected ProgressDialog progressDialog = null;

	/**
	 * 显示进度框
	 */
	protected void showProgressDialog(String message) {
		if (progressDialog == null)
			progressDialog = new ProgressDialog(getActivity());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(true);
		progressDialog.setMessage(message);
		progressDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	protected void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 主UI上的Toast展示
	 * 
	 * @param msg
	 */
	protected void showToast(final String msg) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertUtils.showToast(getActivity(), msg);
			}
		});
	}

	/**
	 * 主UI上的AlertDialog展示，带有一个确定按钮，并对词按钮无监听
	 * 
	 * @param msg
	 * @param title
	 */
	protected void showAlert(final String msg, final String title) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertUtils.showAlert(getActivity(), msg, title);
			}
		});
	}

}
