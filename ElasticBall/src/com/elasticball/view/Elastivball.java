package com.elasticball.view;

import java.lang.ref.SoftReference;

import com.elasticball.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Elastivball extends View {
	private Paint paint;
	private float duringTime;
	private static final int SHADOW_REDIUS = 20;
	private static final int JUMPHEIGHT = 100;
	private static final int WIDTH = 100;
	private static final int HEIGHT = 300;
	private Bitmap bitmap;
	private SoftReference<Bitmap> bit;
	private long speed = 1500;
	public Elastivball(Context context) {
		super(context);
		init();
	}

	public Elastivball(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public Elastivball(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	protected void init(){
		paint = new Paint();
		paint.setColor(Color.BLACK);
		bitmap = BitmapFactory.decodeResource(getContext().getApplicationContext().getResources(), R.drawable.ic_launcher);
		bit = new SoftReference<Bitmap>(bitmap);
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		Log.e("duringTime ============ ", duringTime + "");
		canvas.drawBitmap(bit.get(), (WIDTH - bitmap.getWidth()) / 2 , (HEIGHT - bitmap.getHeight()) / 2 + (int)((duringTime - 1) * JUMPHEIGHT), paint);
//		canvas.drawCircle((WIDTH + REDIUS) / 2, (HEIGHT + REDIUS) / 2 + (int)((duringTime - 1) * JUMPHEIGHT), REDIUS, paint);
//		canvas.drawCircle((WIDTH + REDIUS) / 2, (HEIGHT + REDIUS) / 2 - JUMPHEIGHT, duringTime * REDIUS, paint);
		RectF shadow = new RectF();
		shadow.top = (HEIGHT + bitmap.getHeight()) / 2;
		shadow.bottom = (HEIGHT + bitmap.getHeight()) / 2 + (duringTime + 0.1f) * SHADOW_REDIUS;
		shadow.left = WIDTH / 2 - duringTime * SHADOW_REDIUS;
		shadow.right = WIDTH / 2 + duringTime * SHADOW_REDIUS;
		canvas.drawOval(shadow, paint);
	}
	
	class BallAnimation extends Animation{
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			duringTime = interpolatedTime;
			invalidate();
		}
	}
	
	
	//width and height
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(WIDTH, HEIGHT);
	}
	
	//position
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
	
	public void setSpeed(int speed){
		this.clearAnimation();
		this.speed = speed;
		startLoad();
	}
	
	protected void startLoad(){
		BallAnimation anim = new BallAnimation();
		anim.setDuration(speed);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		startAnimation(anim);
	}
	
	
	protected void stopLoad(){
		this.clearAnimation();
		postInvalidate();
	}
	
	
	//start animation when arrached to window
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startLoad();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		stopLoad();
		if(!bitmap.isRecycled()){
			bitmap.recycle();
		}
		bit.clear();
		super.onDetachedFromWindow();
	}
}
