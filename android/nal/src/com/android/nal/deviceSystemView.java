package com.android.nal;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.nal.service.MainService;
import com.android.nal.utils.*;

public class deviceSystemView extends viewBase{
	Handler mmH;

	TextView d_name;
	Button save;

	void updateNode() {
        new Thread() {
			@Override
            public void run(){
				for(int x = 0 ;x < 8;x++) {
					mS.syncNetNode(mN[x]);
					log("now save name "+x+" == " + mN[x].nodeName);
				}
            }
         }.start();
    }


	void save(){
		EditText t = null;
		for(int x = 0 ;x < 8;x++) {
			t = (EditText)findViewById(IDHelper.getViewID(mC,"system_n"+(x+1)));
			String nodeName = t.getText().toString().trim();
			if(t == null || t.equals(""))
				mN[x].nodeName="未命名";
			mN[x].nodeName = t.getText().toString();;
			log("save name "+x+" == "+  mN[x].nodeName);
		}
		updateNode();

			try {
				Thread.sleep(1000);
            } catch(Exception e) {
                ;
            }
			updata();
			showToast("更新成功");
	}

	public deviceSystemView(Context c,int id,deviceConfig d,nodeConfig n[],MainService s,deviceActivity a) {
		super(c,id,d,n,s,a);
		for(int i = 0;i<8;i++) {
		}
	}

	@Override
	public void initView() {
		for(int i = 0;i<8;i++) {
		}
		d_name = (TextView)findViewById(IDHelper.getViewID(mC,"d_name"));
		d_name.setText(mD.deviceName);
		save = (Button)findViewById(IDHelper.getViewID(mC,"system_save"));
		EditText t = null;
		for(int x = 0 ;x < 8;x++) {
			t = (EditText)findViewById(IDHelper.getViewID(mC,"system_n"+(x+1)));
			final nodeConfig n = mN[x];
			log("display name "+x+" == " +mN[x].nodeName);
			if(n == null || n.nodeName == null || n.nodeName.equals("")) {
				t.setText("未命名");
			} else {
				t.setText(n.nodeName);
			}
		}

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				save();
			}
		});

	}
	@Override
    void log(String line) {
        android.util.Log.d("erer",line);
    }		

}

