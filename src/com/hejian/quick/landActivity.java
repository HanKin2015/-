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
	
		private Button bt_land; 		//��½��ť
		private TextView new_user,forget;		//���������ע�����û�
		
		private EditText etName,etPwd;
		private ImageView ivName,ivPwd;			//ȡ������ͼƬ��ť
		private String user,password;
		private String state;
		private CheckBox cb;		//��ס����
		private String ip=null;
		
		protected static final int CHANGE = 1;
		protected static final int EOEER = 2;
		private Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==CHANGE){
						cb.setChecked(true);
				}
				else if (msg.what==EOEER) {
					Toast.makeText(landActivity.this, "��ȡʧ��", 0).show();
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
	        
	        //���������������ʾ
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
			
			//ip��ַ
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
						Toast.makeText(landActivity.this,"�������û���",0).show();
						return;
					}
					password=etPwd.getText().toString().trim();
					if(TextUtils.isEmpty(password)) {
						Toast.makeText(landActivity.this,"����������",0).show();
						return;
					}else {
						getUserInfoFromDb();
					}
					
					//��¼���ȶԻ���
					final ProgressDialog loginprogress=new ProgressDialog(landActivity.this);
					loginprogress.setTitle("���Ժ�");
					loginprogress.setMessage("���ڵ�¼������");
					
					login();
					if(cb.isChecked()){
						boolean ret=LoginService.saveUserInfo(landActivity.this,user,password);
						if(ret){
							Log.w("test", "��������ɹ���");
						}
						Log.w("test", "��ѡ�ɹ���");
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
	    	new Thread() {		//�����߳���������ݲ���
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
									if(state.equals("��¼�ɹ�")){
										Toast.makeText(landActivity.this,state,0).show();
										startActivity(new Intent(landActivity.this,bottomActivity.class));
										finish();
									}
									else if(state.equals("���ݿ�����ʧ��")){
										Toast.makeText(landActivity.this,"������δ��Ӧ",0).show();
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
    			// �����˳��Ի���
    			AlertDialog isExit = new AlertDialog.Builder(this).create();
    			// ���öԻ������
    			isExit.setTitle("ϵͳ��ʾ");
    			// ���öԻ�����Ϣ
    			isExit.setMessage("ȷ��Ҫ�˳���?");
    			// ���ѡ��ť��ע�����
    			isExit.setButton("ȡ��", listener);
    			isExit.setButton2("ȷ��", listener);
    			// ��ʾ�Ի���
    			isExit.show();

    		}
    		
    		return false;
    		
    	}
    	/**�����Ի��������button����¼�*/
    	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    	{
    		public void onClick(DialogInterface dialog, int which)
    		{
    			switch (which)
    			{
    			case AlertDialog.BUTTON_NEGATIVE:// "ȷ��"��ť�˳�����
    				finish();
    				break;
    			case AlertDialog.BUTTON_POSITIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
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
	    	new Thread() {		//�����߳���������ݲ���
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
									if(state.equals("��ȡ�ɹ�")){
										String str=name+"##"+sex+"##"+age+"##"+job;
										boolean ret=dataService.savePeo(landActivity.this, str);
										if(ret){
											Log.w("t", "sucess");
										}
									}
									else if(state.equals("���ݿ�����ʧ��")){
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

