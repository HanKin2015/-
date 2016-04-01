package com.hejian.quick;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;

import com.hejian.quick.service.dataService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class main6 extends Activity{
	
	private static int camera_code=1;
	private static int file=2;
	private static int cropcode=3;
	private String ip=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main6);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
		
		ip=dataService.getSaveIp(this);
		
        Button bt1=(Button) findViewById(R.id.bt1);
        Button bt2=(Button) findViewById(R.id.bt2);
        
        bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, camera_code);
			}
		});
        
        bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(main6.this, "服务器未响应", 0).show();
			}
		});
    }

    private void startimagezoom(Uri uri){
    	Intent intent=new Intent("com.android.camera.action.CROP");
    	intent.setDataAndType(uri, "image/*");
    	intent.putExtra("crop", "true");
    	intent.putExtra("aspectX", 1);
    	intent.putExtra("aspectY", 1);
    	intent.putExtra("outputX", 150);
    	intent.putExtra("outputY", 150);
    	intent.putExtra("return-data", true);
    	startActivityForResult(intent, cropcode);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode==camera_code){
    		if(data==null){
    			return ;
    		}
    		else {
    			Bundle bundle=data.getExtras();
    			Bitmap bm=bundle.getParcelable("data");
    			ImageView imgImageView=(ImageView) findViewById(R.id.img);
    			imgImageView.setImageBitmap(bm);
    			sendImage(bm);
			}
    	}
    }
    
    private void sendImage(Bitmap bm)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("img", img);
        client.post("http://"+ip+"/ImgUpload.php", params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(main6.this, "上传失败!", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				Toast.makeText(main6.this, "上传成功!", Toast.LENGTH_LONG).show();
			}
        });
    }
}
