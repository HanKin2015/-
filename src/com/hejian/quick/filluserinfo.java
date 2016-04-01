package com.hejian.quick;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.hejian.quick.service.dataService;
import com.hejian.quick.variable.variable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class filluserinfo extends Activity{
	
	private String ip=dataService.getSaveIp(this); 
	
	private String name,sex,job,age;
	private EditText et1,et2,et3,et4;
	private String user=dataService.getSaveUser(this),state;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filluserinfo);
		
		if(ip==""||ip==null){
			ip=variable.getIp();
		}
		
		et1=(EditText) findViewById(R.id.et11);
		et2=(EditText) findViewById(R.id.et22);
		et3=(EditText) findViewById(R.id.et33);
		et4=(EditText) findViewById(R.id.et44);
	}
	
	public void ok(View view){
		name=et1.getText().toString();
		sex=et2.getText().toString();
		age=et3.getText().toString();
		job=et4.getText().toString();
		database();
	}
	
	public void database() {
    	new Thread() {		//在子线程里进行数据操作
    		public void run() {
		    	HttpClient client=new DefaultHttpClient();
		    	HttpPost post=new HttpPost("http://"+ip+"/filluserinfo.php");
		    	List<NameValuePair> params=new ArrayList<NameValuePair>();
		    	params.add(new BasicNameValuePair("user",user));
		    	params.add(new BasicNameValuePair("name",name));
		    	params.add(new BasicNameValuePair("sex",sex));
		    	params.add(new BasicNameValuePair("age",age));
		    	params.add(new BasicNameValuePair("job",job));
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
								if(state.equals("成功")){
									String str=name+"##"+sex+"##"+age+"##"+job;
									boolean ret=dataService.savePeo(filluserinfo.this, str);
									if(ret){
										Log.w("t", "sucess");
									}
									Toast.makeText(filluserinfo.this,"欢迎进入",0).show();
									startActivity(new Intent(filluserinfo.this,bottomActivity.class));
									finish();
								}
								else if(state.equals("数据库连接失败")){
									Toast.makeText(filluserinfo.this,state,0).show();
								}
								else {
									Toast.makeText(filluserinfo.this,state,0).show();
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
}
