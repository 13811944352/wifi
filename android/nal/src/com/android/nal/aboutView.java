package com.android.nal;

import android.content.Context;
import android.widget.Button;
import android.view.View;

import com.android.nal.utils.l;
import com.android.nal.service.MainService;
import com.android.nal.local.localConfig;
import com.android.nal.net.netConfig;
import java.util.ArrayList;



import com.lenovo.lps.sus.SUS;

public class aboutView extends viewBase{
	Button update;
	public aboutView(Context c,int id,deviceConfig d,nodeConfig n[],MainService s) {
		super(c,id,d,n,s);
        //initView();
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

	@Override
	public void initView() {

		update = (Button)findViewById(R.id.update);
		update.setOnClickListener(new updateClick());
	}

}

