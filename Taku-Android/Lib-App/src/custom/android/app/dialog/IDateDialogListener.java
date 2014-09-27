package custom.android.app.dialog;

import java.util.Date;

/**
 * Implement this interface in Activity or Fragment to react to positive and
 * negative buttons.
 * 
 * @author Ervin Wang
 */
public interface IDateDialogListener {
	public void onPositiveButtonClicked(int requestCode, Date date);

	public void onNegativeButtonClicked(int requestCode, Date date);
}
