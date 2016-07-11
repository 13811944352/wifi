package com.android.nal;

import org.json.JSONObject;

public final class nodeConfig{
	public String		deviceId;
	public String		nodeId;
	public String		nodeName;
	public String		nodeDesc;

	public int			nodeConfig;	//0 -温控 1-非温控
	public int			nodeType;	// 卧室 书房 客厅 等
	public int			nodeTime;	// 0 -非定时 1，定时
	public int			nodeTemp;   //设定温度

	public int			nodeStart[] = new int[7]; 
	public int			nodeEnd[] = new int[7]; 
	public int			nodeTempS[] = new int[7];
	public int			nodeTempE[] = new int[7];

	public int			homeType;
	public int			homeDire;
	public int			homeFloor;
	public int			homeFitment;

	//public nodeConfig() {
	public nodeConfig(String did,String nid) {
		deviceId = did;
		nodeId = nid;
		nodeName = "";
		nodeDesc = "";
		nodeConfig = 0;
		nodeType = 0;
		nodeTime = 0;
		nodeTemp = 5;
		homeType = 0;
		homeDire = 0;
		homeFloor = 0;
		homeFitment = 0;
	}

	public static nodeConfig j2n(String json) {
		nodeConfig n = null;//new nodeConfig();
		try {
			JSONObject o = new JSONObject(json);
			n = new nodeConfig("","");
			n.deviceId = o.getString("deviceId");
			n.nodeId = o.getString("nodeId");
			n.nodeName = o.getString("nodeName");
			n.nodeDesc = o.getString("nodeDesc");
			n.nodeConfig = o.getInt("nodeConfig");
			n.nodeType = o.getInt("nodeType");
			n.nodeTime = o.getInt("nodeTime");
			n.nodeTemp = o.getInt("nodeTemp");
			n.homeType = o.getInt("homeType");
			n.homeDire= o.getInt("homeDire");
			n.homeFloor = o.getInt("homeFloor");
			n.homeFitment = o.getInt("homeFitment");
			for(int i = 0;i<7;i++) {
				n.nodeStart[i] = o.getInt("nodeStart"+i);
				n.nodeEnd[i] = o.getInt("nodeEnd"+i);
				n.nodeTempS[i] = o.getInt("nodeTempS"+i);
				n.nodeTempE[i] = o.getInt("nodeTempE"+i);
			}
		}catch (Exception e) {
			return null;
		}
		return n;
	}

	public static String n2j(nodeConfig n) {
		try {
			JSONObject jo = new JSONObject();
			jo.put("deviceId",n.deviceId);
			jo.put("nodeId",n.nodeId);
			jo.put("nodeName",n.nodeName);
			jo.put("nodeDesc",n.nodeDesc);
			jo.put("nodeConfig",n.nodeConfig);
			jo.put("nodeType",n.nodeType);
			jo.put("nodeTime",n.nodeTime);
			jo.put("nodeTemp",n.nodeTemp);
			jo.put("homeType",n.homeType );
			jo.put("homeDire",n.homeDire);
			jo.put("homeFloor",n.homeFloor );
			jo.put("homeFitment",n.homeFitment );
			for(int i = 0;i<7;i++) {
				jo.put("nodeStart"+i,n.nodeStart[i]);
				jo.put("nodeEnd"+i,n.nodeEnd[i]);
				jo.put("nodeTempS"+i,n.nodeTempS[i]);
				jo.put("nodeTempE"+i,n.nodeTempE[i]);
			}
			return jo.toString();
		} catch (Exception e) {
			android.util.Log.i("er","n2j:"+e);
			return null;
		}

	}
	static public int hm2int(int h,int m) {
		return h*60+m;
	}

	static public int geth(int m) {
		return m/60;
	}
	static int getm(int m) {
		return m%60;
	}
}
