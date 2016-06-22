package com.android.nal;

import org.json.JSONObject;
import org.json.JSONArray;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;
import java.net.URLEncoder;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;
import java.util.ArrayList;
import com.android.nal.local.localConfig;

import android.app.Activity;
import android.os.Bundle;
import java.util.Date;
import java.util.Calendar;
import android.widget.TextView;
import android.os.Bundle; 
import android.widget.CheckBox; 
import android.widget.CompoundButton; 
import android.widget.EditText; 

import java.text.SimpleDateFormat; 

public class historyView extends viewBase{
	Context mC = null;
	LineGraphicView tu;
	ArrayList<Double> temp1 = new ArrayList<Double>();
	ArrayList<Double> temp2 = new ArrayList<Double>();
	ArrayList<Double> temp3 = new ArrayList<Double>();
	ArrayList<Double> temp4 = new ArrayList<Double>();
	ArrayList<Double> temp5 = new ArrayList<Double>();
	ArrayList<Double> temp6 = new ArrayList<Double>();
	ArrayList<Double> temp7 = new ArrayList<Double>();
	ArrayList<Double> temp8 = new ArrayList<Double>();
	ArrayList<String> time = new ArrayList<String>();
    deviceConfig mD;
    //MainService mS;

	//ArrayList<Double>[] temp = new ArrayList<Double>[8];

	public historyView(Context c,int id,Object o) {
        super(c,id,null);
        mC = c;
        //mS = MainActivity.getService();
        Intent intent = (Intent)o;
        String xx = intent.getStringExtra("device");  
        mD = deviceConfig.j2d(xx);
		setMH(tH);
		initNode(mD);
        //initView();
    }

    View findViewById(int id) {
        return v.findViewById(id);
    }

	String getYestoday() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String str=sdf.format(date);  
		return URLEncoder.encode(str);
	}

	ArrayList<String> getTime() {
		Date now = new Date();
		SimpleDateFormat hourFormat= new SimpleDateFormat("HH");//可以方便地修改日期格式
		SimpleDateFormat minFormat = new SimpleDateFormat("mm");//可以方便地修改日期格式
		String h = hourFormat.format(now);
		String m = minFormat.format(now);
		log(h+m); 
		return null;
	}


	void setData(ArrayList<Double> l,double v) {
		l.add(v);
	}

	String getString(JSONObject j,String k) {
		try {
			return j.getString(k);
		} catch(Exception e) {
			return null;
		}
	}

	ArrayList<Double> getList(String k) {
		if(k.equals("temp1"))
			return temp1;
		if(k.equals("temp2"))
			return temp2;
		if(k.equals("temp3"))
			return temp3;
		if(k.equals("temp4"))
			return temp4;
		if(k.equals("temp5"))
			return temp5;
		if(k.equals("temp6"))
			return temp6;
		if(k.equals("temp7"))
			return temp7;
		if(k.equals("temp8"))
			return temp8;
		return null;
	}

	void getData() {
		new Thread() {
			@Override
            public void run() {
				//dd();
				ArrayList<temp_historyConfig> his = netConfig.getInstance().getHistory(mD.deviceId,getYestoday());
				if(his == null) {
					return ;
				}
				for(int i = 0;i<his.size();i++) {
					temp_historyConfig d = his.get(i);
					log("history:"+d.temp);
					org.json.JSONArray ja = null;
					JSONObject json = null;
					try {
						ja = new org.json.JSONArray(d.temp);
						log("ja:"+ja);
					} catch(Exception e) {
						log("his:"+e);
						continue;
					}
					for(int k = 0;k<ja.length();k++) {
						try{
							json = ja.getJSONObject(k);
							log(""+json);
						} catch (Exception e) {
							continue;
						}
						for(int j = 1;j<9;j++) {
							if(json.has("temp"+j)) {
								log("has temp:"+j);
								ArrayList<Double> l = getList("temp"+j);
								log("l temp:"+j);
								String v = getString(json,"temp"+j);
								log("has temp:"+v);
								setData(l,Double.parseDouble(v));
								log("has temp:"+"end");
							}
							}
						}
						time.add(d.time);
				}

			for(int i = 0;i<temp1.size();i++) {
				log("temp1:"+temp1.get(i));
				log("time:"+time.get(i));
			}

				//tu.setData(temp1, time, 36, 2);
				tu.setData(time, 36, 2);
				tu.addData(temp1,0);
				tu.addData(temp2,1);
				tu.addData(temp3,2);
				tu.addData(temp4,3);
				tu.addData(temp5,4);
				tu.addData(temp6,5);
				tu.addData(temp7,6);
				tu.addData(temp8,7);

          }
		}.start();
	}

	void dd(){
/*
		ArrayList<Double> yList = new ArrayList<Double>();
		yList.add((double) 2.103);
		yList.add(4.05);
		yList.add(6.60);
		yList.add(3.08);
		yList.add(4.32);
		yList.add(2.0);
		yList.add(5.0);

		ArrayList<String> xRawDatas = new ArrayList<String>();
		xRawDatas.add("05-19");
		xRawDatas.add("05-20");
		xRawDatas.add("05-21");
		xRawDatas.add("05-22");
		xRawDatas.add("05-23");
		xRawDatas.add("05-24");
		xRawDatas.add("05-25");
		xRawDatas.add("05-26");
		tu.setData(yList, xRawDatas, 22, 2);


		ArrayList<Double> y1= new ArrayList<Double>();
		y1.add(1.103);
		y1.add(1.05);
		y1.add(1.60);
		y1.add(1.08);
		y1.add(1.32);
		y1.add(1.0);
		y1.add(1.0);
		tu.addData(y1);
		ArrayList<Double> y2= new ArrayList<Double>();
		y2.add(2.103);
		y2.add(2.05);
		y2.add(3.60);
		y2.add(4.08);
		y2.add(5.32);
		y2.add(6.0);
		y2.add(7.0);
		tu.addData(y2);
*/
	}

	void initView() {
		tu = (LineGraphicView) findViewById(R.id.line_graphic);
		for(int x = 0 ;x < 8;x++) {
            final int id = x;
            TextView t = (TextView)findViewById(IDHelper.getViewID(mC,"n"+(x+1)));
			t.setText("fangjian"+id);
			if(mN == null) {
				log("mN[x].nodeName =="+mN[x].nodeName);
				t.setText(mN[x].nodeName);
			}
			t.setText("fangjian"+id);
			CheckBox c = (CheckBox)findViewById(IDHelper.getViewID(mC,"c"+(x+1)));
			c.setChecked(true);  
			c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                if(isChecked){ 
					if(id == 0)
						tu.addData(temp1,0);
					if(id == 1)
						tu.addData(temp2,1);
					if(id == 2)
						tu.addData(temp3,2);
					if(id == 3)
						tu.addData(temp4,3);
					if(id == 4)
						tu.addData(temp5,4);
					if(id == 5)
						tu.addData(temp6,5);
					if(id == 6)
						tu.addData(temp7,6);
					if(id == 7)
						tu.addData(temp8,7);
                }else{ 
					tu.rmData(id);
                }	
				tu.postInvalidate(); 
            } 
        });
		}
		getData();
	}

    private Handler tH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case(0):
                    initView();
                    break;
                case(1):
                default:
                    break;
            }
        }
    
    }; 

}

