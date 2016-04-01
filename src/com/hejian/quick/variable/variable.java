package com.hejian.quick.variable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class variable {
	public static String getIp(){
		String ip="quickcheck.applinzi.com";
		return ip;
	}
	
	public static boolean saveRefresh(Context context){
		try {
			File file=new File("/sdcard/Android/boolean.txt");
			FileOutputStream fos=new FileOutputStream(file);
			try {
				String text="true";
				fos.write(text.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getSaveRefresh(Context context){
		try {
			File file=new File("/sdcard/Android/boolean.txt");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String string=null;
			try {
				string = br.readLine();
				fis.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return string;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return null;
		}
	} 
}
