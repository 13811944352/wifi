package com.android.nal;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.android.nal.service.MainService;
import com.android.nal.utils.*;

public class deviceNotempSystemView extends viewBase{
	Handler mmH;

	TextView d_name;
	Button save;

	void updateNode() {
    }


	void save(){
	}

	public deviceNotempSystemView(Context c,int id,deviceConfig d,nodeConfig n[],MainService s,deviceActivity a) {
		super(c,id,d,n,s,a);
	}

	TextView specifications,gap,material;
	Button specifications_u,specifications_d,gap_u,gap_d,material_u,material_d;
	int specifications_v = 0;
	String specifications_value[] = {"16管径","20管径","25管径"};
	int gap_v = 0;
	String gap_value[]= {"150间距","200间距","300间距"};
	int material_v = 0;
	String material_value[] = {"PE-RT","PB"};
	void initMaterial(){
		material = (TextView)findViewById(IDHelper.getViewID(mC,"material"));
		material.setText(material_value[material_v]);
		material_u = (Button)findViewById(IDHelper.getViewID(mC,"material_up"));
		material_d = (Button)findViewById(IDHelper.getViewID(mC,"material_down"));

		material_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				material_v--;
				if (material_v < 0)
					material_v = 0;
				material.setText(material_value[material_v]);
			}
		});

		material_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				material_v++;
				if(material_v >= material_value.length)
					material_v = material_value.length - 1;
				material.setText(material_value[material_v]);
			}
		});
	}
	void initGap(){
		gap = (TextView)findViewById(IDHelper.getViewID(mC,"gap"));
		gap.setText(gap_value[gap_v]);
		gap_u = (Button)findViewById(IDHelper.getViewID(mC,"gap_up"));
		gap_d = (Button)findViewById(IDHelper.getViewID(mC,"gap_down"));

		gap_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				gap_v--;
				if (gap_v < 0)
					gap_v = 0;
				gap.setText(gap_value[gap_v]);
			}
		});

		gap_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				gap_v++;
				if(gap_v >= gap_value.length)
					gap_v = gap_value.length - 1;
				gap.setText(gap_value[gap_v]);
			}
		});
	}
	
	
	void initSpecifications(){
		specifications = (TextView)findViewById(IDHelper.getViewID(mC,"specifications"));
		specifications.setText(specifications_value[specifications_v]);
		specifications_u = (Button)findViewById(IDHelper.getViewID(mC,"specifications_up"));
		specifications_d = (Button)findViewById(IDHelper.getViewID(mC,"specifications_down"));

		specifications_u.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				specifications_v--;
				if (specifications_v < 0)
					specifications_v = 0;
				specifications.setText(specifications_value[specifications_v]);
			}
		});

		specifications_d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				specifications_v++;
				if(specifications_v >= specifications_value.length)
					specifications_v = specifications_value.length - 1;
				specifications.setText(specifications_value[specifications_v]);
			}
		});
	}
	@Override
	public void initView() {
		initGap();
		initMaterial();
		initSpecifications();
/*
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

*/
	}
	@Override
    void log(String line) {
        android.util.Log.d("erer",line);
    }		

}

