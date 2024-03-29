package me.linkcube.taku.ui.sportsgame.dashboardgame.wheel;


import me.linkcube.taku.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;


public class ImageTextButton extends Button 
{
	private final String namespace = "http://www.javaeye.com/custom";
	private int resourceId = 0;
	private Bitmap bitmap;

	public ImageTextButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		setClickable(true);
		// Ĭ��ʹ��R.drawable.icon����ͼƬ��ͨ��icon�����������ȡͼƬ
		resourceId = attrs.getAttributeResourceValue(namespace, "icon",R.drawable.ic_launcher);
		bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
		this.setTextSize(12);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// ͼƬ����������ʾ
		int x = (this.getMeasuredWidth() - bitmap.getWidth()) >> 1;
		int y = 0;
		canvas.drawBitmap(bitmap, x, y, null);
		// ������Ҫת������ΪĬ�������Button�е����־�����ʾ
		// ������Ҫ�������ڵײ���ʾ
		canvas.translate(0,(this.getMeasuredHeight() >> 1) - (int) this.getTextSize());
		super.onDraw(canvas);
	}

	public void setIcon(Bitmap bitmap) 
	{
		this.bitmap = bitmap;
		invalidate();
	}

	public void setIcon(int resourceId) 
	{
		this.bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
		invalidate();
	}

}
