package com.hejian.quick;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class main3 extends Activity{
	
	private static final int MSG_DATA_CHANGE = 0x11;
	private LineView mLineView;
	private Handler mHandler;
	private int mX = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
		
		mLineView = (LineView) this.findViewById(R.id.line);
		
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case MSG_DATA_CHANGE:
					mLineView.setLinePoint(msg.arg1, msg.arg2);
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		
		new Thread(){
			public void run() {
				for (int index=0; index<20; index++)
				{
					Message message = new Message();
					message.what = MSG_DATA_CHANGE;
					message.arg1 = mX;
					message.arg2 = (int)(Math.random()*200);;
					mHandler.sendMessage(message);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mX += 30;
				}
			};
		}.start();
	}
}

