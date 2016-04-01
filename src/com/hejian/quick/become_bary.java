package com.hejian.quick;

import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class become_bary extends Activity{
	
	private EditText et;
	private ImageView ig;
	private Button become;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.become_bary);
		
		et=(EditText) findViewById(R.id.et);
        become=(Button) findViewById(R.id.become);
        ig=(ImageView) findViewById(R.id.ig);
		become.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String in=et.getText().toString();
				if(in.equals("")){
					Toast.makeText(become_bary.this, "«Î ‰»ÎŒƒ±æ", Toast.LENGTH_SHORT).show();
				}
				else {
					try {
						Bitmap qrcode=EncodingHandler.createQRCode(in, 400);
						ig.setImageBitmap(qrcode);
					} catch (WriterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.bar);
		actionBar.setBackgroundDrawable(myDrawable);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
