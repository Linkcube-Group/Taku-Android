package me.linkcube.taku.ui.welcome;

import java.util.ArrayList;

import me.linkcube.taku.R;
import me.linkcube.taku.common.ui.BasePagerAdapter;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 引导页Adapter
 * 
 * @author Orange
 * 
 */
public class GuidePagerAdapter extends BasePagerAdapter {

	public final static int GUIDE_VIEW_NUM = 3;

	private final static int[] GUIDE_IMAGES_RES = new int[] {
			R.drawable.guide_0, R.drawable.guide_1, R.drawable.guide_2 };

	private OnGuideCompleteListener listener;

	public GuidePagerAdapter(Context context) {
		super(context);
		initPager();
	}

	private void initPager() {
		mViewList = new ArrayList<View>();
		for (int i = 0; i < GUIDE_IMAGES_RES.length; i++) {
			View view = getImageView(GUIDE_IMAGES_RES[i]);
			mViewList.get(i).setOnClickListener(new OnGuideViewClick(i));
			mViewList.add(i, view);
		}

	}

	private class OnGuideViewClick implements OnClickListener {

		private int mPosition;

		public OnGuideViewClick(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			if (listener != null) {
				listener.onGuideViewClick(mPosition);
			}
		}

	}

	public void setOnCompleteListener(OnGuideCompleteListener listener) {
		this.listener = listener;
	}

	private ImageView getImageView(int resId) {
		ImageView imageView = new ImageView(mContext);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setImageResource(resId);
		return imageView;
	}

	/**
	 * 引导页面完成后的回调函数
	 * 
	 * @author NewCom
	 * 
	 */
	public interface OnGuideCompleteListener {

		void onGuideViewClick(int position);

	}

}
