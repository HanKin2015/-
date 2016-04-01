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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class test extends Activity{
	
	private TextView lat,lon,alt,acc,tim,spe,ber;
	private LocationManager mLocationManager=null;
	private LocationListener mLocationListener=null;
	private String mProviderName=null;
	private TextView php;
	private String ip=null,content="hj",state;
	
	private String[] arr_data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.location);
		setContentView(R.layout.location);
		
		ip=dataService.getSaveIp(this);
		php=(TextView) findViewById(R.id.php);
		
		lat=(TextView) findViewById(R.id.lat);
		lon=(TextView) findViewById(R.id.lon);
		acc=(TextView) findViewById(R.id.acc);
		tim=(TextView) findViewById(R.id.tim);
		spe=(TextView) findViewById(R.id.spe);
		ber=(TextView) findViewById(R.id.ber);
		alt=(TextView) findViewById(R.id.alt);
		
		mLocationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationListener=new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location arg0) {
				// TODO Auto-generated method stub
				if(arg0!=null){
					lat.setText((int) arg0.getLatitude());
					lon.setText((int) arg0.getLongitude());
					alt.setText((int) arg0.getAltitude());
					acc.setText((int) arg0.getAccuracy());
					spe.setText((int) arg0.getSpeed());
					tim.setText((int) arg0.getTime());
					ber.setText((int) arg0.getBearing());
				}else {
					Toast.makeText(test.this, "no gps", 0).show();
				}
			}
		};
		
		Button bt=(Button) findViewById(R.id.button);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				action();
				Toast.makeText(test.this, "ding wei", 0).show();
			}
		});
//		findViewById(R.id.button).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				//action();
//			}
//		});
	}
	
	public void get(View view){
		action();
		Toast.makeText(this, "获取中", 0).show();
	}
	
	public void data(View view){
		getUserInfoFromDb();
		Toast.makeText(this, "获取中", 0).show();
	}
	
	public void get_text() {
    	new Thread() {		//在子线程里进行数据操作
    		public void run() {
		    	HttpClient client=new DefaultHttpClient();
		    	HttpPost post=new HttpPost("http://"+ip+"/task.php");
		    	List<NameValuePair> params=new ArrayList<NameValuePair>();
		    	params.add(new BasicNameValuePair("content",content));
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
						state=json.getString("content");
						runOnUiThread(new Runnable() {
							public void run() {
								if(TextUtils.isEmpty(state)){
									Toast.makeText(test.this,state,0).show();
								}
								else {
									php.setText(state);
									Toast.makeText(test.this,state,0).show();
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
	
	public void action(){
		Location lastKnowLocation=null;
		lastKnowLocation=mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		mProviderName=LocationManager.GPS_PROVIDER;
		if(!TextUtils.isEmpty(mProviderName)){
			mLocationManager.requestLocationUpdates(mProviderName, 1000, 1, mLocationListener);
		}
		if(lastKnowLocation!=null){
			lat.setText((int) lastKnowLocation.getLatitude());
			lon.setText((int) lastKnowLocation.getLongitude());
			alt.setText((int) lastKnowLocation.getAltitude());
			acc.setText((int) lastKnowLocation.getAccuracy());
			spe.setText((int) lastKnowLocation.getSpeed());
			tim.setText((int) lastKnowLocation.getTime());
			ber.setText((int) lastKnowLocation.getBearing());
		}
	}
	
	public void getTask() {
    	new Thread() {		//在子线程里进行数据操作
    		public void run() {
		    	HttpClient client=new DefaultHttpClient();
		    	HttpPost post=new HttpPost("http://"+ip+"/task.php");
		    	List<NameValuePair> params=new ArrayList<NameValuePair>();
		    	String signal="任务";
		    	params.add(new BasicNameValuePair("signal",signal));
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
						arr_data[0]=json.getString("1");
						arr_data[1]=json.getString("2");
						arr_data[2]=json.getString("3");
						arr_data[3]=json.getString("4");
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(test.this, "suexx", 0).show();
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}

    	}.start();
	}
	
	public void getUserInfoFromDb() {
    	new Thread() {		//在子线程里进行数据操作
    		public void run() {
		    	HttpClient client=new DefaultHttpClient();
		    	HttpPost post=new HttpPost("http://"+ip+"/task.php");
		    	List<NameValuePair> params=new ArrayList<NameValuePair>();
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
						
						final String string=json.getString("1");
						Toast.makeText(test.this, string, 0).show();
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(test.this, string, 0).show();
								if(state.equals("提取成功")){
									
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
