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
import android.widget.Spinner;
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

public class deviceActivity extends Activity {
	Context mC = null;
	deviceActivity mA = null;
	deviceConfig mD;
	MainService mS;
	Intent mIntent;
	//nodeConfig[] mN;

	Button b1,b2,b3,b4;
	Button back;
	MySpinnerButton menu;
	viewBase v1,v2,v3,v4;
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
		mIntent = getIntent(); 
		String xx = mIntent.getStringExtra("device");  
		mD = deviceConfig.j2d(xx);
		mS = MainActivity.getService();
		//mN = new nodeConfig[8];
		log("deviceActivity1 getIntent:"+xx);
	}
    @Override
    public void onResume() {
		init();
        super.onResume();
	}


	void init() {
		initViewBase();
		initView();
		initCallBack();
	}

	void initViewBase() {
		v1 = new deviceView1(mC,R.layout.device_main,mIntent,mH);
		v3 = new aboutView(mC,R.layout.about);
		v2 = new historyView(mC,R.layout.history,mIntent);
		v4 = new transView(mC,R.layout.trans,mIntent);
		lists = new ArrayList<View>();
        lists.add(v1.getView());
        lists.add(v2.getView());
        lists.add(v4.getView());
        lists.add(v3.getView());
        myAdapter = new MyAdapter(lists);
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

	class vClick implements View.OnClickListener{
		@Override
        public void onClick(View v) {
			int x = v.getId();
			if(x == R.id.main_ctl) 
				viewPager.setCurrentItem(0);
			if(x == R.id.default_ctl) 
				viewPager.setCurrentItem(3);
			if(x == R.id.his_data) 
				viewPager.setCurrentItem(1);
			if(x == R.id.trans) 
				viewPager.setCurrentItem(2);

        }
	}

	void initCallBack() {
		back.setOnClickListener(new backClick());
		menu.setOnClickListener(new menuClick());
		b1.setOnClickListener(new vClick());
		b2.setOnClickListener(new vClick());
		b3.setOnClickListener(new vClick());
		b4.setOnClickListener(new vClick());
	}

	ISpinnerCallback callBack = new ISpinnerCallback() {
		@Override
		public void onClick(int id) {
			log("id =="+id);
			viewPager.setCurrentItem(id);
		}
	};

	void initView() {
		setContentView(R.layout.device_main1);
		b1 = (Button)findViewById(R.id.main_ctl);
		b2 = (Button)findViewById(R.id.default_ctl);
		b3 = (Button)findViewById(R.id.his_data);
		b4 = (Button)findViewById(R.id.trans);
		back = (Button)findViewById(R.id.back);
		menu = (MySpinnerButton)findViewById(R.id.menu);
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("主控界面");
		temp.add("历史数据");
		temp.add("设备共享");
		temp.add("个人中心");
		menu.setText(temp,callBack);
		viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {                                 //当滑动式，顶部的imageView是通过animation缓慢的滑动
                // TODO Auto-generated method stub
                switch (arg0)
                {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                }
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

    @Override
    public void onStart() {
        super.onStart();
		//init();
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
					myAdapter.notifyDataSetChanged();				
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

