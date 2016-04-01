package com.hejian.quick;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class main8 extends Activity{
	
	private float startX;
	private ViewFlipper flipper;
	private int[] resId={R.drawable.worker1,R.drawable.worker2,
			R.drawable.worker3,R.drawable.worker4,
			R.drawable.worker5};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main8);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
		
		Button bt=(Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setAction(Intent.ACTION_CALL);
				String number="10086";
				intent.setData(Uri.parse("tel:"+number));
				startActivity(intent);
			}
		});
		
		flipper=(ViewFlipper) findViewById(R.id.flipper);
	        //以动态的方式添加子view
	    for(int i=0;i<resId.length;i++)
	       flipper.addView(getImage(resId[i]));
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	switch (event.getAction()) {
    		//手指落下
			case MotionEvent.ACTION_DOWN:
				startX=event.getX();
				break;
			//手指滑动
			case MotionEvent.ACTION_MOVE:
				//向右划
				if(event.getX()-startX>150){
					flipper.setInAnimation(this,R.anim.left_in);
			        flipper.setOutAnimation(this,R.anim.left_out);
			        flipper.showPrevious();//查看前一页
				}
				//向左滑动
				if(startX-event.getX()>150){
					flipper.setInAnimation(this,R.anim.right_in);
			        flipper.setOutAnimation(this,R.anim.right_out);
			        flipper.showNext();//查看后一页
				}
				break;
			//手指离开
			case MotionEvent.ACTION_UP:
				
				break;
		}
    	
    	return super.onTouchEvent(event);
    }
    
    private ImageView getImage(int resId) {
		// TODO Auto-generated method stub
    	ImageView image=new ImageView(this);
    	//image.setImageResource(resId);
    	image.setBackgroundResource(resId);
    	return image;
	}
}
