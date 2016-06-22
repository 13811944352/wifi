package com.android.nal;
/*
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.res.Configuration;
import android.net.Uri;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.webkit.WebView;  
import android.webkit.WebViewClient;
*/
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.content.ComponentName;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.SimpleAdapter;
import android.widget.ListAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.os.Message;
/*
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter.ViewBinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
*/
import android.widget.Toast;
import com.android.nal.utils.l;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import com.android.nal.service.MainService;

//public class MainActivity extends ListActivity {
public class MainActivity extends Activity {
	Context mC = null;
	Activity mA = null;
	public static MainService mS = null;
    SimpleAdapter adapter = null;//new SimpleAdapter(this,getDevice(),R.layout.ll,
	List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	IStatus cb = new IStatus() {
		@Override
		public void onChange() {
			log("onChange");
			//if(adapter != null)
			//	adapter.notifyDataSetChanged();
		}

	};

	public static MainService getService() {
		return mS;
	}

    private void showToast(String msg){
        Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
        toast.show();
    }   

    private Handler mH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case(0):
					log("Handler 0");
					if(mS != null)
						mS.start();
                    //onRegSucess();
                    break;
				case(2):
					log("Handler 2");
					if(mS != null) {
						getDevice();
					}
			//Message m = new Message();
            //m.what = 1;
            //mH.sendMessage(m);
			//}
                case(1):
					log("Handler 1");
					adapter.notifyDataSetChanged();  
					break;
                default:
                    //onRegFail();
                    break;
            }
        }

    };


	void initView() {
		setContentView(R.layout.main);
		ListView listView = (ListView) findViewById(R.id.lv);

		Button config = (Button)findViewById(R.id.config);
		config.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(MainActivity.this,CustomizedActivity.class);
				mC.startActivity(i);
				//mA.finish();
			}
		});
		Button logout = (Button)findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				localConfig.getInstance().logout();
				mS.stop();
				Intent i = new Intent();
				i.setClass(MainActivity.this,WelcomeActivity.class);
				mC.startActivity(i);
				mA.finish();
			}
		});
		Button button = (Button)findViewById(R.id.add);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(MainActivity.this,deviceAdd.class);
				mC.startActivity(i);
			}
		});
        //adapter = new SimpleAdapter(this,getDevice(),R.layout.ll,
        adapter = new SimpleAdapter(this,mData,R.layout.ll,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.ll_dev});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int pos, long id) {
				//List<deviceConfig> device = localConfig.getInstance().getAllDevice();
				Map<String, Object> m = mData.get(pos);
				String name = (String)m.get("title");
				deviceConfig d = (deviceConfig)m.get(name);
				String stat = (String)m.get("info");
				//log("did:"+d.deviceId);
				//String stat = mS.doQuery(d.deviceId,"temp1");
				if(stat.equals("offline")) {
					log("no temp1");
					showToast("device offline");
				} 
				if(stat.equals("online")) {
					Intent i = new Intent();
					//if(localConfig.getInstance().isDeviceInit(d.deviceId))
						i.setClass(MainActivity.this,deviceActivity.class);
					//else
					//	i.setClass(MainActivity.this,deviceInitActicity.class);
					i.putExtra("device",d.d2j(d));
					mC.startActivity(i);
				}
				log("temp1 =="+stat);
			}
		});

	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
	}

	private List<Map<String, Object>> getDevice() {
		//List<Map<String, Object>> m = new ArrayList<Map<String, Object>>();
		//mData = new ArrayList<Map<String, Object>>();
		List<deviceConfig> device = mS.getDeviceList();//netConfig.getInstance().getDeviceList();
		if(device == null) {
			log("devie == null");
			return null;
		}
		mData.clear();
		for(deviceConfig d:device)  
        {  
			log("now add one");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", d.deviceName);
			//String temp1 = mS.doQuery(d.deviceId,"temp1");
			//int set = mS.doSet(d.deviceId,"settemp1",0,"28");
			//int set = mS.doSet(d.deviceId,"setright1",0,"false");
			//log("now set :"+set);
			String temp1 = mS.doQuery(d.deviceId,"temp1");
			
			if(temp1 == null)
				map.put("info", "offline");
				//map.put("info", "online");
			else
				map.put("info", "online");
			map.put("img", R.drawable.ll_dev);
			map.put(d.deviceName,d);
			mData.add(map);
        } 
		return null;
	}

    @Override
    public void onStart() {
        super.onStart();
		initView();
		bindService();
		//if(mS != null)
		//	freshMdata();
/*
        Message m = new Message();
        m.what = 2;
        mH.sendMessage(m);
*/
	}

    @Override
    public void onResume() {
        super.onResume();
	}
	

    @Override
    public void onDestroy() {
        super.onDestroy();
		unbindService();
	}

	private void freshMdata() {
		new Thread() {
				@Override
				public void run(){
					boolean ret = mS.start();
					if(!ret )
						log("mS.start failed");
					log("onServiceConnected getData");
					//mData = mS.getDeviceList();
					getDevice();
					log("onServiceConnected getData end");
					//adapter.setListData(mData); 				
                    Message m = new Message();
                    m.what = 1;
                    mH.sendMessage(m);
					for(int i = 0;i < mData.size();i++) {
						Map<String, Object> map = mData.get(i);
						String name = (String)map.get("title");
						deviceConfig d = (deviceConfig)map.get(name);
						mS.syncLocalNode(d.deviceId);
					}
					//mHandler.sendEmptyMessageDelayed(, 0);
				}
         }.start();
		
	}

	private ServiceConnection mSc = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			mS = ((MainService.MainServiceBinder)service).getService();
			freshMdata();
/*
            Message m = new Message();
            m.what = 0;
            mH.sendMessage(m);
            m = new Message();
            m.what = 2;
            mH.sendMessage(m);
*/
		}

		public void onServiceDisconnected(ComponentName name) {
			mS = null;
		}
    };
	

	void bindService() {
		mC.bindService(new Intent(mC, MainService.class), mSc, Context.BIND_AUTO_CREATE);
	}

	void unbindService() {
		mC.unbindService(mSc);
	}

	void log(String line) {
		l.d("MainActivity:"+line);
	}
}

