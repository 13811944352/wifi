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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import android.widget.Toast;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;


import com.lenovo.lps.sus.SUS;

public class aboutView extends viewBase{
	Context mC = null;
	Button update;
	public aboutView(Context c,int id) {
        super(c,id,null);
        mC = c;
        initView();
    }

    class updateClick implements View.OnClickListener{
        @Override
        public void onClick(View arg0) {

			if (!SUS.isVersionUpdateStarted()) {
				SUS.AsyncStartVersionUpdate(mC);
			} else {

			}

        }
    }  

	void initView() {
		update = (Button)findViewById(R.id.update);
		update.setOnClickListener(new updateClick());

	}

}

