package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.InputType;
import android.util.Log;

import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;

import java.lang.Override;

public class loginActivity extends Activity {
	Context mC = null;
	//Button login,enroll;
	loginActivity mA = null;

    EditText login_user;
    EditText login_pwd;
    Button login_show;
    Button login_login;
    Button login_forget,login_enroll;

    private void showToast(String msg){
        Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
        toast.show();
    }

    private View.OnClickListener changeShow = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Log.d("er", "" + login_pwd.getInputType());
            if(login_pwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                login_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                login_show.setBackgroundResource(R.drawable.login_eye_show);
            } else {
                login_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                login_show.setBackgroundResource(R.drawable.login_eye_hide);
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		setContentView(R.layout.login);
        login_user = (EditText)findViewById(R.id.login_user);
        login_pwd = (EditText)findViewById(R.id.login_pwd);
        login_forget =  (Button)findViewById(R.id.login_forget);
        login_show =  (Button)findViewById(R.id.login_show);
        login_login =  (Button)findViewById(R.id.login_login);
        login_enroll = (Button)findViewById(R.id.login_enroll);

        login_show.setOnClickListener(changeShow);

        login_login.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {

					final String n = login_user.getText().toString();
					final String p = login_pwd.getText().toString();
/*
					String token = netConfig.getInstance().login(n,p);
					android.util.Log.i("er","login:"+token);
					if(token != null) {
                        localConfig.getInstance().setUname(n);
                        localConfig.getInstance().setToken(token);
						Intent i = new Intent();
						i.setClass(loginActivity.this,MainActivity.class);
						mC.startActivity(i);
						mA.finish();
					}
*/					
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

		login_enroll.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					android.util.Log.i("er", "enroll click");
					Intent i = new Intent();
					i.setClass(loginActivity.this, regActivity1.class);
					mC.startActivity(i);
					//mA.finish();
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
                    onRegSucess();
                    break;
                case(1):
                default:
                    onRegFail();
                    break;
            }
        }

    };

    private void onRegSucess() {
        showToast("登陆成功");
        Intent i = new Intent();
        i.setClass(loginActivity.this,mainActivity.class);
        mC.startActivity(i);
        mA.finish();
    }
    private void onRegFail() {
        showToast("登陆失败");
    }

}

