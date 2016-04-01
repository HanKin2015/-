package com.hejian.quick;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class people_message extends Activity{
	
	private TextView tv1,tv2,tv3,tv4;
	private String[] str;
	
	protected static final int LOCATION = 1;
	protected static final int EOEER = 2;
	private String ip=dataService.getSaveIp(this);
	private String user=dataService.getSaveUser(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.people_message);
		
		tv1=(TextView) findViewById(R.id.tv1);
		tv2=(TextView) findViewById(R.id.tv2);
		tv3=(TextView) findViewById(R.id.tv3);
		tv4=(TextView) findViewById(R.id.tv4);
		if(ip==""||ip==null){
			ip=variable.getIp();
		}
		getUserInfoFromDb();
	}

	public void change(View view){
		Intent intent=new Intent(this,change_people_message.class);
		startActivity(intent);
	}
	
	public void back(View view){
		finish();
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
									tv1.setText(name);
									tv2.setText(sex);
									tv3.setText(age);
									tv4.setText(job);
								}
								else if(state.equals("数据库连接失败")){
									Toast.makeText(people_message.this,state,0).show();
								}
								else {
									Toast.makeText(people_message.this,state,0).show();
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
