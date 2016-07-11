package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.nal.local.localConfig;
import com.android.nal.service.MainService;

import java.util.Timer;
import java.util.TimerTask;
 
public class welcomeActivity extends Activity {
	Context mC = null;
	Activity mA = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mC = this;
		mA = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		startService();
		Timer timer = new Timer();
		TimerTask t = new TimerTask() {
			@Override
			public void run(){
				jump();
			}
		};
		timer.schedule(t,1000);
	}

	private void startService() {
		Intent i = new Intent();
		i.setClass(welcomeActivity.this,MainService.class);
		mC.startService(i);
		
	}

	private void jump() {
		boolean isLogin = localConfig.getInstance().isLogin();
		Intent i = new Intent();
		if(isLogin)
			i.setClass(welcomeActivity.this,mainActivity.class);
		else
			i.setClass(welcomeActivity.this,loginActivity.class);
		mC.startActivity(i);
		mA.finish();
	}
}
