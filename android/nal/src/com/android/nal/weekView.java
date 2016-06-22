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

public class weekView extends viewBase{
	Context mC = null;
	weekView mA = null;
	deviceConfig mD;
	MainService mS;

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}


	View findViewById(int id) {
		return v.findViewById(id);
	}

	public weekView(Context c,int id,Object o) {
		super(c,id,null);
		mC = c;
		mS = MainActivity.getService();
		Intent intent = (Intent)o;
		String xx = intent.getStringExtra("device");  
		mD = deviceConfig.j2d(xx);
		initView();
	}
/*
*/
	void initView() {
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
			//if(n == null) {
			//	b.setBackgroundResource(R.drawable.device_main_no);
			//	t.setText("");
			//	continue;
			//}
			b.setBackgroundResource(R.drawable.device_main_no);
			String temp = mS.doQuery(mD.deviceId,"temp"+(x+1));
			//b.setText(""+n.nodeTemp);
			if(temp != null) {
				b.setText(""+temp);
				b.setBackgroundResource(R.drawable.device_main_online);
			}
			nodeConfig n = localConfig.getInstance().getNodeConfig(mD.deviceId,""+(x+1));
			if(n == null) {
				t.setText("未命名");
			} else {
				t.setText(n.nodeName);
			}
			
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					log("x=="+((Button)arg0).getText());
					Intent i = new Intent();
					//i.setClass(weekView.this,nodeActivity.class);
					i.setClass(mC,nodeActivity1.class);
					//deviceConfig d = device.get(pos);
					//log("onclick :"+d.d2j(d));  
					//Intent i = new Intent();
					//todo
					//here trye
					//i.setClass(MainActivity.this,weekView.class);
					//nodeConfig n = localConfig.getInstance().getNodeConfig();
					i.putExtra("device",mD.d2j(mD));
					i.putExtra("nodeId",""+nodeId);
					mC.startActivity(i);
				}
			});
		}
/*
		}
*/
	}
/*
	private Handler mH = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case(0):
					//onRegSucess();
					break;
				case(1):
				default:
					//onRegFail();
					break;
			}
		}
		
	};
*/

}

