package com.android.nal.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.nal.local.localConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util {
	private static String uuid = null;
	private util(){
	}

	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);  
		System.out.println(m.matches()+"---");  
		return m.matches();  
	} 

	

	public static String getUUID(){
		if(uuid == null)
			uuid = ""+System.currentTimeMillis();//formatter.format(curDate);       
		return uuid;
/*
		SharedPreferences mShare = getSysShare(context, "sysCacheMap");
		if(mShare != null){
			uuid = mShare.getString("uuid", "");
		}

		if(isEmpty(uuid)){
			uuid = UUID.randomUUID().toString();
			saveSysMap(context, "sysCacheMap", "uuid", uuid);
		}

	return uuid;
*/
	} 

}
