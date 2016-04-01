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

import com.google.zxing.WriterException;
import com.hejian.quick.service.dataService;
import com.hejian.quick.variable.variable;
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
import android.support.annotation.StringDef;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.widget.SwipeRefreshLayout;  

public class bt2 extends Fragment implements OnItemClickListener,SwipeRefreshLayout.OnRefreshListener  
{  
  
    private static final int REFRESH_COMPLETE = 0X110;  
    private SwipeRefreshLayout mSwipeLayout;  

	private ListView lv;
	private ArrayAdapter<String> aa;
	private List<Map<String,Object>> datalist=new ArrayList<Map<String,Object>>();
	private int cnt=0;
	private String[] arr_data=null;
	private String[] data={"A栋楼房3单元有任务!","明天开始放国庆长假!","请给我打一个电话!","做的很好!"};
	private String ip=dataService.getSaveIp(getActivity());
	private TextView refresh;
	
	private Handler mHandler = new Handler()  
    {  
        public void handleMessage(android.os.Message msg)  
        {  
        	getTask();
        	mSwipeLayout.setRefreshing(false); 
        	Toast.makeText(getActivity(), "刷新成功", 0).show();
        };  
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.bt2, container, false);
		if(ip==""||ip==null){
			ip=variable.getIp();
		}
		
		lv=(ListView) view.findViewById(R.id.listView);
		lv.setOnItemClickListener(this);
		
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);  
        mSwipeLayout.setOnRefreshListener(this);  
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,  
                android.R.color.holo_orange_light, android.R.color.holo_red_light); 
		
        if(variable.getSaveRefresh(getActivity())!=null){
        	getTask();
        }
        refresh=(TextView) view.findViewById(R.id.refresh);
        return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String text=lv.getItemAtPosition(arg2)+"";
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setTitle(text);
		builder.setMessage(data[arg2]);
		builder.setNegativeButton("确定", listener);
		builder.setPositiveButton("返回", listener);
		builder.create().show();
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
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
		//refresh.setVisibility(gone);
		boolean ret=variable.saveRefresh(getActivity());
		if(ret){
			//refresh.setVisibility(false);
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
						final String s0,s1,s2,s3;
						s0=json.getString("1");
						s1=json.getString("2");
						s2=json.getString("3");
						s3=json.getString("4");
						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								String[] strings={s0,s1,s2,s3};
								aa=new ArrayAdapter<String>(getActivity(),R.layout.list_item, strings);
				            	lv.setAdapter(aa);
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
