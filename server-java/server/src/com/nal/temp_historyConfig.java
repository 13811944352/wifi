package com.nal;

import net.sf.json.*;
public final class temp_historyConfig{
	public String		deviceId;
	public String		temp;
	public String		time;

	public temp_historyConfig(){
	}
/*
	public boolean equals(temp_historyConfig d) {
		if(deviceType != d.deviceType)
			return false;
		if(!deviceId.equals(d.deviceId))
			return false;
		if(!deviceName.equals(d.deviceName))
			return false;
		if(!deviceDesc.equals(d.deviceDesc))
			return false;
		if(!deviceAddr.equals(d.deviceAddr))
			return false;
		return true;
	}
*/

	public static temp_historyConfig j2d(String json) {
		temp_historyConfig d = null;
        JSONArray ja = null;//o.getJSONArray("nodeList");
		try {
			d = new temp_historyConfig();
			JSONObject o = JSONObject.fromObject(json); 
			d.deviceId = o.getString("deviceId");
			d.temp = o.getString("temp");
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	public static String d2j(temp_historyConfig d) {
        try {
            JSONObject jo = new JSONObject();
            jo.put("deviceId",d.deviceId);
            jo.put("time",d.time);
/*
			JSONArray ja = new JSONArray();
			for(int i = 0;i<8;i++) {
				JSONObject o = new JSONObject();
				o.put("temp",d.temp[i]);
				ja.add(o);
			}
			jo.put("tempList",ja);
*/  
			jo.put("temp",d.temp);
          return jo.toString();
        } catch (Exception e) {
            return null;
        }
	}

}
