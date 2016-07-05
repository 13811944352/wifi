package com.android.nal;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.content.DialogInterface;
import android.app.AlertDialog;
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

import android.widget.Toast;
import com.android.nal.utils.l;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import com.android.nal.service.MainService;

public class MainActivity extends Activity {
	Context mC = null;
	Activity mA = null;
	public static MainService mS = null;
    SimpleAdapter adapter = null;
	List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	IStatus cb = new IStatus() {
		@Override
		public void onChange() {
			log("onChange");
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
                case(1):
					log("Handler 1");
					adapter.notifyDataSetChanged();  
					break;
                default:
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
        //adapter = new SimpleAdapter(this,mData,R.layout.ll,
        //        new String[]{"title","info","img","del"},
        //        new int[]{R.id.title,R.id.info,R.id.ll_dev,R.id.ll_del});
        adapter = new SimpleAdapter(this,mData,R.layout.ll,
                new String[]{"title","info","img","del"},
                new int[]{R.id.title,R.id.info,R.id.ll_dev,R.id.ll_del});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int pos, long id) {
				Map<String, Object> m = mData.get(pos);
				String name = (String)m.get("title");
				deviceConfig d = (deviceConfig)m.get(name);
				String stat = (String)m.get("info");
				if(stat.equals("offline")) {
					log("no temp1");
					showToast("device offline");
				} 
				//if(stat.equals("online")) {
					Intent i = new Intent();
					//if(localConfig.getInstance().isDeviceInit(d.deviceId))
						i.setClass(MainActivity.this,deviceActivity.class);
					//else
					//	i.setClass(MainActivity.this,deviceInitActicity.class);
					i.putExtra("device",d.d2j(d));
					mC.startActivity(i);
				//}
				log("temp1 =="+stat);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {  
            @Override  
            public boolean onItemLongClick(AdapterView<?> parent, View view,int pos,long id){
				showDelDialog(pos);
				return true;

            }  
        }); 
	}

	void delDev(final deviceConfig d) {
        new Thread() {
                    @Override
                    public void run() {
                        boolean ret = netConfig.getInstance().delDeviceConfig(d);
						if(mS != null)
							freshMdata();
/*
                        if(ret == true ) { 
                            Message m = new Message();
                            m.what = 0;
                            mH.sendMessage(m);
                        } else {
                            Message m = new Message();
                            m.what = 1;
                            mH.sendMessage(m);
                        }
*/
                    }
                }.start();
	}

	void showDelDialog(final int pos) {
		new AlertDialog.Builder(mC).setTitle("确认删除该设备")
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
				@Override  
				public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
					//finish();  
					Map<String, Object> m = mData.get(pos);
					String name = (String)m.get("title");
					deviceConfig d = (deviceConfig)m.get(name);
					String stat = (String)m.get("info");
					log("name=="+name);
					delDev(d);
				}  
			})
			.setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮  
				@Override  
				public void onClick(DialogInterface dialog, int which) {//响应事件  
					log("cancel");
				}  
			})
			.show();//在按键响应事件中显示此对话框  
	}  
  

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mC = this;
		mA = this;
	}

	private List<Map<String, Object>> getDevice() {
		List<deviceConfig> device = mS.getDeviceList();//netConfig.getInstance().getDeviceList();
		if(device == null) {
			log("devie == null");
			return null;
		}
		//mData.clear();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for(deviceConfig d:device)  
        {  
			log("now add one");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", d.deviceName);
			String temp1 = mS.doQuery(d.deviceId,"temp1");
			
			if(temp1 == null)
				map.put("info", "offline");
				//map.put("info", "online");
			else
				map.put("info", "online");
			map.put("img", R.drawable.ll_dev);
			map.put("del", R.drawable.ll_del);
			map.put(d.deviceName,d);
			data.add(map);
			mData.clear();
			mData.addAll(data);
			//mData.add(map);
			freshUi();
        } 
		return null;
	}

    @Override
    public void onStart() {
        super.onStart();
		initView();
		bindService();
		if(mS != null)
			freshMdata();
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

	private void freshUi() {
		Message m = new Message();
        m.what = 1;
        mH.sendMessage(m);
	}

	private void initNet() {
		new Thread() {
			@Override
			public void run() {
				if(mS != null)
					mS.start();
					//boolean ret = mS.start();
			}
        }.start();
	}


	private void freshMdata() {
		new Thread() {
				@Override
				public void run(){
					getDevice();
					log("onServiceConnected getData end");
					freshUi();
					for(int i = 0;i < mData.size();i++) {
						Map<String, Object> map = mData.get(i);
						String name = (String)map.get("title");
						deviceConfig d = (deviceConfig)map.get(name);
						mS.syncLocalNode(d.deviceId);
					}
				}
         }.start();
		
	}

	private ServiceConnection mSc = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			mS = ((MainService.MainServiceBinder)service).getService();
			initNet();
			if(mS != null)
				freshMdata();
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

