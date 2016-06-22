package com.android.nal.service;

/*
import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
*/
import android.os.Looper;
import android.os.HandlerThread;
import android.app.Service;// 服务的类
import android.os.IBinder;
import android.os.Binder;
import android.content.Intent;

import com.android.nal.utils.l;
//import com.android.nal.Istatus;
import com.android.nal.deviceConfig;
import com.android.nal.nodeConfig;
import com.android.nal.net.netConfig;
import com.android.nal.local.localConfig;
import java.util.ArrayList;

public class MainService extends Service {
    private static HandlerThread mHt = null;
    private volatile Looper mLooper = null;
    private volatile MainServiceHandler mMsh;
	private tcpC mTcp;

	//public IStatus mCB = null;
	//public void setCallBack(IStatus cb) {
	//	if(mCB == null)
	//		mCB = cb;
	//}

	public boolean start(){
		return mTcp.start();
	}
	
	public boolean stop() {
		mTcp.sstop();
		mTcp = null;
		return true;
	}

	public String doQuery(String did,String var) {
		return mTcp.doQuery(did,var);
	}

	public int doSet(String did,String var,int type,String value) {
		return mTcp.doSet(did,var,type,value);
	}

	public ArrayList<deviceConfig> getDeviceList() {
		return netConfig.getInstance().getDeviceList();
	}

	public void syncLocalNode(String did) {
/*
		for(int i = 1;i<8;i++) {
			nodeConfig n = getNodeConfig(did,""+i);
			if(n == null)
				localConfig.getInstance().delNodeConfig(did,""+i);
			else
				localConfig.getInstance().setNodeConfig(n);
		}
*/
	}

	public boolean syncNetNode(nodeConfig n) {
		return (setNodeConfig(n));
/*
		if(setNodeConfig(n))
			localConfig.getInstance().setNodeConfig(n);
		else
			return false;
		return true;
*/
	}
	
	public nodeConfig getNodeConfig(String did,String nid) {
		return netConfig.getInstance().getNodeConfig(did,nid);
	}

	public boolean setNodeConfig(nodeConfig n) {
		return netConfig.getInstance().setNodeConfig(n);
	}

    @Override
    public void onCreate() {
        super.onCreate();      
		d("MainService onCreate");
		mHt = new HandlerThread("MainService");
        try{
            mHt.start();
        }catch(IllegalThreadStateException e){ 
            e.printStackTrace();
            d("!! sWorkerThread start IllegalThreadStateException: "+e.toString());
        }
        mLooper = mHt.getLooper();
        mMsh = new MainServiceHandler(mLooper,this);
		d("MainService mTcp start");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		d("MainService onStartCommand:"+intent);
/*
		if(mTcp == null) {
			mTcp = tcpC.getInstance();
			mTcp.start();
		}
        return START_STICKY;
*/
		mTcp = tcpC.getInstance();
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent arg0) {
		d("MainService onBind");
		//status.getdeviceConfig();
		mMsh.sendEmptyMessage(MainServiceHandler.MSG_INIT);
        return mMsb;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

	public class MainServiceBinder extends Binder{
		public MainService getService(){
			return MainService.this;
		}
	}
	
	private MainServiceBinder mMsb = new MainServiceBinder();

	private void d(String line) {
		l.d(line);
	}

}
