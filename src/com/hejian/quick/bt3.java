package com.hejian.quick;

import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class bt3 extends Fragment{
	
	private Button scan,btt1,btt2,btt3;
	private TextView message;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.bt3, container, false);
		
		message=(TextView) view.findViewById(R.id.message);
		
		scan=(Button) view.findViewById(R.id.scan);
		scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "你可以开始扫描二维码", Toast.LENGTH_SHORT).show();
				Intent startscan=new Intent(getActivity(), CaptureActivity.class);
				//startActivity(startscan);
				startActivityForResult(startscan, 0);
			}
		});
		
		btt1=(Button) view.findViewById(R.id.btt1);
		btt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent become=new Intent(getActivity(), become_bary.class);
				startActivity(become);
			}
		});
		btt2=(Button) view.findViewById(R.id.btt2);
		btt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent become=new Intent(getActivity(), main3.class);
				startActivity(become);
			}
		});
		btt3=(Button) view.findViewById(R.id.btt3);
		btt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent become=new Intent(getActivity(), main8.class);
				startActivity(become);
			}
		});
		
		message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
				builder.setTitle("设备具体信息");
				builder.setMessage(message.getText().toString());
				
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==getActivity().RESULT_OK){
			String result=data.getExtras().getString("result");
			message.setText(result);
		}
	}
}
