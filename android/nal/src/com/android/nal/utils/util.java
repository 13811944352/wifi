package com.android.nal.utils;

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
