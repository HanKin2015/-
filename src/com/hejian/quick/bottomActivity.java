package com.hejian.quick;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class bottomActivity extends Activity implements
		OnCheckedChangeListener, OnItemClickListener {

	private RadioGroup rg;
	private final static int I0 = Menu.FIRST;
	private final static int I1 = Menu.FIRST + 1;
	private final static int I2 = Menu.FIRST + 2;
	private final static int I3 = Menu.FIRST + 3;
	private final static int I4 = Menu.FIRST + 4;
	private DrawerLayout drawerLayout;
	private ListView listView;

	private SimpleAdapter sa;
	private List<Map<String, Object>> datalist;

	private String string;
	private ActionBarDrawerToggle aToggle;

	private int[] icon = { R.drawable.tou,R.drawable.null_w,R.drawable.icon1, R.drawable.icon2,
			R.drawable.icon3, R.drawable.icon4, R.drawable.icon5,
			R.drawable.icon6 };
	private String[] strings=null;
	
	private String[] iconName = {"测试\n测试员" ,"","首页", "个人资料", "我的任务", "个性装扮", "我的笔记", "我的文件",
			"注销用户" };
	
	private String user=dataService.getSaveUser(this);
	private String ip=dataService.getSaveIp(this);
	
	//切换任务
	private RadioButton rb1,rb2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom);
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		if(ip==""||ip==null){
			ip=variable.getIp();
		}
		
		strings=dataService.getSavePeo(bottomActivity.this);
		
		/*strings=dataService.getSavePeo(bottomActivity.this);
		if(strings.length!=4){
			strings[0]="测试";
			strings[1]="";
			strings[2]="";
			strings[3]="测试员";
			iconName[0]=strings[0]+"\n"+strings[3];
		}
		else {
			iconName[0]=strings[0]+"\n"+strings[3];
		}*/
		
		rb1=(RadioButton) findViewById(R.id.rb1);
		rb2=(RadioButton) findViewById(R.id.rb2);

		string = (String) getTitle();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		listView = (ListView) findViewById(R.id.list);

		datalist = new ArrayList<Map<String, Object>>();
		sa = new SimpleAdapter(this, getdata(), R.layout.item, new String[] {
				"pic", "num" }, new int[] { R.id.pic, R.id.num });
		listView.setAdapter(sa);

		/*
		 * arrayList = new ArrayList<String>(); arrayList.add("首页");
		 * arrayList.add("个人资料"); arrayList.add("我的任务"); arrayList.add("个性装扮");
		 * arrayList.add("我的笔记"); arrayList.add("我的文件"); arrayList.add("注销用户");
		 * adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, arrayList);
		 * listView.setAdapter(adapter);
		 */
		listView.setOnItemClickListener(this);

		bt1 mf = new bt1();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction bt = fm.beginTransaction();
		bt.replace(R.id.frame, mf);
		bt.commit();

		aToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.search, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("个人资料");
				invalidateOptionsMenu();
			};

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(string);
				invalidateOptionsMenu();
			}
		};

		drawerLayout.setDrawerListener(aToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
	}

	private List<Map<String, Object>> getdata() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 8; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", icon[i]);
			map.put("num", iconName[i]);
			datalist.add(map);
		}
		return datalist;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		setIconEnable(menu, true);
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, I0, 0, "注销").setIcon(android.R.drawable.ic_menu_delete);
		menu.add(0, I1, 1, "帮助").setIcon(android.R.drawable.ic_menu_help);
		menu.add(0, I2, 2, "关于").setIcon(android.R.drawable.ic_menu_directions);
		menu.add(0, I3, 3, "退出").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		MenuItem helpItem = menu.add(0, I4, 4, "取消").setIcon(
				android.R.drawable.ic_menu_revert);
		return true;
	}
	
	//子菜单操作
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (aToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.search:
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://www.baidu.com");
			intent.setData(uri);
			startActivity(intent);
			break;

		case I0:
			// 创建退出对话框
			AlertDialog isExit2 = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit2.setTitle("系统提示");
			// 设置对话框消息
			isExit2.setMessage("确定要注销吗?");
			// 添加选择按钮并注册监听
			isExit2.setButton("取消", listener2);
			isExit2.setButton2("确定", listener2);
			// 显示对话框
			isExit2.show();
			break;
		case I1:
			Intent none =new Intent(bottomActivity.this,none.class);
			startActivity(none);
			break;
		case I2:
			Intent L6=new Intent(bottomActivity.this,set6.class);
			startActivity(L6);
			break;
		case I3:
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要退出吗?");
			// 添加选择按钮并注册监听
			isExit.setButton("取消", listener);
			isExit.setButton2("确定", listener);
			// 显示对话框
			isExit.show();
			break;
		case I4:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
				Intent cansel =new Intent(bottomActivity.this,landActivity.class);
				startActivity(cansel);
				finish();
				break;
			case AlertDialog.BUTTON_POSITIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		boolean flag = drawerLayout.isDrawerOpen(listView);
		menu.findItem(R.id.search).setVisible(!flag);
		return super.onPrepareOptionsMenu(menu);
	}

	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);
			// 下面传入参数
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case R.id.rb1: {
			bt1 mf = new bt1();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction bt = fm.beginTransaction();
			bt.replace(R.id.frame, mf);
			bt.commit();
			break;
		}
		case R.id.rb2: {
			bt2 mf = new bt2();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction bt = fm.beginTransaction();
			bt.replace(R.id.frame, mf);
			bt.commit();
			break;
		}
		case R.id.rb3: {
			bt3 mf = new bt3();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction bt = fm.beginTransaction();
			bt.replace(R.id.frame, mf);
			bt.commit();
			break;
		}
		case R.id.rb4: {
			bt4 mf = new bt4();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction bt = fm.beginTransaction();
			bt.replace(R.id.frame, mf);
			bt.commit();
			break;
		}
		case R.id.rb5: {
			bt5 mf = new bt5();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction bt = fm.beginTransaction();
			bt.replace(R.id.frame, mf);
			bt.commit();
			break;
		}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要退出吗?");
			// 添加选择按钮并注册监听
			isExit.setButton("取消", listener);
			isExit.setButton2("确定", listener);
			// 显示对话框
			isExit.show();
		}
		return false;
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_POSITIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		/*
		 * Fragment content=new fragment(); Bundle bundle=new Bundle();
		 * bundle.putString("text", arrayList.get(arg2));
		 * content.setArguments(bundle); FragmentManager
		 * fa=getFragmentManager(); fa.beginTransaction().replace(R.id.frame,
		 * content).commit(); drawerLayout.closeDrawer(listView);
		 */
		switch (arg2) {
		case 0:
			//Intent I0=new Intent(bottomActivity.this,people_message.class);
			//startActivity(I0);
			//drawerLayout.closeDrawers();
			break;

		case 2:
			drawerLayout.closeDrawers();
			break;
			
		case 3:			//个人资料
			Intent I2=new Intent(bottomActivity.this,people_message.class);
			startActivity(I2);
			drawerLayout.closeDrawers();
			break;
			
		case 4:			//我的任务
			drawerLayout.closeDrawers();
			rb1.setChecked(false);
			rb2.setChecked(true);
			bt2 mf = new bt2();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction bt = fm.beginTransaction();
			bt.replace(R.id.frame, mf);
			bt.commit();
			break;
		
		case 5:
			Intent I5=new Intent(bottomActivity.this,none.class);
			startActivity(I5);
			drawerLayout.closeDrawers();
			break;
			
		case 6:			//我的笔记
			Intent I6=new Intent(bottomActivity.this,note.class);
			startActivity(I6);
			drawerLayout.closeDrawers();
			break;	
		
		case 7:
			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.setType("image/*");
			startActivityForResult(i, RESULT_OK);
			drawerLayout.closeDrawers();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 11)//自定义的一个static final int常量
		{
		}
		if(resultCode == RESULT_OK)
		{
			//得到文件的Uri
			Uri uri = data.getData();
			ContentResolver resolver = getContentResolver();
			//ContentResolver对象的getType方法可返回形如content://的Uri的类型
			//如果是一张图片，返回结果为image/jpeg或image/png等
			String fileType = resolver.getType(uri);
			if(fileType.startsWith("image"))//判断用户选择的是否为图片
			{
				//根据返回的uri获取图片路径
				Cursor cursor=resolver.query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
				cursor.moveToFirst();
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
		}
	}
}
