package com.android.nal.local;

import com.android.nal.deviceConfig;
import com.android.nal.nodeConfig;
import com.android.nal.nodeConfig1;
import android.content.Context;
import com.android.nal.mainApp;
import java.util.List;

import android.content.SharedPreferences;  
import android.content.SharedPreferences.Editor;
import android.app.Activity;

public class localConfig {
	private Context mC = null;
    private static localConfig mLc;

    public static synchronized localConfig getInstance() {
        if (mLc == null) {
            mLc = new localConfig();
        }
        return mLc;
    }



	private localConfig() {
		mC = mainApp.getContext();
	}

	public boolean isDeviceInit(String did) {
		SharedPreferences sp = mC.getSharedPreferences("user",Activity.MODE_PRIVATE); 
        String ret = sp.getString(did, "false"); 
		if(ret.equals("true"))
			return true;
		return false;
	}

	public void deviceInit(String did) {
		SharedPreferences sp = mC.getSharedPreferences("user",Activity.MODE_PRIVATE); 
		Editor editor = sp.edit();//获取编辑器
		editor.putString(did, "true");
		editor.commit();//提交修改
	}

	public void setUname(String name) {
		SharedPreferences sp = mC.getSharedPreferences("user",Activity.MODE_PRIVATE); 
		Editor editor = sp.edit();//获取编辑器
		editor.putString("name", name);
		editor.commit();//提交修改
	}

	public void setToken(String token) {
		SharedPreferences sp = mC.getSharedPreferences("user",Activity.MODE_PRIVATE); 
		Editor editor = sp.edit();//获取编辑器
		editor.putString("token", token);
		editor.commit();//提交修改
	}

	public String getUname() {
		SharedPreferences sp = mC.getSharedPreferences("user",Activity.MODE_PRIVATE); 
        String name  = sp.getString("name", ""); 
		return name;
	}

	public String getToken() {
		SharedPreferences sp = mC.getSharedPreferences("user",Activity.MODE_PRIVATE); 
        String token = sp.getString("token", ""); 
		return token;
	}

    public boolean isLogin() {
        String name = getUname();
        if(name == null || name.equals(""))
            return false;
        return true;
    }

	public boolean logout() {
		setToken("");
		setUname("");
		return true;
	}

	public boolean setAllDevice(List<deviceConfig> d) {
		return localSqlite.getInstance().setDeviceConfig(d);
	}

	public List<deviceConfig> getAllDevice() {
		List<deviceConfig> list = localSqlite.getInstance().getDeviceConfig();
		return list;
	}

    public deviceConfig getDeviceConfig(String did) {
		return localSqlite.getInstance().getDeviceConfig(did);
	}

    public nodeConfig getNodeConfig(String did,String nid) {
		return localSqlite.getInstance().getNodeConfig(did,nid);
	}

	public void delNodeConfig(String did,String nid) {
		localSqlite.getInstance().delNodeConfig(did,nid);
	}
	
    public void setNodeConfig(nodeConfig n) {
		localSqlite.getInstance().setNodeConfig(n);
	}
	
    public nodeConfig1 getNodeConfig1(String did,String nid) {
		return localSqlite.getInstance().getNodeConfig1(did,nid);
	}

    public void setNodeConfig1(nodeConfig1 n) {
		localSqlite.getInstance().setNodeConfig1(n);
	}

}
