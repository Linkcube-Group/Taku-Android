package custom.android.app;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseListAdapter<E> extends android.widget.BaseAdapter {

	protected List<E> items;

	protected Context mContext;

	public BaseListAdapter(Context context, List<E> items) {
		mContext = context;
		this.items = items;
		if (items == null) {
			throw new IllegalArgumentException(
					"The second argument is non-null");
		}
	}

	public void setList(List<E> items) {
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public E getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
