package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.content.ComponentName;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.content.ComponentName;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;

import com.android.nal.utils.l;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;

public class transView extends viewBase{
	Button trans;
	TextView deviceaName;
	EditText uname;
	transView mA = null;

    public transView(Context c,int id,deviceConfig d,nodeConfig n[],MainService s) {
        super(c,id,d,n,s);
        initView();
    }   



    public void initView() {
		trans =  (Button)findViewById(R.id.ok);
		deviceaName = (TextView)findViewById(R.id.deviceName);
		uname = (EditText)findViewById(R.id.user);
        if(mD !=  null) {
            log("trans"+mD.d2j(mD));
        } else {
            log("trans null");
        }
		deviceaName.setText(mD.deviceName);
		
/*
		login.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					EditText user = (EditText)findViewById(R.id.user);
					EditText pwd = (EditText)findViewById(R.id.pwd);
					final String n = user.getText().toString();
					final String p = pwd.getText().toString();
                    new Thread() {
                        @Override
                        public void run() {
                            String token = netConfig.getInstance().login(n,p);
                            if(token == null) {
                                Message m = new Message();
                                m.what = 1;
                                mH.sendMessage(m);
                            } else {
                                Message m = new Message();
                                m.what = 0;
                                android.util.Log.i("er","login uname:"+n+" token:"+token);
                                localConfig.getInstance().setUname(n);
                                localConfig.getInstance().setToken(token);
                                mH.sendMessage(m);
                            }
                        }
                    }.start();

					
				}
			}
		);
*/
	}

    private Handler mH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case(0):
  //                  onRegSucess();
                    break;
                case(1):
                default:
  //                  onRegFail();
                    break;
            }
        }

    };
}
/*
*/

