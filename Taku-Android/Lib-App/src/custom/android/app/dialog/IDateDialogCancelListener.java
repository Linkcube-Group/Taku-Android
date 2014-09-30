package custom.android.app.dialog;

import java.util.Date;

/**
 * @author Ervin Wang
 */
public interface IDateDialogCancelListener {
	public void onCancelled(int requestCode, Date date);
}
