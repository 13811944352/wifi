package com.android.nal;

import android.app.Application;
import android.content.Context;

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
		mContext = this;

		if (!isStartVersionUpdateFlag) {
			isStartVersionUpdateFlag = true;
	
		if (!SUS.isVersionUpdateStarted()) {
			SUS.AsyncStartVersionUpdate(this);
		}
}

	}
	
}
