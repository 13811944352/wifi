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

import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import com.android.nal.service.MainService;
import com.android.nal.utils.l;
import com.android.nal.utils.util;

public class regActivity2 extends Activity {
	Context mC = null;
	MainService mS = null;
	regActivity2 mA = null;

	Button back; 
	Button captButton;


	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

    private View.OnClickListener getCapt = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
			EditText uname = (EditText)findViewById(R.id.uname);
			EditText pwd = (EditText)findViewById(R.id.pwd);
			final String n = uname.getText().toString().trim();
			final String p = pwd.getText().toString().trim();
			if(n == null) {
				showToast("plz input uname");
				return ;
			}
			if(n.trim().equals("")){
				showToast("plz input name");
				return ;
			}
			if(!util.isMobileNO(n)) {
				showToast("plz input phoneNo");
				return ;
			}
			new Thread() {
				@Override
				public void run() {
					log("send capt");
					//netConfig.getInstance().capt(n);
				}
			}.start();
			Intent i = new Intent();
			i.setClass(regActivity2.this,regActivity2.class);
			mC.startActivity(i);
			mA.finish();
		}
    }; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		setContentView(R.layout.reg2);
/*
		captButton = (Button)findViewById(R.id.captButton);
		captButton.setOnClickListener(getCapt);

		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent();
					i.setClass(regActivity2.this,loginActivity.class);
					mA.finish();
				}
			}
		);
*/
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

    private void log(String line) {
        l.d(line);
    }  

}

