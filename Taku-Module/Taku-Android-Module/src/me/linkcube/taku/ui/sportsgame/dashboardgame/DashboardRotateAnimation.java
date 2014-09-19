package me.linkcube.taku.ui.sportsgame.dashboardgame;

import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
/**
 * 旋转动画类
 * */
public class DashboardRotateAnimation {
	//存放要旋转图片的ImageView
	private ImageView rotateImageView;
	//起始角度
	private float fromDegrees=0.0f;
	//终止角度
	private float toDegrees=0.0f;
	//动画时间,单位ms
	private long durationMillis=500; 
	//
	private RotateAnimation rotateAnimation=null;
	
	//getters and setters
	public float getFromDegrees() {
		return fromDegrees;
	}

	public void setFromDegrees(float fromDegrees) {
		this.fromDegrees = fromDegrees;
	}

	public float getToDegrees() {
		return toDegrees;
	}

	public void setToDegrees(float toDegrees) {
		this.toDegrees = toDegrees;
	}
	

	public ImageView getRotateImageView() {
		return rotateImageView;
	}

	public void setRotateImageView(ImageView rotateImageView) {
		this.rotateImageView = rotateImageView;
	}


	/*
	 *  pivotXType: Specifies how pivotXValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
	 	pivotXValue: The X coordinate of the point about which the object is being rotated, specified as an absolute number where 0 is the left edge. This value can either be an absolute number if pivotXType is ABSOLUTE, or a percentage (where 1.0 is 100%) otherwise.
	 	pivotYType: Specifies how pivotYValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
		pivotYValue: The Y coordinate of the point about which the object is being rotated, specified as an absolute number where 0 is the top edge. This value can either be an absolute number if pivotYType is ABSOLUTE, or a percentage (where 1.0 is 100%) otherwise.
	 * */
	private int pivotXType=Animation.RELATIVE_TO_SELF;
	private int pivotYType=Animation.RELATIVE_TO_SELF;
	private float pivotXValue=0.5f;
	private float pivotYValue=0.5f;
	
	/**
	 * 展示动画
	 @param fromDegrees :Rotation offset to apply at the start of the animation.
	 @param toDegrees: Rotation offset to apply at the end of the animation.
	 * */
	public void showAnimation(float fromDegrees, float toDegrees){
		Log.i("CXC","-----showAnimation()");
		if(rotateImageView==null){
			return ;
		}
		rotateAnimation=new RotateAnimation(fromDegrees, 
				toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
		if(rotateAnimation==null){
			return ;
		}
		//How long this animation should last. The duration cannot be negative.
		rotateAnimation.setDuration(durationMillis);//500ms
		//If fillAfter is true, the transformation that this animation performed will persist when it is finished. 
		//Defaults to false if not set. 
		//Note that this applies to individual animations and when using an AnimationSet to chain animations.
		rotateAnimation.setFillAfter(true);
		//设置变化速率--先慢后快
		rotateAnimation.setInterpolator(new AccelerateInterpolator());
		//Start the specified animation now
		rotateImageView.startAnimation(rotateAnimation);
	}

}
