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
	boolean isModify = false;

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
    void updateNode() {
        new Thread() {
                @Override
                public void run(){
                    log("mN:"+mN.n2j(mN));
                    mS.syncNetNode(mN);
                }
         }.start();
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
/*
	String getTemp() {
		String temp = mS.doQuery(mD.deviceId,"temp"+(mId));
		return temp;
	}
	int setTemp(int type,String value) {
		int result = mS.doSet(mD.deviceId,"tempcfg"+(mId),type,value);
		return result;
	}
*/
	void initNodeName() {
		final TextView nodename = (TextView)findViewById(R.id.nodename);
		nodename.setText(mN.nodeName);
		if(mN.nodeName.equals("")) {
			nodename.setText("未命名");
		}
		Button nodename_modify = (Button)findViewById(R.id.nodename_modify);
		final EditText ett = new EditText(mC);
		nodename_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
					new AlertDialog.Builder(mC).
						setTitle("给房间改名:").
						setIcon(android.R.drawable.ic_dialog_info).
						setView(ett).
						setPositiveButton("确定", new DialogInterface.OnClickListener(){
    						public void onClick(DialogInterface arg0,int arg1){
								mN.nodeName = ett.getText().toString();  
								nodename.setText(mN.nodeName);
								//log("mN=="+mN.deviceId+"-"+mN.nodeId+"-"+mN.nodeName);
								//localConfig.getInstance().setNodeConfig(mN);
								//localConfig.getInstance().getNodeConfig(mD.deviceId,mId);
								isModify = true;
							}
						}).
						setNegativeButton("取消", null).
						show();
                }
		});
	}

/*
	int home_type_v = 0;
	int direction_v = 0;//(Spinner)findViewById(R.id.nodetype);
	int floor_v = 0;// (Spinner)findViewById(R.id.nodetype);
	int home_design_v = 0;//(Spinner)findViewById(R.id.nodetype);
*/

	void initView() {
		setContentView(R.layout.node_no_temp);
		initNodeName();
		Spinner home_type = (Spinner)findViewById(R.id.home_type);
		Spinner direction = (Spinner)findViewById(R.id.direction);
		Spinner floor = (Spinner)findViewById(R.id.floor);
		Spinner home_design = (Spinner)findViewById(R.id.home_design);

        home_type.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("onItemSelected arg2=="+arg2);
					mN.homeType = arg2;
								isModify = true;
					//localConfig.getInstance().setNodeConfig(mN);
				}

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );
        direction.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("onItemSelected arg2=="+arg2);
					mN.homeDire= arg2;
								isModify = true;
					//localConfig.getInstance().setNodeConfig(mN);
				}

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );
        floor.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("onItemSelected arg2=="+arg2);
					mN.homeFloor= arg2;
								isModify = true;
					//localConfig.getInstance().setNodeConfig(mN);
				}

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );
        home_design.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("onItemSelected arg2=="+arg2);
					mN.nodeTemp= arg2;
								isModify = true;
					//localConfig.getInstance().setNodeConfig(mN);
				}

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );

		//final EditText et = (EditText)findViewById(R.id.node_temp);
		final Button done = (Button)findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(isModify) {
                    //String  s = et.getText().toString();
                    //int temp = getSetTemp();// = -1;
                    mN.nodeTime = 18;
                    //log("set temp :"+temp);
                    updateNode();
                    //if(mN.nodeTime == 0)
                    //    setTemp(2,""+temp);
                }
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {
                    ;
                }
                mA.finish();
            }

/*
            @Override
            public void onClick(View arg0) {
                //mA.finish();
				String  s = et.getText().toString();
				int i = -1;
				try {
					i=Integer.parseInt(s);
				}catch(ece)
				mN.nodeConfig = i;
				//setTemp(2,""+i);
				localConfig.getInstance().setNodeConfig(mN);
            }
*/  
      });

		Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mA.finish();
            }
        });
/*
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

