package com.android.nal;


import com.android.nal.utils.HttpUtil;
import com.android.nal.utils.l;

public class status {
	private static status mstatus;
	private status() {
		
    }

	public static synchronized status getInstance() {
        if (mstatus == null) {
            mstatus = new status();
        }   
        return mstatus;
    }
/*
	public static boolean isLogin() {
		String name = localConfig.getInstance().getUname();
		if(name == null || name.equals(""))
			return false;
		return true;
	}
*/
	public static boolean Login(String name,String pwd) {
		log("name:"+name+" pwd:"+pwd);
		return true;
	}

	public static int getUid() {
		return 0;
	}

	public static deviceConfig getdeviceConfig() {
		String result = HttpUtil.httpGetT(UURL.deviceConfig);
		log(result);
		return null;
	}

	static void log(String line) {
		if(line == null)
			l.d("log null");
		else
			l.d(line);
    } 
}

