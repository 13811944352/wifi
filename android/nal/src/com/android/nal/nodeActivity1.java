package com.android.nal;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;
import android.content.DialogInterface;


public class nodeActivity1 extends Activity {
	Context mC = null;
	nodeActivity1 mA = null;
	deviceConfig mD;
	String mId;
	nodeConfig mN = null;
	MainService mS = null;

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

	private nodeConfig initNodeConfig(deviceConfig d,String id) {
		nodeConfig n = new nodeConfig();
		n.deviceId = d.deviceId;
		n.nodeId = id;
		n.nodeConfig  = -1;
		return n;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
		mS = MainActivity.getService();
		Intent i = getIntent(); 
		String xx = i.getStringExtra("device");  
		log("nodeActivity1 getIntent:"+xx);
		mD = deviceConfig.j2d(xx);
		mId = i.getStringExtra("nodeId");
		log("nodeActivity1 getDevice:"+xx);
		log("nodeActivity1 nodeId:"+mId);
		mN = localConfig.getInstance().getNodeConfig(mD.deviceId,mId);
		if(mN == null) {
			mN = initNodeConfig(mD,mId);
		}
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

	void initNodeName() {
		final TextView nodename = (TextView)findViewById(R.id.nodename);
		nodename.setText(mN.nodeName);
		if(mN.nodeName.equals("")) {
			nodename.setText("未命名");
		}
		Button temp = (Button)findViewById(R.id.temp);
		temp.setText(getTemp());

		Button nodename_modify = (Button)findViewById(R.id.nodename_modify);
		final EditText ett = new EditText(mC);
		nodename_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //Intent i = new Intent();
                    //i.setClass(nodeActivity1.this,nodeActivity1.class);
                    //mC.startActivity(i);
					new AlertDialog.Builder(mC).
						setTitle("给房间改名:").
						setIcon(android.R.drawable.ic_dialog_info).
						setView(ett).
						setPositiveButton("确定", new DialogInterface.OnClickListener(){
    						public void onClick(DialogInterface arg0,int arg1){
								mN.nodeName = ett.getText().toString();  
								nodename.setText(mN.nodeName);
								log("mN=="+mN.deviceId+"-"+mN.nodeId+"-"+mN.nodeName);
								localConfig.getInstance().setNodeConfig(mN);
								//localConfig.getInstance().getNodeConfig(mD.deviceId,mId);
							}
						}).
						setNegativeButton("取消", null).
						show();
                }
		});
	}

	void initView() {
		setContentView(R.layout.node_no_temp);
		//initNodeName();
/*
		final LinearLayout smart = (LinearLayout) findViewById(R.id.smart);
		final LinearLayout nosmart = (LinearLayout) findViewById(R.id.nosmart);
		Spinner mode_select = (Spinner)findViewById(R.id.mode_select);;
		String[] m={"手动","智能"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode_select.setAdapter(adapter);

		Spinner nodetype = (Spinner)findViewById(R.id.nodetype);
		String[] mm={"卧室","客厅","书房","卫生间","其他"};
		adapter =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mm);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		nodetype.setAdapter(adapter);

		int type = mN.nodeType;
		if(type == -1)
			nodetype.setSelection(0,true);
		else
			nodetype.setSelection(type,true);

        nodetype.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("onItemSelected arg2=="+arg2);
					mN.nodeType = arg2;
					localConfig.getInstance().setNodeConfig(mN);
				}

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );

		int mode = mN.nodeConfig;//- 100;

		if(mode > 100) {
			mode_select.setSelection(1,true);
			nosmart.setVisibility(View.GONE);
			smart.setVisibility(View.VISIBLE);

		}else {
			mode_select.setSelection(0,true);
			smart.setVisibility(View.GONE);
			nosmart.setVisibility(View.VISIBLE);
		}
        //mode_select.setVisibility(View.VISIBLE);
        mode_select.setOnItemSelectedListener(new OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					if(arg2 == 0) {
						smart.setVisibility(View.GONE);
						nosmart.setVisibility(View.VISIBLE);
					}
					if(arg2 == 1) {
						nosmart.setVisibility(View.GONE);
						smart.setVisibility(View.VISIBLE);
					}
                }
 
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );
        //mode_select.setVisibility(View.VISIBLE);
		final Button safe = (Button)findViewById(R.id.safe);
		final Button jieneng = (Button)findViewById(R.id.jieneng);
		final Button fangdong = (Button)findViewById(R.id.fangdong);
		final Button done = (Button)findViewById(R.id.done);
		final EditText et = (EditText)findViewById(R.id.node_temp);
		if(mode == 101) {
				safe.setBackgroundResource(R.drawable.node_main_safe_select);
				jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
				fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
		}
		else if(mode == 102) {
				safe.setBackgroundResource(R.drawable.node_main_safe);
				jieneng.setBackgroundResource(R.drawable.node_main_jieneng_select);
				fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
		}
		else if(mode == 103) {
				safe.setBackgroundResource(R.drawable.node_main_safe);
				jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
				fangdong.setBackgroundResource(R.drawable.node_main_fangdong_select);
		}else {
			if(mode == -1) {
				et.setText("-1");
			} else 
				et.setText(""+(mode));
		}


        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				safe.setBackgroundResource(R.drawable.node_main_safe_select);
				jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
				fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
                //mA.finish();
				mN.nodeConfig = 101;
				int v = -1;
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
				setTemp(2,""+v);
				localConfig.getInstance().setNodeConfig(mN);
            }
        });
        jieneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				safe.setBackgroundResource(R.drawable.node_main_safe);
				jieneng.setBackgroundResource(R.drawable.node_main_jieneng_select);
				fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
				mN.nodeConfig = 102;
				int v = -1;
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
				setTemp(2,""+v);
				localConfig.getInstance().setNodeConfig(mN);
            }
        });
        fangdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				safe.setBackgroundResource(R.drawable.node_main_safe);
				jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
				fangdong.setBackgroundResource(R.drawable.node_main_fangdong_select);
				mN.nodeConfig = 103;
				int v = 5;
*/
/*
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
*/
/*
				setTemp(2,""+v);
				localConfig.getInstance().setNodeConfig(mN);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //mA.finish();
				String  s = et.getText().toString();
				int i = -1;
				//try {
					i=Integer.parseInt(s);
				//}catch(ece)
				mN.nodeConfig = i;
				setTemp(2,""+i);
				localConfig.getInstance().setNodeConfig(mN);
            }
        });


		Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mA.finish();
            }
        });
		Button menu = (Button)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //mA.finish();
            }
        });
*/
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

