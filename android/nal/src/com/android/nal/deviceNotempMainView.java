package com.android.nal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import com.android.nal.service.MainService;
import com.android.nal.utils.IDHelper;

public class deviceNotempMainView extends viewBase{
	String temp[];
	public deviceNotempMainView(Context c,int id,deviceConfig d,nodeConfig n[],MainService s) {
		super(c,id,d,n,s);
		mS = mainActivity.getService();
		temp = new String[8];
		for(int i = 0;i<8;i++){
			temp[i] = "syncing";
		}
		//freshConnect();
	}
	
	boolean quit = true;

	//nodeConfig mN[8];
	TextView ctl,type,direction,design;
	Button ctl_u,ctl_d,type_u,type_d,direction_u,direction_d,design_u,design_d;
	int ctl_v = 0;
	String ctl_value[] = {"No.1","No.2","No.3","No.4"};
	int type_v = 0;
	String type_value[]= {"卧室","其他"};
	int direction_v = 0;
	String direction_value[] = {"北","南","东/西","东南/西南"};
	int design_v = 0;
	String design_value[] = {"实木地板","瓷砖"};

	void initButton(){
		Button b;
		for(int x = 0 ;x < 8;x++) {
			//final int nodeId = (x + 1);
			b = (Button) findViewById(IDHelper.getViewID(mC, "b" + (x + 1)));
			//t = (TextView)findViewById(IDHelper.getViewID(mC,"n_name_0"+(x+1)));
			//setImage(b,R.drawable.device_main_online,"offline");
			//final nodeConfig n = mN[x];//netConfig.getInstance().getNodeConfig(mD.deviceId,""+(x+1));
			//if(n == null || n.nodeName == null || n.nodeName.equals("")) {
			//	t.setText("未命名");
			//} else {
			//	t.setText(n.nodeName);
			//}
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					for(int i = 0 ;i < 8;i++) {
						Button tmp = (Button) findViewById(IDHelper.getViewID(mC, "b" + (i + 1)));
						//Resources resource = (Resources) getBaseContext().getResources();
						//ColorStateList csl = (ColorStateList) mC.getResources().getColorStateList(R.color.black);
						tmp.setTextColor(mC.getResources().getColor(R.color.black));
						//tmp.setTextColor(0);
					}
					((Button)arg0).setTextColor(mC.getResources().getColor(R.color.red));
					//log("x=="+((Button)arg0).getText());
					//Intent i = new Intent();
					//if(mD.deviceType == 0)
					//	i.setClass(mC,nodeActivity.class);
					//if(mD.deviceType == 1)
					//	i.setClass(mC,nodeActivity1.class);
					//log("mD:"+mD.d2j(mD));
					//log("id:"+nodeId);
					//i.putExtra("device",mD.d2j(mD));
					//i.putExtra("nodeId",""+nodeId);
					//if(n != null) {
					//	i.putExtra("node",n.n2j(n));
					//} else {
					//	i.putExtra("node","null");
					//}
					//mC.startActivity(i);
				}
			});
		}
	}

	void initDesign(){
		design = (TextView)findViewById(IDHelper.getViewID(mC,"design"));
		design.setText(design_value[design_v]);
		design_u = (Button)findViewById(IDHelper.getViewID(mC,"design_up"));
		design_d = (Button)findViewById(IDHelper.getViewID(mC,"design_down"));

		design_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				design_v--;
				if (design_v < 0)
					design_v = 0;
				design.setText(design_value[design_v]);
			}
		});

		design_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				design_v++;
				if(design_v >= design_value.length)
					design_v = design_value.length - 1;
				design.setText(design_value[design_v]);
			}
		});
	}

	void initDirection(){
		direction = (TextView)findViewById(IDHelper.getViewID(mC,"direction"));
		direction.setText(direction_value[direction_v]);
		direction_u = (Button)findViewById(IDHelper.getViewID(mC,"direction_up"));
		direction_d = (Button)findViewById(IDHelper.getViewID(mC,"direction_down"));

		direction_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				direction_v--;
				if (direction_v < 0)
					direction_v = 0;
				direction.setText(direction_value[direction_v]);
			}
		});

		direction_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				direction_v++;
				if(direction_v >= direction_value.length)
					direction_v = direction_value.length - 1;
				direction.setText(direction_value[direction_v]);
			}
		});
	}

	void initType(){
		type = (TextView)findViewById(IDHelper.getViewID(mC,"type"));
		type.setText(type_value[type_v]);
		type_u = (Button)findViewById(IDHelper.getViewID(mC,"type_up"));
		type_d = (Button)findViewById(IDHelper.getViewID(mC,"type_down"));

		type_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				type_v--;
				if (type_v < 0)
					type_v = 0;
				type.setText(type_value[type_v]);
			}
		});

		type_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				type_v++;
				if(type_v >= type_value.length)
					type_v = type_value.length - 1;
				type.setText(type_value[type_v]);
			}
		});
	}

	void initCtl(){
		log("initCtl");
		ctl = (TextView)findViewById(IDHelper.getViewID(mC,"ctl"));
		ctl.setText(ctl_value[0]);
		ctl_u = (Button)findViewById(IDHelper.getViewID(mC,"ctl_up"));
		ctl_d = (Button)findViewById(IDHelper.getViewID(mC,"ctl_down"));

		ctl_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				log("ctl_u");
				ctl_v--;
				if(ctl_v < 0)
					ctl_v = 0;
				log("ctl_u set:"+ctl_value[ctl_v]);
				ctl.setText(ctl_value[ctl_v]);
			}
		});

		ctl_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				log("ctl_d");
				ctl_v++;
				if(ctl_v >= ctl_value.length)
					ctl_v = ctl_value.length-1;
				log("ctl_d set:"+ctl_value[ctl_v]);
				ctl.setText(ctl_value[ctl_v]);
			}
		});
	}

	@Override
	public void initView() {
		initCtl();
		initType();
		initDirection();
		initDesign();
		initButton();
	}

	void quitThread(){
		quit = true;
	}
/*
	void freshConnect() {
		if(quit = false)
			return;
		quit = false;
		log("thread start");
		new Thread() {
            @Override
            public void run() {
				while(true) {
					for (int x = 0; x < 8; x++) {
						String t = mS.doQuery(mD.deviceId, "temp" + (x + 1));
						log("get "+x+" temp:"+t);
						temp[x] = t;
						freshUITemp();
					}
					if(quit)
						break;
					try {
						Thread.sleep(30*1000) ;
					} catch(Exception e) {

					}
				}
			}
		}.start();
		log("thread");
	}
	*/



	void freshUITemp(){
		Message m = new Message();
		m.what = 0;
		uiHandle.sendMessage(m);
	}


/*

*/
	String getTemp(int id) {
		return temp[id];
	}

	@Override
	public void destroyView() {
		quit = true;
	}


/*
	HandlerThread ht = null;
	private Handler mH;
	void initHandle() {
        ht = new HandlerThread("deviceView");
        try{
            ht.start();
        }catch(IllegalThreadStateException e){
            e.printStackTrace();
        }
		mH = new devicehandle(ht.getLooper());
		//freshUITemp();
		//freshUITemp_delay();
	}

	private class devicehandle extends Handler {
		public devicehandle(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			onHandleIntent(msg);
		}

		void onHandleIntent(Message message) {
			int m = message.what;
			switch (m) {
				case (0):
					log("case 0");
					syncTemp();
					freshUITemp();
					freshTemp_delay();
					break;
				default:
					break;
			}
		}
	}
*/

	private Handler uiHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
/*
			if(msg.getTarget() == null)
				if(mH != null)
					mH.getLooper().quitSafely();
*/
			switch(msg.what) {
				case(1):
					//syncTemp();
					//freshUITemp_delay();
					break;
				case(0):
					initView();
				default:
					break;
			}
		}
		
	};

}

