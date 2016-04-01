package com.hejian.quick.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.util.Log;

public class dataService {
	
	public static boolean saveNote(Context context,String text){
		try {
			//File file=new File(context.getCacheDir(),"info.txt");
			File file=new File("/sdcard/Android/note.txt");
			FileOutputStream fos=new FileOutputStream(file);
			try {
				fos.write(text.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w("test", "¥Ê»° ß∞‹1");
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return false;
		}
	}
	
	public static String getSaveNote(Context context){
		try {
			File file=new File("/sdcard/Android/note.txt");
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
	
	//ip∂Àø⁄…Ë÷√
	public static boolean saveIp(Context context,String text){
		try {
			//File file=new File(context.getCacheDir(),"info.txt");
			File file=new File("/sdcard/Android/ip.txt");
			FileOutputStream fos=new FileOutputStream(file);
			try {
				fos.write(text.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w("test", "¥Ê»° ß∞‹1");
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return false;
		}
	}
	
	public static String getSaveIp(Context context){
		try {
			File file=new File("/sdcard/Android/ip.txt");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String str=null;
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return str;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return null;
		}
	} 
	
	//people…Ë÷√
	public static boolean savePeo(Context context,String text){
		try {
			//File file=new File(context.getCacheDir(),"info.txt");
			File file=new File("/sdcard/Android/people.txt");
			FileOutputStream fos=new FileOutputStream(file);
			try {
				fos.write(text.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w("test", "¥Ê»° ß∞‹1");
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return false;
		}
	}
	
	public static String[] getSavePeo(Context context){
		try {
			File file=new File("/sdcard/Android/people.txt");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String str=null;
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] strings=str.split("##");
			return strings;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return null;
		}
	} 
	
	//user…Ë÷√
	public static boolean saveUser(Context context,String text){
		try {
			//File file=new File(context.getCacheDir(),"info.txt");
			File file=new File("/sdcard/Android/user.txt");
			FileOutputStream fos=new FileOutputStream(file);
			try {
				fos.write(text.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w("test", "¥Ê»° ß∞‹1");
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return false;
		}
	}
	
	public static String getSaveUser(Context context){
		try {
			File file=new File("/sdcard/Android/user.txt");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String str=null;
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return str;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "¥Ê»° ß∞‹2");
			return null;
		}
	} 
}
