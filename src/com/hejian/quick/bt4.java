package com.hejian.quick;

import com.hejian.quick.service.dataService;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class bt4 extends Fragment{
	
	private TextView tv;
	private EditText editText1,editText2;
	private LocationManager mLocationManager=null;
	private LocationListener mLocationListener=null;
	private String mProviderName=null;
	private TextView lat,lon,alt,acc,tim,spe,ber;
	
	protected static final int LOCATION = 1;
	protected static final int EOEER = 2;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==LOCATION){
					//action();
			}
			else if (msg.what==EOEER) {
				Toast.makeText(getActivity(), "获取失败", 0).show();
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.bt4, container, false);
		tv=(TextView) view.findViewById(R.id.tv);
		editText1=(EditText) view.findViewById(R.id.ip);
		editText2=(EditText) view.findViewById(R.id.duan);
		String str=dataService.getSaveIp(getActivity());
		if(str!=null){
			editText1.setText(str);
			tv.setText("http://"+editText1.getText().toString()+":"+editText2.getText().toString());
		}
		
		lat=(TextView) view.findViewById(R.id.lat);
		lon=(TextView) view.findViewById(R.id.lon);
		acc=(TextView) view.findViewById(R.id.acc);
		tim=(TextView) view.findViewById(R.id.tim);
		spe=(TextView) view.findViewById(R.id.spe);
		ber=(TextView) view.findViewById(R.id.ber);
		alt=(TextView) view.findViewById(R.id.alt);
		final Button button=(Button) view.findViewById(R.id.button);
		/*button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg=new Message();
				msg.what=LOCATION;
				msg.obj=true;
				handler.sendMessage(msg);
			}
		});*/
		
		mLocationManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
					Toast.makeText(getActivity(), "no gps", 0).show();
				}
			}
		};
		new Thread(){
			public void run() {
					Message msg=new Message();
					msg.what=LOCATION;
					msg.obj=true;
					handler.sendMessage(msg);
			};
		}.start();
		
		view.findViewById(R.id.sure).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tv.setText("http://"+editText1.getText().toString()+":"+editText2.getText().toString());
				boolean ret=dataService.saveIp(getActivity(),editText1.getText().toString());
				if(ret){
					Toast.makeText(getActivity(), "保存成功！", 0).show();
				}
			}
		});
		
		view.findViewById(R.id.start_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LayoutInflater inflater=LayoutInflater.from(getActivity());
				final View loc=inflater.inflate(R.layout.location, null);
				AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
				builder.setTitle("GPS定位信息");
				builder.setView(loc);
				
				//action();
				builder.setNegativeButton("确定", listener);
				builder.setPositiveButton("返回", listener);
				builder.create().show();
			}
		});
		
		return view;
	}
	
	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
				break;
			case AlertDialog.BUTTON_POSITIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};
	
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
}
