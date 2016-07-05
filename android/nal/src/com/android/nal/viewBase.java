package com.android.nal;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.os.Handler;
import android.os.Message;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings ;
import android.widget.LinearLayout;


public class viewBase {//extends WebView{
	View v;
	String mUrl;

	nodeConfig mN[] ;
	deviceConfig mD;
	Context mC;
	//Handler mH;
    MainService mS;
	deviceActivity mA;
	

    public viewBase(Context c,int id,deviceConfig d,nodeConfig n[],MainService s) {
        mC = c;
        LayoutInflater inflater = LayoutInflater.from(c);
        v = inflater.inflate(id, null);
        mD = d;
        mN = new nodeConfig[8];
        if(n != null) {
            for (int i = 0; i < 8; i++)
                mN[i] = n[i];
        }
        mS = s;
    }

    public viewBase(Context c,int id,deviceConfig d,nodeConfig n[],MainService s,deviceActivity a) {
        mC = c;
        LayoutInflater inflater = LayoutInflater.from(c);
        v = inflater.inflate(id, null);
        mD = d;
        mN = new nodeConfig[8];
        if(n != null) {
            for (int i = 0; i < 8; i++)
                mN[i] = n[i];
        }
        mS = s;
        mA = a;
/*
        LayoutInflater inflater = LayoutInflater.from(c);
        v = inflater.inflate(id, null);
        mD = d;
        mN = new nodeConfig[8];
        if(n != null) {
            for (int i = 0; i < 8; i++)
                mN[i] = n[i];
*/  
    }

	public void setData(deviceConfig d,nodeConfig n[]) {
		mD = d;
		mN = n;
	}

	public void updata() {
		if(mA != null)
			mA.updata(mD,mN);
	}

	nodeConfig[] getN() {
		return mN;
	}

	deviceConfig getD(){
		return mD;
	}

	public void initView() {
		return;
	}
	public void destroyView() {
		return;
	}
	
/*
    public viewBase(Context c,int id,Handler h) {
		mC = c;
		mH = h;
		LayoutInflater inflater = LayoutInflater.from(c);
		v = inflater.inflate(id, null);
    }
	void setMH(Handler h) {
		mH = h;
	}
*/

	public View getView() {
		return v;
	}


    void showToast(String msg){
        Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT);
        toast.show();
    }   


    View findViewById(int id) {
        if(v == null)
            log("v not init");
        return v.findViewById(id);
    } 
/*
    void initNode(deviceConfig d){
		mD = d;
        new Thread() {
            @Override
            public void run(){
				if(mN == null)
					mN = new nodeConfig[8];
                for(int i = 0;i<8;i++)
                    mN[i] = netConfig.getInstance().getNodeConfig(mD.deviceId,""+(i+1));
                //initView();
                Message m = new Message();
                m.what = 0;
				if(mH != null)
					mH.sendMessage(m);
            }
        }.start();
    }
*/
/*
    private Handler mH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case(0):
                    initView();
                    break;
                case(1):
                default:
                    break;
            }
        }
        
    };
*/

    void log(String line) {
        l.d(line);
    }  

} 
