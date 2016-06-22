package com.android.nal.local;

import java.io.File;
import java.util.*;

import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.android.nal.utils.l;
import com.android.nal.mainApp;
import com.android.nal.deviceConfig;
import com.android.nal.nodeConfig;
import com.android.nal.nodeConfig1;

public class localSqlite {
	public static final String TAG = "task";
	private static final String DB_PATH = "/data/preload/task.db";
	
	private static localSqlite mLs;
	private static SQLiteDatabase db;
	private Context mC;

	private static final String TABLE_DEVICE = "device";
	private static final String D_TYPE   = "type";
	private static final String D_ID   = "d_id";
	private static final String D_NAME = "d_name";
	private static final String D_DESC = "d_desc";
	private static final String D_ADDR = "d_addr";
	private static final String D_PER = "d_per";
	private static final String N1	   = "n1";
	private static final String N2	   = "n2";
	private static final String N3	   = "n3";
	private static final String N4	   = "n4";
	private static final String N5	   = "n5";
	private static final String N6	   = "n6";
	private static final String N7	   = "n7";
	private static final String N8	   = "n8";

	private static final String TABLE_NODE = "node";
	private static final String N_TYPE  = "n_type";
	private static final String N_CONFIG= "n_config";
	private static final String N_ID    = "n_id";
	private static final String N_NAME  = "n_name";
	private static final String N_TEMP  = "n_temp";
	private static final String N_DESC  = "n_desc";
	private static final String N_DAY= "n_day";
	private static final String N_S_S= "n_s_s";
	private static final String N_S_E= "n_s_e";
	private static final String N_J_S= "n_j_s";
	private static final String N_J_E= "n_j_e";
	private static final String N_F_S= "n_f_s";
	private static final String N_F_E= "n_f_e";

/*
    public String       deviceId;
    public String       nodeId;
    public String       nodeName;
    public int          nodeType;
    public int          nodeDire;
    public int          nodeFloor;
    public int          nodeFitMent;
    public int          nodeTemp;
    public int          nodeWaterTemp;
    public String       nodeSpec;
    public String       nodeSpace;
*/
	private static final String TABLE_NODE_1 = "node_1";
	private static final String N1_ID   = "id";
	private static final String N1_NAME = "name";
	private static final String N1_TYPE = "type";
	private static final String N1_DIRE = "dire";
	private static final String N1_FLOOR= "floor";
	private static final String N1_FITM = "fitment";
	private static final String N1_TEMP = "temp";
	private static final String N1_TEMPW= "tempw";
	private static final String N1_SPEC = "spec";
	private static final String N1_SPAC = "space";

	private static final String TABLE_USER = "user";
	private static final String U_UNAME = "uname";
	private static final String U_TOKEN = "token";

	public synchronized void createTable() {
		db.execSQL("CREATE TABLE IF not exists " + TABLE_NODE_1 + " (" + 
				D_ID + " TEXT," + 
				N1_ID + " TEXT," + 
				N1_NAME + " TEXT," + 
				N1_TYPE + " INT," + 
				N1_DIRE + " INT," + 
				N1_FLOOR + " INT," + 
				N1_FITM + " INT," + 
				N1_TEMP + " INT," + 
				N1_TEMPW + " INT," + 
				N1_SPEC +" TEXT," + 
				N1_SPAC +" TEXT," + 
				"primary key ("+ D_ID +","+N1_ID+"));"
				);
		db.execSQL("CREATE TABLE IF not exists " + TABLE_DEVICE + " (" + 
				D_TYPE + " INT," + 
				D_ID + " TEXT primary key,"  + 
				D_NAME +" TEXT," + 
				D_DESC +" TEXT," + 
				D_ADDR +" TEXT," + 
				D_PER +" INT," + 
				N1 +" TEXT," + 
				N2 +" TEXT," + 
				N3 +" TEXT," + 
				N4 +" TEXT," + 
				N5 +" TEXT," + 
				N6 +" TEXT," + 
				N7 +" TEXT," + 
				N8 +" TEXT);");
		db.execSQL("CREATE TABLE IF not exists " + TABLE_NODE + " (" + 
				D_ID + " TEXT," + 
				N_TYPE + " INT," + 
				N_CONFIG + " INT," + 
				N_ID + " TEXT," + 
				N_NAME +" TEXT," + 
				N_TEMP +" INT," + 
				N_DESC +" TEXT," +
				N_DAY+ "0 INT," + 
				N_DAY+ "1 INT," + 
				N_DAY+ "2 INT," + 
				N_DAY+ "3 INT," + 
				N_DAY+ "4 INT," + 
				N_DAY+ "5 INT," + 
				N_DAY+ "6 INT," + 
				N_S_S+ "0 INT," + 
				N_S_S+ "1 INT," + 
				N_S_E+ "0 INT," + 
				N_S_E+ "1 INT," + 
				N_J_S+ "0 INT," + 
				N_J_S+ "1 INT," + 
				N_J_E+ "0 INT," + 
				N_J_E+ "1 INT," + 
				N_F_S+ "0 INT," + 
				N_F_S+ "1 INT," + 
				N_F_E+ "0 INT," + 
				N_F_E+ "1 INT," + 
				"primary key ("+ D_ID +","+N_ID+"));"
				);
	}


	private localSqlite() {
		if(mC == null)
			mC = mainApp.getContext();
		db = SQLiteDatabase.openDatabase("/sdcard/nal.db",null,0x10000000);
		//db = mC.openOrCreateDatabase("nal.db", Context.MODE_PRIVATE, null);  
		createTable();
	}

	public static synchronized localSqlite getInstance() {
		if (mLs == null) {
			mLs = new localSqlite();
		}
		return mLs;
	}

	public boolean setNodeConfig1(nodeConfig1 n) {
/*
		ContentValues cv=new ContentValues();
        cv.put(D_ID, n.deviceId);
        cv.put(N1_ID, n.nodeId);
        cv.put(N1_NAME, n.nodeName);
        cv.put(N1_TYPE, n.nodeType);
        cv.put(N1_DIRE, n.nodeDire);
        cv.put(N1_FLOOR, n.nodeFloor);
        cv.put(N1_FITM, n.nodeFitMent);
        cv.put(N1_TEMP, n.nodeTemp);
        cv.put(N1_TEMPW, n.nodeWaterTemp);
        cv.put(N1_SPEC, n.nodeSpec);
        cv.put(N1_SPAC, n.nodeSpace);
        long row = -1;
        try{
            if(getNodeConfig1(n.deviceId,n.nodeId) == null){
				//log("insert d:"+n.n2j(n));	
                row = db.insert(TABLE_NODE_1, null, cv);
            }else{
                //LogUtil.d(TAG,"insert but t.taskId: "+t.taskId+" ALREADY exist, do update...");
				String where = D_ID+ "=? and "+N_ID+"=?";
                String[] whereValue = { n.deviceId,n.nodeId };
				//log("update n:"+n.n2j(n));	
                db.update(TABLE_NODE_1, cv, where, whereValue);
            }
        }catch(Exception e){
			log("set Device :"+e);
            //e.printStackTrace();
        }
*/
		return true;
	}
    public static nodeConfig1 getNodeConfig1(String did,String nid) {
        nodeConfig1 n = null;//new deviceConfig();
        String selection = D_ID+ "=? and "+N_ID+"=?";
        Cursor cr = db.query(TABLE_NODE_1,null,selection,new String[] {did,nid},null,null,null);
        if(cr == null)
            return null;
/*
        cv.put(D_ID, n.deviceId);
        cv.put(N1_ID, n.nodeId);
        cv.put(N1_NAME, n.nodeName);
        cv.put(N1_TYPE, n.nodeType);
        cv.put(N1_DIRE, n.nodeDire);
        cv.put(N1_FLOOR, n.nodeFloor);
        cv.put(N1_FITM, n.nodeFitMent);
        cv.put(N1_TEMP, n.nodeTemp);
        cv.put(N1_TEMPW, n.nodeWaterTemp);
        cv.put(N1_SPEC, n.nodeSpec);
        cv.put(N1_SPAC, n.nodeSpace);
*/
        if(cr.moveToNext()){
            n = new nodeConfig1();
            int tmpIdx = cr.getColumnIndexOrThrow(D_ID);
            n.deviceId= cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_ID);
            n.nodeId = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_NAME);
            n.nodeName = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_TYPE);
            n.nodeType = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_DIRE);
            n.nodeDire = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_FLOOR);
            n.nodeFloor = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_FITM);
            n.nodeFitMent = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_TEMP);
            n.nodeTemp = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_TEMPW);
            n.nodeWaterTemp = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_SPEC);
            n.nodeSpec = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(N1_SPAC);
            n.nodeSpace = cr.getString(tmpIdx);
        }
        return n;
    }


	public boolean setNodeConfig(nodeConfig n) {
/*
		ContentValues cv=new ContentValues();
        cv.put(D_ID, n.deviceId);
        cv.put(N_TYPE, n.nodeType);
        cv.put(N_CONFIG, n.nodeConfig);
        cv.put(N_ID, n.nodeId);
        cv.put(N_NAME, n.nodeName);
        cv.put(N_TEMP, n.nodeTemp);
        cv.put(N_DESC, n.nodeDesc);
		for(int i = 0;i<7;i++) {
			cv.put(N_DAY+i, n.day[i]);
        }

		cv.put(N_S_S+0,n.s_s[0]);
        cv.put(N_S_S+1,n.s_s[1]);
        cv.put(N_S_E+0,n.s_e[0]);
        cv.put(N_S_E+1,n.s_e[1]);
        cv.put(N_J_S+0,n.j_s[0]);
        cv.put(N_J_S+1,n.j_s[1]);
        cv.put(N_J_E+0,n.j_e[0]);
        cv.put(N_J_E+1,n.j_e[1]);
        cv.put(N_F_S+0,n.f_s[0]);
        cv.put(N_F_S+1,n.f_s[1]);
        cv.put(N_F_E+0,n.f_e[0]);
        cv.put(N_F_E+1,n.f_e[1]);


        long row = -1;
        try{
            if(getNodeConfig(n.deviceId,n.nodeId) == null){
				log("insert d:"+n.n2j(n));	
                row = db.insert(TABLE_NODE, null, cv);
            }else{
				String where = D_ID+ "=? and "+N_ID+"=?";
                String[] whereValue = { n.deviceId,n.nodeId };
				log("update n:"+n.n2j(n));	
                db.update(TABLE_NODE, cv, where, whereValue);
            }
        }catch(Exception e){
			log("set Device :"+e);
        }
*/
		return true;
	}

	private static String getString(Cursor cr,String index) {
            int tmpIdx = cr.getColumnIndexOrThrow(index);
            return cr.getString(tmpIdx);
	}

	private static int getInt(Cursor cr,String index) {
            int tmpIdx = cr.getColumnIndexOrThrow(index);
            return cr.getInt(tmpIdx);
	}
	public static void delNodeConfig(String did,String nid) {
/*
		String where = D_ID+ "=? and "+N_ID+"=?";
        String[] whereValue = { did,nid};
        db.delete(TABLE_NODE, where, whereValue);
*/		
	}

	public static nodeConfig getNodeConfig(String did,String nid) {
/*
		nodeConfig n = null;//new deviceConfig();
        String selection = D_ID+ "=? and "+N_ID+"=?";
        Cursor cr = db.query(TABLE_NODE,null,selection,new String[] {did,nid},null,null,null);
        if(cr == null)
            return null;
        if(cr.moveToNext()){
            n = new nodeConfig();
			n.deviceId = getString(cr,D_ID);
			n.nodeType = getInt(cr,N_TYPE);
			n.nodeConfig = getInt(cr,N_CONFIG);
			n.nodeId = getString(cr,N_ID);
			n.nodeName = getString(cr,N_NAME);
			n.nodeTemp= getInt(cr,N_TEMP);
			n.nodeDesc= getString(cr,N_DESC);
			for(int i = 0;i<7;i++) {
				n.day[i] = getInt(cr,N_DAY+i);
			}
            n.s_s[0] = getInt(cr,N_S_S+0);
            n.s_s[1] = getInt(cr,N_S_S+1);
            n.s_e[0] = getInt(cr,N_S_E+0);
            n.s_e[1] = getInt(cr,N_S_E+1);
            n.j_s[0] = getInt(cr,N_J_S+0);
            n.j_s[1] = getInt(cr,N_J_S+1);
            n.j_e[0] = getInt(cr,N_J_E+0);
            n.j_e[1] = getInt(cr,N_J_E+1);
            n.f_s[0] = getInt(cr,N_F_S+0);
            n.f_s[1] = getInt(cr,N_F_S+1);
            n.f_e[0] = getInt(cr,N_F_E+0);
            n.f_e[1] = getInt(cr,N_F_E+1);
            //int tmpIdx = cr.getColumnIndexOrThrow(D_ID);
            //n.deviceId= cr.getString(tmpIdx);
		}
		return n;
*/
		return null;
	}
	public boolean setDeviceConfig(List<deviceConfig> list) {
		for(deviceConfig d:list) {
			setDeviceConfig(d);
		}
		return true;
	}

	public boolean setDeviceConfig(deviceConfig d) {
		ContentValues cv=new ContentValues();
        cv.put(D_TYPE, d.deviceType);
        cv.put(D_ID, d.deviceId);
        cv.put(D_NAME, d.deviceName);
        cv.put(D_DESC, d.deviceDesc);
        cv.put(D_ADDR, d.deviceAddr);
        cv.put(D_PER, d.devicePer);

		for(int i = 0;i<8;i++) {
			cv.put("n"+(i+1),d.nodeId[i]);
		}

        long row = -1;
        try{
            if(getDeviceConfig(d.deviceId) == null){
				log("insert d:"+d.d2j(d));	
                row = db.insert(TABLE_DEVICE, null, cv);
            }else{
                //LogUtil.d(TAG,"insert but t.taskId: "+t.taskId+" ALREADY exist, do update...");
                String where = D_ID+ " = ?";
                String[] whereValue = { d.deviceId };
				log("update d:"+d.d2j(d));	
                db.update(TABLE_DEVICE, cv, where, whereValue);
            }
        }catch(Exception e){
			log("set Device :"+e);
            //e.printStackTrace();
        }
		return true;
	}

	public List<deviceConfig> getDeviceConfig() {
		deviceConfig d = null;//new deviceConfig();
        //#String selection = D_ID+ "=?";
        Cursor cr = db.query(TABLE_DEVICE,null,null,null,null,null,null);
        if(cr == null)
            return null;
        List<deviceConfig> list = new ArrayList<deviceConfig>();
        while(cr.moveToNext()){
            d = new deviceConfig();
            int tmpIdx = cr.getColumnIndexOrThrow(D_TYPE);
            d.deviceType = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_ID);
            d.deviceId = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_NAME);
            d.deviceName = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_DESC);
            d.deviceDesc= cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_ADDR);
            d.deviceAddr= cr.getString(tmpIdx);
			for(int i = 0;i<8;i++) {
				tmpIdx = cr.getColumnIndexOrThrow("n"+(i+1));
				d.nodeId[i]= cr.getString(tmpIdx);
				//cv.put("N"+(i+1),d.nodeId[i]);
			}
			list.add(d);
		}
		cr.close();
		return list;
	}
	
	public static deviceConfig getDeviceConfig(String id) {
		deviceConfig d = null;//new deviceConfig();
        String selection = D_ID+ "=?";
        Cursor cr = db.query(TABLE_DEVICE,null,selection,new String[] {id},null,null,null);
        if(cr == null)
            return null;
        if(cr.moveToNext()){
            d = new deviceConfig();
            int tmpIdx = cr.getColumnIndexOrThrow(D_TYPE);
            d.deviceType = cr.getInt(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_ID);
            d.deviceId = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_NAME);
            d.deviceName = cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_DESC);
            d.deviceDesc= cr.getString(tmpIdx);
            tmpIdx = cr.getColumnIndexOrThrow(D_ADDR);
            d.deviceAddr= cr.getString(tmpIdx);
		}
		return d;
	}

    static void log(String line) {
        if(line == null)
            l.d("log null");
        else
            l.d(line);
    } 

}

