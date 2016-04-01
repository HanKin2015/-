package com.hejian.quick;

import java.util.Calendar;

import com.hejian.quick.service.LoginService;
import com.hejian.quick.service.dataService;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class note extends Activity{
	
	private int year,month,day;
	private Calendar cal;
	private TextView date;
	private EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);
		
		cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH)+1;
        day=cal.get(Calendar.DAY_OF_MONTH);
        date=(TextView) findViewById(R.id.date);
        et=(EditText) findViewById(R.id.et);
        
        String str=dataService.getSaveNote(this);;
        if (str!=null) {
        	et.setText(str);
		}
	}
	
	public void save(View view){
		boolean ret=dataService.saveNote(this,et.getText().toString());
		if(ret){
			Toast.makeText(this, "保存成功！", 0).show();
		}
		back(view);
	}
	
	public void back(View view){
		finish();
	}
	
	public void change_date(View view){
		new DatePickerDialog(this, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				date.setText(arg1+"年"+(arg2+1)+"月"+arg3+"日");
			}
		}, year, cal.get(Calendar.MONTH), day).show();
	}
}
