package com.android.nal;


import org.json.JSONArray;
import org.json.JSONObject;

public final class temp_historyConfig{
	public String		deviceId;
	public String		temp;
	public String		time;

	public temp_historyConfig(){
		//temp= new String[8];
	}

	public static temp_historyConfig j2d(String json) {
		temp_historyConfig d = null;
        JSONArray ja = null;//o.getJSONArray("nodeList");
		try {
			d = new temp_historyConfig();
			//JSONObject o = JSONObject.fromObject(json); 
			JSONObject o = new JSONObject(json);
			d.deviceId = o.getString("deviceId");
			d.time = o.getString("time");
/*
			ja = o.getJSONArray("tempList");
			for(int i = 0;i < ja.length();i++) {
				try {
					JSONObject oo = ja.getJSONObject(i);
					String n = oo.getString("temp");
					d.temp[i] = n;
				}catch(Exception e) {
					continue;
				}
			}
*/
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
				ja.put(o);
			}
*/
			jo.put("temp",d.temp);
            return jo.toString();
        } catch (Exception e) {
            return null;
        }
	}

}
