package com.android.nal;

public final class nodeConfig1{
	public String		deviceId;
	public String		nodeId;
	public String		nodeName;
	public int			nodeType;
	public int			nodeDire;
	public int			nodeFloor;
	public int			nodeFitMent;
	public int			nodeTemp;

	public int			nodeWaterTemp;
	public String		nodeSpec;
	public String		nodeSpace;

	public nodeConfig1() {
		deviceId = null;
		nodeId = null;
		nodeName = "";
	}

/*
	public static nodeConfig1 j2n(String json) {
		nodeConfig1 n = null;//new nodeConfig1();
		try {
			JSONObject o = new JSONObject(json);
			n = new nodeConfig1();
			n.nodeType = o.getInt("nodeType");
			//n.nodeConfig1 = o.getInt("nodeConfig1");
			n.nodeId = o.getString("nodeId");
			n.nodeName = o.getString("nodeName");
			n.nodeTemp = o.getInt("nodeTemp");
			n.nodeDesc = o.getString("nodeDesc");
			n.deviceId = o.getString("deviceId");
		}catch (Exception e) {
			android.util.Log.i("er","j2n:"+e);
			return null;
		}
		return n;
	}

	public static String n2j(nodeConfig1 n) {
		try {
			JSONObject jo = new JSONObject();
			jo.put("nodeType",n.nodeType);
			jo.put("nodeConfig1",n.nodeConfig1);
			jo.put("nodeId",n.nodeId);
			jo.put("nodeName",n.nodeName);
			jo.put("nodeTemp",n.nodeTemp);
			jo.put("nodeDesc",n.nodeDesc);
			jo.put("deviceId",n.deviceId);
			return jo.toString();
		} catch (Exception e) {
			android.util.Log.i("er","n2j:"+e);
			return null;
		}

	}
*/
}
