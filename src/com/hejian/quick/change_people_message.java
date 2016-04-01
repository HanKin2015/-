package com.hejian.quick;

import com.hejian.quick.service.LoginService;
import com.hejian.quick.service.dataService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class change_people_message extends Activity{
	
	private EditText et1,et2,et3,et4;
	
	protected static final int LOCATION = 1;
	protected static final int EOEER = 2;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==LOCATION){
				String[] str=dataService.getSavePeo(change_people_message.this);
				if(str.length==4){
					et1.setText(str[0]);
					et2.setText(str[1]);
					et3.setText(str[2]);
					et4.setText(str[3]);
				}
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_people_message);
		
		et1=(EditText) findViewById(R.id.et1);
		et2=(EditText) findViewById(R.id.et2);
		et3=(EditText) findViewById(R.id.et3);
		et4=(EditText) findViewById(R.id.et4);
		
		new Thread(){
			public void run() {
					Message msg=new Message();
					msg.what=LOCATION;
					msg.obj=true;
					handler.sendMessage(msg);
			};
		}.start();
	}
	
	public void back(View view){
		finish();
	}
	
	public void ok(View view){
		String str=et1.getText().toString()+"##"+et2.getText().toString()+"##"
		+et3.getText().toString()+"##"+et4.getText().toString();
		boolean ret=dataService.savePeo(this, str);
		if(ret){
			Toast.makeText(this, "±£´æ³É¹¦£¡", 0).show();
		}
		back(view);
	}
}
