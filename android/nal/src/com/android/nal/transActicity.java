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

public class transActicity extends Activity {
	Context mC = null;
	Button trans,cancel; 
	transActicity mA = null;


    private void showToast(String msg){
        Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
        toast.show();
    }  

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		setContentView(R.layout.trans);
		trans =  (Button)findViewById(R.id.ok);
		cancel= (Button)findViewById(R.id.cancel);
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
		cancel.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					mA.finish();
				}
			}
		);
		mA = this;
	}

    @Override
    public void onStart() {
        super.onStart();
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
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
/*
    private void onRegSucess() {
        showToast("reg sucess");
        Intent i = new Intent();
        i.setClass(transActicity.this,MainActivity.class);
        mC.startActivity(i);
        mA.finish();
    }
    private void onRegFail() {
        showToast("reg Fail");
    }
*/
}

