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
import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings ;
import android.widget.LinearLayout;

public class deviceView1 extends viewBase{
	Context mC = null;
	//deviceConfig mD;
	//nodeConfig mN[] ;//= new ;
	MainService mS;
	Handler mmH;

/*
	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}


	View findViewById(int id) {
		return v.findViewById(id);
	}
*/
	public deviceView1(Context c,int id,Object o,Handler h) {
		super(c,id,null);
		mC = c;
		mS = MainActivity.getService();
		Intent intent = (Intent)o;
		String xx = intent.getStringExtra("device");  
		mD = deviceConfig.j2d(xx);
		mmH = h;
		setMH(tH);
		initNode(mD);
		//mLL = (LinearLayout) findViewById(R.id.device_main);
		//mLL.setVisibility(View.GONE);
	}
/*
	void initNode(){
		new Thread() {
			@Override
			public void run(){
				mN = new nodeConfig[8];
				for(int i = 0;i<8;i++)
					mN[i] = netConfig.getInstance().getNodeConfig(mD.deviceId,""+(i+1));
				//initView();
				Message m = new Message();
				m.what = 0;
				mH.sendMessage(m);
			}
		}.start();
	}
*/	

	void initView() {
		//mLL.setVisibility(View.VISIBLE);
		//setContentView(R.layout.device_main);
		TextView t = (TextView)findViewById(IDHelper.getViewID(mC,"d_name"));
		t.setText(mD.deviceName);
		Button b;
		for(int x = 0 ;x < 8;x++) {
			final int nodeId = (x+1);
			log("id == :"+R.id.n01);
			log("id1 == :"+IDHelper.getViewID(mC, "n0"+nodeId));
			b = (Button) findViewById(IDHelper.getViewID(mC, "n0"+(x+1)));
			t = (TextView)findViewById(IDHelper.getViewID(mC,"n_name_0"+(x+1)));
			b.setBackgroundResource(R.drawable.device_main_no);
			b.setBackgroundResource(R.drawable.device_main_online);
			final nodeConfig n = mN[x];//netConfig.getInstance().getNodeConfig(mD.deviceId,""+(x+1));
			if(n == null || n.nodeName == null || n.nodeName.equals("")) {
				t.setText("未命名");
			} else {
				t.setText(n.nodeName);
			}

			String temp = mS.doQuery(mD.deviceId,"temp"+(x+1));
			do{
			if(temp != null) {
				b.setText(""+temp);
				if(n == null) {
					b.setBackgroundResource(R.drawable.device_main_online);
					break;
				}
				if(n.nodeConfig == 0)
					b.setBackgroundResource(R.drawable.device_main_online);
				else {
					if(n.nodeTemp == -101)
						b.setBackgroundResource(R.drawable.device_main_online_blue);
					if(n.nodeTemp == -102)
						b.setBackgroundResource(R.drawable.device_main_online_yellow);
					if(n.nodeTemp == -103)
						b.setBackgroundResource(R.drawable.device_main_online_red);
				}
			}
			}while(false);

			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					log("x=="+((Button)arg0).getText());
					Intent i = new Intent();
					if(mD.deviceType == 1)
						i.setClass(mC,nodeActivity.class);
					if(mD.deviceType == 2)
						i.setClass(mC,nodeActivity1.class);
					log("mD:"+mD.d2j(mD));
					log("id:"+nodeId);
					i.putExtra("device",mD.d2j(mD));
					i.putExtra("nodeId",""+nodeId);
					if(n != null) {
						i.putExtra("node",n.n2j(n));
					} else {
						i.putExtra("node","null");
					}
					mC.startActivity(i);
				}
			});
		}
	}

	private Handler tH = new Handler() {
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

/*

    private void log(String line) {
        l.d(line);
    }  
*/
}

