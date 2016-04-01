package com.hejian.quick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxing.decoding.FinishListener;

import android.R.anim;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class bt5 extends Fragment{
	
	private ListView lv,lv2;
	private ArrayAdapter<String> aa,bb;
	private List<Map<String,Object>> datalist;
	private int cnt=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.bt5, container, false);
		
		lv=(ListView) view.findViewById(R.id.listView);
        datalist=new ArrayList<Map<String,Object>>();
        Context contextThis = getActivity();
        String[] arr_data={"������Ѽ����Ѳ��","��ջ���","�ֺŴ�С","��Ҫ����",
        		"��������"};
        aa=new ArrayAdapter<String>(contextThis,android.R.layout.simple_list_item_1, arr_data);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
					case 0:
						break;
					case 1:
						AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
						builder.setTitle("��ջ���");
						builder.setMessage("�Ƿ�ȷ������");
						
						builder.setNegativeButton("ȷ��", listener);
						builder.setPositiveButton("����", listener);
						builder.create().show();
						break;
			
					case 2:
					case 3:
						String text=lv.getItemAtPosition(arg2)+"";
						Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
						break;
					case 4:
						AlertDialog.Builder builder4=new AlertDialog.Builder(getActivity());
						builder4.setTitle("��������");
						builder4.setMessage("�Ƿ�ȷ�����룿");
						
						builder4.setNegativeButton("ȷ��", listener);
						builder4.setPositiveButton("����", listener);
						builder4.create().show();
						break;
				}
			}
        	
		});
        
        lv2=(ListView) view.findViewById(R.id.listView2);
        String[] brr_data={"�������","������","�������","���������","����","֧��һ��"};
        bb=new ArrayAdapter<String>(contextThis,android.R.layout.simple_list_item_1, brr_data);
        lv2.setAdapter(bb);
        lv2.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					Toast.makeText(getActivity(), "�����쳣", Toast.LENGTH_SHORT).show();
					break;
				
				case 1:
					Toast.makeText(getActivity(),"�Ѿ������°汾��", Toast.LENGTH_SHORT).show();
					break;
					
				case 2:
					Intent L6=new Intent(getActivity(),set6.class);
					startActivity(L6);
					break;
					
				case 3:
					Intent L8=new Intent(getActivity(),main8.class);
					startActivity(L8);
					break;
					
				case 4:
					Intent L9=new Intent(getActivity(),none.class);
					startActivity(L9);
					break;
					
				case 5:
					Toast.makeText(getActivity(),"��л���֧�֣����֧�����������Ķ�����", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		return view;
	}
	
    /** �����Ի��������button����¼� */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_NEGATIVE:// "ȷ��"��ť�˳�����
				break;
			case AlertDialog.BUTTON_POSITIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
				break;
			default:
				break;
			}
		}
	};
}
