package com.android.nal;

import android.text.InputFilter;  
import android.view.inputmethod.EditorInfo;
import android.text.Editable; 
import java.text.NumberFormat;
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
import android.content.res.Resources; 
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.text.TextWatcher; 

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;
import android.content.DialogInterface;


public class nodeActivity extends Activity {
	Context mC = null;
	nodeActivity mA = null;
	deviceConfig mD;
	String mId;
	nodeConfig mN = null;
	MainService mS = null;
	boolean isModify;

	Spinner mode_select;// = (Spinner)findViewById(R.id.mode_select);;
	LinearLayout smart;// = (LinearLayout) findViewById(R.id.smart);
	LinearLayout nosmart;// = (LinearLayout) findViewById(R.id.smart);
	Button safe;// = (Button)findViewById(R.id.safe);
	Button jieneng;// = (Button)findViewById(R.id.jieneng);
	Button fangdong;// = (Button)findViewById(R.id.fangdong);
	Button done;// = (Button)findViewById(R.id.done);
	Button timer;// = (Button)findViewById(R.id.done);
	EditText et;// = (EditText)findViewById(R.id.node_temp);

	private void showToast(String msg){
		Toast toast=Toast.makeText(mC, msg ,Toast.LENGTH_SHORT); 
		toast.show();
	}

	private nodeConfig initNodeConfig(deviceConfig d,String id) {
		nodeConfig n = new nodeConfig();
		n.deviceId = d.deviceId;
		n.nodeId = id;
		n.nodeConfig  = 0;
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
		log("nodeActivity getIntent:"+xx);
		mD = deviceConfig.j2d(xx);
		mId = i.getStringExtra("nodeId");
		xx = i.getStringExtra("node");
		if(xx.equals("null")) 
			mN = null;
		else
			mN = nodeConfig.j2n(xx);
		log("nodeActivity getDevice:"+xx);
		log("nodeActivity getId:"+mId);
		//mN = localConfig.getInstance().getNodeConfig(mD.deviceId,mId);
		if(mN == null) {
			mN = initNodeConfig(mD,mId);
		}

		log("mN:"+mN.n2j(mN));
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

	void setModify() {
		isModify = true;
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
					new AlertDialog.Builder(mC).
						setTitle("给房间改名:").
						setIcon(android.R.drawable.ic_dialog_info).
						setView(ett).
						setPositiveButton("确定", new DialogInterface.OnClickListener(){
    						public void onClick(DialogInterface arg0,int arg1){
								mN.nodeName = ett.getText().toString();  
								nodename.setText(mN.nodeName);
								log("mN=="+mN.deviceId+"-"+mN.nodeId+"-"+mN.nodeName);
								setModify();
							}
						}).
						setNegativeButton("取消", null).
						show();
                }
		});
	}



	void setMt() {
		smart.setVisibility(View.GONE);
		nosmart.setVisibility(View.VISIBLE);
		et.setText(""+(mN.nodeTemp));
	}


	void setSmart() {
		mode_select.setSelection(1,true);
		nosmart.setVisibility(View.GONE);
		smart.setVisibility(View.VISIBLE);
		if(mN.nodeTemp == -101) { //safe
			safe.setBackgroundResource(R.drawable.node_main_safe_select);
			jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
			fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
			return ;
		}
		if(mN.nodeTemp == -102) { //jieneng
			safe.setBackgroundResource(R.drawable.node_main_safe);
			jieneng.setBackgroundResource(R.drawable.node_main_jieneng_select);
			fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
			return ;
		}
		if(mN.nodeTemp == -103) { //fangdong
			safe.setBackgroundResource(R.drawable.node_main_safe);
			jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
			fangdong.setBackgroundResource(R.drawable.node_main_fangdong_select);
			return ;
		}
		safe.setBackgroundResource(R.drawable.node_main_safe);
		jieneng.setBackgroundResource(R.drawable.node_main_jieneng);
		fangdong.setBackgroundResource(R.drawable.node_main_fangdong);
	}

	void initView() {
		setContentView(R.layout.node_main);
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
		//timer = (Button)findViewById(R.id.timer);
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
					}
					if(arg2 == 1) {
						mN.nodeConfig = 1;
						setModify();
						setSmart();
					}
                }
 
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            }
        );

        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				mN.nodeTemp = -101;
				setModify();
				setSmart();
            }
        });
        jieneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				mN.nodeTemp = -102;
				setModify();
				setSmart();
            }
        });
        fangdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				mN.nodeTemp = -103;
				setModify();
				setSmart();
          }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				String  s = et.getText().toString();
				int i = -1;
				i=Integer.parseInt(s);
				mN.nodeTemp= i;
				setModify();
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				initTimeView();
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
				if(isModify) {
					mN.nodeTime = 0;
					updateNode();
					String  s = et.getText().toString();
					int temp = getSetTemp();// = -1;
					log("set temp :"+temp);
					if(mN.nodeTime == 0)
						setTemp(2,""+temp);
				}
				try {
					Thread.sleep(1000);
				} catch(Exception e) {
					;
				}
                mA.finish();
            }
        });
*/
	}

	private int getSetTemp() {
		log("set temp mN:"+mN.nodeTemp);
		log("set temp type:"+mN.nodeType);

		if(mN.nodeTemp > -50)
			return mN.nodeTemp;
		int v = -1;
		if(mN.nodeTemp== -101) {
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
		}
		if(mN.nodeTemp == -102) {
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
		}
		if(mN.nodeTemp == -103) {
				v = 5;
		}
		return v;
		
	}

	private void setDate(String id,Calendar c) {
		int i = mWeek;//= week.getSelectedItem();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		log("h:"+h+"  m:"+m);
		NumberFormat nf = NumberFormat.getInstance();  
        nf.setGroupingUsed(false);  
        nf.setMaximumIntegerDigits(2);  
        nf.setMinimumIntegerDigits(2); 
		TextView t = null;
		if(id.equals("b_s1")) {
			mN.nodeStart[i] = h*60+m;
			log("mN:"+mN.n2j(mN));
			t = t_s1;
		}
/*
		if(id.equals("b_s2")) {
			mN.nodeEnd[i] = h*60+m;
			t = t_s2;
		}
*/
		if(id.equals("b_e1")) {
			mN.nodeEnd[i] = h*60+m;
			log("mN:"+mN.n2j(mN));
			t = t_e1;
		}
/*
		if(id.equals("b_e2")) {
			t = t_e2;
		}
*/
		t.setText(nf.format(h)+":"+nf.format(m));
	}
	private View.OnClickListener temp_set = new View.OnClickListener(){
		@Override
        public void onClick(View arg0) {
			Resources res = mC.getResources();
			final String id = res.getResourceEntryName(arg0.getId());
			final EditText et=new EditText(mC);
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
			et.setInputType(EditorInfo.TYPE_CLASS_NUMBER); 
			//et.maxLength = 2;
			//et.numeric="integer";
    //android:text="0"
			new AlertDialog.Builder(mC)
				.setView(et).
				setTitle("dialog").
				setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
						log("aaaaaaaaaaaa:"+et.getText());
						if(id.equals("b_t1")) {
							String s = et.getText().toString();
							t1.setText(et.getText().toString());
							Integer integer;
							try {
								integer = Integer.valueOf(s.toString());
								int i = integer.intValue();
								mN.nodeTempS[mWeek] = i;
								log("mN:"+mN.n2j(mN));
								} catch (Exception e) {
									;
								}		
						}
						if(id.equals("b_t2")) {
							String s = et.getText().toString();
							t2.setText(et.getText().toString());
							Integer integer;
							try {
								integer = Integer.valueOf(s.toString());
								int i = integer.intValue();
								mN.nodeTempE[mWeek] = i;
								log("mN:"+mN.n2j(mN));
								} catch (Exception e) {
									;
								}
						}
					}
				})
				.create()
				.show();
		}
	};

	private View.OnClickListener time_select = new View.OnClickListener(){
		@Override
        public void onClick(View arg0) {
			Resources res = mC.getResources();
			final String id = res.getResourceEntryName(arg0.getId());
			final Calendar c = Calendar.getInstance();
			TimePickerDialog dialog = new TimePickerDialog(mC,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
                    public void onTimeSet(android.widget.TimePicker view, int h,int m) {
						c.set(Calendar.HOUR_OF_DAY,h);                        
						c.set(Calendar.MINUTE, m);                        
						c.set(Calendar.SECOND, 0);                        
						c.set(Calendar.MILLISECOND, 0);   
						log("time:"+h+":"+m);
						log("id:"+id);
						//if(id.equals(""))
						setDate(id,c);
					}
                }, 0,0,true);
            dialog.show();
        }
	};

	Button b_s1,b_e1,b_t1,b_t2;
	TextView t_s1,t_e1;
	TextView t1,t2;
	Button timesave,timecancel;
	Spinner week;
	int mWeek = 0;


	String getTime(int time) {
		NumberFormat nf = NumberFormat.getInstance();  
        nf.setGroupingUsed(false);  
        nf.setMaximumIntegerDigits(2);  
        nf.setMinimumIntegerDigits(2); 
		int h = time/60;
		int m = time%60;
		//t.setText(nf.format(h)+":"+nf.format(m));
		return(nf.format(h)+":"+nf.format(m));
	}

	void initTimeView() {
		setContentView(R.layout.node_time);
		Button timetemp = (Button)findViewById(R.id.timetemp);
		timetemp.setText(getTemp());
		b_s1 = (Button)findViewById(R.id.b_s1);
		b_t1 = (Button)findViewById(R.id.b_t1);
		b_e1 = (Button)findViewById(R.id.b_e1);
		b_t2 = (Button)findViewById(R.id.b_t2);
		timesave = (Button)findViewById(R.id.timesave);
		timecancel = (Button)findViewById(R.id.timecancel);

		t_s1 = (TextView)findViewById(R.id.t_s1);
		t_e1 = (TextView)findViewById(R.id.t_e1);
		t1 = (TextView)findViewById(R.id.t1);
		t2 = (TextView)findViewById(R.id.t2);
/*
		t1.addTextChangedListener(new TextWatcher() {
        @Override  
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,  int arg3) {  
        }  
        @Override  
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {  
        }  
         
        @Override  
        public void afterTextChanged(Editable s) {  
			int i = 0;
			log("t1:"+t1.getText());
			Integer integer;
			try {
			//integer = Integer.valueOf(t1.getText().toString());
			integer = Integer.valueOf(s.toString());
			i = integer.intValue();
			mN.nodeTempS[mWeek] = i;
			log("mN:"+mN.n2j(mN));
			} catch (Exception e) {
				;
			}		
        } 
        });

		t2.addTextChangedListener(new TextWatcher() {
        @Override  
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,  int arg3) {  
        }  
        @Override  
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {  
        }  
         
        @Override  
        public void afterTextChanged(Editable s) {  
			log("t2:"+t2.getText());
			int i = 0;
			Integer integer;
			try {
			integer = Integer.valueOf(s.toString());
			i = integer.intValue();
			mN.nodeTempE[mWeek] = i;
			log("mN:"+mN.n2j(mN));
			} catch (Exception e) {

			}
		}
        });
*/

		week = (Spinner)findViewById(R.id.week);

        b_t1.setOnClickListener(temp_set);
        b_t2.setOnClickListener(temp_set);


        b_s1.setOnClickListener(time_select);
        b_e1.setOnClickListener(time_select);
        week.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					log("arg2:"+arg2);
				//if(arg2 == 0) {
					t_s1.setText(getTime(mN.nodeStart[arg2]));
					t_e1.setText(getTime(mN.nodeEnd[arg2]));
					t1.setText(""+mN.nodeTempS[arg2]);
					t2.setText(""+mN.nodeTempE[arg2]);
					mWeek = arg2;
				//}
			}
 
             public void onNothingSelected(AdapterView<?> arg0) {
             }
        }
		);
        timecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mA.finish();
            }
        });
        timesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				//if(isModify) {
					mN.nodeTime = 1;
					updateNode();
					//String  s = et.getText().toString();
					//int temp = getSetTemp();// = -1;
					//log("set temp :"+temp);
					//if(mN.nodeTime == 0)
					//	setTemp(2,""+temp);
				//}
				try {
					Thread.sleep(1000);
				} catch(Exception e) {
					;
				}
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

