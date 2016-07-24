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
import android.widget.CheckBox;
import android.text.InputType;
import android.util.Log;

import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import com.android.nal.service.MainService;
import com.android.nal.utils.l;
import com.android.nal.utils.util;

public class regActivity1 extends Activity {
	Context mC = null;
	//MainService mS = null;
	//regActivity1 mA = null;

	//Button back;
	Button reg1_capt = null;
	Button reg1_show = null;
	EditText reg1_user = null;//(EditText)findViewById(R.id.uname);
	EditText reg1_pwd = null;//(EditText)findViewById(R.id.pwd);
	CheckBox reg1_check = null;


	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

    private View.OnClickListener getCapt = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
			final String n = reg1_user.getText().toString().trim();
			final String p = reg1_pwd.getText().toString().trim();
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
					netConfig.getInstance().capt(n);
				}
			}.start();
			Intent i = new Intent();
			i.setClass(regActivity1.this, regActivity2.class);
			mC.startActivity(i);
			//mA.finish();
		}
    };

	private View.OnClickListener changeShow = new View.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			if(reg1_pwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
				reg1_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
				reg1_show.setBackgroundResource(R.drawable.login_eye_show);
			} else {
				reg1_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				reg1_show.setBackgroundResource(R.drawable.login_eye_hide);
			}

		}
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		setContentView(R.layout.reg1);

		reg1_capt = (Button)findViewById(R.id.reg1_capt);
		reg1_capt.setOnClickListener(getCapt);

		reg1_show = (Button)findViewById(R.id.reg1_show);
		reg1_show.setOnClickListener(changeShow);
		reg1_user = (EditText)findViewById(R.id.reg1_user);
		reg1_pwd = (EditText)findViewById(R.id.reg1_pwd);
		reg1_check = (CheckBox)findViewById(R.id.reg1_check);
/*
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent();
					i.setClass(regActivity1.this,loginActivity.class);
					mA.finish();
				}
			}
		);
*/
		//mA = this;
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

