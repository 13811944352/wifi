package com.android.nal;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.lenovo.lps.sus.SUS;

public class mainApp extends Application {
	private static Context mContext = null;
	static boolean isStartVersionUpdateFlag = false;
	
	public static Context getContext() {
		return mContext;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		//Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler.getIntance(this));
		
		mContext = this;

		if (!isStartVersionUpdateFlag) {
			isStartVersionUpdateFlag = true;
	
		if (!SUS.isVersionUpdateStarted()) {
			SUS.AsyncStartVersionUpdate(this);
		}
}

/*
		try{
			ApplicationInfo aInfo = getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
			if(aInfo.metaData.containsKey("address")){
				Constant.severAddr = aInfo.metaData.getString("address");
			}
			if(aInfo.metaData.containsKey("BeBo")){
				boolean on = aInfo.metaData.getBoolean("BeBo");
				LogUtil.setDebug(on);
				LogUtil.i("debug on");
			}else {
				LogUtil.setDebug(false);
			}
		} catch(NameNotFoundException e){
			e.printStackTrace();
			LogUtil.setDebug(false);
		}
*/		
	}
	
}
