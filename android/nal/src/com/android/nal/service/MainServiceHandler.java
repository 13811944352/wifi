package com.android.nal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.nal.IStatus;
import com.android.nal.deviceConfig;
import com.android.nal.nodeConfig;
import com.android.nal.utils.l;
import com.android.nal.net.netConfig;
import com.android.nal.local.localConfig;

public class MainServiceHandler extends Handler {
	private MainService mService;

	public static final int MSG_INIT = 0;
	public static final int MSG_TIME = 1;

	public MainServiceHandler(Looper looper, MainService service) {
		super(looper);
		mService = service;
	}

	@Override
	public void handleMessage(Message msg) {
		String content = null;
		switch(msg.what){
		default:
			content = "NO MESSAGE RECV!!";
			break;
		}
		onHandleIntent(msg);

	}

	private void onHandleIntent(Message message) {
		int msg = message.what;
		switch(msg){
			case(MSG_INIT):
				//status.getdeviceConfig();
				//break;
			case(MSG_TIME):
				sync();
				Message m = obtainMessage();
				m.what = MSG_TIME;
				sendMessageDelayed(m, 10000);
				break;
			default:
				break;
		}
	}

	private void sync() { 
/*
		ArrayList<deviceConfig> d = netConfig.getInstance().getDeviceList();
		if(d == null) {
			return ;
		}
		localConfig.getInstance().setAllDevice(d);
		for(deviceConfig aa:d) {
			ArrayList<nodeConfig> node = netConfig.getInstance().getNodeList(aa.deviceId);
			if(node == null) {
				d("net get node err:"+aa.deviceId);
				continue;
			}
			for(nodeConfig n:node) {
				d("net get node:"+n.n2j(n));
				localConfig.getInstance().setNodeConfig(n);
			}
		}
		if(mService.mCB != null)
			mService.mCB.onChange();
*/
	}

	private void updataConfig() {
		//int uid = status.getUid()
	}
/*
    public void ER_sendMessage(int what, PairParameters params) {
        Message msg = obtainMessage();
        msg.what = what;
        if(params != null){
            msg.obj = params;
        }
        sendMessage(msg);
    }   

    public void ER_sendMessage(int what) {
        Message msg = obtainMessage();
        msg.what = what;
        sendMessage(msg);
    }   

    public void ER_sendMessageDelayed(int what, long delayTime) {
        Message msg = obtainMessage();
        msg.what = what;
        sendMessageDelayed(msg, delayTime);
    }   

    public void ER_sendMessageDelayed(int what, PairParameters params, long delayTime) {
        Message msg = obtainMessage();
        msg.what = what;
        if(params != null){
            msg.obj = params;
        }
        sendMessageDelayed(msg, delayTime);
    } 
*/
    private void d(String line) {
        l.d(line);
    } 
}
