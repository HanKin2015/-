package com.hejian.quick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class phone_number extends Activity implements OnItemClickListener{
	
	private ListView listView;
	private SimpleAdapter sa;
	private TextView num,peo;
	private List<Map<String, Object>> datalist;
	private String[] name={"����","����","������","С��","չ��","����","�ʵ�","�ʺ�","�ƶ��ͷ�","����"},
			number={"13358747","124358747","1232347","1322747","12338747","32358747","12338747","32358747"
			,"10086","18712779076"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_number);
		
		listView=(ListView) findViewById(R.id.list);
		datalist = new ArrayList<Map<String, Object>>();
		sa = new SimpleAdapter(this, getdata(), R.layout.item2, new String[] {
				"pic", "num" }, new int[] { R.id.pic, R.id.num });
		listView.setAdapter(sa);
		listView.setOnItemClickListener(this);
		
		num=(TextView) findViewById(R.id.number);
		peo=(TextView) findViewById(R.id.peo);
		
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
		listView.setOnCreateContextMenuListener(this);  
	}

	private List<Map<String, Object>> getdata() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
    		Map<String,Object> map=new HashMap<String, Object>();
    		map.put("pic",R.drawable.preple);
    		map.put("num","  "+name[i]+"           "+number[i]);
    		datalist.add(map);
    	}
    	return datalist;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		final String pnumber=number[arg2];
		//peo.setText(name[arg2]);
		//num.setText(pnumber);
		//LayoutInflater inflater=LayoutInflater.from(this);
		//final View phoneNumber=inflater.inflate(R.layout.phone_dialog, null);
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle(name[arg2]);
		builder.setIcon(R.drawable.preple);
		builder.setMessage(pnumber);
		//builder.setView(phoneNumber);
		builder.setPositiveButton("������", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setNegativeButton("��绰", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+pnumber));
				startActivity(intent);
			}
		});
		
		builder.create().show();
	}
	
	/** �����Ի��������button����¼� */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.setHeaderTitle("��ϵ������");
		menu.add(Menu.NONE,Menu.FIRST,Menu.NONE,"�޸���Ϣ");
		menu.add(Menu.NONE,Menu.FIRST+1,Menu.NONE,"���Ϊ��Ҫ");
		menu.add(Menu.NONE,Menu.FIRST+2,Menu.NONE,"�����ϵ��");
		menu.add(Menu.NONE,Menu.FIRST+3,Menu.NONE,"ɾ����ϵ��");
		menu.add(Menu.NONE,Menu.FIRST+4,Menu.NONE,"���Ͷ���");
		menu.add(Menu.NONE,Menu.FIRST+5,Menu.NONE,"����");
		menu.add(Menu.NONE,Menu.FIRST+6,Menu.NONE,"ȡ��");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.search:
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://www.baidu.com");
			intent.setData(uri);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
