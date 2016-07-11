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
import android.widget.TextView;

import com.android.nal.service.MainService;
import com.android.nal.utils.IDHelper;

public class deviceMainView extends viewBase{
	String temp[];
	public deviceMainView(Context c,int id,deviceConfig d,nodeConfig n[],MainService s) {
		super(c,id,d,n,s);
		mS = mainActivity.getService();
		temp = new String[8];
		for(int i = 0;i<8;i++){
			temp[i] = "syncing";
		}
		freshConnect();
	}
	
	boolean quit = true;

	void quitThread(){
		quit = true;
	}

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

	void setImage(ImageButton b,int id,String s) {
		Bitmap bm = drawTextToBitmap(mC,id,s,b);
		b.setImageBitmap(bm);
	}

	public Bitmap drawTextToBitmap(Context gContext,   int gResId,   String gText,View v) {  
		Resources resources = gContext.getResources();  
		float scale = resources.getDisplayMetrics().density;  
		Bitmap src=BitmapFactory.decodeResource(resources, gResId).copy(Bitmap.Config.ARGB_8888, true);
		int w = src.getWidth();  
		int h = src.getHeight();
		Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );
		Canvas cv = new Canvas( newb );
		cv.drawBitmap( src, 0, 0, null );
		Paint p= new Paint( Paint.ANTI_ALIAS_FLAG);  
		p.setStrokeWidth(3);  
		p.setTextSize(60);  
		p.setTextAlign(Align.LEFT);
		p.setColor( Color.RED);  


		Rect bounds = new Rect(); 
		p.getTextBounds(gText, 0, gText.length(), bounds);  
		FontMetricsInt fontMetrics = p.getFontMetricsInt();
		int baseline = (h - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		cv.drawText(gText,w/ 2 - bounds.width() / 2, baseline, p); 
		return newb;
	}


	void freshUITemp(){
		Message m = new Message();
		m.what = 0;
		uiHandle.sendMessage(m);
	}


/*
	void syncTemp() {
		for (int x = 0; x < 8; x++) {
			String t = mS.doQuery(mD.deviceId, "temp" + (x + 1));
			log("get "+x+" temp:"+t);
			temp[x] = t;
		}
	}
*/
	String getTemp(int id) {
		return temp[id];
	}

	@Override
	public void destroyView() {
		quit = true;
	}

	@Override
	public void initView() {
		log("initView");
		TextView t = (TextView)findViewById(IDHelper.getViewID(mC,"d_name"));
		t.setText(mD.deviceName);
		ImageButton b;
		//Button b;
		for(int x = 0 ;x < 8;x++) {
			final int nodeId = (x+1);
			b = (ImageButton) findViewById(IDHelper.getViewID(mC, "n0"+(x+1)));
			t = (TextView)findViewById(IDHelper.getViewID(mC,"n_name_0"+(x+1)));
			setImage(b,R.drawable.device_main_online,"offline");
			final nodeConfig n = mN[x];//netConfig.getInstance().getNodeConfig(mD.deviceId,""+(x+1));
			if(n == null || n.nodeName == null || n.nodeName.equals("")) {
				t.setText("未命名");
			} else {
				t.setText(n.nodeName);
			}

			String tt = getTemp(x);
			do{
			if(tt != null) {
				//b.setText(""+temp);
				if(n == null) {
					setImage(b,R.drawable.device_main_online,tt);
					break;
				}
				if(n.nodeConfig == 0) {
					setImage(b,R.drawable.device_main_online,tt);
					//b.setBackgroundResource(R.drawable.device_main_online);
				} else {
					if(n.nodeTemp == -101) {
						setImage(b,R.drawable.device_main_online_blue,tt);
						//b.setBackgroundResource(R.drawable.device_main_online_blue);
					}
					if(n.nodeTemp == -102) {
						setImage(b,R.drawable.device_main_online_yellow,tt);
						//b.setBackgroundResource(R.drawable.device_main_online_yellow);
					}
					if(n.nodeTemp == -103) {
						setImage(b,R.drawable.device_main_online_red,tt);
						//b.setBackgroundResource(R.drawable.device_main_online_red);
					}
				}
			}
			}while(false);

			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					//log("x=="+((Button)arg0).getText());
					Intent i = new Intent();
					if(mD.deviceType == 0)
						i.setClass(mC,nodeActivity.class);
					//if(mD.deviceType == 1)
					//	i.setClass(mC,nodeActivity1.class);
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
/*
	HandlerThread ht = null;
	private Handler mH;
	void initHandle() {
        ht = new HandlerThread("deviceMainView");
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

