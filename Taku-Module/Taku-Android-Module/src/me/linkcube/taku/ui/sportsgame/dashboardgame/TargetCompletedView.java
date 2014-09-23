package me.linkcube.taku.ui.sportsgame.dashboardgame;

import me.linkcube.taku.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

public class TargetCompletedView extends View {

	// 画实心圆的画笔
	private Paint mCirclePaint;
	// 画圆环的画笔
	private Paint mRingPaint;
	// 画字体的画笔
	private Paint mProgressTextPaint;
	private Paint mDistanceTextPaint;
	// 圆形颜色
	private int mCircleColor;
	// 圆环颜色
	private int mRingColor;
	// 半径
	private float mRadius;
	// 圆环半径
	private float mRingRadius;
	// 圆环宽度
	private float mStrokeWidth;
	// 圆心x坐标
	private int mXCenter;
	// 圆心y坐标
	private int mYCenter;
	// 字的长度
	private float mProgressTxtWidth;
	private float mDistanceTxtWidth;
	// 字的高度
	private float mProgressTxtHeight;
	private float mDistanceTxtHeight;
	// 总进度
	private int mTotalProgress = 100;
	// 当前进度
	private int mProgress;
	//运行目标
	private int targetDistance=10;

	public TargetCompletedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取自定义的属性
		initAttrs(context, attrs);
		initVariable();
	}

	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.TargetCompletedView, 0, 0);
		mRadius = typeArray.getDimension(
				R.styleable.TargetCompletedView_mRadius, 80);
		mStrokeWidth = typeArray.getDimension(
				R.styleable.TargetCompletedView_mStrokeWidth, 10);
		mCircleColor = typeArray.getColor(
				R.styleable.TargetCompletedView_mCircleColor, 0xFFFFFFFF);
		mRingColor = typeArray.getColor(
				R.styleable.TargetCompletedView_mRingColor, 0xFFFFFFFF);

		mRingRadius = mRadius + mStrokeWidth / 2;
	}

	private void initVariable() {
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.STROKE);

		mRingPaint = new Paint();
		mRingPaint.setAntiAlias(true);
		mRingPaint.setColor(mRingColor);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setStrokeWidth(mStrokeWidth);

		mProgressTextPaint = new Paint();
		mProgressTextPaint.setAntiAlias(true);
		mProgressTextPaint.setStyle(Paint.Style.FILL);
		mProgressTextPaint.setARGB(255, 0, 0, 255);
		mProgressTextPaint.setTextSize(mRadius / 2);
		
		mDistanceTextPaint=new Paint();
		mDistanceTextPaint.setAntiAlias(true);
		mDistanceTextPaint.setStyle(Paint.Style.FILL);
		mDistanceTextPaint.setARGB(255, 255, 0, 255);
		mDistanceTextPaint.setTextSize(mRadius/3);

		FontMetrics fm = mProgressTextPaint.getFontMetrics();
		mProgressTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

	}

	RectF oval = new RectF();

	@Override
	protected void onDraw(Canvas canvas) {

		mXCenter = getWidth() / 2;
		mYCenter = getHeight() / 2;

		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

		if (mProgress > 0) {

			oval.left = (mXCenter - mRingRadius);
			oval.top = (mYCenter - mRingRadius);
			oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
			oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
			canvas.drawArc(oval, -90,
					((float) mProgress / mTotalProgress) * 360, false,
					mRingPaint); //
			String txt = mProgress + "%";
			mProgressTxtWidth = mProgressTextPaint.measureText(txt, 0,
					txt.length());
			canvas.drawText(txt, mXCenter - mProgressTxtWidth / 2, mYCenter
					+ mProgressTxtHeight / 4, mProgressTextPaint);
			// 这里设定运动目标距离
			String txt2 = targetDistance+"KM";
			mDistanceTxtWidth = mDistanceTextPaint.measureText(txt2, 0,
					txt2.length());
			canvas.drawText(txt2, mXCenter - mDistanceTxtWidth / 2, mYCenter
					+ mProgressTxtHeight, mDistanceTextPaint);
		}
	}

	public void setProgress(int progress) {
		mProgress = progress;
		postInvalidate();
	}

	public int getTargetDistance() {
		return targetDistance;
	}

	/**
	 * 设定运动目标值－－－“设定运动目标”界面设定的值
	 * */
	public void setTargetDistance(int targetDistance) {
		this.targetDistance = targetDistance;
	}
	

}
