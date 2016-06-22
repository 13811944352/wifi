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
import android.support.v4.view.ViewPager;
import java.util.List;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CheckBox;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class deviceInitActicity extends Activity {
	Context mC = null;
	deviceInitActicity mA = null;
	deviceConfig mD;
	nodeConfig mN[] = new nodeConfig[8];
	MainService mS;
	Intent mIntent;


    private Handler mH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case(0):
                    log("Handler 0");
                    //onRegSucess();
                    break;
                case(2):
                    log("Handler 2");
            //Message m = new Message();
            //m.what = 1;
            //mH.sendMessage(m);
            //}
                case(1):
                    log("Handler 1");
					freshUi();
                    break;
                default:
                    //onRegFail();
                    break;
            }
        }

    };


	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
		mIntent = getIntent(); 
		String xx = mIntent.getStringExtra("device");  
		mD = deviceConfig.j2d(xx);
		mS = MainActivity.getService();
	}
    @Override
    public void onResume() {
        super.onResume();
		init();
	}

	void freshUi() {
		log("freshUi");
        for(int x = 0 ;x < 8;x++) {
            CheckBox c  = (CheckBox) findViewById(IDHelper.getViewID(mC, "nodeCheck_"+x));
            EditText e  = (EditText) findViewById(IDHelper.getViewID(mC, "nodeName_"+x));
			c.setChecked(false);
			e.setText(null);
			if(mN[x] == null) continue;
/*
				boolean ret = c.isChecked();
				log(x+":save ret ="+ret);
*/

			if(e == null) {
				log(x+"e == null");
			} else {
				if(mN[x].nodeName == null || mN[x].nodeName.equals("")) {
					continue;
				}
				e.setText(mN[x].nodeName);
			}

		}

	}


	void initNodeConfig() {
        new Thread() {
                @Override
                public void run(){
                    mS.syncLocalNode(mD.deviceId);
					for(int i = 0;i<8;i++) {
						nodeConfig n = localConfig.getInstance().getNodeConfig(mD.deviceId,""+(i+1));
						//if(n == null)
						//	n = new nodeConfig();
						mN[i] = n;
					}
					log("Thread end");
                    Message m = new Message();
                    m.what = 1;
                    mH.sendMessage(m);
                }
         }.start();
	}

	void init() {
				log("init");
		initView();
		initNodeConfig();
	}

	void save() {
        for(int x = 0 ;x < 8;x++) {
            CheckBox c  = (CheckBox) findViewById(IDHelper.getViewID(mC, "nodeCheck_"+x));
            EditText e  = (EditText) findViewById(IDHelper.getViewID(mC, "nodeName_"+x));
			if(c == null) {
				log(x+"c == null");
			}else {
				boolean ret = c.isChecked();
				log(x+":save ret ="+ret);
			}
			if(e == null) {
				log(x+"e == null");
			}else {
				String r = e.getText().toString();   
				log(x+":save r ="+r);
			}

		}
	}

	void initView() {
		setContentView(R.layout.main_init);

		Button b = (Button)findViewById(R.id.save);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
/*
				localConfig.getInstance().deviceInit(mD.deviceId);
                Intent i = new Intent();
				i.setClass(deviceInitActicity.this,deviceActivity.class);
				i.putExtra("device",mD.d2j(mD));
                mC.startActivity(i);
                mA.finish();
*/
				save();
            }
        });
	}

    @Override
    public void onStart() {
        super.onStart();
		//init();
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    private void log(String line) {
        l.d(line);
    }  

}

