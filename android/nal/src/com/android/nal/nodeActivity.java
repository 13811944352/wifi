package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nal.service.MainService;
import com.android.nal.utils.l;


public class nodeActivity extends Activity {
	Context mC = null;
	nodeActivity mA = null;
	deviceConfig mD;
	String mId;

	nodeConfig mN = null;
	MainService mS = null;
	EditText temp_et = null;
	Button up,down;

	CheckBox r1,r2,r3,r4,r5;
	Button timer,save;

	Button back = null;
	ImageButton tempButton = null;
	TextView d_name = null;


	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

	private nodeConfig initNodeConfig(deviceConfig d,String id) {
		nodeConfig n = new nodeConfig(d.deviceId,id);
		n.nodeConfig  = 0;
		return n;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
		mS = mainActivity.getService();
		Intent i = getIntent(); 
		String xx = i.getStringExtra("device");  
		log("nodeActivity getIntent:"+xx);
		mD = deviceConfig.j2d(xx);
		mId = i.getStringExtra("nodeId");
		xx = i.getStringExtra("node");
		if(xx.equals("null")) 
			mN = null;
		else
			mN = nodeConfig.j2n(xx);
		log("nodeActivity getDevice:"+xx);
		log("nodeActivity getId:"+mId);
		//mN = localConfig.getInstance().getNodeConfig(mD.deviceId,mId);
		if(mN == null) {
			mN = initNodeConfig(mD,mId);
		}

		log("mN:"+mN.n2j(mN));
		if(mN != null) {
			initView();
		}
	}

	String getTemp() {
		String temp = mS.doQuery(mD.deviceId,"temp"+(mId));
		return temp;
	}

	int setTemp(int type,String value) {
		int result = mS.doSet(mD.deviceId,"tempcfg"+(mId),type,value);
		return result;
	}

	void setModify() {
		//isModify = true;
	}

	void updateNode() {
        new Thread() {
                @Override
                public void run(){
					log("mN:"+mN.n2j(mN));
                    mS.syncNetNode(mN);
                }
         }.start();
	}

	void initDisplayTemp() {
		;
	}

	void initSetTemp() {
		temp_et = (EditText)findViewById(R.id.temp);
		temp_et.setText(""+mN.nodeTemp);
		up = (Button)findViewById(R.id.up);
		down = (Button)findViewById(R.id.down);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				mN.nodeTemp++;
				if(mN.nodeTemp > 30)
					mN.nodeTemp = 30;
				temp_et.setText(""+mN.nodeTemp);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				mN.nodeTemp--;
				if(mN.nodeTemp < 5)
					mN.nodeTemp = 5;
				temp_et.setText(""+mN.nodeTemp);
            }
        });
	}
	void initNodeName() {
		final TextView d_name = (TextView)findViewById(R.id.d_name);
		d_name.setText(mN.nodeName);
		if(mN.nodeName.equals("")) {
			d_name.setText("未命名");
		}
	}


/*
	void setMt() {
		smart.setVisibility(View.GONE);
		nosmart.setVisibility(View.VISIBLE);
		et.setText(""+(mN.nodeTemp));
	}


	void setSmart() {
		mode_select.setSelection(1,true);
		nosmart.setVisibility(View.GONE);
		smart.setVisibility(View.VISIBLE);
		if(mN.nodeTemp == -101) { //safe
			safe.setBackgroundResource(R.drawable.node_main_safe_select);
			jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
			fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
			return ;
		}
		if(mN.nodeTemp == -102) { //jieneng
			safe.setBackgroundResource(R.drawable.node_main_safe);
			jieneng.setBackgroundResource(R.drawable.node_main_jieneng_select);
			fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
			return ;
		}
		if(mN.nodeTemp == -103) { //fangdong
			safe.setBackgroundResource(R.drawable.node_main_safe);
			jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
			fangdong.setBackgroundResource(R.drawable.node_main_fangdong_select);
			return ;
		}
		safe.setBackgroundResource(R.drawable.node_main_safe);
		jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
		fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
	}
*/

	void setMt() {
		r1.setChecked(false);
		r2.setChecked(false);
		r3.setChecked(false);
		r4.setChecked(true);
		r5.setChecked(false);
		mN.nodeConfig = 0;
		EditText temp_et = (EditText)findViewById(R.id.temp);
		temp_et.setText("" + mN.nodeTemp);
	}

	void setSafe() {
		r1.setChecked(true);
		r2.setChecked(false);
		r3.setChecked(false);
		r4.setChecked(false);
		r5.setChecked(false);
		mN.nodeConfig = 1;
		mN.nodeTemp = -101;
	}

	void setJieneng(){
		r1.setChecked(false);
		r2.setChecked(true);
		r3.setChecked(false);
		r4.setChecked(false);
		r5.setChecked(false);
		mN.nodeConfig = 1;
		mN.nodeTemp = -102;
	}

	void setFangdong(){
		r1.setChecked(false);
		r2.setChecked(false);
		r3.setChecked(true);
		r4.setChecked(false);
		r5.setChecked(false);
		mN.nodeConfig = 1;
		mN.nodeTemp = -103;
	}

	void setTime(){
		r1.setChecked(false);
		r2.setChecked(false);
		r3.setChecked(false);
		r4.setChecked(false);
		r5.setChecked(true);
		mN.nodeConfig = 2;
	}

	void initView() {
		setContentView(R.layout.node_main);
		initNodeName();
		initDisplayTemp();
		initSetTemp();
		Button save = (Button)findViewById(R.id.save);
		Button back = (Button)findViewById(R.id.back);
		Button timer = (Button)findViewById(R.id.timer);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				//if(isModify) {
					mN.nodeTime = 0;
					updateNode();
					String  s = temp_et.getText().toString();
					int temp = getSetTemp();// = -1;
					log("set temp :"+temp);
					if(mN.nodeTime == 0)
						setTemp(2,""+temp);
				//}
				try {
					Thread.sleep(1000);
				} catch(Exception e) {
					;
				}
                mA.finish();
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				Intent i = new Intent();
                i.setClass(mC,nodeTime.class);
				i.putExtra("device",mD.d2j(mD));
                i.putExtra("nodeId",""+mId);
                mC.startActivity(i);
          }
		});

		back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mA.finish();
            }
        });


		r1=(CheckBox)findViewById(R.id.rb1);
		r2=(CheckBox)findViewById(R.id.rb2);
		r3=(CheckBox)findViewById(R.id.rb3);
		r4=(CheckBox)findViewById(R.id.rb4);
		r5=(CheckBox)findViewById(R.id.rb5);

		r1.setOnClickListener(new CheckBox.OnClickListener() {
			@Override
			public void onClick(View v) {
				setSafe();
			}
		});

		r2.setOnClickListener(new CheckBox.OnClickListener() {
			@Override
			public void onClick(View v) {
				setJieneng();
			}
		});

		r3.setOnClickListener(new CheckBox.OnClickListener() {
			@Override
			public void onClick(View v) {
				setFangdong();
			}
		});

		r4.setOnClickListener(new CheckBox.OnClickListener() {
			@Override
			public void onClick(View v) {
				setMt();
			}
		});

		r5.setOnClickListener(new CheckBox.OnClickListener() {
			@Override
			public void onClick(View v) {
				setTime();
			}
		});

		if(mN.nodeConfig == 0) {
			setMt();
		} else if(mN.nodeConfig == 1) {
			if(mN.nodeTemp == -101) {
				setSafe();
			} else if(mN.nodeTemp == -102) {
				setJieneng();
			} else if(mN.nodeTemp == -103) {
				setFangdong();
			}
		} else if(mN.nodeConfig == 2) {
				setTime();
		}

/*
*/
	}

	private int getSetTemp() {
		log("set temp mN:"+mN.nodeTemp);
		log("set temp type:"+mN.nodeType);

		if(mN.nodeTemp > -50)
			return mN.nodeTemp;
		int v = -1;
		if(mN.nodeTemp== -101) {
			if(mN.nodeType == 0) 
				v = 20;
			if(mN.nodeType == 1) 
				v = 18;
			if(mN.nodeType == 2) 
				v = 18;
			if(mN.nodeType == 3) 
				v = 16;
			if(mN.nodeType == 4) 
				v = 18;
		}
		if(mN.nodeTemp == -102) {
			if(mN.nodeType == 0) 
				v = 18;
			if(mN.nodeType == 1) 
				v = 16;
			if(mN.nodeType == 2) 
				v = 16;
			if(mN.nodeType == 3) 
				v = 14;
			if(mN.nodeType == 4) 
				v = 18;
		}
		if(mN.nodeTemp == -103) {
				v = 5;
		}
		return v;
		
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
					//onRegSucess();
					break;
				case(1):
				default:
					//onRegFail();
					break;
			}
		}
		
	};


    private void log(String line) {
        l.d(line);
    }  

}

