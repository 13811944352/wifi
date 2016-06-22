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
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class deviceAdd extends Activity {
	Context mC = null;
	deviceAdd mA = null;

	Button enroll;//  = (Button)findViewById(R.id.login);

	private static final String[] m={"设备A","设备B"};
	int deviceType = 0;
	Spinner type = null;
	EditText id = null;
	EditText name = null;
	EditText desc = null;

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		setContentView(R.layout.device_add);
		mA = this;
		Button add = (Button)findViewById(R.id.save);
		Button cancel = (Button)findViewById(R.id.cancel);
		id = (EditText)findViewById(R.id.device_id);
		name = (EditText)findViewById(R.id.device_name);
		desc = (EditText)findViewById(R.id.device_desc);
		type = (Spinner)findViewById(R.id.device_type);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new OnItemSelectedListener() {
			   public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("deviceType =="+arg2);
					deviceType = arg2;
					log("deviceType =="+deviceType);
				}
 
				public void onNothingSelected(AdapterView<?> arg0) {
					//deviceType = -1;
				}
			}
		);
        type.setVisibility(View.VISIBLE);
		
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				if(deviceType != 0 && deviceType != 1) {
					log("no deviceType =="+deviceType);
					onRegFail("plz select a device Type");
					return ;
				}
                new Thread() {
					@Override
                    public void run() {
						deviceConfig d = new deviceConfig();
						d.deviceType= deviceType;//Integer.parseInt(type.getText().toString());
						d.deviceId=id.getText().toString();
						d.deviceName=name.getText().toString();
						d.deviceDesc=desc.getText().toString();
						String json = d.d2j(d);
						log("json:"+json);
						boolean ret = netConfig.getInstance().regDeviceConfig(d);
						if(ret == true ) {
							Message m = new Message();
                            m.what = 0;
                            mH.sendMessage(m);
                        } else {
                            Message m = new Message();
                            m.what = 1;
                            mH.sendMessage(m);
                        }
                    }
                }.start();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				mA.finish();
            }
        });

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
		showToast("reg sucess");
		//Intent i = new Intent();
		//i.setClass(deviceAdd.this,MainActivity.class);
		//mC.startActivity(i);
		mA.finish();
	}
	private void onRegFail() {
		showToast("reg Fail");
	}
	private void onRegFail(String s) {
		showToast(s);
	}

    private void log(String line) {
        l.d(line);
    }  

}

