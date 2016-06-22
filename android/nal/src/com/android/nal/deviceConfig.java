package com.android.nal;

import org.json.JSONArray;
import org.json.JSONObject;
import com.android.nal.utils.l;

public final class deviceConfig{
	public int			deviceType;
	public String		deviceId;
	public String		deviceName;
	public String		deviceDesc;
	public String		deviceAddr;
	public int			devicePer;
	public String[]		nodeId;

	public deviceConfig(){
		deviceType = -1;
		deviceId = "";
		deviceName = "";
		deviceDesc = "";
		deviceAddr = "";
		devicePer = 0;
		nodeId = new String[8];
	}


	public static deviceConfig j2d(String json) {
		deviceConfig d = null;
        JSONArray ja = null;//o.getJSONArray("nodeList");
		try {
			d = new deviceConfig();
			JSONObject o = new JSONObject(json);
			d.deviceType = o.getInt("deviceType");
			d.deviceId = o.getString("deviceId");
			d.deviceDesc = o.getString("deviceDesc");
			d.deviceAddr = o.getString("deviceAddr");
			d.deviceName = o.getString("deviceName");
			d.devicePer = o.getInt("devicePer");
			ja = o.getJSONArray("nodeList");
			for(int i = 0;i < ja.length();i++) {
				try {
					JSONObject oo = ja.getJSONObject(i);
					String n = oo.getString("nodeId");
					d.nodeId[i] = n;
				}catch(Exception e) {
					continue;
				}
			}
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	public static String d2j(deviceConfig d) {
        try {
            JSONObject jo = new JSONObject();
            jo.put("deviceType",d.deviceType);
            jo.put("deviceId",d.deviceId);
            jo.put("deviceDesc",d.deviceDesc);
            jo.put("deviceAddr",d.deviceAddr);
            jo.put("deviceName",d.deviceName);
            jo.put("devicePer",d.devicePer);
			JSONArray ja = new JSONArray();
			for(int i = 0;i<8;i++) {
				JSONObject o = new JSONObject();
				o.put("nodeId",d.nodeId[i]);
				ja.put(o);
			}
			jo.put("nodeList",ja);
            return jo.toString();
        } catch (Exception e) {
            return null;
        }
	}

	public static void d(deviceConfig d) {
		l.d(""+d2j(d));
	}

}