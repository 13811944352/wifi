package com.android.nal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nal.net.netConfig;
import com.android.nal.service.MainService;

import java.util.ArrayList;
import java.util.List;

public class deviceActivity extends Activity {
	Context mC = null;
	deviceActivity mA = null;
	deviceConfig mD;
	nodeConfig mN[];
	MainService mS;
	Intent mIntent;

	
	public void updata(deviceConfig d,nodeConfig n[]) {
		android.util.Log.d("erer","activity updata");
		mD = d;
		mN = n;
	}

	void initNode(deviceConfig d,final boolean first){
		mD = d;
		new Thread() {
			@Override
			public void run(){
				if(mN == null)
					mN = new nodeConfig[8];
				for(int i = 0;i<8;i++) {
					mN[i] = netConfig.getInstance().getNodeConfig(mD.deviceId,""+(i+1));
					if(mN[i] == null)
						mN[i] = new nodeConfig(mD.deviceId,""+(i+1));
				}
				Message m = new Message();
				m.what = 0;
				if(first)
					m.what = 1;
				if(mH != null)
					mH.sendMessage(m);
			}
		}.start();
	}

	ImageView title;
	Button b1,b2,b3,b4;
	Button back;
	MySpinnerButton menu;
	viewBase v1,v2,v3,v4,v5;
	viewBase vv1,vv2,vv3,vv4;
	private ViewPager viewPager;
	private List<View> lists;// = new ArrayList<View>();
	MyAdapter myAdapter;

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
		initNode(mD,true);
		mIntent = getIntent(); 
		String xx = mIntent.getStringExtra("device");  
		mD = deviceConfig.j2d(xx);
		mS = mainActivity.getService();
		log("deviceActivity1 getIntent:" + xx);
	}
    @Override
    public void onResume() {
		initNode(mD,false);
        super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	void end() {
		if(mD.deviceType == 0) {
			if(v1!=null)
				v1.destroyView();
			if(v2!=null)
				v2.destroyView();
			if(v3!=null)
				v3.destroyView();
			if(v4!=null)
				v4.destroyView();
			if(v5!=null)
				v5.destroyView();
		}
		if(mD.deviceType == 1) {
			if(vv1!=null)
				vv1.destroyView();
			if(vv2!=null)
				vv2.destroyView();
			if(vv3!=null)
				vv3.destroyView();
			if(vv4!=null)
				vv4.destroyView();
		}
	}


	void fresh() {
		if(mD.deviceType == 0) {
			if(v1!=null)
				v1.setData(mD,mN);
			if(v2!=null)
				v2.setData(mD,mN);
			if(v3!=null)
				v3.setData(mD,mN);
			if(v4!=null)
				v4.setData(mD,mN);
			if(v5!=null)
				v5.setData(mD,mN);
		}
		if(mD.deviceType == 1) {
			if(vv1!=null)
				vv1.setData(mD,mN);
			if(vv2!=null)
				vv2.setData(mD,mN);
			if(vv3!=null)
				vv3.setData(mD,mN);
			if(vv4!=null)
				vv4.setData(mD,mN);
		}
	}

	void init() {
		initViewBase();
		initView();
		initCallBack();
		viewPager.setCurrentItem(0);
	}


	class backClick implements View.OnClickListener{
		@Override
        public void onClick(View arg0) {
			mA.finish();
        }
	}

	class menuClick implements View.OnClickListener{
		@Override
        public void onClick(View arg0) {
			//mA.finish();
        }
	}

	void initCallBack() {
		back.setOnClickListener(new backClick());
	}

	ISpinnerCallback callBack = new ISpinnerCallback() {
		@Override
		public void onClick(int id) {
			log("id =="+id);
			viewPager.setCurrentItem(id);
		}
	};


	void initViewBase() {
		if(mD.deviceType == 0) {
			if(v1 == null)
				v1 = new deviceMainView(mC,R.layout.device_main_view,mD ,mN,mS);
			if(v2 == null)
				v2 = new deviceHistoryView(mC,R.layout.device_history_view,mD ,mN,mS);
			if(v3 == null)
				v3 = new transView(mC,R.layout.trans_view,mD ,mN,mS);
			if(v4 == null)
				v4 = new aboutView(mC,R.layout.about_view,mD ,mN,mS);
			if(v5 == null)
				v5 = new deviceSystemView(mC,R.layout.device_system_view,mD,mN,mS,mA);
			if(lists == null) {
				lists = new ArrayList<View>();
				lists.add(v1.getView());
				lists.add(v2.getView());
				lists.add(v3.getView());
				lists.add(v4.getView());
				lists.add(v5.getView());
				myAdapter = new MyAdapter(lists);
			}
		}
		if(mD.deviceType == 1) {
			if(vv1 == null)
				vv1 = new deviceNotempMainView(mC,R.layout.device_notemp_main_view,mD ,mN,mS);
			if(vv2 == null)
				vv2 = new transView(mC,R.layout.trans_view,mD ,mN,mS);
			if(vv3 == null)
				vv3 = new aboutView(mC,R.layout.about_view,mD ,mN,mS);
			if(vv4 == null)
				vv4 = new deviceNotempSystemView(mC,R.layout.device_notemp_system_view,mD,mN,mS,mA);
			if(lists == null) {
				lists = new ArrayList<View>();
				lists.add(vv1.getView());
				lists.add(vv2.getView());
				lists.add(vv3.getView());
				lists.add(vv4.getView());
				myAdapter = new MyAdapter(lists);
			}
		}
	}

	void initView() {
		setContentView(R.layout.device_main);
		back = (Button)findViewById(R.id.back);
		title = (ImageView)findViewById(R.id.title);
		if(mD.deviceType == 0) {
			title.setBackgroundResource(R.drawable.device_main_main);
			v1.initView();
		} 
		if(mD.deviceType == 1) {
			title.setBackgroundResource(R.drawable.device_notemp_main_title);
			vv1.initView();
		} 
		menu = (MySpinnerButton)findViewById(R.id.menu);
		if(mD.deviceType == 0) {
			ArrayList<String> list = new ArrayList<String>();
			list.add("总控页面");
			list.add("历史数据");
			list.add("设备共享");
			list.add("个人中心");
			list.add("系统设置");
			menu.setText(list,callBack);
		}
		if(mD.deviceType == 1) {
			ArrayList<String> list = new ArrayList<String>();
			list.add("房间设置");
			list.add("设备共享");
			list.add("个人中心");
			list.add("系统设置");
			menu.setText(list,callBack);
		}
		viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {                                 //当滑动式，顶部的imageView是通过animation缓慢的滑动
                // TODO Auto-generated method stub
				//String t = null;
				int id = 0;
				viewBase v = null;
				android.util.Log.d("erer","pageview selected");
                switch (arg0)
                {

                case 0:
					if(mD.deviceType == 0) {
						//id = getResources().getDrawable(R.drawable.device_main_main);
						id = R.drawable.device_main_main;
						v = v1;
					}
					if(mD.deviceType == 1) {
						id=R.drawable.device_notemp_main_title;
						v = vv1;
					}
                    break;
                case 1:
					if(mD.deviceType == 0) {
						id = R.drawable.device_main_his;
						v = v2;
					}
					if(mD.deviceType == 1) {
						v = vv2;//.initView();
					}
                    break;
                case 2:
					if(mD.deviceType == 0) {
						id = 0;
						v = v3;
					}
					if(mD.deviceType == 1) {
						v = vv3;//.initView();
					}
					//title.setText("         设备共享");
					//v3.initView();
                    break;
                case 3:
					if(mD.deviceType == 0) {
						id = 0;
						v = v4;
					}
					if(mD.deviceType == 1) {
						id = R.drawable.device_notemp_system_title;
						v = vv4;
					}
                    break;
                case 4:
					if(mD.deviceType == 0) {
						id = R.drawable.device_main_system;
						v = v5;
					}
					if(mD.deviceType == 1) {
						//t = null;//("			系统设置");
						//v = vv4;//.initView();
					}
                    break;
                default :
                    break;
                }
				//title.setText(t);
				//title.setBackgroundResource(id);
				title.setBackgroundResource(id);
				v.initView();
            }

			@Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
		
		});
	}


	private Handler mH = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case(0):
					fresh();
					break;
				case(1):
					init();
					break;
				default:
					break;
			}
		}
		
	};

    private void log(String line) {
		android.util.Log.d("erer","activity updata");
    }  

}

