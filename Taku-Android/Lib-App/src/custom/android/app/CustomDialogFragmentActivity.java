package custom.android.app;

import custom.android.app.dialog.ISimpleDialogListener;
import custom.android.util.AlertUtils;
import android.app.ProgressDialog;

public abstract class CustomDialogFragmentActivity extends
		CustomFragmentActivity implements ISimpleDialogListener {

	protected ProgressDialog progressDialog = null;

	/**
	 * 显示进度框
	 */
	protected void showProgressDialog(String message) {
		if (progressDialog == null)
			progressDialog = new ProgressDialog(this);
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

	@Override
	public void onPositiveButtonClicked(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNegativeButtonClicked(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNeutralButtonClicked(int requestCode) {
		// TODO Auto-generated method stub

	}

}
