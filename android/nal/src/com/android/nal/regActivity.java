package com.android.nal;

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

public class regActivity extends Activity {
	Context mC = null;
	MainService mS = null;
	regActivity mA = null;

	Button enroll;//  = (Button)findViewById(R.id.login);
	//Button enroll = (Button)findViewById(R.id.enroll);
	Button captButton;


	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

    private View.OnClickListener getCapt = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
			new Thread() {
				@Override
				public void run() {
					int i = 60;
					while(true) {
						try {
							Thread.sleep(1000);
						}catch(Exception e) {
							;
						}
						i--;
						if(i <= 0) {
							captButton.setOnClickListener(getCapt);
							Message m = new Message();
							m.what = 3;
							mH.sendMessage(m);
						}
						String s = "剩余点击时间:"+i;
						Message m = new Message();
						m.what = 2;
						m.obj = s;
						mH.sendMessage(m);
					}
				}
			}.start();
		}
    }; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		setContentView(R.layout.reg);

		captButton = (Button)findViewById(R.id.captButton);
		captButton.setOnClickListener(getCapt);

		enroll = (Button)findViewById(R.id.enen);
		enroll.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					EditText uname = (EditText)findViewById(R.id.uname);
					//EditText mail = (EditText)findViewById(R.id.email);
					EditText pwd = (EditText)findViewById(R.id.pwd);
					EditText pwda = (EditText)findViewById(R.id.pwda);
					final String n = uname.getText().toString();
					//String m = mail.getText().toString();
					final String p = pwd.getText().toString();
					String pa = pwda.getText().toString();
					if(n == null) {
						showToast("plz input uname");
						return ;
					}
					if(n.trim().equals("")){
						showToast("plz input name");
						return ;
					}
					if(p.trim().equals("")){
						showToast("plz input pwd");
						return ;
					}
					if(!p.equals(pa)) {
						showToast("plz input same password");
						return ;
					}
					new Thread() {
						@Override
						public void run() {
							String token = netConfig.getInstance().reg(n,p);
							if(token == null) {
								Message m = new Message();
								m.what = 1;
								mH.sendMessage(m);
							} else {
								Message m = new Message();
								m.what = 0;
								log("reg uname:"+n+" token:"+token);
								localConfig.getInstance().setUname(n);
								localConfig.getInstance().setToken(token);
								mH.sendMessage(m);
							}
						}
					}.start();
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
				case(2):
					onCaptSucess(msg.obj);
					break;
				case(3):
					onCaptReset();
					break;
				case(1):
				default:
					onRegFail();
					break;
			}
		}
		
	};

	private void onCaptReset() {
		captButton.setBackgroundResource(R.drawable.capt);
		captButton.setText(null);
	}

	private void onCaptSucess(Object obj) {
		String s = (String)obj;
		captButton.setBackground(null);
		captButton.setText(s);
	}

	private void onRegSucess() {
		showToast("reg sucess");
		Intent i = new Intent();
		i.setClass(regActivity.this,MainActivity.class);
		mC.startActivity(i);
		mA.finish();
	}
	private void onRegFail() {
		showToast("reg Fail");
	}

    private void log(String line) {
        l.d(line);
    }  

}
