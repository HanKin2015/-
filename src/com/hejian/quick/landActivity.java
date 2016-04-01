package com.hejian.quick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.hejian.quick.service.LoginService;
import com.hejian.quick.service.dataService;
import com.hejian.quick.variable.variable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class landActivity extends SuperActivity implements OnClickListener{
	
		private Button bt_land; 		//登陆按钮
		private TextView new_user,forget;		//忘记密码和注册新用户
		
		private EditText etName,etPwd;
		private ImageView ivName,ivPwd;			//取消输入图片按钮
		private String user,password;
		private String state;
		private CheckBox cb;		//记住密码
		private String ip=null;
		
		protected static final int CHANGE = 1;
		protected static final int EOEER = 2;
		private Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==CHANGE){
						cb.setChecked(true);
				}
				else if (msg.what==EOEER) {
					Toast.makeText(landActivity.this, "获取失败", 0).show();
				}
			};
		};
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.land);
			
			findViewById(R.id.land_window).setOnClickListener(this); 
			
			etName=(EditText)findViewById(R.id.name_et);
	        ivName=(ImageView)findViewById(R.id.name_iv);
	        initForm(etName,ivName);
	        etPwd=(EditText)findViewById(R.id.pwd_et);
	        ivPwd=(ImageView)findViewById(R.id.pwd_iv);
	        initForm(etPwd,ivPwd);
	        
	        //保存的密码重新显示
	        Map<String, String> map=LoginService.getSaveUserInfo(this);
			if(map!=null){
				etName.setText(map.get("username"));
				etPwd.setText(map.get("password"));
				new Thread(){
					public void run() {
							Message msg=new Message();
							msg.what=CHANGE;
							msg.obj=true;
							handler.sendMessage(msg);
					};
				}.start();
			}
			
			//ip地址
			ip=dataService.getSaveIp(this);
			if(ip==null||ip==""){
				ip=variable.getIp();
			}
	        
	        bt_land=(Button) findViewById(R.id.bt_land);
	        cb=(CheckBox) findViewById(R.id.checkBox);
	        bt_land.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					user=etName.getText().toString().trim();
					if(TextUtils.isEmpty(user)) {
						Toast.makeText(landActivity.this,"请输入用户名",0).show();
						return;
					}
					password=etPwd.getText().toString().trim();
					if(TextUtils.isEmpty(password)) {
						Toast.makeText(landActivity.this,"请输入密码",0).show();
						return;
					}else {
						getUserInfoFromDb();
					}
					
					//登录进度对话框
					final ProgressDialog loginprogress=new ProgressDialog(landActivity.this);
					loginprogress.setTitle("请稍候");
					loginprogress.setMessage("正在登录。。。");
					
					login();
					if(cb.isChecked()){
						boolean ret=LoginService.saveUserInfo(landActivity.this,user,password);
						if(ret){
							Log.w("test", "保存密码成功！");
						}
						Log.w("test", "勾选成功！");
					}
				}
	        });
			
			new_user=(TextView) findViewById(R.id.new_user);
			new_user.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(landActivity.this,RegisterActivity.class);
					startActivity(intent);
					finish();
				}
			});
			
			/*forget=(TextView) findViewById(R.id.forget_pw);
			forget.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(landActivity.this,bottomActivity.class);
					startActivity(intent);
					finish();
				}
			});*/
		}
		
		public void login() {
	    	new Thread() {		//在子线程里进行数据操作
	    		public void run() {
			    	HttpClient client=new DefaultHttpClient();
			    	HttpPost post=new HttpPost("http://"+ip+"/log.php");
			    	List<NameValuePair> params=new ArrayList<NameValuePair>();
			    	params.add(new BasicNameValuePair("user",user));
			    	params.add(new BasicNameValuePair("password",password));
			    	try {
						HttpEntity entity=new UrlEncodedFormEntity(params,"utf-8");
						post.setEntity(entity);
						HttpResponse response=client.execute(post);
						if(response.getStatusLine().getStatusCode()==200) {
							StringBuilder sb=new StringBuilder();
							BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
							String s=br.readLine();
							for(;s!=null;s=br.readLine()) {
								sb.append(s);
							}
							JSONObject json=new JSONObject(sb.toString());
							state=json.getString("state");
							runOnUiThread(new Runnable() {
								public void run() {
									if(state.equals("登录成功")){
										Toast.makeText(landActivity.this,state,0).show();
										startActivity(new Intent(landActivity.this,bottomActivity.class));
										finish();
									}
									else if(state.equals("数据库连接失败")){
										Toast.makeText(landActivity.this,"服务器未响应",0).show();
									}
									else {
										Toast.makeText(landActivity.this,state,0).show();
									}
								}
							});
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    	}.start();
	    }
		
		@Override
    	public boolean onKeyDown(int keyCode, KeyEvent event)
    	{
    		if (keyCode == KeyEvent.KEYCODE_BACK )
    		{
    			// 创建退出对话框
    			AlertDialog isExit = new AlertDialog.Builder(this).create();
    			// 设置对话框标题
    			isExit.setTitle("系统提示");
    			// 设置对话框消息
    			isExit.setMessage("确定要退出吗?");
    			// 添加选择按钮并注册监听
    			isExit.setButton("取消", listener);
    			isExit.setButton2("确定", listener);
    			// 显示对话框
    			isExit.show();

    		}
    		
    		return false;
    		
    	}
    	/**监听对话框里面的button点击事件*/
    	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    	{
    		public void onClick(DialogInterface dialog, int which)
    		{
    			switch (which)
    			{
    			case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
    				finish();
    				break;
    			case AlertDialog.BUTTON_POSITIVE:// "取消"第二个按钮取消对话框
    				break;
    			default:
    				break;
    			}
    		}
    	};

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {  
		    case R.id.land_window:  
		         InputMethodManager imm = (InputMethodManager)  
		         getSystemService(Context.INPUT_METHOD_SERVICE);  
		         imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
		        break;  
		    }  
		}
	
		public void getUserInfoFromDb() {
	    	new Thread() {		//在子线程里进行数据操作
	    		public void run() {
			    	HttpClient client=new DefaultHttpClient();
			    	HttpPost post=new HttpPost("http://"+ip+"/userinfo.php");
			    	List<NameValuePair> params=new ArrayList<NameValuePair>();
			    	params.add(new BasicNameValuePair("user",user));
			    	try {
						HttpEntity entity=new UrlEncodedFormEntity(params,"utf-8");
						post.setEntity(entity);
						HttpResponse response=client.execute(post);
						if(response.getStatusLine().getStatusCode()==200) {
							StringBuilder sb=new StringBuilder();
							BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
							String s=br.readLine();
							for(;s!=null;s=br.readLine()) {
								sb.append(s);
							}
							JSONObject json=new JSONObject(sb.toString());
							final String state=json.getString("state");
							final String name=json.getString("name");
							final String sex=json.getString("sex");
							final String age=json.getString("age");
							final String job=json.getString("job");
							runOnUiThread(new Runnable() {
								public void run() {
									if(state.equals("提取成功")){
										String str=name+"##"+sex+"##"+age+"##"+job;
										boolean ret=dataService.savePeo(landActivity.this, str);
										if(ret){
											Log.w("t", "sucess");
										}
									}
									else if(state.equals("数据库连接失败")){
										Toast.makeText(landActivity.this,state,0).show();
									}
									else {
										Toast.makeText(landActivity.this,state,0).show();
									}
								}
							});
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		}
	    	}.start();
	    }	
}

