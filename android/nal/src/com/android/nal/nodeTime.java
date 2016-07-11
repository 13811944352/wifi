package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.nal.service.MainService;
import com.android.nal.utils.l;


public class nodeTime extends Activity {
	Context mC = null;
	nodeTime mA = null;
	deviceConfig mD;
	String mId;
	nodeConfig mN = null;
	MainService mS = null;
	boolean isModify;

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

	private nodeConfig initNodeConfig(deviceConfig d,String id) {
		return null;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
		mS = mainActivity.getService();
/*
		Intent i = getIntent(); 
		String xx = i.getStringExtra("device");  
		log("nodeTime getIntent:"+xx);
		mD = deviceConfig.j2d(xx);
		mId = i.getStringExtra("nodeId");
		xx = i.getStringExtra("node");
		if(xx.equals("null")) 
			mN = null;
		else
			mN = nodeConfig.j2n(xx);
		log("nodeTime getDevice:"+xx);
		log("nodeTime getId:"+mId);
		//mN = localConfig.getInstance().getNodeConfig(mD.deviceId,mId);
		if(mN == null) {
			mN = initNodeConfig(mD,mId);
		}

		log("mN:"+mN.n2j(mN));
		if(mN != null) {
			initView();
		}
*/
			initView();
	}

	void initView() {
		setContentView(R.layout.node_time);
/*
		initNodeName();
		smart = (LinearLayout) findViewById(R.id.smart);
		nosmart = (LinearLayout) findViewById(R.id.nosmart);
		mode_select = (Spinner)findViewById(R.id.mode_select);;
		safe = (Button)findViewById(R.id.safe);
		jieneng = (Button)findViewById(R.id.jieneng);
		fangdong = (Button)findViewById(R.id.fangdong);
		et = (EditText)findViewById(R.id.node_temp);

		done = (Button)findViewById(R.id.done);
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
					//localConfig.getInstance().setNodeConfig(mN);
								setModify();
				}

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );

		int mode = mN.nodeConfig;
		mode_select.setSelection(mode,true);
		if(mode == 0) { //手动
			setMt();
		} else {
			setSmart();
		}
        mode_select.setOnItemSelectedListener(new OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					if(arg2 == 0) {
						mN.nodeConfig = 0;
						setModify();
						setMt();
						//smart.setVisibility(View.GONE);
						//nosmart.setVisibility(View.VISIBLE);
					}
					if(arg2 == 1) {
						mN.nodeConfig = 1;
						setModify();
						setSmart();
						//nosmart.setVisibility(View.GONE);
						//smart.setVisibility(View.VISIBLE);
					}
                }
 
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );
*/

/*
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
*/
/*		
		Button b;
		b = (Button)findViewById(R.id.safe_start);
		b.setOnClickListener(time_select);
		b = (Button)findViewById(R.id.safe_end);
		b.setOnClickListener(time_select);
		b = (Button)findViewById(R.id.jieneng_start);
		b.setOnClickListener(time_select);
		b = (Button)findViewById(R.id.jieneng_end);
		b.setOnClickListener(time_select);
		b = (Button)findViewById(R.id.fangdong_start);
		b.setOnClickListener(time_select);
		b = (Button)findViewById(R.id.fangdong_end);
		b.setOnClickListener(time_select);
*/
        //mode_select.setVisibility(View.VISIBLE);

/*
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
*/


		Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
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

    private void log(String line) {
        l.d(line);
    }  

}

