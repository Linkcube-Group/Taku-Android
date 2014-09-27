package custom.android.app.dialog;

/**
 * Implement this interface in Activity or Fragment to react to positive and
 * negative buttons.
 * 
 * @author Ervin Wang
 */
public interface ISimpleDialogListener {
	public void onPositiveButtonClicked(int requestCode);

	public void onNegativeButtonClicked(int requestCode);

	public void onNeutralButtonClicked(int requestCode);
}
