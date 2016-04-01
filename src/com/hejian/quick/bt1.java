package com.hejian.quick;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class bt1 extends Fragment{
	
	private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.bt1, container, false);
		ib1=(ImageButton) view.findViewById(R.id.ib1);
		ib2=(ImageButton) view.findViewById(R.id.ib2);
		ib3=(ImageButton) view.findViewById(R.id.ib3);
		ib4=(ImageButton) view.findViewById(R.id.ib4);
		ib5=(ImageButton) view.findViewById(R.id.ib5);
		ib6=(ImageButton) view.findViewById(R.id.ib6);
		ib8=(ImageButton) view.findViewById(R.id.ib8);
		ib7=(ImageButton) view.findViewById(R.id.ib7);
		ib7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), phone_number.class);
				startActivity(intent);
			}
		});
		ib1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main1.class);
				startActivity(intent);
			}
		});
		ib2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main2.class);
				startActivity(intent);
			}
		});
		ib3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main3.class);
				startActivity(intent);
			}
		});
		ib4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main4.class);
				startActivity(intent);
			}
		});
		ib5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main5.class);
				startActivity(intent);
			}
		});
		ib6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main6.class);
				startActivity(intent);
			}
		});
		ib8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),main8.class);
				startActivity(intent);
			}
		});
		return view;
	}
}
