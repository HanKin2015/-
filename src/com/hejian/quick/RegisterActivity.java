package com.hejian.quick;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.hejian.quick.service.LoginService;
import com.hejian.quick.service.dataService;
import com.hejian.quick.variable.variable;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends SuperActivity implements OnClickListener{
	private EditText etName,etPwd,etEnsure;
	private ImageView ivName,ivPwd,ivEnsure;
	private String user,password,ensure;
	private TextView register;
	private String state;
	private String ip=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        
        ip=dataService.getSaveIp(this);
        if(ip==""||ip==null){
        	ip=variable.getIp();
        }
        findViewById(R.id.reg_window).setOnClickListener(this);
        
        ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
        
        etName=(EditText)findViewById(R.id.name_etr);
        ivName=(ImageView)findViewById(R.id.name_ivr);
        initForm(etName,ivName);
        etPwd=(EditText)findViewById(R.id.pwd_etr);
        ivPwd=(ImageView)findViewById(R.id.pwd_ivr);
        initForm(etPwd,ivPwd);
        etEnsure=(EditText)findViewById(R.id.ensure_et);
        ivEnsure=(ImageView)findViewById(R.id.ensure_iv);
        initForm(etEnsure,ivEnsure);
        
        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user=etName.getText().toString();
				if(TextUtils.isEmpty(user)) {
					Toast.makeText(RegisterActivity.this,"请输入用户名",0).show();
					return;
				}
				password=etPwd.getText().toString();
				if(TextUtils.isEmpty(password)) {
					Toast.makeText(RegisterActivity.this,"请输入密码",0).show();
					return;
				}
				ensure=etEnsure.getText().toString();
				if(TextUtils.isEmpty(ensure)) {
					Toast.makeText(RegisterActivity.this,"请再次确认密码",0).show();
					return;
				}
				if(!password.equals(ensure)) {
					Toast.makeText(RegisterActivity.this,"确认密码和输入密码不一致",0).show();
					return;
				}
				register();
				
				boolean ret=dataService.saveUser(RegisterActivity.this,user);
				if(ret){
					Log.w("test", "保存数据表成功！");
				}
				Log.w("test", "勾选成功！");
			}
        });
    }
    
    public void register() {
    	new Thread() {
    		public void run() {
    			HttpClient client=new DefaultHttpClient();
				HttpPost post=new HttpPost("http://"+ip+"/reg.php");
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
						String js=sb.toString();
						JSONObject json=new JSONObject(js);
						state=json.getString("state");
						runOnUiThread(new Runnable() {
							public void run() {
								if(state.equals("注册成功")){
									Toast.makeText(RegisterActivity.this,state,0).show();
									Intent intent =new Intent(RegisterActivity.this,filluserinfo.class);
									startActivity(intent);
									finish();
								}
								else if(state.equals("数据库连接失败")){
									Toast.makeText(RegisterActivity.this,state,0).show();
								}
								else{
									Toast.makeText(RegisterActivity.this,state,0).show();
								}
							}
						});
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {  
	    case R.id.reg_window:  
	         InputMethodManager imm = (InputMethodManager)  
	         getSystemService(Context.INPUT_METHOD_SERVICE);  
	         imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
	        break;  
	    }  
	}
}