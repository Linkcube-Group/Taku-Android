package me.linkcube.taku.view;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class HistoryCircleChartView extends View {
	
	private RectF oval1=new RectF(10,10,410,410);
	
	private int percent=0;
	
	private Paint paint = new Paint();  
	
	public HistoryCircleChartView(Context context) {
		this(context, null);
	}
	
	public HistoryCircleChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);// 设置红色 
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了  
        canvas.drawArc(oval1, 0, 360, false, paint);
        paint.setStrokeWidth(5);
        paint.setTextSize(68);
        canvas.drawText("12Km", 130, 220, paint);
        paint.setTextSize(40);
        canvas.drawText("70%", 190, 270, paint);
        if(percent!=0){
        	paint.setStrokeWidth(8);
        	paint.setColor(Color.YELLOW);
        	canvas.drawArc(oval1, -90, (float) (percent*3.6), false, paint);
        }
	}
	
	public void onDrawHistory(int percent){
		this.percent=percent;
	}
	
//	private Handler handler=new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			Paint paint = new Paint();
//			paint.setColor(Color.YELLOW);
//	        canvas.drawArc(oval1, -90, (float) (msg.what*3.6), false, paint);
//		}
//		
//	};

}
