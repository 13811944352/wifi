package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.nal.net.netConfig;
import com.android.nal.utils.l;

public class deviceAdd extends Activity {
	Context mC = null;
	deviceAdd mA = null;

	Button enroll;   

	private static final String[] m={"带温控器","不带温控器"};
	int deviceType = 0;
	Spinner type = null;

	EditText id = null;
	EditText name = null;
	EditText desc = null;

	EditText tempIn= null;
	EditText tempOut= null;

	Spinner specifications_select , gap_select , material_select;
	int specifications_v = 0,gap_v = 0,material_v = 0;
	LinearLayout tempset,specifications,gap,material;


	private void setTempSet(boolean set) {
		if(!set) {
			tempset.setVisibility(View.GONE);  
			specifications.setVisibility(View.GONE);  
			gap.setVisibility(View.GONE);  
			material.setVisibility(View.GONE);  
		} else {
			tempset.setVisibility(View.VISIBLE);  
			specifications.setVisibility(View.VISIBLE);  
			gap.setVisibility(View.VISIBLE);  
			material.setVisibility(View.VISIBLE);  
		}

	}

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

	void initView() {
		setContentView(R.layout.device_add);
		Button add = (Button)findViewById(R.id.save);
		Button cancel = (Button)findViewById(R.id.cancel);
		id = (EditText)findViewById(R.id.device_id);
		name = (EditText)findViewById(R.id.device_name);
		desc = (EditText)findViewById(R.id.device_desc);
		tempIn = (EditText)findViewById(R.id.device_temp_in);
		tempOut = (EditText)findViewById(R.id.device_temp_out);
		type = (Spinner)findViewById(R.id.device_type);
		specifications_select = (Spinner)findViewById(R.id.specifications_select);
		gap_select = (Spinner)findViewById(R.id.gap_select);
		material_select = (Spinner)findViewById(R.id.material_select);

		tempset = (LinearLayout)findViewById(R.id.tempset);
		specifications= (LinearLayout)findViewById(R.id.specifications);
		gap= (LinearLayout)findViewById(R.id.gap);
		material= (LinearLayout)findViewById(R.id.material);

		setTempSet(true);
		material_select.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				material_v = arg2;			
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		gap_select.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				gap_v= arg2;			
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		specifications_select.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				specifications_v= arg2;			
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				log("deviceType =="+arg2);
				deviceType = arg2;
				log("deviceType =="+deviceType);
				if(arg2 == 0)
					setTempSet(false);
				if(arg2 == 1)
					setTempSet(true);
			}
 
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
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
						try {
							d.deviceTempIn = Integer.parseInt(tempIn.getText().toString().trim());//tempIn.getText().toString();
							d.deviceTempOut = Integer.parseInt(tempOut.getText().toString().trim());//tempOut.getText().toString();
						}catch(java.lang.NumberFormatException e) {
							;
						}
						d.deviceSpec=specifications_v;
						d.deviceGap=gap_v;
						d.deviceMaterial=material_v;
						String json = d.d2j(d);
						log("json:"+json);
						Log.e("===",json);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
	}
    @Override
    public void onStart() {
        super.onStart();
		initView();
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
		showToast("设备添加成功");
		//Intent i = new Intent();
		//i.setClass(deviceAdd.this,MainActivity.class);
		//mC.startActivity(i);
		mA.finish();
	}
	private void onRegFail() {
		showToast("设备添加失败");
	}
	private void onRegFail(String s) {
		showToast(s);
	}

    private void log(String line) {
        l.d(line);
    }  

}

