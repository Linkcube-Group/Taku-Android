package me.linkcube.taku.view;

import me.linkcube.taku.ui.sportsgame.SportsGameManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class HistoryCircleChartView extends View {

	private RectF oval1 = new RectF(10, 10, 410, 410);

	private double percent = 0;
	
	private Paint innerPaint = new Paint();
	
	private Paint textPaint = new Paint();
	
	private Paint outPaint = new Paint();

	public HistoryCircleChartView(Context context) {
		this(context, null);
	}

	public HistoryCircleChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		innerPaint.setStyle(Paint.Style.STROKE);
		innerPaint.setColor(Color.WHITE);// 设置红色
		innerPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		canvas.drawArc(oval1, 0, 360, false, innerPaint);
		textPaint.setColor(Color.WHITE);
		textPaint.setStrokeWidth(5);
		textPaint.setTextSize(76);
		canvas.drawText(SportsGameManager.getTargetDistance() + "km", 130, 220,
				textPaint);
		textPaint.setTextSize(44);
		canvas.drawText(percent + "%", 160, 280, textPaint);
		if (percent != 0) {
			outPaint.setStrokeWidth(12);
			outPaint.setStyle(Paint.Style.STROKE);
			outPaint.setAntiAlias(true);
			outPaint.setColor(Color.YELLOW);
			canvas.drawArc(oval1, -90, (float) (percent * 3.6), false, outPaint);
		}
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

}
