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

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class LoginService {
	
	/**
	 * 保存用户信息的业务方法
	 * @param username
	 * @param password
	 * @return
	 */
	
	public static boolean saveUserInfo(Context context,String username,String password){
		try {
			File file=new File(context.getCacheDir(),"info.txt");
			//File file=new File("/sdcard/info.txt");
			FileOutputStream fos=new FileOutputStream(file);
			try {
				fos.write((username+"##"+password).getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w("test", "存取失败1");
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "存取失败2");
			return false;
		}
	}
	
	public static Map<String, String> getSaveUserInfo(Context context){
		try {
			File file=new File(context.getCacheDir(),"info.txt");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String str=null;
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] infos=str.split("##");
			Map<String, String> map=new HashMap<String, String>();
			map.put("username", infos[0]);
			map.put("password", infos[1]);
			return map;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("test", "存取失败2");
			return null;
		}
	} 
}
